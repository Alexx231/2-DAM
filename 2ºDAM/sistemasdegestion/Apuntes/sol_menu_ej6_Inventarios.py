# UD6_Ejercicio: Gestión de inventario y facturación

import json

## Paso 1: crear el fichero de inventario

with open("inventario.txt", "w") as fichero:
    productos = ["manzanas, 1.5, 10", "naranjas, 2.0, 5", "plátanos, 1.2, 20", "peras, 2.5, 0"]
    for producto in productos:
        fichero.write(producto + "\n")
print("Fichero 'inventario.txt' creado con éxito")

## Paso 2: lee el fichero de texto e importa los datos a un diccionario; genera un aviso si hay productos sin stock

inventario = {}
with open("inventario.txt", "r") as fichero:
    for linea in fichero:
        nombre, precio, cantidad = linea.strip().split(",")
        inventario[nombre] = {"precio": float(precio), "cantidad": int(cantidad)}
print("Inventario cargado desde 'inventario.txt': ")
print(inventario, "\n")

## Verifica si el diccionario incluye productos sin existencias
for producto, detalles in inventario.items():
    if detalles["cantidad"] == 0:
        print(f"¡AVISO!: El producto {producto} no tiene existencias y necesita reposición.")

## Paso 3: Menú de opciones
while True:
    print("\nMenú de opciones:")
    print("1. Ver inventario")
    print("2. Ver productos fuera de stock")
    print("3. Iniciar proceso de venta")
    print("4. Salir")

    opcion = input("Selecciona una opción (1-4): ")

    if opcion == "1":
        # Ver inventario
        print("\nProductos disponibles: ")
        for producto, detalles in inventario.items():
            print(f"{producto}: ${detalles['precio']} (Cantidad: {detalles['cantidad']})")

    elif opcion == "2":
        # Ver productos fuera de stock
        print("\nProductos fuera de stock: ")
        for producto, detalles in inventario.items():
            if detalles["cantidad"] == 0:
                print(f"{producto} no tiene existencias.")

    elif opcion == "3":
        # Iniciar proceso de venta
        while True:
            print("\nProductos disponibles para la venta:")
            for producto, detalles in inventario.items():
                print(f"{producto}: ${detalles['precio']} (Cantidad: {detalles['cantidad']})")

            producto_vendido = input("\nIntroduce el nombre del producto que quieres comprar (o escribe 'salir' para volver al menú): ").lower()

            if producto_vendido == "salir":
                break

            if producto_vendido in inventario and inventario[producto_vendido]["cantidad"] > 0:
                cantidad_vendida = int(input(f"Introduce la cantidad de {producto_vendido} a comprar: "))

                if cantidad_vendida <= inventario[producto_vendido]["cantidad"]:
                    inventario[producto_vendido]["cantidad"] -= cantidad_vendida
                    print(f"Venta realizada: {cantidad_vendida} {producto_vendido}")
                    if inventario[producto_vendido]["cantidad"] == 0:
                        print(f"¡AVISO!: El producto {producto_vendido} se ha agotado y necesita reposición.")
                else:
                    print("Lo sentimos. No hay suficientes existencias de producto.")
            else:
                print("Producto no disponible o stock insuficiente.")

    elif opcion == "4":
        print("Saliendo de la aplicación.")
        break

    else:
        print("Opción no válida. Por favor, selecciona una opción entre 1 y 4.")

## Paso 4: Guarda el inventario actualizado en un fichero de tipo JSON

with open("Inventario_actualizado.json", "w") as fichero_json:
    json.dump(inventario, fichero_json)

print("\nInventario actualizado guardado con éxito en 'inventario_actualizado.json'.")
