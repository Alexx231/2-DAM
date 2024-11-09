import tkinter as tk
from tkinter import ttk, messagebox
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

class InterfazSaludAlcohol:
    def __init__(self):
        self.ventana = tk.Tk()
        self.ventana.title("Sistema de Monitoreo de Salud - Dr. Fernando")
        self.ventana.geometry("800x600")
        
        # Crear el menú principal
        self.menu_principal = tk.Menu(self.ventana)
        self.ventana.config(menu=self.menu_principal)
        
        # Menú Archivo
        menu_archivo = tk.Menu(self.menu_principal, tearoff=0)
        self.menu_principal.add_cascade(label="Archivo", menu=menu_archivo)
        menu_archivo.add_command(label="Guardar Datos", command=self.guardar_datos)
        menu_archivo.add_command(label="Cargar Datos", command=self.cargar_datos)
        menu_archivo.add_separator()
        menu_archivo.add_command(label="Salir", command=self.ventana.quit)
        
        # Crear notebook para pestañas
        self.notebook = ttk.Notebook(self.ventana)
        
        # Pestaña de Registro de Datos
        self.tab_registro = ttk.Frame(self.notebook)
        self.notebook.add(self.tab_registro, text="Registro de Datos")
        self.crear_panel_registro()
        
        # Pestaña de Visualización
        self.tab_visualizacion = ttk.Frame(self.notebook)
        self.notebook.add(self.tab_visualizacion, text="Visualización")
        self.crear_panel_visualizacion()
        
        # Pestaña de Estadísticas
        self.tab_estadisticas = ttk.Frame(self.notebook)
        self.notebook.add(self.tab_estadisticas, text="Estadísticas")
        self.crear_panel_estadisticas()
        
        self.notebook.pack(expand=True, fill='both')
    
    def crear_panel_registro(self):
        # Frame para entrada de datos
        frame_entrada = ttk.LabelFrame(self.tab_registro, text="Registro de Consumo y Salud")
        frame_entrada.pack(padx=10, pady=10, fill='x')
        
        # Campos de entrada
        ttk.Label(frame_entrada, text="Fecha:").grid(row=0, column=0, padx=5, pady=5)
        self.entrada_fecha = ttk.Entry(frame_entrada)
        self.entrada_fecha.grid(row=0, column=1, padx=5, pady=5)
        
        ttk.Label(frame_entrada, text="Consumo de Alcohol (ml):").grid(row=1, column=0, padx=5, pady=5)
        self.entrada_alcohol = ttk.Entry(frame_entrada)
        self.entrada_alcohol.grid(row=1, column=1, padx=5, pady=5)
        
        ttk.Label(frame_entrada, text="Presión Arterial:").grid(row=2, column=0, padx=5, pady=5)
        self.entrada_presion = ttk.Entry(frame_entrada)
        self.entrada_presion.grid(row=2, column=1, padx=5, pady=5)
        
        # Botón de registro
        ttk.Button(frame_entrada, text="Registrar Datos", 
                  command=self.registrar_datos).grid(row=3, column=0, columnspan=2, pady=10)

    def crear_panel_visualizacion(self):
        # Frame para gráficos
        self.frame_grafico = ttk.Frame(self.tab_visualizacion)
        self.frame_grafico.pack(expand=True, fill='both')
        
        # Aquí se agregará el gráfico dinámicamente
        self.figura = plt.Figure(figsize=(6, 4))
        self.canvas = FigureCanvasTkAgg(self.figura, self.frame_grafico)
        self.canvas.get_tk_widget().pack(side=tk.TOP, fill=tk.BOTH, expand=1)

    def crear_panel_estadisticas(self):
        # Frame para estadísticas
        frame_stats = ttk.LabelFrame(self.tab_estadisticas, text="Resumen Estadístico")
        frame_stats.pack(padx=10, pady=10, fill='both', expand=True)
        
        # Árbol de datos
        self.tree = ttk.Treeview(frame_stats, columns=("Valor"), show='headings')
        self.tree.heading("Valor", text="Valor")
        self.tree.pack(padx=5, pady=5, fill='both', expand=True)

    def registrar_datos(self):
        try:
            # Aquí iría la lógica para guardar los datos
            messagebox.showinfo("Éxito", "Datos registrados correctamente")
        except Exception as e:
            messagebox.showerror("Error", f"Error al registrar datos: {str(e)}")

    def guardar_datos(self):
        # Implementar guardado de datos
        pass

    def cargar_datos(self):
        # Implementar carga de datos
        pass

    def ejecutar(self):
        self.ventana.mainloop()

if __name__ == "__main__":
    app = InterfazSaludAlcohol()
    app.ejecutar()