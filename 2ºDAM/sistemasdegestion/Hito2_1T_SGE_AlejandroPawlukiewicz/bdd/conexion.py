# bdd/conexion.py
import mysql.connector
from mysql.connector import Error
from tkinter import messagebox

class ConexionBD:
    def __init__(self):
        try:
            print("Intentando conectar a MySQL...")
            self.conexion = mysql.connector.connect(
                host='localhost',
                user='root',           # Modifica según tu usuario
                password='',           # Modifica según tu contraseña
                database='encuestas',
                port=3306
            )
            if self.conexion.is_connected():
                db_info = self.conexion.get_server_info()
                print(f"Conexión exitosa a MySQL. Versión del servidor: {db_info}")
                cursor = self.conexion.cursor()
                cursor.execute("SELECT DATABASE()")
                db_name = cursor.fetchone()[0]
                print(f"Base de datos actual: {db_name}")
                cursor.close()
            else:
                raise Error("No se pudo establecer la conexión")
        except Error as e:
            print(f"Error de conexión: {str(e)}")
            messagebox.showerror("Error de Conexión", 
                               "Verifica tus credenciales de MySQL y que el servidor esté activo")
            raise

    def verificar_base_datos(self):
        try:
            cursor = self.conexion.cursor()
            # Crear base de datos si no existe
            cursor.execute("CREATE DATABASE IF NOT EXISTS encuestas")
            cursor.execute("USE encuestas")
            
            # Crear tabla si no existe
            cursor.execute("""
                CREATE TABLE IF NOT EXISTS encuestas (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    fecha DATE,
                    consumo_alcohol FLOAT,
                    presion_arterial VARCHAR(20),
                    edad INT,
                    problemas_salud TEXT
                )
            """)
            self.conexion.commit()
        except Error as e:
            print(f"Error al verificar base de datos: {str(e)}")
            raise
        finally:
            if cursor:
                cursor.close()

    def ejecutar_query(self, query, params=None):
        cursor = None
        try:
            cursor = self.conexion.cursor()
            if params:
                cursor.execute(query, params)
            else:
                cursor.execute(query)
            self.conexion.commit()
            return cursor
        except Error as e:
            self.conexion.rollback()
            raise Exception(f"Error en la consulta: {str(e)}")
        finally:
            if cursor:
                cursor.close()

    def cerrar_conexion(self):
        try:
            if hasattr(self, 'conexion') and self.conexion.is_connected():
                self.conexion.close()
                print("Conexión cerrada correctamente")
        except Error as e:
            print(f"Error al cerrar la conexión: {e}")