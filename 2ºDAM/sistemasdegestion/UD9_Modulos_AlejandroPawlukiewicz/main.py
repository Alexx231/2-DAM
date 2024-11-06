import sqlite3
from datetime import datetime
import matplotlib
matplotlib.use('Agg')  # Configurar matplotlib para no usar display
import matplotlib.pyplot as plt

class RestauranteConsola:
    def __init__(self):
        self.crear_bd()
        
    def crear_bd(self):
        conn = sqlite3.connect('restaurante.db')
        c = conn.cursor()
        c.execute('''CREATE TABLE IF NOT EXISTS menu
                    (id INTEGER PRIMARY KEY,
                     nombre TEXT,
                     precio REAL,
                     categoria TEXT)''')
        c.execute('''CREATE TABLE IF NOT EXISTS pedidos
                    (id INTEGER PRIMARY KEY,
                     nombre TEXT,
                     cantidad INTEGER,
                     fecha TEXT)''')
        conn.commit()
        conn.close()

    def agregar_plato(self, nombre, precio, categoria):
        conn = sqlite3.connect('restaurante.db')
        c = conn.cursor()
        c.execute("INSERT INTO menu (nombre, precio, categoria) VALUES (?, ?, ?)",
                 (nombre, float(precio), categoria))
        conn.commit()
        conn.close()
        print(f"Plato '{nombre}' agregado exitosamente")

    def crear_pedido(self, nombre, cantidad):
        conn = sqlite3.connect('restaurante.db')
        c = conn.cursor()
        fecha = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        c.execute("INSERT INTO pedidos (nombre, cantidad, fecha) VALUES (?, ?, ?)",
                 (nombre, cantidad, fecha))
        conn.commit()
        conn.close()
        print(f"Pedido registrado: {cantidad}x {nombre}")

    def generar_reporte(self):
        conn = sqlite3.connect('restaurante.db')
        c = conn.cursor()
        c.execute("SELECT nombre, COUNT(*) FROM pedidos GROUP BY nombre")
        datos = c.fetchall()
        conn.close()

        if datos:
            nombres = [row[0] for row in datos]
            cantidades = [row[1] for row in datos]
            
            plt.figure(figsize=(10,6))
            plt.bar(nombres, cantidades)
            plt.title('Platos más vendidos')
            plt.xlabel('Platos')
            plt.ylabel('Cantidad')
            plt.xticks(rotation=45)
            plt.tight_layout()
            plt.savefig('reporte.png')
            plt.close()
            print("Reporte generado como 'reporte.png'")
        else:
            print("No hay datos para generar el reporte")

    def menu_principal(self):
        while True:
            print("\n=== MENÚ RESTAURANTE ===")
            print("1. Agregar plato")
            print("2. Crear pedido")
            print("3. Generar reporte")
            print("4. Salir")
            
            opcion = input("\nSeleccione una opción: ")
            
            if opcion == "1":
                nombre = input("Nombre del plato: ")
                precio = input("Precio: ")
                categoria = input("Categoría: ")
                self.agregar_plato(nombre, precio, categoria)
            
            elif opcion == "2":
                nombre = input("Nombre del plato: ")
                cantidad = int(input("Cantidad: "))
                self.crear_pedido(nombre, cantidad)
            
            elif opcion == "3":
                self.generar_reporte()
            
            elif opcion == "4":
                print("¡Hasta luego!")
                break
            
            else:
                print("Opción no válida")

if __name__ == "__main__":
    app = RestauranteConsola()
    app.menu_principal()