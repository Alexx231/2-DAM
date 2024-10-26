# scripts/file_operations.py

def guardar_datos_concatenados(colecciones_datos, archivo_salida, encoding='utf-8'):
    with open(archivo_salida, 'w', encoding=encoding) as file:
        for coleccion in colecciones_datos:
            for dato in coleccion:
                file.write(f"{dato}\n")