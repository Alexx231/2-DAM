# data_statistics.py

def calculate_statistics(df):
    statistics = {}
    for column in df.columns:
        if pd.api.types.is_numeric_dtype(df[column]):
            mean = df[column].mean()
            variance = df[column].var()
            mode_result = stats.mode(df[column])
            mode = mode_result.mode[0] if len(mode_result.mode) > 0 else None
            statistics[column] = {
                'mean': mean,
                'variance': variance,
                'mode': mode
            }
    return statistics