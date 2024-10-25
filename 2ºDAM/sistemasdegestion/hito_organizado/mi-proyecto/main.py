# mi-proyecto/main.py
import os
import pandas as pd
from scripts.data_extraction import download_excel_file, load_excel_file, read_excel_sheet
from scripts.data_cleaning import clean_dataframe, replace_nan_with_zero, clean_data, clean_incorrect_data
from scripts.data_transformation import transpose_dataframe, extract_data_collections
from scripts.file_operations import save_concatenated_data
from scripts.data_statistics import calculate_statistics
from scripts.data_class import DataRow

# Crear la carpeta 'data' si no existe
output_dir = 'data'
os.makedirs(output_dir, exist_ok=True)

# Descargar el archivo Excel
url = 'https://www.ine.es/jaxiT3/files/t/es/xlsx/60272.xlsx?nocab=1'
output_path = os.path.join(output_dir, 'datos_ine.xlsx')
download_excel_file(url, output_path)

# Cargar el archivo Excel
excel_data = load_excel_file(output_path)

# Leer la hoja de datos
sheet_name = excel_data.sheet_names[0]
df = read_excel_sheet(excel_data, sheet_name)

# Guardar el DataFrame original
df.to_excel(os.path.join(output_dir, 'datos_ine_original.xlsx'), index=False)

# Limpiar el DataFrame
df_cleaned = clean_dataframe(df)
df_cleaned.to_excel(os.path.join(output_dir, 'datos_ine_cleaned.xlsx'), index=False)

# Reemplazar NaN por 0
df_no_nan = replace_nan_with_zero(df_cleaned)
df_no_nan.to_excel(os.path.join(output_dir, 'datos_ine_no_nan.xlsx'), index=False)

# Limpiar datos incorrectos
df_corrected = clean_incorrect_data(df_no_nan)
df_corrected.to_excel(os.path.join(output_dir, 'datos_ine_corrected.xlsx'), index=False)

# Transformar los datos
df_transposed = transpose_dataframe(df_corrected)
df_transposed.to_excel(os.path.join(output_dir, 'datos_ine_transposed.xlsx'), index=False)

# Extraer colecciones de datos
data_collections = extract_data_collections(df_transposed)

# Guardar datos concatenados en un archivo de texto
save_concatenated_data(data_collections, os.path.join(output_dir, 'lista.txt'))

# Calcular estadísticas
statistics = calculate_statistics(df_corrected)

# Guardar estadísticas en lista.txt
with open(os.path.join(output_dir, 'lista.txt'), 'a') as file:
    file.write("\nEstadísticas:\n")
    for column, stats in statistics.items():
        file.write(f"{column} - Media: {stats['mean']}, Varianza: {stats['variance']}, Moda: {stats['mode']}\n")

# Crear objetos de la clase para al menos cinco filas
data_objects = [DataRow(**row) for _, row in df_corrected.head(5).iterrows()]

# Probar todos los métodos
for obj in data_objects:
    print(obj)

# Modificar un atributo
data_objects[0].modify_attribute('some_column', 100)

# Comparar objetos
print(data_objects[0] == data_objects[1])

# Sumar y restar objetos
print(data_objects[0] + data_objects[1])
print(data_objects[0] - data_objects[1])

# Mostrar la tabla transformada en la consola de manera visual e intuitiva
print("\nTabla transformada:")
print(df_transposed.to_string(index=False))

# Ejecución principal del programa
if __name__ == "__main__":
    # Añade tu código aquí para ejecutar la funcionalidad deseada
    pass