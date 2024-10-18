import json

try:
    
    inventario = {
        "Melon": {"Precio": 1.5 , "Cantidad": 50},
        "Zumo": {"Precio": 0.9, "Cantidad": 20},
        "Carne": {"Precio": 1.9, "Cantidad": 65},
        "Verdura": {"Precio": 0.5, "Cantidad": 90},
        "Yogur": {"Precio": 2.0, "Cantidad": 0},
    }

    print("-------------------")
    
    with open("inventario.txt", "w") as fichero:
        for nombre, detalles in inventario.items():
            if detalles["Cantidad"] > 0:
                fichero.write(
                    f"{nombre}, {detalles['Precio']}, {detalles['Cantidad']}\n"
                )
            else:
                print(f"El producto {nombre} no tiene existencias")
                
    print("-------------------")
                
    def vender_producto(nombre, cantidad):
        if nombre in inventario:
            if inventario[nombre]["Cantidad"] >= cantidad:
                inventario[nombre]["Cantidad"] -= cantidad
                print(f"Se han vendido {cantidad} unidades de {nombre}.")
                actualizar_diccionario()
            else:
                print(f"No hay suficiente stock de {nombre}.")
        else:
            print(f"El producto {nombre} no existe en el inventario.")
            
            

    def actualizar_diccionario():
        with open("inventario_actualizado.json", "w") as fichero:
            json.dump(inventario, fichero)
        print("Inventario actualizado y guardado en inventario_actualizado.json")
    

# +++++++++++++++ PARA VENDER UN PRODUCTO +++++++++++++++

    producto_a_vender = "Carne"
    cantidad_a_vender = 20
    vender_producto(producto_a_vender, cantidad_a_vender)
    
    print("-------------------")
    
    
    with open("inventario_actualizado.json", "r") as fichero:
        inventario = json.load(fichero)

    for nombre, detalles in inventario.items():
        print(f"Producto: {nombre}")
        for precio, cantidad in detalles.items():
            print(f"{precio}: {cantidad}")
            
except:
    print("El sistema fallo")