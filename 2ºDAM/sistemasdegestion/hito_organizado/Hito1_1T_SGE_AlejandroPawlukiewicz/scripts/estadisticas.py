

import pandas as pd
import numpy as np
from scipy import stats

def calcular_estadisticas(df):
    estadisticas = {}
    for column in df.columns:
        if df[column].dtype in ['int64', 'float64']:
            media = df[column].mean()
            varianza = df[column].var()
            moda = stats.mode(df[column])[0][0]
            estadisticas[column] = {'media': media, 'varianza': varianza, 'moda': moda}
    return estadisticas