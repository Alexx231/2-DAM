# data_transformation.py

# Function to transpose the dataframe
def transpose_dataframe(dataframe):
    return dataframe.transpose()

# Function to extract data collections using a different row for each type
def extract_data_collections(df):
    collections = {}
    for index, row in df.iterrows():
        collections[index] = row.tolist()
    return collections