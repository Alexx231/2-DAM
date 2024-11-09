from bdd.conexion import ConexionBD
from bdd.consultas import ConsultasEncuesta
from graficas.visualizargraficas import VisualizadorGraficas
from interfaz.interfazusuario import InterfazSaludAlcohol
import pandas as pd
import sys

class SistemaMonitoreoSalud:
    def __init__(self):
        try:
            # Inicializar conexión a base de datos
            self.conexion = ConexionBD()
            
            # Inicializar módulo de consultas
            self.consultas = ConsultasEncuesta(self.conexion)
            
            # Inicializar visualizador de gráficas
            self.visualizador = VisualizadorGraficas()
            
            # Inicializar interfaz de usuario
            self.interfaz = InterfazSaludAlcohol()
            
            # Conectar eventos
            self.conectar_eventos()
            
        except Exception as e:
            print(f"Error al inicializar el sistema: {e}")
            sys.exit(1)
    
    def conectar_eventos(self):
        """Conecta los eventos de la interfaz con las funciones correspondientes"""
        self.interfaz.registrar_callback(self.registrar_datos)
        self.interfaz.visualizar_callback(self.mostrar_graficas)
        self.interfaz.estadisticas_callback(self.mostrar_estadisticas)
    
    def registrar_datos(self, datos):
        """Registra nuevos datos en la base de datos"""
        try:
            self.conexion.crear_encuesta(datos)
            return True
        except Exception as e:
            print(f"Error al registrar datos: {e}")
            return False
    
    def mostrar_graficas(self):
        """Muestra las gráficas basadas en los datos actuales"""
        try:
            # Obtener datos
            datos_consumo = self.consultas.obtener_estadisticas_consumo()
            datos_problemas = self.consultas.filtrar_problemas_salud()
            
            # Crear dashboard
            dashboard_datos = {
                'edad': datos_consumo,
                'salud': datos_problemas,
                'tiempo': self.consultas.ordenar_por_campo('fecha')
            }
            
            self.visualizador.crear_dashboard(dashboard_datos)
            self.visualizador.mostrar_grafico()
            
        except Exception as e:
            print(f"Error al mostrar gráficas: {e}")
    
    def mostrar_estadisticas(self):
        """Muestra las estadísticas generales"""
        try:
            stats = self.consultas.obtener_estadisticas_consumo()
            alto_consumo = self.consultas.filtrar_alto_consumo()
            self.interfaz.actualizar_estadisticas(stats, alto_consumo)
        except Exception as e:
            print(f"Error al mostrar estadísticas: {e}")
    
    def ejecutar(self):
        """Inicia la ejecución del sistema"""
        try:
            self.interfaz.ejecutar()
        finally:
            self.conexion.cerrar_conexion()
    
    def exportar_datos(self, formato="excel"):
        """Exporta los datos en el formato especificado"""
        try:
            datos = self.consultas.ordenar_por_campo('fecha')
            if formato == "excel":
                self.consultas.exportar_a_excel(datos, "reporte_salud.xlsx")
        except Exception as e:
            print(f"Error al exportar datos: {e}")

def main():
    try:
        sistema = SistemaMonitoreoSalud()
        sistema.ejecutar()
    except Exception as e:
        print(f"Error en la ejecución del sistema: {e}")
        sys.exit(1)

if __name__ == "__main__":
    main()