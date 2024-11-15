# bdd/consultas.py
import pandas as pd
from mysql.connector import Error

class ConsultasEncuesta:
    def __init__(self, conexion):
        self.conexion = conexion

    def ordenar_por_campo(self, campo):
        """Obtiene todos los registros ordenados por el campo especificado"""
        # Mapeo de nombres de columnas
        campo_map = {
            'fecha': 'idEncuesta',  # Usamos idEncuesta en lugar de fecha
            'alcohol': 'BebidasSemana',
            'presion': None,  # No existe esta columna
            'edad': 'edad',
            'problemas': 'ProblemasDigestivos'
        }
        
        campo_bd = campo_map.get(campo, campo)
        if not campo_bd:
            raise ValueError(f"Campo {campo} no existe en la base de datos")
            
        query = f"""
        SELECT * FROM encuesta 
        ORDER BY {campo_bd}
        """
        try:
            return pd.read_sql(query, self.conexion.conexion)
        except Error as e:
            raise Exception(f"Error al ordenar datos: {str(e)}")

    def insertar_encuesta(self, datos):
        query = """
        INSERT INTO encuesta (
            idEncuesta, edad, Sexo, 
            BebidasSemana, CervezasSemana, BebidasFinSemana, 
            BebidasDestiladasSemana, VinosSemana,
            PerdidasControl, DiversionDependenciaAlcohol,
            ProblemasDigestivos, TensionAlta, DolorCabeza
        ) VALUES (
            %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s
        )
        """
        try:
            cursor = self.conexion.conexion.cursor()
            
            # Obtener siguiente ID
            cursor.execute("SELECT MAX(idEncuesta) FROM encuesta")
            max_id = cursor.fetchone()[0] or 0
            nuevo_id = max_id + 1
    
            valores = (
                nuevo_id,
                int(datos['edad']),
                datos['sexo'],
                float(datos['bebidas_semana']),
                float(datos['cervezas']),
                float(datos['finde']),
                float(datos['destiladas']),
                float(datos['vinos']),
                datos['perdidas_control'],
                datos['diversion_alcohol'],
                datos['problemas_digestivos'],
                datos['tension_alta'],
                datos['dolor_cabeza']
            )
            
            cursor.execute(query, valores)
            self.conexion.conexion.commit()
            return True
            
        except Error as e:
            self.conexion.conexion.rollback()
            raise Exception(f"Error al insertar datos: {str(e)}")
        finally:
            cursor.close()

    def obtener_tendencia_temporal(self):
        """Obtiene la tendencia del consumo por edad"""
        query = """
        SELECT 
            edad,
            AVG(BebidasSemana) as consumo_semanal,
            AVG(BebidasFinSemana) as consumo_finde
        FROM encuesta
        GROUP BY edad
        ORDER BY edad
        """
        return pd.read_sql(query, self.conexion.conexion)

    def obtener_estadisticas_consumo(self):
        """Obtiene estadísticas detalladas de consumo"""
        query = """
        SELECT 
            idEncuesta,
            edad,
            Sexo,
            BebidasSemana,
            CervezasSemana,
            BebidasFinSemana,
            BebidasDestiladasSemana,
            VinosSemana,
            AVG(BebidasSemana) as promedio_semanal,
            AVG(CervezasSemana) as promedio_cerveza,
            AVG(BebidasFinSemana) as promedio_finde,
            AVG(BebidasDestiladasSemana) as promedio_destiladas,
            AVG(VinosSemana) as promedio_vinos
        FROM encuesta
        GROUP BY idEncuesta, edad, Sexo, BebidasSemana, CervezasSemana, 
                BebidasFinSemana, BebidasDestiladasSemana, VinosSemana
        """
        try:
            return pd.read_sql(query, self.conexion.conexion)
        except Exception as e:
            print(f"Error en obtener_estadisticas_consumo: {str(e)}")
            return pd.DataFrame()  # Retorna DataFrame vacío en caso de error

    def filtrar_alto_consumo(self, limite=20):
        """Filtra registros con alto consumo de alcohol"""
        query = """
        SELECT 
            idEncuesta,
            edad,
            Sexo,
            BebidasSemana,
            CervezasSemana,
            BebidasFinSemana,
            BebidasDestiladasSemana,
            VinosSemana
        FROM encuesta 
        WHERE BebidasSemana > %s 
           OR BebidasFinSemana > %s
           OR CervezasSemana > %s
           OR BebidasDestiladasSemana > %s
           OR VinosSemana > %s
        ORDER BY BebidasSemana DESC
        LIMIT 100
        """
        params = (limite,) * 5
        return pd.read_sql(query, self.conexion.conexion, params=params)

    def filtrar_problemas_salud(self):
        """Analiza problemas de salud relacionados con el consumo"""
        query = """
        SELECT 
            Sexo,
            SUM(CASE WHEN ProblemasDigestivos = 'Sí' THEN 1 ELSE 0 END) as problemas_digestivos,
            SUM(CASE WHEN TensionAlta = 'Sí' THEN 1 ELSE 0 END) as tension_alta,
            SUM(CASE WHEN DolorCabeza IN ('A menudo', 'Muy a menudo') THEN 1 ELSE 0 END) as dolor_cabeza_frecuente,
            AVG(BebidasSemana) as promedio_consumo_semanal,
            COUNT(*) as total_casos
        FROM encuesta
        GROUP BY Sexo
        """
        return pd.read_sql(query, self.conexion.conexion)

    def obtener_correlacion_salud_consumo(self):
        """Analiza la correlación entre consumo y problemas de salud"""
        query = """
        SELECT 
            CASE 
                WHEN BebidasSemana = 0 THEN 'No consume'
                WHEN BebidasSemana <= 5 THEN 'Consumo bajo'
                WHEN BebidasSemana <= 15 THEN 'Consumo moderado'
                ELSE 'Consumo alto'
            END as nivel_consumo,
            COUNT(*) as total_personas,
            SUM(CASE WHEN ProblemasDigestivos = 'Sí' THEN 1 ELSE 0 END) as casos_digestivos,
            SUM(CASE WHEN TensionAlta = 'Sí' THEN 1 ELSE 0 END) as casos_tension,
            SUM(CASE WHEN DolorCabeza IN ('A menudo', 'Muy a menudo') THEN 1 ELSE 0 END) as casos_dolor_cabeza
        FROM encuesta
        GROUP BY 
            CASE 
                WHEN BebidasSemana = 0 THEN 'No consume'
                WHEN BebidasSemana <= 5 THEN 'Consumo bajo'
                WHEN BebidasSemana <= 15 THEN 'Consumo moderado'
                ELSE 'Consumo alto'
            END
        """
        return pd.read_sql(query, self.conexion.conexion)

    def exportar_a_excel(self, df, nombre_archivo):
        """Exporta un DataFrame a Excel"""
        try:
            df.to_excel(nombre_archivo, index=False)
            return True
        except Exception as e:
            print(f"Error al exportar: {e}")
            return False