# mi-proyecto/scripts/file_operations.py
def save_concatenated_data(data_collections, file_path):
    with open(file_path, 'w') as file:
        for key, value in data_collections.items():
            concatenated_data = ' '.join(map(str, value))
            file.write(concatenated_data + '\n')