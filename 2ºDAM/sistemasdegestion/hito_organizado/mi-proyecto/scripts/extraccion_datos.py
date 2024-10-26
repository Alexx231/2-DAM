# mi-proyecto/scripts/extraccion_datos.py
import requests
import pandas as pd
import os

# Función para descargar el archivo Excel desde una URL
def descargar_archivo_excel(url, ruta_salida):
    response = requests.get(url)
    with open(ruta_salida, 'wb') as file:
        file.write(response.content)

# Función para cargar el archivo Excel
def cargar_archivo_excel(ruta_archivo):
    return pd.ExcelFile(ruta_archivo)

# Función para leer una hoja de datos del archivo Excel
def leer_hoja_excel(datos_excel, nombre_hoja):
    df = datos_excel.parse(nombre_hoja)
    # Asumimos que los nombres están en la primera fila y columna
    df.columns = df.iloc[0]
    df = df[1:]
    df.reset_index(drop=True, inplace=True)
    return df