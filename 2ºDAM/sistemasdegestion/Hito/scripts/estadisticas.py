import pandas as pd

def calculate_statistics(dataframe):
    statistics = {}
    for column in dataframe.columns:
        if pd.api.types.is_numeric_dtype(dataframe[column]):
            mean = dataframe[column].mean()
            variance = dataframe[column].var()
            mode = dataframe[column].mode()[0] if not dataframe[column].mode().empty else None
            statistics[column] = {
                'mean': mean,
                'variance': variance,
                'mode': mode
            }
    return statistics