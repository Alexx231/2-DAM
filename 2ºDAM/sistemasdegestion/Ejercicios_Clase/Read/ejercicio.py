# Paso 1: Escribir productos en el fichero
with open("productos.txt", "w") as fichero:
    productos = ["Laptop,1200", "Teclado,45", "Ratón,25",
"Monitor,300", "Impresora,150"]
    
    for producto in productos:
        fichero.write(producto + "\n")
    
# Paso 2: Leer el fichero y calcular el total
total = 0
with open("productos.txt", "r") as fichero:
    for linea in fichero:
        nombre, precio = linea.strip().split(",") # Separar nombre y precio

        total += int(precio) # Sumar el precio al total
        print(f"Producto: {nombre}, Precio: {precio}")
print(f"Total de productos: {total}")