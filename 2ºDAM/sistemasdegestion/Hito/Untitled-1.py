# %%
%pip install requests pandas tabulate openpyxl
from tabulate import tabulate
import requests
import pandas as pd
import openpyxl

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
    return pd.read_excel(excel_data, sheet_name)

# Función para limpiar el dataframe
def clean_dataframe(df):
    # Eliminar filas y columnas vacías
    df.dropna(how='all', inplace=True)
    df.dropna(axis=1, how='all', inplace=True)
    return df

# Función para reemplazar valores NaN por 0
def replace_nan_with_zero(df):
    return df.fillna(0)

# Función para limpiar datos incorrectos
def clean_data(df):
    # Aquí puedes agregar más reglas de limpieza si es necesario
    return df

# Descargar el archivo Excel
url = 'https://www.ine.es/jaxiT3/files/t/es/xlsx/60272.xlsx?nocab=1'
output_path = 'datos_ine.xlsx'
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

# Limpiar el dataframe
df = clean_dataframe(df)

# Reemplazar valores NaN por 0
df = replace_nan_with_zero(df)

# Limpiar datos incorrectos
df = clean_data(df)

# Mostrar el dataset limpio
print("Primeras filas del DataFrame limpio:")
print(tabulate(df.head(), headers='keys', tablefmt='grid'))

# Función para transponer el dataframe
def transpose_dataframe(dataframe):
    return dataframe.transpose()

# Transponer el dataframe
df_transposed = transpose_dataframe(df)

# Mostrar el dataset transpuesto
print("Primeras filas del DataFrame transpuesto:")
print(tabulate(df_transposed.head(), headers='keys', tablefmt='grid'))

# Extraer una colección de datos de cada tipo utilizando una fila distinta
collection = df_transposed.iloc[0].tolist()
print("Colección de datos de la primera fila transpuesta:", collection)

# %%
# Función para limpiar el dataframe
def clean_dataframe(df):
    # Reemplazar valores NaN por 0
    df.fillna(0, inplace=True)
    
    # Reemplazar valores menores que cero por 0 solo en columnas numéricas
    num_cols = df.select_dtypes(include=['number']).columns
    df[num_cols] = df[num_cols].applymap(lambda x: 0 if x < 0 else x)
    
    # Convertir todas las celdas a string para verificar texto en celdas numéricas
    df = df.applymap(str)
    
    # Reemplazar texto en celdas numéricas por 0
    df = df.applymap(lambda x: 0 if not x.replace('.', '', 1).isdigit() else x)
    
    # Convertir de nuevo a numérico donde sea posible
    df = df.apply(pd.to_numeric, errors='ignore')
    
    return df

df = pd.read_excel('datos_ine.xlsx')

# Limpiar el dataframe
df_limpio = clean_dataframe(df)

# Mostrar el dataset limpio
print(df_limpio.head())

# %%
# Función para transponer el dataframe
def transpose_dataframe(df):
    return df.transpose()

# Función para extraer una colección de datos de cada tipo utilizando una fila distinta
def extract_data_collections(df):
    collections = {}
    for index, row in df.iterrows():
        collections[index] = row.tolist()
    return collections

df = pd.read_excel('datos_ine.xlsx')

# Limpiar el dataframe
df_limpio = clean_dataframe(df)

# Transponer el dataframe
df_transposed = transpose_dataframe(df_limpio)

# Extraer colecciones de datos
data_collections = extract_data_collections(df_transposed)

# Mostrar el dataset limpio y transpuesto
print("Dataset limpio y transpuesto:")
print(df_transposed.head())

# Mostrar las colecciones de datos
print("Colecciones de datos extraídas:")
for key, value in data_collections.items():
    print(f"{key}: {value}")

# %%
# Función para guardar datos concatenados en un fichero de texto
def save_concatenated_data(data_collections, file_path):
    with open(file_path, 'w') as file:
        for key, value in data_collections.items():
            concatenated_data = ' '.join(map(str, value))
            file.write(concatenated_data + '\n')

data_collections = extract_data_collections(df_transposed)

# Guardar los datos concatenados en un fichero de texto
save_concatenated_data(data_collections, 'lista.txt')

# Mostrar mensaje de confirmación
print("Datos concatenados guardados en 'lista.txt'")

# %%
# Función para calcular operaciones estadísticas
def calculate_statistics(df):
    statistics = {}
    for column in df.columns:
        if pd.api.types.is_numeric_dtype(df[column]):
            mean = df[column].mean()
            variance = df[column].var()
            mode_result = stats.mode(df[column])
            mode = mode_result.mode[0] if len(mode_result.mode) > 0 else None
            statistics[column] = {
                'mean': mean,
                'variance': variance,
                'mode': mode
            }
    return statistics

# Ejemplo de uso
df_limpio = clean_dataframe(df)

# Calcular estadísticas
statistics = calculate_statistics(df_limpio)

# Preparar datos para tabulate
statistics_table = [(col, column_stats['mean'], column_stats['variance'], column_stats['mode']) for col, column_stats in statistics.items()]

# Mostrar estadísticas
print("Estadísticas calculadas:")
print(tabulate(statistics_table, headers=["Columna", "Media", "Varianza", "Moda"], tablefmt="grid"))

# %%
class DataRow:
    def __init__(self, **kwargs):
        for key, value in kwargs.items():
            setattr(self, key, value)
    
    def __str__(self):
        attributes = [[key, value] for key, value in self.__dict__.items()]
        return tabulate(attributes, headers=["Atributo", "Valor"], tablefmt="grid")
    
    def __eq__(self, other):
        return getattr(self, list(self.__dict__.keys())[1]) == getattr(other, list(other.__dict__.keys())[1])
    
    def __lt__(self, other):
        return getattr(self, list(self.__dict__.keys())[1]) < getattr(other, list(other.__dict__.keys())[1])
    
    def __add__(self, other):
        result = {}
        for key in self.__dict__.keys():
            result[key] = getattr(self, key) + getattr(other, key)
        return DataRow(**result)
    
    def __sub__(self, other):
        result = {}
        for key in self.__dict__.keys():
            result[key] = getattr(self, key) - getattr(other, key)
        return DataRow(**result)
    
    def modify_attribute(self, attribute, value):
        if attribute in self.__dict__:
            setattr(self, attribute, value)
        else:
            raise AttributeError(f"{attribute} no es un atributo válido.")


df_limpio = clean_dataframe(df)

# Crear objetos de la clase para al menos cinco filas
rows = [DataRow(**row) for _, row in df_limpio.head(5).iterrows()]

# Probar los métodos
print("Objetos creados:")
for row in rows:
    print(row)
    print()

# Modificar un atributo
rows[0].modify_attribute(list(rows[0].__dict__.keys())[0], 999)
print("Después de modificar un atributo:")
print(rows[0])
print()

# Comparar objetos
print("Comparación de objetos:")
print(rows[0] == rows[1])
print(rows[0] < rows[1])
print()

# Sumar y restar objetos
print("Suma de objetos:")
print(rows[0] + rows[1])
print()

print("Resta de objetos:")
print(rows[0] - rows[1])
print()

