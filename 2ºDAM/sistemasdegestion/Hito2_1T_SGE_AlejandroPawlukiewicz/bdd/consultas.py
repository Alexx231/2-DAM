import mysql.connector
import pandas as pd
from mysql.connector import Error

class ConsultasEncuesta:
    def __init__(self, conexion):
        self.conexion = conexion

    def ordenar_por_campo(self, campo, ascendente=True):
        """Ordena resultados por cualquier campo"""
        try:
            orden = "ASC" if ascendente else "DESC"
            query = f"SELECT * FROM ENCUESTA ORDER BY {campo} {orden}"
            return pd.read_sql(query, self.conexion)
        except Error as e:
            print(f"Error al ordenar: {e}")
            return None

    def filtrar_alto_consumo(self, limite_semanal=20):
        """Filtra encuestados con alto consumo de alcohol"""
        query = """
        SELECT * FROM ENCUESTA 
        WHERE BebidasSemana > %s 
        ORDER BY BebidasSemana DESC
        """
        return pd.read_sql(query, self.conexion, params=(limite_semanal,))

    def filtrar_perdidas_control(self, minimo_perdidas=3):
        """Filtra personas con pérdidas de control frecuentes"""
        query = """
        SELECT * FROM ENCUESTA 
        WHERE PerdidasControl > %s 
        ORDER BY PerdidasControl DESC
        """
        return pd.read_sql(query, self.conexion, params=(minimo_perdidas,))

    def filtrar_problemas_salud(self, problema):
        """Filtra por problema de salud específico"""
        query = """
        SELECT * FROM ENCUESTA 
        WHERE ProblemasDigestivos = 'Sí' 
        OR TensionAlta = 'Sí' 
        OR DolorCabeza = 'Muy a menudo'
        """
        return pd.read_sql(query, self.conexion)

    def obtener_estadisticas_consumo(self):
        """Obtiene estadísticas generales de consumo"""
        query = """
        SELECT 
            AVG(BebidasSemana) as promedio_bebidas,
            MAX(BebidasSemana) as maximo_bebidas,
            AVG(PerdidasControl) as promedio_perdidas,
            COUNT(CASE WHEN ProblemasDigestivos = 'Sí' THEN 1 END) as total_problemas_digestivos,
            COUNT(CASE WHEN TensionAlta = 'Sí' THEN 1 END) as total_tension_alta
        FROM ENCUESTA
        """
        return pd.read_sql(query, self.conexion)

    def exportar_a_excel(self, df, nombre_archivo):
        """Exporta resultados a Excel"""
        try:
            df.to_excel(nombre_archivo, index=False)
            print(f"Datos exportados exitosamente a {nombre_archivo}")
            return True
        except Exception as e:
            print(f"Error al exportar: {e}")
            return False
