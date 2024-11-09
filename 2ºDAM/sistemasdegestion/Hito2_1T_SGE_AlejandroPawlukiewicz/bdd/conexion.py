import mysql.connector
from mysql.connector import Error
from tkinter import messagebox

class ConexionBD:
    def __init__(self):
        try:
            self.conexion = mysql.connector.connect(
                host='localhost',
                user='root',
                password='tu_password',
                database='clinica_db'
            )
            if self.conexion.is_connected():
                print("Conexión exitosa a MySQL")
        except Error as e:
            print(f"Error de conexión: {e}")

    def crear_encuesta(self, datos):
        try:
            cursor = self.conexion.cursor()
            sql = """INSERT INTO encuestas 
                    (fecha, edad, consumo_alcohol, presion_arterial, problemas_salud) 
                    VALUES (%s, %s, %s, %s, %s)"""
            cursor.execute(sql, datos)
            self.conexion.commit()
            return True
        except Error as e:
            print(f"Error al crear encuesta: {e}")
            return False
        finally:
            if cursor:
                cursor.close()

    def leer_encuestas(self):
        try:
            cursor = self.conexion.cursor()
            cursor.execute("SELECT * FROM encuestas")
            return cursor.fetchall()
        except Error as e:
            print(f"Error al leer encuestas: {e}")
            return []
        finally:
            if cursor:
                cursor.close()

    def actualizar_encuesta(self, id_encuesta, datos):
        try:
            cursor = self.conexion.cursor()
            sql = """UPDATE encuestas 
                    SET fecha=%s, edad=%s, consumo_alcohol=%s, 
                        presion_arterial=%s, problemas_salud=%s 
                    WHERE id=%s"""
            cursor.execute(sql, datos + (id_encuesta,))
            self.conexion.commit()
            return True
        except Error as e:
            print(f"Error al actualizar encuesta: {e}")
            return False
        finally:
            if cursor:
                cursor.close()

    def eliminar_encuesta(self, id_encuesta):
        try:
            if messagebox.askyesno("Confirmar eliminación", 
                                 "¿Está seguro de eliminar este registro?"):
                cursor = self.conexion.cursor()
                sql = "DELETE FROM encuestas WHERE id=%s"
                cursor.execute(sql, (id_encuesta,))
                self.conexion.commit()
                return True
            return False
        except Error as e:
            print(f"Error al eliminar encuesta: {e}")
            return False
        finally:
            if cursor:
                cursor.close()

    def cerrar_conexion(self):
        if hasattr(self, 'conexion') and self.conexion.is_connected():
            self.conexion.close()
            print("Conexión cerrada")