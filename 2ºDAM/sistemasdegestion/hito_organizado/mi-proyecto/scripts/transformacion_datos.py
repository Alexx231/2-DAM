import pandas as pd

# Función para transponer el DataFrame (cambiar filas por columnas)
def transponer_dataframe(df):
    return df.transpose()

# Función para extraer colecciones de datos de cada tipo utilizando una fila distinta
def extraer_colecciones_datos(df):
    colecciones = {}
    for index, row in df.iterrows():
        colecciones[index] = row.tolist()
    return colecciones