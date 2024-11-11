# main.py
import tkinter as tk
from bdd.conexion import ConexionBD
from bdd.consultas import ConsultasEncuesta
from graficas.visualizargraficas import VisualizadorGraficas
from interfaz.interfazusuario import InterfazSaludAlcohol
from tkinter import messagebox
from mysql.connector import Error as DatabaseError
import pandas as pd
import sys

class SistemaMonitoreoSalud:
    def __init__(self):
        """Inicializa el sistema de monitoreo de salud"""
        try:
            self.root = tk.Tk()
            self.root.title("Sistema de Monitoreo de Salud y Alcohol")
            self.root.geometry("1200x800")
            
            # Inicializar visualizador primero
            print("Inicializando visualizador de gráficas...")
            self.visualizador = VisualizadorGraficas()
            
            # Luego el resto de componentes
            print("Inicializando conexión BD...")
            self.conexion = ConexionBD()
            if not hasattr(self.conexion, 'conexion') or not self.conexion.conexion.is_connected():
                raise DatabaseError("No se pudo establecer la conexión con la base de datos")
            
            print("Inicializando consultas...")
            self.consultas = ConsultasEncuesta(self.conexion)
            
            print("Inicializando interfaz...")
            self.interfaz = InterfazSaludAlcohol(self.root)
            
            self.conectar_eventos()
            self.cargar_datos_iniciales()
            
        except ImportError as e:
            messagebox.showerror("Error", f"Error al importar módulos necesarios: {str(e)}")
            sys.exit(1)
        except Exception as e:
            messagebox.showerror("Error de Inicialización", str(e))
            sys.exit(1)

    def conectar_eventos(self):
        """Conecta los eventos de la interfaz con las funciones correspondientes"""
        try:
            self.interfaz.registrar_callback(self.registrar_datos)
            self.interfaz.visualizar_callback(self.mostrar_graficas)
            self.interfaz.estadisticas_callback(self.mostrar_estadisticas)
            self.interfaz.exportar_callback(self.exportar_datos)
        except AttributeError as e:
            messagebox.showerror("Error", "Error al conectar eventos: Interfaz no inicializada correctamente")
            raise
        except Exception as e:
            messagebox.showerror("Error", f"Error al conectar eventos: {str(e)}")
            raise

    def cargar_datos_iniciales(self):
        """Carga los datos iniciales en la interfaz"""
        try:
            print("Cargando datos iniciales...")
            stats = self.consultas.obtener_estadisticas_consumo()
            alto_consumo = self.consultas.filtrar_alto_consumo()
            
            if stats is not None and not stats.empty:
                datos_formateados = {
                    'promedio_semanal': stats['promedio_semanal'],
                    'promedio_cerveza': stats['promedio_cerveza'],
                    'promedio_finde': stats['promedio_finde'],
                    'promedio_destiladas': stats['promedio_destiladas'],
                    'promedio_vinos': stats['promedio_vinos'],
                    'total_registros': stats['total_registros']
                }
                
                alto_consumo_formateado = {
                    'BebidasSemana': alto_consumo['BebidasSemana'],
                    'BebidasFinSemana': alto_consumo['BebidasFinSemana'],
                    'edad': alto_consumo['edad'],
                    'Sexo': alto_consumo['Sexo']
                }
                
                print("Actualizando estadísticas en interfaz...")
                self.interfaz.actualizar_estadisticas(stats, alto_consumo)
                self.mostrar_graficas()
            else:
                print("No hay datos disponibles")
                messagebox.showinfo("Información", "No hay datos disponibles para mostrar")
                
        except Exception as e:
            print(f"Error al cargar datos: {str(e)}")
            messagebox.showerror("Error", f"Error al cargar datos iniciales: {str(e)}")

    def registrar_datos(self, datos):
        """Registra nuevos datos en la base de datos"""
        try:
            if not self.validar_datos_entrada(datos):
                return False
                
            self.consultas.insertar_encuesta(datos)
            self.interfaz.mostrar_mensaje("Éxito", "Datos registrados correctamente")
            self.cargar_datos_iniciales()
            return True
            
        except DatabaseError as e:
            messagebox.showerror("Error de Base de Datos", str(e))
            return False
        except ValueError as e:
            messagebox.showerror("Error de Validación", str(e))
            return False
        except Exception as e:
            messagebox.showerror("Error", f"Error al registrar datos: {str(e)}")
            return False

    def validar_datos_entrada(self, datos):
        """Valida los datos de entrada antes de registrarlos"""
        try:
            campos_requeridos = ['edad', 'alcohol', 'sexo']
            if not all(campo in datos and datos[campo] for campo in campos_requeridos):
                raise ValueError("Faltan campos requeridos: edad, alcohol, sexo")
            
            # Validar valores numéricos
            try:
                float(datos['alcohol'])
                int(datos['edad'])
            except ValueError:
                raise ValueError("Valores numéricos inválidos")
            
            return True
            
        except ValueError as e:
            messagebox.showerror("Error de Validación", str(e))
            return False

    def mostrar_graficas(self):
        """Muestra las gráficas basadas en los datos actuales"""
        try:
            print("Obteniendo datos para gráficas...")
            datos_consumo = self.consultas.obtener_estadisticas_consumo()
            datos_problemas = self.consultas.filtrar_problemas_salud()
            datos_tiempo = self.consultas.ordenar_por_campo('idEncuesta')
            
            if all(dato is not None and not dato.empty for dato in [datos_consumo, datos_problemas, datos_tiempo]):
                dashboard_datos = {
                    'edad': datos_consumo,
                    'salud': datos_problemas,
                    'tiempo': datos_tiempo
                }
                print("Creando dashboard...")
                fig = self.visualizador.crear_dashboard(dashboard_datos)
                if fig is not None:
                    print("Actualizando gráfico en interfaz...")
                    self.interfaz.actualizar_grafico(fig)
                else:
                    raise ValueError("Error al crear el dashboard")
            else:
                raise ValueError("No hay datos suficientes para mostrar gráficas")
                
        except Exception as e:
            print(f"Error al mostrar gráficas: {str(e)}")
            messagebox.showerror("Error", f"Error al mostrar gráficas: {str(e)}")

    def mostrar_estadisticas(self):
        """Muestra las estadísticas generales"""
        try:
            stats = self.consultas.obtener_estadisticas_consumo()
            alto_consumo = self.consultas.filtrar_alto_consumo()
            
            if stats is None or stats.empty:
                messagebox.showinfo("Información", "No hay estadísticas disponibles")
                return
                
            self.interfaz.actualizar_estadisticas(stats, alto_consumo)
            
        except DatabaseError as e:
            messagebox.showerror("Error de Base de Datos", str(e))
        except Exception as e:
            messagebox.showerror("Error", f"Error al mostrar estadísticas: {str(e)}")

    def exportar_datos(self, formato="excel"):
        """Exporta los datos en el formato especificado"""
        try:
            # Usar idEncuesta en lugar de fecha
            datos = self.consultas.ordenar_por_campo('idEncuesta')
            if datos is None or datos.empty:
                raise ValueError("No hay datos para exportar")
                
            if formato == "excel":
                nombre_archivo = "reporte_salud.xlsx"
                try:
                    datos.to_excel(nombre_archivo, index=False)
                    self.interfaz.mostrar_mensaje("Éxito", 
                        f"Datos exportados correctamente a {nombre_archivo}")
                except Exception as e:
                    raise Exception(f"Error al exportar a Excel: {str(e)}")
        except ValueError as e:
            messagebox.showinfo("Información", str(e))
        except Exception as e:
            messagebox.showerror("Error", f"Error al exportar datos: {str(e)}")

    def ejecutar(self):
        """Inicia la ejecución del sistema"""
        try:
            self.root.mainloop()
        except Exception as e:
            messagebox.showerror("Error", f"Error en la ejecución: {str(e)}")
        finally:
            if hasattr(self, 'conexion'):
                self.conexion.cerrar_conexion()

def main():
    try:
        print("=== Iniciando Sistema de Monitoreo ===")
        # Verificar conexión BD primero
        conexion = ConexionBD()
        print("Conexión BD verificada")
        
        app = SistemaMonitoreoSalud()
        print("Sistema inicializado correctamente")
        app.ejecutar()
    except Exception as e:
        messagebox.showerror("Error Fatal", str(e))
        print(f"Error fatal: {str(e)}")
        sys.exit(1)

if __name__ == "__main__":
    main()