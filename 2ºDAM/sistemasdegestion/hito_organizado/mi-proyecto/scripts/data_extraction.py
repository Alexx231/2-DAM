import requests
import pandas as pd
import os

# Función para descargar el archivo Excel
def download_excel_file(url, output_path):
    response = requests.get(url)
    with open(output_path, 'wb') as file:
        file.write(response.content)

# Función para cargar el archivo Excel
def load_excel_file(file_path):
    return pd.ExcelFile(file_path)

# Función para leer la hoja de datos
def read_excel_sheet(excel_data, sheet_name):
    df = excel_data.parse(sheet_name)
    # Asumimos que los nombres están en la primera fila y columna
    df.columns = df.iloc[0]
    df = df[1:]
    df.reset_index(drop=True, inplace=True)
    return df

# Crear la carpeta 'data' si no existe
output_dir = 'data'
os.makedirs(output_dir, exist_ok=True)

# Descargar el archivo Excel
url = 'https://www.ine.es/jaxiT3/files/t/es/xlsx/60272.xlsx?nocab=1'
output_path = os.path.join(output_dir, 'datos_ine.xlsx')
download_excel_file(url, output_path)

# Cargar el archivo Excel en una variable
excel_data = load_excel_file(output_path)

# Identificar la hoja de datos (asumimos que es la primera hoja)
sheet_name = excel_data.sheet_names[0]

# Leer la hoja de datos
df = read_excel_sheet(excel_data, sheet_name)

# Mostrar las primeras filas para identificar la fila y columna de los nombres
print("Primeras filas del DataFrame original:")
print(df.head())