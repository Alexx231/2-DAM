# mi-proyecto/scripts/data_transformation.py
import pandas as pd

def transpose_dataframe(df):
    return df.transpose()

def extract_data_collections(df):
    collections = {}
    for index, row in df.iterrows():
        collections[index] = row.tolist()
    return collections