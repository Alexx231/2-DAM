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
        
        # Inicializar campos de entrada
        self.entrada_fecha = None
        self.entrada_sexo = None
        self.entrada_edad = None
        self.entrada_alcohol = None
        self.entrada_presion = None
        self.entrada_problemas = None
        
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
        
        # Inicializar canvas
        self.canvas = None

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

    def _crear_seccion_campos(self, titulo, campos, frame_principal):
        """
        Crea una sección de campos en el formulario
        Args:
            titulo (str): Título de la sección
            campos (list): Lista de tuplas con la información de los campos
            frame_principal (ttk.Frame): Frame contenedor
        """
        frame = ttk.LabelFrame(frame_principal, text=titulo, style="Custom.TLabelframe")
        frame.pack(fill='x', padx=20, pady=10)

        frame_grid = ttk.Frame(frame)
        frame_grid.pack(padx=15, pady=15)

        for i, campo in enumerate(campos):
            if len(campo) == 4:  # Campo con etiqueta, nombre, ancho y hint
                label, attr, width, hint = campo
                ttk.Label(frame_grid, text=label).grid(row=i, column=0, padx=10, pady=8, sticky='e')
                setattr(self, attr, ttk.Entry(frame_grid, width=width))
                getattr(self, attr).grid(row=i, column=1, padx=10, pady=8, sticky='w')
                if hint:
                    ttk.Label(frame_grid, text=hint, foreground="gray").grid(row=i, column=2, padx=5, pady=8, sticky='w')
            
            elif len(campo) == 3:  # Campo con etiqueta, nombre y opciones
                label, attr, opciones = campo
                ttk.Label(frame_grid, text=label).grid(row=i, column=0, padx=10, pady=8, sticky='e')
                setattr(self, attr, ttk.Combobox(frame_grid, values=opciones, state='readonly'))
                getattr(self, attr).grid(row=i, column=1, padx=10, pady=8, sticky='w')
                getattr(self, attr).set(opciones[0])

    def crear_panel_registro(self):
        """Crea un panel de registro mejorado y más intuitivo"""
        frame_principal = ttk.Frame(self.tab_registro, style="Custom.TLabelframe")
        frame_principal.pack(padx=30, pady=20, fill='both', expand=True)

        # Título principal
        titulo = ttk.Label(frame_principal, 
                        text="REGISTRO DE PACIENTE",
                        style="Title.TLabel")
        titulo.pack(pady=(0, 20))

        # Frame para datos personales con diseño mejorado
        frame_personal = ttk.LabelFrame(frame_principal, 
                                    text="Datos Personales",
                                    style="Custom.TLabelframe")
        frame_personal.pack(fill='x', padx=20, pady=10)

        # Grid para datos personales
        frame_grid_personal = ttk.Frame(frame_personal)
        frame_grid_personal.pack(padx=15, pady=15)

        # Campos mejorados
        campos_personales = [
            ("Fecha:", "entrada_fecha", 15, "(YYYY-MM-DD)"),
            ("Edad:", "entrada_edad", 5, "años"),
            ("Sexo:", "entrada_sexo", 10, None)
        ]
        
            # Campos de consumo
        campos_consumo = [
            ("Bebidas por Semana:", "entrada_bebidas_semana", 10, "unidades"),
            ("Cervezas por Semana:", "entrada_cervezas", 10, "unidades"),
            ("Bebidas Fin de Semana:", "entrada_finde", 10, "unidades"),
            ("Bebidas Destiladas:", "entrada_destiladas", 10, "unidades"),
            ("Vinos por Semana:", "entrada_vinos", 10, "unidades")
        ]

        # Campos de salud
        campos_salud = [
            ("Pérdidas de Control:", "entrada_perdidas_control", ["Sí", "No"]),
            ("Diversión Depende del Alcohol:", "entrada_diversion_alcohol", ["Sí", "No"]),
            ("Problemas Digestivos:", "entrada_problemas_digestivos", ["Sí", "No"]),
            ("Tensión Alta:", "entrada_tension_alta", ["Sí", "No"]),
            ("Frecuencia Dolor de Cabeza:", "entrada_dolor_cabeza", 
            ["Nunca", "Raramente", "A menudo", "Muy a menudo"])
        ]

        # Crear y organizar campos
        self._crear_seccion_campos("Datos Personales", campos_personales, frame_principal)
        self._crear_seccion_campos("Datos de Consumo", campos_consumo, frame_principal)
        self._crear_seccion_campos("Datos de Salud", campos_salud, frame_principal)

        for i, (label, attr, width, hint) in enumerate(campos_personales):
            ttk.Label(frame_grid_personal, text=label).grid(row=i, column=0, padx=10, pady=8, sticky='e')
            
            if attr == "entrada_sexo":
                setattr(self, attr, ttk.Combobox(frame_grid_personal, 
                                            values=['Hombre', 'Mujer'],
                                            state='readonly',
                                            width=width,
                                            style="Custom.TCombobox"))
                getattr(self, attr).set('Hombre')
            else:
                setattr(self, attr, ttk.Entry(frame_grid_personal, 
                                            width=width,
                                            style="Custom.TEntry"))
            
            getattr(self, attr).grid(row=i, column=1, padx=10, pady=8, sticky='w')
            
            if hint:
                ttk.Label(frame_grid_personal, 
                        text=hint,
                        foreground="gray").grid(row=i, column=2, padx=5, pady=8, sticky='w')

        # Frame para datos de salud
        frame_salud = ttk.LabelFrame(frame_principal, 
                                    text="Datos de Salud",
                                    style="Custom.TLabelframe")
        frame_salud.pack(fill='x', padx=20, pady=10)

        # Grid para datos de salud
        frame_grid_salud = ttk.Frame(frame_salud)
        frame_grid_salud.pack(padx=15, pady=15)

        # Campos de salud mejorados
        self.entrada_alcohol = ttk.Entry(frame_grid_salud, 
                                    width=10,
                                    style="Custom.TEntry")
        self.entrada_presion = ttk.Entry(frame_grid_salud, 
                                        width=10,
                                        style="Custom.TEntry")
        self.entrada_problemas = tk.Text(frame_grid_salud, 
                                        height=3, 
                                        width=30,
                                        font=("Segoe UI", 10),
                                        bg="white",
                                        relief="flat")

        # Grid layout mejorado
        campos_salud = [
            ("Consumo de Alcohol:", self.entrada_alcohol, "ml/semana"),
            ("Presión Arterial:", self.entrada_presion, "(120/80)"),
            ("Problemas de Salud:", self.entrada_problemas, None)
        ]

        for i, (label, widget, hint) in enumerate(campos_salud):
            ttk.Label(frame_grid_salud, text=label).grid(row=i, column=0, padx=10, pady=8, sticky='e')
            widget.grid(row=i, column=1, padx=10, pady=8, sticky='w')
            if hint:
                ttk.Label(frame_grid_salud, 
                        text=hint,
                        foreground="gray").grid(row=i, column=2, padx=5, pady=8, sticky='w')

        # Frame para botones
        frame_botones = ttk.Frame(frame_principal)
        frame_botones.pack(pady=25)

        # Botones mejorados
        ttk.Button(frame_botones,
                text="Limpiar Formulario",
                style="Secondary.TButton",
                command=self.limpiar_formulario).pack(side='left', padx=15)

        ttk.Button(frame_botones,
                text="Registrar Paciente",
                style="Primary.TButton",
                command=self._on_registrar).pack(side='left', padx=15)

        # Configurar validación
        self._configurar_validacion()
        
    def _configurar_validacion(self):
        """Configura la validación de campos con feedback mejorado"""
        
        def validar_fecha(event):
            try:
                fecha = self.entrada_fecha.get()
                if not fecha:
                    self.entrada_fecha.configure(style='Warning.TEntry')
                    self._mostrar_tooltip(self.entrada_fecha, "La fecha es obligatoria")
                    return
                    
                fecha_obj = datetime.strptime(fecha, '%Y-%m-%d')
                if fecha_obj > datetime.now():
                    raise ValueError("La fecha no puede ser futura")
                    
                self.entrada_fecha.configure(style='Valid.TEntry')
                self._ocultar_tooltip(self.entrada_fecha)
                
            except ValueError as e:
                self.entrada_fecha.configure(style='Error.TEntry')
                self._mostrar_tooltip(self.entrada_fecha, "Formato inválido (YYYY-MM-DD)")
    
        def validar_edad(event):
            try:
                edad = self.entrada_edad.get()
                if not edad:
                    self.entrada_edad.configure(style='Warning.TEntry')
                    self._mostrar_tooltip(self.entrada_edad, "La edad es obligatoria")
                    return
                    
                edad = int(edad)
                if not (0 <= edad <= 120):
                    raise ValueError("Edad fuera de rango")
                    
                self.entrada_edad.configure(style='Valid.TEntry')
                self._ocultar_tooltip(self.entrada_edad)
                
            except ValueError:
                self.entrada_edad.configure(style='Error.TEntry')
                self._mostrar_tooltip(self.entrada_edad, "Edad inválida (0-120)")
    
        def validar_alcohol(event):
            try:
                cantidad = self.entrada_alcohol.get()
                if not cantidad:
                    self.entrada_alcohol.configure(style='Warning.TEntry')
                    self._mostrar_tooltip(self.entrada_alcohol, "El consumo es obligatorio")
                    return
                    
                cantidad = float(cantidad)
                if cantidad < 0:
                    raise ValueError("Cantidad negativa")
                    
                self.entrada_alcohol.configure(style='Valid.TEntry')
                self._ocultar_tooltip(self.entrada_alcohol)
                
            except ValueError:
                self.entrada_alcohol.configure(style='Error.TEntry')
                self._mostrar_tooltip(self.entrada_alcohol, "Cantidad inválida")
    
        # Configurar estilos de validación
        style = ttk.Style()
        style.configure('Error.TEntry',
                       fieldbackground='#ffd1d1',
                       bordercolor='#ff0000')
        style.configure('Warning.TEntry',
                       fieldbackground='#fff3cd',
                       bordercolor='#ffeeba')
        style.configure('Valid.TEntry',
                       fieldbackground='#d4edda',
                       bordercolor='#c3e6cb')
    
        # Vincular eventos de validación
        self.entrada_fecha.bind('<FocusOut>', validar_fecha)
        self.entrada_edad.bind('<FocusOut>', validar_edad)
        self.entrada_alcohol.bind('<FocusOut>', validar_alcohol)
    
    def _mostrar_tooltip(self, widget, mensaje):
        """Muestra un tooltip con el mensaje de error"""
        try:
            x, y, _, _ = widget.bbox("insert")
            x += widget.winfo_rootx() + 25
            y += widget.winfo_rooty() + 20
    
            self.tooltip = tk.Toplevel(widget)
            self.tooltip.wm_overrideredirect(True)
            self.tooltip.wm_geometry(f"+{x}+{y}")
    
            label = ttk.Label(self.tooltip, text=mensaje,
                             justify='left',
                             background="#ffeeee",
                             relief='solid',
                             borderwidth=1,
                             font=("Segoe UI", 9))
            label.pack()
            
        except Exception as e:
            print(f"Error al mostrar tooltip: {e}")
    
    def _ocultar_tooltip(self, widget):
        """Oculta el tooltip si existe"""
        if hasattr(self, 'tooltip'):
            self.tooltip.destroy()

    # interfaz/interfazusuario.py
    def crear_panel_visualizacion(self):
        """Crea el panel de visualización de gráficas"""
        self.frame_grafico = ttk.LabelFrame(self.tab_visualizacion, text="Gráficos")
        self.frame_grafico.pack(padx=10, pady=10, fill='both', expand=True)
        
        # Panel de controles
        frame_controles = ttk.Frame(self.frame_grafico)
        frame_controles.pack(fill='x', padx=5, pady=5)
        
        # Selector de tipo de gráfica
        ttk.Label(frame_controles, text="Tipo de Gráfica:").pack(side='left', padx=5)
        self.tipo_grafica = ttk.Combobox(
            frame_controles,
            values=[
                'Consumo por Edad',
                'Problemas de Salud',
                'Tendencia Temporal'
            ],
            state='readonly',
            width=30
        )
        self.tipo_grafica.pack(side='left', padx=5)
        self.tipo_grafica.set('Consumo por Edad')
        
        # Frame para contener el gráfico
        self.frame_figura = ttk.Frame(self.frame_grafico)
        self.frame_figura.pack(fill='both', expand=True, padx=5, pady=5)
        
        # Botón actualizar
        ttk.Button(
            frame_controles,
            text="Mostrar Gráfica",
            command=self._on_visualizar
        ).pack(side='right', padx=5)

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
        Obtiene y valida los datos del formulario.
        Retorna:
            dict: Diccionario con los datos validados del formulario con las claves:
                fecha, sexo, alcohol, presion, edad, problemas
        Raises:
            ValueError: Si algún campo requerido está vacío o es inválido
        """
        try:
            # Obtener datos de los campos
            datos = {
                'fecha': self.entrada_fecha.get().strip(),
                'sexo': self.entrada_sexo.get().strip(),
                'alcohol': self.entrada_alcohol.get().strip(),
                'presion': self.entrada_presion.get().strip(),
                'edad': self.entrada_edad.get().strip(),
                'problemas': self.entrada_problemas.get('1.0', 'end-1c').strip()
            }
    
            # Validar campos requeridos
            campos_requeridos = ['fecha', 'sexo', 'alcohol', 'edad']
            for campo in campos_requeridos:
                if not datos[campo]:
                    self._mostrar_error_campo(campo)
                    raise ValueError(f"El campo {campo} es obligatorio")
    
            # Validar formato de fecha
            try:
                datetime.strptime(datos['fecha'], '%Y-%m-%d')
            except ValueError:
                self._mostrar_error_campo('fecha')
                raise ValueError("Formato de fecha inválido (YYYY-MM-DD)")
    
            # Validar edad
            try:
                edad = int(datos['edad'])
                if not (0 <= edad <= 120):
                    raise ValueError
            except ValueError:
                self._mostrar_error_campo('edad')
                raise ValueError("Edad inválida (0-120 años)")
    
            # Validar alcohol
            try:
                alcohol = float(datos['alcohol'])
                if alcohol < 0:
                    raise ValueError
            except ValueError:
                self._mostrar_error_campo('alcohol')
                raise ValueError("Cantidad de alcohol inválida")
    
            # Si todo es válido y existe callback, registrar y limpiar
            if self._registrar_callback and self._registrar_callback(datos):
                self.limpiar_formulario()
                messagebox.showinfo("Éxito", "Datos registrados correctamente")
    
            return datos
    
        except ValueError as e:
            messagebox.showerror("Error de Validación", str(e))
            return None
        except Exception as e:
            messagebox.showerror("Error", f"Error al procesar el formulario: {str(e)}")
            return None
    
    def _mostrar_error_campo(self, campo):
        """Resalta visualmente el campo con error"""
        if hasattr(self, f'entrada_{campo}'):
            widget = getattr(self, f'entrada_{campo}')
            widget.configure(style='Error.TEntry')
            widget.focus()

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
            # Limpiar el frame de la figura
            for widget in self.frame_figura.winfo_children():
                widget.destroy()
            
            if figura is None:
                messagebox.showinfo("Info", "No hay datos para mostrar")
                return
                
            # Crear canvas para la figura
            canvas = FigureCanvasTkAgg(figura, self.frame_figura)
            canvas.draw()
            widget = canvas.get_tk_widget()
            widget.pack(fill='both', expand=True)
            
        except Exception as e:
            print(f"Error al actualizar gráfico: {str(e)}")
            messagebox.showerror("Error", f"Error al actualizar gráfico: {str(e)}")

    def actualizar_estadisticas(self, stats, alto_consumo):
        try:
            # Limpiar widgets anteriores
            for widget in self.frame_estadisticas.winfo_children():
                widget.destroy()
            
            # Panel principal con pestañas
            notebook = ttk.Notebook(self.frame_estadisticas)
            notebook.pack(fill='both', expand=True, padx=10, pady=5)
            
            # Solo mantenemos las dos pestañas principales
            tab_resumen = ttk.Frame(notebook)
            tab_alto_riesgo = ttk.Frame(notebook)
            
            notebook.add(tab_resumen, text='Resumen General')
            notebook.add(tab_alto_riesgo, text='Alto Riesgo')
            
            # Crear contenido de las pestañas
            self._crear_resumen_general(tab_resumen, stats)
            self._crear_panel_alto_riesgo(tab_alto_riesgo, alto_consumo)
            
        except Exception as e:
            messagebox.showerror("Error", f"Error al actualizar estadísticas: {str(e)}")

    def _crear_resumen_general(self, tab, stats):
        frame_stats = ttk.LabelFrame(tab, text="Estadísticas Generales")
        frame_stats.pack(padx=10, pady=5, fill='both', expand=True)

        # Estadísticas por género
        for sexo in ['Hombre', 'Mujer']:
            datos_sexo = stats[stats['Sexo'] == sexo]
            frame_genero = ttk.LabelFrame(frame_stats, text=f"Estadísticas {sexo}")
            frame_genero.pack(padx=5, pady=5, fill='x')

            estadisticas = {
                'Promedio Bebidas/Semana': datos_sexo['BebidasSemana'].mean(),
                'Promedio Cervezas': datos_sexo['CervezasSemana'].mean(),
                'Promedio Fin de Semana': datos_sexo['BebidasFinSemana'].mean(),
                'Promedio Destiladas': datos_sexo['BebidasDestiladasSemana'].mean(),
                'Promedio Vinos': datos_sexo['VinosSemana'].mean(),
                'Total Personas': len(datos_sexo)
            }

            for i, (label, value) in enumerate(estadisticas.items()):
                ttk.Label(frame_genero, text=f"{label}:", 
                        style="Bold.TLabel").grid(row=i, column=0, padx=5, pady=2, sticky='e')
                ttk.Label(frame_genero, text=f"{value:.2f}").grid(row=i, column=1, padx=5, pady=2, sticky='w')

    def _crear_panel_alto_riesgo(self, tab, alto_consumo):
        frame = ttk.LabelFrame(tab, text="Casos de Alto Consumo")
        frame.pack(padx=10, pady=5, fill='both', expand=True)

        # Crear Treeview con más detalles
        columns = ('ID', 'Edad', 'Sexo', 'Bebidas', 'Cerveza', 'Finde', 'Destiladas', 'Vinos')
        tree = ttk.Treeview(frame, columns=columns, show='headings', height=10)
        
        # Configurar columnas
        for col in columns:
            tree.heading(col, text=col)
            tree.column(col, width=80, anchor='center')

        # Insertar datos
        for _, row in alto_consumo.iterrows():
            tree.insert('', 'end', values=(
                row['idEncuesta'],
                row['edad'],
                row['Sexo'],
                f"{row['BebidasSemana']:.1f}",
                f"{row['CervezasSemana']:.1f}",
                f"{row['BebidasFinSemana']:.1f}",
                f"{row['BebidasDestiladasSemana']:.1f}",
                f"{row['VinosSemana']:.1f}"
            ))

        # Agregar scrollbars
        scrolly = ttk.Scrollbar(frame, orient='vertical', command=tree.yview)
        scrollx = ttk.Scrollbar(frame, orient='horizontal', command=tree.xview)
        tree.configure(yscrollcommand=scrolly.set, xscrollcommand=scrollx.set)

        # Eventos del árbol
        tree.bind('<<TreeviewSelect>>', self._on_select_paciente)

        # Empaquetar componentes
        tree.pack(side='left', fill='both', expand=True)
        scrolly.pack(side='right', fill='y')
        scrollx.pack(side='bottom', fill='x')

    def _on_select_paciente(self, event):
        """Maneja la selección de un paciente en el TreeView"""
        tree = event.widget
        try:
            if tree.selection():
                item_id = tree.selection()[0]
                valores = tree.item(item_id)['values']
                messagebox.showinfo("Detalles del Paciente", 
                                f"ID: {valores[0]}\n"
                                f"Edad: {valores[1]}\n"
                                f"Sexo: {valores[2]}\n"
                                f"Bebidas/Semana: {valores[3]}")
        except Exception as e:
            messagebox.showerror("Error", f"Error al mostrar detalles: {str(e)}")

    def mostrar_mensaje(self, titulo, mensaje):
        messagebox.showinfo(titulo, mensaje)