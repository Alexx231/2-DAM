# mi-proyecto/scripts/data_cleaning.py
import pandas as pd

def clean_dataframe(df):
    # Eliminate empty rows and columns
    df.dropna(how='all', inplace=True)
    df.dropna(axis=1, how='all', inplace=True)
    return df

def replace_nan_with_zero(df):
    return df.fillna(0)

def clean_incorrect_data(df):
    for column in df.columns:
        if pd.api.types.is_numeric_dtype(df[column]):
            df[column] = pd.to_numeric(df[column], errors='coerce')
            df[column] = df[column].apply(lambda x: x if x >= 0 else 0)
        else:
            df[column] = df[column].astype(str)
    return df

def clean_data(df):
    df = clean_dataframe(df)
    df = replace_nan_with_zero(df)
    df = clean_incorrect_data(df)
    return df