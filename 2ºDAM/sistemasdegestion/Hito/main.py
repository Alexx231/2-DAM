from scripts.procesamiento_datos import (
    download_excel_file,
    load_excel_file,
    read_excel_sheet,
    clean_dataframe,
    replace_nan_with_zero,
    clean_data,
    remove_empty_rows_and_columns,
    transpose_dataframe
)
from scripts.estadisticas import calculate_statistics
from scripts.data_row import DataRow
from tabulate import tabulate

def main():
    url = 'https://www.ine.es/jaxiT3/files/t/es/xlsx/60272.xlsx?nocab=1'
    output_path = 'datos_ine.xlsx'

    # Descargar el archivo Excel
    download_excel_file(url, output_path)

    # Cargar el archivo Excel en una variable
    excel_data = load_excel_file(output_path)

    # Identificar la hoja de datos (asumimos que es la primera hoja)
    sheet_name = excel_data.sheet_names[0]

    # Leer la hoja de datos
    df = read_excel_sheet(excel_data, sheet_name)

    # Mostrar las primeras filas para identificar la fila y columna de los nombres
    print(df.head())

    # Limpiar el dataframe
    df = clean_dataframe(df)

    # Eliminar filas y columnas vacías
    df = remove_empty_rows_and_columns(df)

    # Mostrar el dataset limpio
    print(tabulate(df.head(), headers='keys', tablefmt='grid'))

    # Reemplazar valores NaN por 0
    df = replace_nan_with_zero(df)

    # Limpiar datos incorrectos
    df = clean_data(df)

    # Mostrar el dataset final
    print(tabulate(df.head(), headers='keys', tablefmt='grid'))

    # Transponer el dataframe
    df_transposed = transpose_dataframe(df)

    # Mostrar el dataset transpuesto
    print(tabulate(df_transposed.head(), headers='keys', tablefmt='grid'))

    # Extraer una colección de datos de cada tipo utilizando una fila distinta
    collection = df_transposed.iloc[0].tolist()
    print("Colección de datos de la primera fila transpuesta:", collection)

    # Calcular las estadísticas del dataset
    stats = calculate_statistics(df)

    # Mostrar las estadísticas calculadas
    for column, stat in stats.items():
        print(f"Columna: {column}")
        print(f"  Media: {stat['mean']}")
        print(f"  Varianza: {stat['variance']}")
        print(f"  Moda: {stat['mode']}")

    # Crear objetos de la clase para al menos cinco filas
    rows = [DataRow(**row) for _, row in df.head(5).iterrows()]

    # Probar los métodos
    for row in rows:
        print(row)

    # Modificar un atributo
    rows[0].modify_attribute(list(rows[0].__dict__.keys())[1], 999)
    print("Después de modificar un atributo:")
    print(rows[0])

    # Comparar objetos
    print("Comparación de objetos:")
    print(rows[0] == rows[1])
    print(rows[0] < rows[1])
    print(rows[0] > rows[1])

    # Sumar y restar objetos
    print("Suma de objetos:")
    print(rows[0] + rows[1])
    print("Resta de objetos:")
    print(rows[0] - rows[1])

    # Guardar datos concatenados en un fichero de texto
    with open('lista.txt', 'w') as f:
        for row in df.values:
            f.write(' '.join(map(str, row)) + '\n')

if __name__ == "__main__":
    main()