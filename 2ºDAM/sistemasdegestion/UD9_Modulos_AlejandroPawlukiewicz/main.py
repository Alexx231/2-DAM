"""Módulo para la gestión de un restaurante con interfaz gráfica."""
import os
import sqlite3
from datetime import datetime
import tkinter as tk
from tkinter import ttk, messagebox
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt

class RestauranteGUI:
    """Clase que implementa la interfaz gráfica del sistema de gestión de restaurante."""
    
    def __init__(self):
        # Inicializar ventana principal
        self.root = tk.Tk()
        self.root.title("Gestión de Restaurante")
        self.root.geometry("600x400")

        # Eliminar la base de datos si existe
        if os.path.exists('restaurante.db'):
            os.remove('restaurante.db')
        self.crear_bd()
        self.crear_interfaz()
        
    def crear_bd(self):
        """Crea la base de datos y las tablas necesarias."""
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
        conn.close()

    def crear_interfaz(self):
        """Crea la interfaz gráfica de usuario."""
        # Crear notebook para pestañas
        notebook = ttk.Notebook(self.root)
        notebook.pack(expand=True, fill='both')

        # Pestaña Agregar Plato
        frame_agregar = ttk.Frame(notebook)
        notebook.add(frame_agregar, text='Agregar Plato')
        
        ttk.Label(frame_agregar, text="Nombre:").grid(row=0, column=0, padx=5, pady=5)
        self.nombre_plato = ttk.Entry(frame_agregar)
        self.nombre_plato.grid(row=0, column=1, padx=5, pady=5)
        
        ttk.Label(frame_agregar, text="Precio:").grid(row=1, column=0, padx=5, pady=5)
        self.precio_plato = ttk.Entry(frame_agregar)
        self.precio_plato.grid(row=1, column=1, padx=5, pady=5)
        
        ttk.Label(frame_agregar, text="Categoría:").grid(row=2, column=0, padx=5, pady=5)
        self.categoria_plato = ttk.Entry(frame_agregar)
        self.categoria_plato.grid(row=2, column=1, padx=5, pady=5)
        
        ttk.Button(frame_agregar, text="Agregar", command=self.agregar_plato).grid(row=3, column=0, columnspan=2, pady=20)

        # Pestaña Crear Pedido
        frame_pedido = ttk.Frame(notebook)
        notebook.add(frame_pedido, text='Crear Pedido')
        
        ttk.Label(frame_pedido, text="Nombre plato:").grid(row=0, column=0, padx=5, pady=5)
        self.nombre_pedido = ttk.Entry(frame_pedido)
        self.nombre_pedido.grid(row=0, column=1, padx=5, pady=5)
        
        ttk.Label(frame_pedido, text="Cantidad:").grid(row=1, column=0, padx=5, pady=5)
        self.cantidad_pedido = ttk.Entry(frame_pedido)
        self.cantidad_pedido.grid(row=1, column=1, padx=5, pady=5)
        
        ttk.Button(frame_pedido, text="Crear Pedido", command=self.crear_pedido).grid(row=2, column=0, columnspan=2, pady=20)

        # Botón para generar reporte
        ttk.Button(self.root, text="Generar Reporte", command=self.generar_reporte).pack(pady=10)

    def agregar_plato(self):
        """Agrega un nuevo plato al menú."""
        try:
            nombre = self.nombre_plato.get()
            precio = float(self.precio_plato.get())
            categoria = self.categoria_plato.get()
            
            conn = sqlite3.connect('restaurante.db')
            c = conn.cursor()
            c.execute("INSERT INTO menu (nombre, precio, categoria) VALUES (?, ?, ?)", 
                     (nombre, precio, categoria))
            conn.commit()
            conn.close()
            
            messagebox.showinfo("Éxito", f"Plato '{nombre}' agregado exitosamente")
            # Limpiar campos
            self.nombre_plato.delete(0, tk.END)
            self.precio_plato.delete(0, tk.END)
            self.categoria_plato.delete(0, tk.END)
        except Exception as e:
            messagebox.showerror("Error", str(e))

    def crear_pedido(self):
        """Registra un nuevo pedido en el sistema."""
        try:
            nombre = self.nombre_pedido.get()
            cantidad = int(self.cantidad_pedido.get())
            
            conn = sqlite3.connect('restaurante.db')
            c = conn.cursor()
            fecha = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
            c.execute("INSERT INTO pedidos (nombre, cantidad, fecha) VALUES (?, ?, ?)",
                     (nombre, cantidad, fecha))
            conn.commit()
            conn.close()
            
            messagebox.showinfo("Éxito", f"Pedido registrado: {cantidad}x {nombre}")
            # Limpiar campos
            self.nombre_pedido.delete(0, tk.END)
            self.cantidad_pedido.delete(0, tk.END)
        except Exception as e:
            messagebox.showerror("Error", str(e))

    def generar_reporte(self):
        """Genera un reporte gráfico de los platos más vendidos."""
        try:
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
                plt.xlabel('Platos')
                plt.ylabel('Cantidad')
                plt.xticks(rotation=45)
                plt.tight_layout()
                plt.savefig('reporte.png')
                plt.close()
                messagebox.showinfo("Éxito", "Reporte generado como 'reporte.png'")
            else:
                messagebox.showwarning("Aviso", "No hay datos para generar el reporte")
        except Exception as e:
            messagebox.showerror("Error", str(e))

    def iniciar(self):
        """Inicia la aplicación."""
        self.root.mainloop()

if __name__ == "__main__":
    app = RestauranteGUI()
    app.iniciar()