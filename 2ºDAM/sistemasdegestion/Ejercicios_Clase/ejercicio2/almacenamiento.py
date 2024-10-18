import json


productos = {
    "Laptop": 1200,
    "Teclado": 45,
    "Ratón": 25,
    "Monitor": 300,
    "Impresora": 150
}


with open("productos.json", "w") as fichero:
    json.dump(productos, fichero)
# Paso 2: Leer desde JSON y calcular total
total_inventario = 0
with open("productos.json", "r") as fichero:
    datos = json.load(fichero)
    for producto, precio in datos.items():
        total_inventario += precio
        if precio > 100:
            print(f"Producto: {producto}, Precio: {precio}")
print(f"Valor total del inventario: {total_inventario}")