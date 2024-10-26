import pandas as pd
from scipy import stats

# Función para calcular estadísticas (media, varianza, moda) de cada columna del DataFrame
def calcular_estadisticas(df):
    estadisticas = {}
    for column in df.columns:
        if pd.api.types.is_numeric_dtype(df[column]):
            media = df[column].mean()
            varianza = df[column].var()
            moda = stats.mode(df[column])[0][0]
            estadisticas[column] = {'media': media, 'varianza': varianza, 'moda': moda}
    return estadisticas