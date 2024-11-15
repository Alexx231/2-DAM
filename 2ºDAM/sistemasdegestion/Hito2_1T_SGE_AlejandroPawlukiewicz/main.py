import tkinter as tk
from tkinter import ttk, messagebox
from bdd.conexion import ConexionBD
from bdd.consultas import ConsultasEncuesta
from graficas.visualizargraficas import VisualizadorGraficas
from interfaz.interfazusuario import InterfazSaludAlcohol
from mysql.connector import Error as DatabaseError
import pandas as pd
import sys
from datetime import datetime

class SistemaMonitoreoSalud:
    def __init__(self):
        try:
            self.root = tk.Tk()
            self.root.title("Sistema de Monitoreo de Salud y Alcohol")
            self.root.geometry("1200x800")
            self.root.minsize(1000, 600)
            
            # Configurar tema y estilos
            self.configurar_estilos()
            
            # Protocolo de cierre
            self.root.protocol("WM_DELETE_WINDOW", self.cerrar_aplicacion)
            
            # Inicialización de componentes
            self.inicializar_componentes()
            
        except Exception as e:
            messagebox.showerror("Error de Inicialización", str(e))
            sys.exit(1)

    def configurar_estilos(self):
        style = ttk.Style()
        
        # Colores principales
        self.COLOR_PRIMARY = "#2196F3"
        self.COLOR_SECONDARY = "#64B5F6" 
        self.COLOR_BACKGROUND = "#F5F5F5"
        self.COLOR_SUCCESS = "#4CAF50"
        self.COLOR_WARNING = "#FFC107"
        self.COLOR_ERROR = "#F44336"
        self.COLOR_TEXT = "#212121"
        self.COLOR_ACCENT = "#1976D2"
        
        # Configuración general
        self.root.configure(bg=self.COLOR_BACKGROUND)
        
        # Estilos personalizados
        style.configure(".",
            font=("Segoe UI", 10),
            background=self.COLOR_BACKGROUND,
            foreground=self.COLOR_TEXT
        )
        
        style.configure("Title.TLabel",
            font=("Segoe UI", 24, "bold"),
            foreground=self.COLOR_PRIMARY,
            padding=(0, 10)
        )
        
        style.configure("Custom.TLabelframe",
            background=self.COLOR_BACKGROUND,
            padding=15,
            relief="solid"
        )
        
        style.configure("Primary.TButton",
            font=("Segoe UI", 10, "bold"),
            background=self.COLOR_PRIMARY,
            foreground="black",
            padding=(20, 10)
        )
        
        style.configure("Secondary.TButton",
            font=("Segoe UI", 10),
            background=self.COLOR_SECONDARY,
            foreground="black",  # Agregamos esta línea
            padding=(15, 8)
        )
        
        style.configure("Custom.TEntry",
            padding=8,
            fieldbackground="white"
        )

    def inicializar_componentes(self):
        try:
            print("Inicializando visualizador de gráficas...")
            self.visualizador = VisualizadorGraficas()
            
            print("Inicializando conexión BD...")
            self.conexion = ConexionBD()
            if not hasattr(self.conexion, 'conexion') or not self.conexion.conexion.is_connected():
                raise DatabaseError("No se pudo establecer la conexión con la base de datos")
            
            print("Inicializando consultas...")
            self.consultas = ConsultasEncuesta(self.conexion)
            
            print("Inicializando interfaz...")
            self.interfaz = InterfazSaludAlcohol(self.root)
            
            # Conectar callbacks
            self.interfaz.registrar_callback(self.registrar_paciente)
            self.interfaz.visualizar_callback(self.mostrar_graficas)
            self.interfaz.estadisticas_callback(self.mostrar_estadisticas)
            self.interfaz.exportar_callback(self.exportar_a_excel)
            
            self.cargar_datos_iniciales()
            
        except Exception as e:
            raise Exception(f"Error al inicializar componentes: {str(e)}")

    def cargar_datos_iniciales(self):
        """Carga los datos iniciales y actualiza la interfaz"""
        try:
            print("Cargando datos iniciales...")
            stats = self.consultas.obtener_estadisticas_consumo()
            alto_consumo = self.consultas.filtrar_alto_consumo()
            
            if stats is not None and not stats.empty:
                self.interfaz.actualizar_estadisticas(stats, alto_consumo)
                self.mostrar_graficas()
            else:
                print("No hay datos iniciales disponibles")
                
        except Exception as e:
            print(f"Error al cargar datos iniciales: {str(e)}")

    def registrar_paciente(self, datos_paciente):
        try:
            self.consultas.insertar_encuesta(datos_paciente)
            messagebox.showinfo("Éxito", "Paciente registrado correctamente")
            self.mostrar_graficas()  # Actualizar gráficas después del registro
            return True
        except Exception as e:
            messagebox.showerror("Error", f"Error al registrar paciente: {str(e)}")
            return False

    def mostrar_graficas(self):
        try:
            tipo_grafica = self.interfaz.tipo_grafica.get()
            datos = self._obtener_datos_grafica(tipo_grafica)
            
            if datos is not None and not datos.empty:
                fig = self._crear_grafica(tipo_grafica, datos)
                if fig:
                    self.interfaz.actualizar_grafico(fig)
                else:
                    messagebox.showinfo("Info", "No se pudo crear la gráfica")
            else:
                messagebox.showinfo("Info", "No hay datos disponibles para graficar")
            
        except Exception as e:
            print(f"Error en mostrar_graficas: {str(e)}")
            messagebox.showerror("Error", f"Error al mostrar gráfica: {str(e)}")

    def mostrar_estadisticas(self):
        try:
            # Obtener datos y verificar que no estén vacíos
            stats = self.consultas.obtener_estadisticas_consumo()
            if stats is None or stats.empty:
                raise ValueError("No hay datos disponibles")
    
            # Verificar que existan todas las columnas necesarias
            columnas_requeridas = ['BebidasSemana', 'CervezasSemana', 'BebidasFinSemana',
                                'BebidasDestiladasSemana', 'VinosSemana', 'Sexo']
            
            for columna in columnas_requeridas:
                if columna not in stats.columns:
                    raise KeyError(f"Columna {columna} no encontrada en los datos")
    
            # Convertir columnas numéricas si es necesario
            columnas_numericas = ['BebidasSemana', 'CervezasSemana', 'BebidasFinSemana',
                                'BebidasDestiladasSemana', 'VinosSemana']
            
            for col in columnas_numericas:
                stats[col] = pd.to_numeric(stats[col], errors='coerce')
    
            # Crear columnas temporales con manejo de errores
            try:
                stats['promedio_semanal'] = stats['BebidasSemana'].fillna(0)
                stats['promedio_cerveza'] = stats['CervezasSemana'].fillna(0)
                stats['promedio_finde'] = stats['BebidasFinSemana'].fillna(0)
                stats['promedio_destiladas'] = stats['BebidasDestiladasSemana'].fillna(0)
                stats['promedio_vinos'] = stats['VinosSemana'].fillna(0)
            except Exception as e:
                print(f"Error al crear columnas temporales: {str(e)}")
                raise
    
            # Obtener resto de datos necesarios
            alto_consumo = self.consultas.filtrar_alto_consumo(limite=15)
            problemas_salud = self.consultas.filtrar_problemas_salud()
            correlacion = self.consultas.obtener_correlacion_salud_consumo()
    
            # Calcular estadísticas
            estadisticas_detalladas = {
                'general': stats,
                'alto_riesgo': alto_consumo,
                'problemas_salud': {
                    'digestivos': problemas_salud['problemas_digestivos'].sum(),
                    'tension': problemas_salud['tension_alta'].sum(),
                    'dolor_cabeza': problemas_salud['dolor_cabeza_frecuente'].sum()
                },
                'correlaciones': correlacion,
                'por_genero': stats.groupby('Sexo').agg({
                    'promedio_semanal': 'mean',
                    'promedio_cerveza': 'mean',     
                    'promedio_finde': 'mean',   
                    'promedio_destiladas': 'mean',
                    'promedio_vinos': 'mean'
                }).round(2)
            }
    
            # Actualizar interfaz y gráficas
            self.interfaz.actualizar_estadisticas(
                stats=stats,
                alto_consumo=alto_consumo
            )
            self.mostrar_graficas()
    
        except Exception as e:
            messagebox.showerror("Error", 
                f"Error al mostrar estadísticas: {str(e)}\n"
                "Por favor, verifique la conexión a la base de datos y el formato de los datos.")
            print(f"Error detallado: {str(e)}")
            return None
            
    def exportar_a_excel(self, tipo_exportacion):
        try:
            # Obtener datos actuales
            datos = self.consultas.obtener_estadisticas_consumo()
            if datos is None or datos.empty:
                messagebox.showwarning("Advertencia", "No hay datos para exportar")
                return
    
            # Pedir al usuario la ubicación para guardar el archivo
            from tkinter import filedialog
            filename = filedialog.asksaveasfilename(
                defaultextension=".xlsx",
                filetypes=[("Excel files", "*.xlsx"), ("All files", "*.*")],
                title="Guardar como Excel"
            )
            
            if filename:
                # Exportar utilizando el método de consultas
                if self.consultas.exportar_a_excel(datos, filename):
                    messagebox.showinfo("Éxito", "Datos exportados correctamente")
                else:
                    messagebox.showerror("Error", "No se pudo exportar el archivo")
        
        except Exception as e:
            messagebox.showerror("Error", f"Error al exportar: {str(e)}")
            print(f"Error detallado en exportación: {str(e)}")

    def _obtener_datos_grafica(self, tipo_grafica):
        if tipo_grafica == 'Consumo por Edad':
            return self.consultas.obtener_estadisticas_consumo()
        elif tipo_grafica == 'Problemas de Salud':
            return self.consultas.filtrar_problemas_salud()
        elif tipo_grafica == 'Tendencia Temporal':
            return self.consultas.ordenar_por_campo('idEncuesta')
        return None

    def _crear_grafica(self, tipo_grafica, datos):
        if tipo_grafica == 'Consumo por Edad':
            return self.visualizador.crear_grafica_consumo_edad(datos)
        elif tipo_grafica == 'Problemas de Salud':
            return self.visualizador.crear_grafica_problemas_salud(datos)
        elif tipo_grafica == 'Tendencia Temporal':
            return self.visualizador.crear_grafica_tendencia_temporal(datos)
        return None

    def cerrar_aplicacion(self):
        if messagebox.askokcancel("Salir", "¿Desea salir de la aplicación?"):
            if hasattr(self, 'conexion'):
                self.conexion.cerrar_conexion()
            self.root.destroy()

def main():
    try:
        print("=== Iniciando Sistema de Monitoreo de Salud ===")
        
        # Verificar conexión BD primero
        print("Verificando conexión a base de datos...")
        conexion = ConexionBD()
        if not conexion.conexion.is_connected():
            raise DatabaseError("No se pudo establecer la conexión con la base de datos")
        conexion.cerrar_conexion()  # Cerrar conexión de prueba
        print("Conexión a base de datos establecida correctamente")
        
        # Iniciar aplicación
        print("Iniciando aplicación...")
        app = SistemaMonitoreoSalud()
        print("Sistema inicializado correctamente")
        
        # Ejecutar el bucle principal
        app.root.mainloop()
        
    except DatabaseError as e:
        messagebox.showerror("Error de Base de Datos", str(e))
        print(f"Error de base de datos: {str(e)}")
        sys.exit(1)
    except Exception as e:
        messagebox.showerror("Error Fatal", str(e))
        print(f"Error fatal: {str(e)}")
        sys.exit(1)
    finally:
        print("=== Finalizando Sistema de Monitoreo de Salud ===")

if __name__ == "__main__":
    main()