import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
from typing import List, Dict, Any

class VisualizadorGraficas:
    def __init__(self):
        # Configuración del estilo de las gráficas
        plt.style.use('seaborn')
        self.colores = ['#2ecc71', '#3498db', '#e74c3c', '#f1c40f', '#9b59b6']
    
    def grafico_consumo_por_edad(self, datos: pd.DataFrame) -> None:
        """Gráfico de barras para mostrar el consumo promedio por grupo de edad"""
        plt.figure(figsize=(10, 6))
        sns.barplot(x='grupo_edad', y='consumo_alcohol', data=datos, 
                   palette=self.colores)
        plt.title('Consumo Promedio de Alcohol por Grupo de Edad')
        plt.xlabel('Grupo de Edad')
        plt.ylabel('Consumo de Alcohol (ml)')
        plt.xticks(rotation=45)
        plt.tight_layout()
    
    def grafico_correlacion_salud(self, datos: pd.DataFrame) -> None:
        """Gráfico de dispersión para mostrar correlación entre consumo y problemas de salud"""
        plt.figure(figsize=(8, 6))
        sns.scatterplot(data=datos, x='consumo_alcohol', y='problemas_salud',
                       hue='edad', size='frecuencia', sizes=(50, 200))
        plt.title('Correlación entre Consumo de Alcohol y Problemas de Salud')
        plt.xlabel('Consumo de Alcohol (ml)')
        plt.ylabel('Índice de Problemas de Salud')
    
    def grafico_tendencia_temporal(self, datos: pd.DataFrame) -> None:
        """Gráfico de líneas para mostrar tendencias temporales de consumo"""
        plt.figure(figsize=(12, 6))
        plt.plot(datos['fecha'], datos['consumo_alcohol'], 
                marker='o', linewidth=2, color=self.colores[0])
        plt.title('Tendencia de Consumo de Alcohol a lo Largo del Tiempo')
        plt.xlabel('Fecha')
        plt.ylabel('Consumo de Alcohol (ml)')
        plt.grid(True, linestyle='--', alpha=0.7)
        plt.xticks(rotation=45)
    
    def grafico_distribucion_problemas(self, datos: Dict[str, int]) -> None:
        """Gráfico circular para mostrar distribución de problemas de salud"""
        plt.figure(figsize=(10, 8))
        plt.pie(datos.values(), labels=datos.keys(), autopct='%1.1f%%', 
                colors=self.colores, startangle=90)
        plt.title('Distribución de Problemas de Salud Reportados')
    
    def grafico_comparativo_grupos(self, datos: pd.DataFrame) -> None:
        """Gráfico de cajas para comparar distribuciones entre grupos"""
        plt.figure(figsize=(12, 6))
        sns.boxplot(x='grupo', y='consumo_alcohol', data=datos, 
                   palette=self.colores)
        plt.title('Comparación de Consumo entre Grupos')
        plt.xlabel('Grupo')
        plt.ylabel('Consumo de Alcohol (ml)')
        plt.xticks(rotation=45)
    
    def crear_dashboard(self, datos: Dict[str, pd.DataFrame]) -> None:
        """Crea un dashboard con múltiples gráficos"""
        fig = plt.figure(figsize=(15, 10))
        fig.suptitle('Dashboard de Análisis de Consumo de Alcohol', size=16)
        
        # Configurar subplot grid
        gs = fig.add_gridspec(2, 2)
        
        # Gráfico 1: Consumo por edad
        ax1 = fig.add_subplot(gs[0, 0])
        self.grafico_consumo_por_edad(datos['edad'])
        
        # Gráfico 2: Correlación salud
        ax2 = fig.add_subplot(gs[0, 1])
        self.grafico_correlacion_salud(datos['salud'])
        
        # Gráfico 3: Tendencia temporal
        ax3 = fig.add_subplot(gs[1, :])
        self.grafico_tendencia_temporal(datos['tiempo'])
        
        plt.tight_layout()
    
    def guardar_grafico(self, nombre_archivo: str, formato: str = 'png', dpi: int = 300) -> None:
        """Guarda el gráfico actual en un archivo"""
        plt.savefig(f"{nombre_archivo}.{formato}", format=formato, dpi=dpi, bbox_inches='tight')
    
    def mostrar_grafico(self) -> None:
        """Muestra el gráfico actual"""
        plt.show()
