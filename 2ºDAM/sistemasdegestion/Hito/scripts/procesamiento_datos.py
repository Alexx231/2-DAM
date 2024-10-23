import pandas as pd
import requests

def download_excel_file(url, output_path):
    response = requests.get(url)
    with open(output_path, 'wb') as file:
        file.write(response.content)

def load_excel_file(file_path):
    return pd.ExcelFile(file_path)

def read_excel_sheet(excel_data, sheet_name):
    return pd.read_excel(excel_data, sheet_name=sheet_name)

def clean_dataframe(df):
    df = df.iloc[1:]
    df.columns = df.iloc[0]
    df = df[1:]
    return df

def replace_nan_with_zero(dataframe):
    return dataframe.fillna(0)

def clean_data(dataframe):
    def clean_element(element):
        if isinstance(element, str):
            try:
                element = float(element)
            except ValueError:
                element = 0
        return element

    return dataframe.applymap(clean_element)

def remove_empty_rows_and_columns(dataframe):
    dataframe = dataframe.dropna(how='all')  # Eliminar filas completamente vacías
    dataframe = dataframe.dropna(axis=1, how='all')  # Eliminar columnas completamente vacías
    return dataframe

def transpose_dataframe(dataframe):
    return dataframe.transpose()