# mi-proyecto/scripts/limpieza_datos.py
import pandas as pd

# Función para limpiar el DataFrame eliminando filas y columnas vacías
def limpiar_dataframe(df):
    # Eliminar filas y columnas vacías
    df.dropna(how='all', inplace=True)
    df.dropna(axis=1, how='all', inplace=True)
    return df

# Función para reemplazar valores NaN por 0
def reemplazar_nan_por_cero(df):
    return df.fillna(0)

# Función para limpiar datos incorrectos en el DataFrame
def limpiar_datos_incorrectos(df):
    for column in df.columns:
        if pd.api.types.is_numeric_dtype(df[column]):
            df[column] = pd.to_numeric(df[column], errors='coerce')
            df[column] = df[column].apply(lambda x: x if x >= 0 else 0)
        else:
            df[column] = df[column].astype(str)
    return df