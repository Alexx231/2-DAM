import pandas as pd
import requests
from scipy import stats

# URL del archivo Excel del INE
url = 'https://www.ine.es/jaxiT3/files/t/es/xlsx/60272.xlsx?nocab=1'

# Descargar el archivo Excel
response = requests.get(url)
with open('datos_ine.xlsx', 'wb') as file:
    file.write(response.content)

# Cargar el archivo Excel en una variable
excel_data = pd.ExcelFile('datos_ine.xlsx')

# Identificar la hoja de datos (asumimos que es la primera hoja)
sheet_name = excel_data.sheet_names[0]

# Leer la hoja de datos
df = pd.read_excel(excel_data, sheet_name=sheet_name)

# Mostrar las primeras filas para identificar la fila y columna de los nombres
print(df.head())

# Asumimos que los nombres están en la primera fila y las columnas son correctas
# Eliminar la primera fila
df = df.iloc[1:]

# Renombrar las columnas con los nombres correctos
df.columns = df.iloc[0]
df = df[1:]

# Mostrar el dataset limpio
print(df.head())

# Función para reemplazar valores NaN por 0
def replace_nan_with_zero(dataframe):
    return dataframe.fillna(0)

# Función para limpiar datos incorrectos
def clean_data(dataframe):
    def clean_element(element):
        if isinstance(element, str):
            try:
                element = float(element)
            except ValueError:
                element = 0
        if isinstance(element, (int, float)) and element < 0:
            element = 0
        return element
    
    return dataframe.applymap(clean_element)

# Aplicar las funciones de limpieza
df = replace_nan_with_zero(df)
df = clean_data(df)

# Mostrar el dataset limpio
print(df.head())

# Guardar el dataset limpio en una variable
dataset = df

""" Parte reflexiva
Un método alternativo para limpiar datos en Python es utilizar la librería datacleaner, que proporciona funciones predefinidas para la limpieza de datos. Sin embargo, he elegido utilizar pandas porque es una librería más versátil y ampliamente utilizada en la comunidad de ciencia de datos. Además, pandas permite un control más granular sobre el proceso de limpieza, lo que es útil para personalizar la limpieza según las necesidades específicas del dataset. """

# Función para transponer el DataFrame
def transpose_dataframe(dataframe):
    return dataframe.transpose()

# Transponer el DataFrame
transposed_df = transpose_dataframe(df)

# Mostrar el DataFrame transpuesto
print(transposed_df.head())

# Extraer una colección de datos de cada tipo utilizando una fila distinta
# Asumimos que las filas representan diferentes tipos de datos
collection = {}
for index, row in transposed_df.iterrows():
    collection[index] = row.tolist()

# Mostrar la colección de datos
for key, value in collection.items():
    print(f"{key}: {value}")

# Concatenar todos los datos de las listas, separándolos con espacios
concatenated_data = ' '.join([' '.join(map(str, value)) for value in collection.values()])

# Guardar los datos concatenados en un fichero de texto llamado "lista.txt"
with open('lista.txt', 'w') as file:
    file.write(concatenated_data)

# Opcional: Guardar el DataFrame transpuesto en un nuevo archivo Excel
transposed_df.to_excel('datos_ine_transpuesto.xlsx', index=False)

# Función para calcular la media, varianza y moda de cada columna
def calculate_statistics(dataframe):
    statistics = {}
    for column in dataframe.columns:
        if dataframe[column].dtypes in ['int64', 'float64']:
            mean = dataframe[column].mean()
            variance = dataframe[column].var()
            mode = dataframe[column].mode()[0] if not dataframe[column].mode().empty else None
            statistics[column] = {
                'mean': mean,
                'variance': variance,
                'mode': mode
            }
    return statistics

# Calcular las estadísticas del dataset
stats = calculate_statistics(df)

# Mostrar las estadísticas calculadas
for column, stat in stats.items():
    print(f"Columna: {column}")
    print(f"  Media: {stat['mean']}")
    print(f"  Varianza: {stat['variance']}")
    print(f"  Moda: {stat['mode']}")

# Clase DataRow
class DataRow:
    def __init__(self, **kwargs):
        for key, value in kwargs.items():
            setattr(self, key, value)
    
    def __str__(self):
        return ', '.join(f"{key}: {value}" for key, value in self.__dict__.items())
    
    def modify_attribute(self, attr, value):
        if hasattr(self, attr):
            setattr(self, attr, value)
    
    def __eq__(self, other):
        return getattr(self, list(self.__dict__.keys())[1]) == getattr(other, list(other.__dict__.keys())[1])
    
    def __lt__(self, other):
        return getattr(self, list(self.__dict__.keys())[1]) < getattr(other, list(other.__dict__.keys())[1])
    
    def __gt__(self, other):
        return getattr(self, list(self.__dict__.keys())[1]) > getattr(other, list(other.__dict__.keys())[1])
    
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

# Crear objetos de la clase para al menos cinco filas
rows = [DataRow(**row) for _, row in df.head(5).iterrows()]

# Probar los métodos
for row in rows:
    print(row)

# Modificar un atributo
rows[0].modify_attribute(list(rows[0].__dict__.keys())[1], 999)
print("Después de modificar un atributo:")
print(rows[0])

# Comparar objetos
print("Comparación de objetos:")
print(rows[0] == rows[1])
print(rows[0] < rows[1])
print(rows[0] > rows[1])

# Sumar y restar objetos
print("Suma de objetos:")
print(rows[0] + rows[1])
print("Resta de objetos:")
print(rows[0] - rows[1])

# Parte reflexiva
"""
La Programación Orientada a Objetos (POO) puede ayudar a organizar y manipular datos en aplicaciones empresariales al proporcionar una estructura clara y modular para el código. La POO permite encapsular datos y comportamientos relacionados en clases, lo que facilita la reutilización y el mantenimiento del código. Además, la POO permite la creación de objetos que pueden interactuar entre sí, lo que es útil para modelar sistemas complejos.

Por ejemplo, en una aplicación de gestión de inventarios, se pueden crear clases para representar productos, órdenes y clientes. Cada clase puede tener atributos y métodos específicos, lo que facilita la manipulación de los datos y la implementación de funcionalidades específicas.

En conclusión, la POO proporciona una forma estructurada y modular de organizar el código, lo que facilita la gestión y el mantenimiento de aplicaciones empresariales complejas.
"""