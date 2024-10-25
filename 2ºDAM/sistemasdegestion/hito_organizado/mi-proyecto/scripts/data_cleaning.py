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

# Example usage
df = pd.read_excel('../data/datos_ine.xlsx')
df_cleaned = clean_dataframe(df)
df_cleaned = replace_nan_with_zero(df_cleaned)
df_cleaned = clean_data(df_cleaned)

# Display the cleaned dataset
print(df_cleaned.head())