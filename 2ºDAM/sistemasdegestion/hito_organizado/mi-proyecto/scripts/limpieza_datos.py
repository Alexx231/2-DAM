# mi-proyecto/scripts/limpieza_datos.py
import pandas as pd

# Función para limpiar el DataFrame eliminando filas y columnas vacías
def limpiar_dataframe(df):
    df.dropna(how='all', inplace=True)
    df.dropna(axis=1, how='all', inplace=True)
    return df

# Función para reemplazar valores NaN por 0
def reemplazar_nan_por_cero(df):
    return df.fillna(0)

# Función para limpiar datos incorrectos en el DataFrame
def limpiar_datos_incorrectos(df):
    for column in df.columns:
        if df[column].dtype in ['int64', 'float64']:
            df[column] = df[column].clip(lower=0)  # Reemplaza valores negativos por 0
        else:
            df[column] = df[column].astype(str)
    return df


def eliminar_filas_invalidas(df):
    df = df[(df != 0).all(axis=1)]  # Eliminar filas con 0
    df = df.dropna()  # Eliminar filas con NaN
    df = df[df.applymap(lambda x: isinstance(x, (int, float)))]  # Eliminar filas con texto
    return df