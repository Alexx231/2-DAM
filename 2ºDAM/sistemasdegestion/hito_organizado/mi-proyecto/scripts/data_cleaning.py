import pandas as pd

def clean_dataframe(df):
    # Eliminate empty rows and columns
    df.dropna(how='all', inplace=True)
    df.dropna(axis=1, how='all', inplace=True)
    return df

def replace_nan_with_zero(df):
    return df.fillna(0)

def clean_data(df):
    # Add more cleaning rules if necessary
    return df

# mi-proyecto/scripts/data_cleaning.py
def clean_incorrect_data(df):
    for column in df.columns:
        if pd.api.types.is_numeric_dtype(df[column]):
            df[column] = pd.to_numeric(df[column], errors='coerce')
            df[column] = df[column].apply(lambda x: x if x >= 0 else 0)
        else:
            df[column] = df[column].astype(str)
    return df

# Example usage
df = pd.read_excel('../data/datos_ine.xlsx')
df_cleaned = clean_dataframe(df)
df_cleaned = replace_nan_with_zero(df_cleaned)
df_cleaned = clean_data(df_cleaned)

# Display the cleaned dataset
print(df_cleaned.head())