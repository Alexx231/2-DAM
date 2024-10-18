import pandas as pd
import numpy as np
from scipy import stats

# Descarga y creación del dataset
url = 'URL_DEL_ARCHIVO_EXCEL'
df = pd.read_excel(url)

# Identificar y eliminar filas de nombres
df.columns = df.iloc[0]
df = df[1:]

# Limpieza de datos
def limpiar_datos(df):
    df = df.replace(np.nan, 0)
    df = df.applymap(lambda x: 0 if isinstance(x, str) and not x.isdigit() else x)
    df = df.applymap(lambda x: abs(x) if isinstance(x, (int, float)) and x < 0 else x)
    return df

df = limpiar_datos(df)

# Transformación de los datos
def transponer_datos(df):
    return df.transpose()

df_transpuesto = transponer_datos(df)

# Guardar datos concatenados
with open('lista.txt', 'w') as f:
    for row in df_transpuesto.itertuples(index=False):
        f.write(' '.join(map(str, row)) + '\n')

# Operaciones estadísticas
def calcular_estadisticas(df):
    estadisticas = {}
    for col in df.columns:
        media = df[col].mean()
        varianza = df[col].var()
        moda = stats.mode(df[col])[0][0]
        estadisticas[col] = {'media': media, 'varianza': varianza, 'moda': moda}
    return estadisticas

estadisticas = calcular_estadisticas(df)

# Introducción a la POO
class Dataset:
    def __init__(self, **kwargs):
        self.__dict__.update(kwargs)

    def __str__(self):
        return str(self.__dict__)

    def modificar_atributo(self, key, value):
        if key in self.__dict__:
            self.__dict__[key] = value

    def __eq__(self, other):
        return self.__dict__.get('columna2') == other.__dict__.get('columna2')

    def __add__(self, other):
        return {key: self.__dict__[key] + other.__dict__[key] for key in self.__dict__}

    def __sub__(self, other):
        return {key: self.__dict__[key] - other.__dict__[key] for key in self.__dict__}

# Crear objetos y probar métodos
filas = [Dataset(**row) for row in df.head(5).to_dict(orient='records')]
for fila in filas:
    print(fila)
    fila.modificar_atributo('columna1', 100)
    print(fila)

# Reflexión sobre POO
# La POO permite organizar y manipular datos de manera estructurada, facilitando la reutilización y mantenimiento del código.