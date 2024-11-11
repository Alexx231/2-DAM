# interfazusuario.py
import tkinter as tk
from tkinter import ttk, messagebox
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
from datetime import datetime

class InterfazSaludAlcohol:
    def __init__(self, root):
        self.root = root
        self.root.title("Sistema de Monitoreo de Salud")
        
        # Inicializar callbacks
        self._registrar_callback = None
        self._visualizar_callback = None
        self._estadisticas_callback = None
        self._exportar_callback = None
        
        # Crear componentes principales
        self.crear_notebook()
        self.crear_menu()
        self.crear_panel_registro()
        self.crear_panel_visualizacion()
        self.crear_panel_estadisticas()

    def crear_notebook(self):
        self.notebook = ttk.Notebook(self.root)
        self.notebook.pack(fill='both', expand=True)
        
        self.tab_registro = ttk.Frame(self.notebook)
        self.tab_visualizacion = ttk.Frame(self.notebook)
        self.tab_estadisticas = ttk.Frame(self.notebook)
        
        self.notebook.add(self.tab_registro, text='Registro')
        self.notebook.add(self.tab_visualizacion, text='Visualización')
        self.notebook.add(self.tab_estadisticas, text='Estadísticas')

    def crear_menu(self):
        menu_bar = tk.Menu(self.root)
        self.root.config(menu=menu_bar)
        
        # Menú Archivo
        file_menu = tk.Menu(menu_bar, tearoff=0)
        menu_bar.add_cascade(label="Archivo", menu=file_menu)
        file_menu.add_command(label="Exportar a Excel", 
                            command=lambda: self._exportar_callback("excel") if self._exportar_callback else None)
        file_menu.add_separator()
        file_menu.add_command(label="Salir", command=self.root.quit)
        
        # Menú Consultas
        query_menu = tk.Menu(menu_bar, tearoff=0)
        menu_bar.add_cascade(label="Consultas", menu=query_menu)
        query_menu.add_command(label="Ver Estadísticas", 
                             command=lambda: self._on_estadisticas())

    def crear_panel_registro(self):
        frame_entrada = ttk.LabelFrame(self.tab_registro, text="Registro de Consumo y Salud")
        frame_entrada.pack(padx=10, pady=10, fill='both', expand=True)
        
        # Añadir ComboBox para el sexo
        self.entrada_fecha = ttk.Entry(frame_entrada)
        self.entrada_alcohol = ttk.Entry(frame_entrada)
        self.entrada_presion = ttk.Entry(frame_entrada)
        self.entrada_edad = ttk.Entry(frame_entrada)
        self.entrada_sexo = ttk.Combobox(frame_entrada, values=['Hombre', 'Mujer'], state='readonly')
        self.entrada_problemas = ttk.Entry(frame_entrada)
        
        # Establecer valor por defecto para sexo
        self.entrada_sexo.set('Hombre')
        
        campos = [
            ("Fecha (YYYY-MM-DD):", self.entrada_fecha),
            ("Sexo:", self.entrada_sexo),
            ("Consumo de Alcohol (ml):", self.entrada_alcohol),
            ("Presión Arterial (120/80):", self.entrada_presion),
            ("Edad:", self.entrada_edad),
            ("Problemas de Salud:", self.entrada_problemas)
        ]
        
        for i, (label, entry) in enumerate(campos):
            ttk.Label(frame_entrada, text=label).grid(row=i, column=0, padx=5, pady=5, sticky='e')
            entry.grid(row=i, column=1, padx=5, pady=5, sticky='ew')

        ttk.Button(frame_entrada, text="Registrar Datos",
                command=self._on_registrar).grid(row=len(campos), column=0, 
                                                columnspan=2, pady=10)

    def crear_panel_visualizacion(self):
        self.frame_grafico = ttk.LabelFrame(self.tab_visualizacion, text="Gráficos")
        self.frame_grafico.pack(padx=10, pady=10, fill='both', expand=True)
        
        self.figura = plt.Figure(figsize=(10, 6))
        self.canvas = FigureCanvasTkAgg(self.figura, self.frame_grafico)
        self.canvas.get_tk_widget().pack(side=tk.TOP, fill=tk.BOTH, expand=1)
        
        ttk.Button(self.frame_grafico, text="Actualizar Gráficos",
                command=self._on_visualizar).pack(pady=5)

    def crear_panel_estadisticas(self):
        self.frame_estadisticas = ttk.LabelFrame(self.tab_estadisticas, text="Estadísticas")
        self.frame_estadisticas.pack(padx=10, pady=10, fill='both', expand=True)
        
        ttk.Button(self.frame_estadisticas, text="Actualizar Estadísticas",
                command=self._on_estadisticas).pack(pady=5)

    def registrar_callback(self, callback):
        self._registrar_callback = callback

    def visualizar_callback(self, callback):
        self._visualizar_callback = callback

    def estadisticas_callback(self, callback):
        self._estadisticas_callback = callback

    def exportar_callback(self, callback):
        self._exportar_callback = callback

    def _on_registrar(self):
        if self._registrar_callback:
            datos = self.obtener_datos_formulario()
    

    def _on_visualizar(self):
        if self._visualizar_callback:
            self._visualizar_callback()

    def _on_estadisticas(self):
        if self._estadisticas_callback:
            self._estadisticas_callback()
            
    def obtener_datos_formulario(self):
        """
        Obtains data from the form fields and returns it as a dictionary.
        Returns:
            dict: Dictionary containing the form data with keys: fecha, alcohol, presion, edad, problemas
        """
        datos = {
            'fecha': self.entrada_fecha.get(),
            'sexo': self.entrada_sexo.get(),
            'alcohol': self.entrada_alcohol.get(),
            'presion': self.entrada_presion.get(),
            'edad': self.entrada_edad.get(),
            'problemas': self.entrada_problemas.get()
        }
        if self._registrar_callback:
            if self._registrar_callback(datos):
                self.limpiar_formulario()
        return datos

    def validar_datos(self, datos):
        if not all(datos.values()):
            messagebox.showwarning("Error", "Todos los campos son requeridos")
            return False
        try:
            datetime.strptime(datos['fecha'], '%Y-%m-%d')
            float(datos['alcohol'])
            int(datos['edad'])
            if not '/' in datos['presion']:
                raise ValueError("Formato de presión inválido")
            return True
        except ValueError as e:
            messagebox.showerror("Error", f"Error de validación: {str(e)}")
            return False

    def limpiar_formulario(self):
        """Limpia todos los campos del formulario"""
        for attr in ['entrada_fecha', 'entrada_alcohol', 'entrada_presion', 
                    'entrada_edad', 'entrada_problemas']:
            if hasattr(self, attr):
                getattr(self, attr).delete(0, tk.END)
        # Resetear sexo al valor por defecto
        if hasattr(self, 'entrada_sexo'):
            self.entrada_sexo.set('Hombre')
            
    def actualizar_grafico(self, figura):
        """Actualiza el gráfico en la interfaz"""
        try:
            # Limpiar gráfico anterior si existe
            if hasattr(self, 'canvas'):
                self.canvas.get_tk_widget().destroy()
            
            # Crear nuevo canvas con la figura
            self.canvas = FigureCanvasTkAgg(figura, self.frame_grafico)
            self.canvas.draw()
            self.canvas.get_tk_widget().pack(side=tk.TOP, fill=tk.BOTH, expand=1)
            
        except Exception as e:
            print(f"Error al actualizar gráfico: {str(e)}")
            messagebox.showerror("Error", "No se pudo actualizar el gráfico")

    def actualizar_estadisticas(self, stats, alto_consumo):
        """Actualiza las estadísticas mostradas en la interfaz"""
        try:
            # Limpiar widgets anteriores
            for widget in self.frame_estadisticas.winfo_children():
                widget.destroy()
    
            # Crear frame para estadísticas
            frame_stats = ttk.LabelFrame(self.frame_estadisticas, text="Estadísticas Generales")
            frame_stats.pack(padx=10, pady=5, fill='both', expand=True)
    
            # Mostrar estadísticas generales
            estadisticas = {
                'Promedio Bebidas/Semana': stats['promedio_semanal'].mean(),
                'Promedio Cervezas/Semana': stats['promedio_cerveza'].mean(),
                'Promedio Bebidas Fin de Semana': stats['promedio_finde'].mean(),
                'Promedio Destiladas/Semana': stats['promedio_destiladas'].mean(),
                'Promedio Vinos/Semana': stats['promedio_vinos'].mean(),
                'Total Registros': stats['total_registros'].sum()
            }
    
            # Mostrar cada estadística
            for i, (label, value) in enumerate(estadisticas.items()):
                ttk.Label(frame_stats, text=f"{label}:").grid(row=i, column=0, padx=5, pady=2, sticky='e')
                ttk.Label(frame_stats, text=f"{value:.2f}").grid(row=i, column=1, padx=5, pady=2, sticky='w')
    
            # Frame para casos de alto consumo
            if not alto_consumo.empty:
                frame_alto = ttk.LabelFrame(self.frame_estadisticas, text="Casos de Alto Consumo")
                frame_alto.pack(padx=10, pady=5, fill='both', expand=True)
    
                # Crear Treeview para mostrar casos de alto consumo
                tree = ttk.Treeview(frame_alto, columns=('Edad', 'Sexo', 'Bebidas', 'Fin Semana'), 
                                   show='headings', height=5)
                
                # Configurar columnas
                tree.heading('Edad', text='Edad')
                tree.heading('Sexo', text='Sexo')
                tree.heading('Bebidas', text='Bebidas/Semana')
                tree.heading('Fin Semana', text='Bebidas Fin Semana')
    
                # Configurar ancho de columnas
                for col in tree['columns']:
                    tree.column(col, width=100, anchor='center')
    
                # Insertar datos
                for _, row in alto_consumo.iterrows():
                    tree.insert('', 'end', values=(
                        row['edad'],
                        row['Sexo'],
                        f"{row['BebidasSemana']:.1f}",
                        f"{row['BebidasFinSemana']:.1f}"
                    ))
    
                # Agregar scrollbar
                scrollbar = ttk.Scrollbar(frame_alto, orient='vertical', command=tree.yview)
                tree.configure(yscrollcommand=scrollbar.set)
    
                # Empaquetar Treeview y scrollbar
                tree.pack(side='left', fill='both', expand=True)
                scrollbar.pack(side='right', fill='y')
    
            # Botón para actualizar
            ttk.Button(self.frame_estadisticas, text="Actualizar Estadísticas",
                      command=self._on_estadisticas).pack(pady=10)
    
        except Exception as e:
            print(f"Error al actualizar estadísticas: {str(e)}")
            messagebox.showerror("Error", 
                               f"Error al actualizar estadísticas: {str(e)}")
            raise

    def mostrar_mensaje(self, titulo, mensaje):
        messagebox.showinfo(titulo, mensaje)