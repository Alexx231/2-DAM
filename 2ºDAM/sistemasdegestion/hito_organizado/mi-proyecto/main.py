from scripts.data_extraction import download_excel_file, load_excel_file, read_excel_sheet
from scripts.data_cleaning import clean_dataframe, replace_nan_with_zero, clean_data, clean_incorrect_data
from scripts.data_transformation import transpose_dataframe, extract_data_collections
from scripts.file_operations import save_concatenated_data
from scripts.data_statistics import calculate_statistics
from scripts.data_class import DataRow

# Download the Excel file
url = 'https://www.ine.es/jaxiT3/files/t/es/xlsx/60272.xlsx?nocab=1'
output_path = 'data/datos_ine.xlsx'
download_excel_file(url, output_path)

# Load the Excel file
excel_data = load_excel_file(output_path)

# Read the data sheet
sheet_name = excel_data.sheet_names[0]
df = read_excel_sheet(excel_data, sheet_name)

# Clean the data
df = clean_dataframe(df)
df = replace_nan_with_zero(df)
df = clean_incorrect_data(df)
df = clean_data(df)

# Transpose the data
df_transposed = transpose_dataframe(df)

# Extract data collections
data_collections = extract_data_collections(df_transposed)

# Save concatenated data to a text file
save_concatenated_data(data_collections, 'lista.txt')

# Calculate statistics
statistics = calculate_statistics(df)

# Create objects of the class for at least five rows
data_objects = [DataRow(**row) for _, row in df.head(5).iterrows()]

# Test all methods
for obj in data_objects:
    print(obj)

# Modify an attribute
data_objects[0].modify_attribute('some_column', 100)

# Compare objects
print(data_objects[0] == data_objects[1])

# Add and subtract objects
print(data_objects[0] + data_objects[1])
print(data_objects[0] - data_objects[1])

# Main program execution
if __name__ == "__main__":
    # Add your code here to execute the desired functionality
    pass