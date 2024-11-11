# graficas/visualizargraficas.py
import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
from tkinter import messagebox

class VisualizadorGraficas:
    def __init__(self):
        """Inicializa el visualizador de gráficas"""
        try:
            # Configurar el estilo de las gráficas
            plt.style.use('seaborn-v0_8-darkgrid')  # Usar estilo compatible
            sns.set_theme()  # Configuración básica de seaborn
            
            # Configurar el tamaño predeterminado
            plt.rcParams['figure.figsize'] = [10, 6]
            plt.rcParams['figure.dpi'] = 100
            
        except Exception as e:
            print(f"Error al inicializar visualizador: {str(e)}")
            messagebox.showerror("Error", "No se pudo inicializar el visualizador de gráficas")
            raise

    def crear_dashboard(self, datos):
        """Crea el dashboard con las gráficas"""
        try:
            # Limpiar gráficas anteriores
            plt.close('all')
            
            # Crear figura y subplots
            fig, (ax1, ax2, ax3) = plt.subplots(1, 3, figsize=(15, 5))
            
            # Gráfica 1: Consumo por edad
            if 'edad' in datos and not datos['edad'].empty:
                sns.barplot(data=datos['edad'], 
                        x='edad', y='promedio_semanal',
                        hue='Sexo', ax=ax1)
                ax1.set_title('Consumo por Edad')
                ax1.set_xlabel('Edad')
                ax1.set_ylabel('Bebidas por Semana')
            
            # Gráfica 2: Problemas de salud
            if 'salud' in datos and not datos['salud'].empty:
                problemas_df = datos['salud'].melt(
                    id_vars=['Sexo'], 
                    value_vars=['problemas_digestivos', 'tension_alta', 'dolor_cabeza_frecuente']
                )
                sns.barplot(data=problemas_df, 
                        x='variable', y='value',
                        hue='Sexo', ax=ax2)
                ax2.set_title('Problemas de Salud')
                ax2.set_xlabel('Tipo de Problema')
                ax2.set_ylabel('Cantidad de Casos')
            
            # Gráfica 3: Evolución temporal
            if 'tiempo' in datos and not datos['tiempo'].empty:
                sns.lineplot(data=datos['tiempo'], 
                            x='idEncuesta', y='BebidasSemana',
                            ax=ax3)
                ax3.set_title('Evolución del Consumo')
                ax3.set_xlabel('ID Encuesta')
                ax3.set_ylabel('Bebidas por Semana')
            
            plt.tight_layout()
            return fig
            
        except Exception as e:
            print(f"Error al crear dashboard: {str(e)}")
            return None

    def limpiar_graficas(self):
        """Limpia las gráficas actuales"""
        plt.close('all')