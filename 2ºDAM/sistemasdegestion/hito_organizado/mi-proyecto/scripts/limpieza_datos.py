

import pandas as pd
import numpy as np

def limpiar_dataframe(df):
    df.dropna(how='all', inplace=True)
    df.dropna(axis=1, how='all', inplace=True)
    return df

def reemplazar_nan_por_cero(df):
    return df.fillna(0)

def limpiar_datos_incorrectos(df):
    for column in df.columns:
        if df[column].dtype in ['int64', 'float64']:
            df[column] = df[column].clip(lower=0)  # Reemplaza valores negativos por 0
        else:
            df[column] = df[column].astype(str)
    return df
