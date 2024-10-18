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
    
    def vender_producto(nombre, cantidad):
        if nombre in inventario:
            if inventario[nombre]["Cantidad"] >= cantidad :
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

    def reposicion_diccionario():
        with open("inventario_actualizado.json", "r") as fichero:
            return json.load(fichero)

    def ver_inventario():
        with open("inventario.txt", "w") as fichero:
            for nombre, detalles in inventario.items():
                if detalles["Cantidad"] > 0:
                    fichero.write(
                        f"{nombre}, {detalles['Precio']}, {detalles['Cantidad']}\n"
                    )
                    for linea in inventario.items():
                        print(linea)
                else:
                    print(f"El producto {nombre} no tiene existencias")
                    
        print("-------------------")
        
            
    def menu():        
        while True:
            print("\n---MENU---\n")
            print("1.Ver Tienda \n")
            print("2.Hacer una venta \n")
            print("3.Sobreescribir fichero \n")
            print("4.Reposicion fichero \n")
            print("5.Salir\n")
            opcion = input("Selecciona la opcion: ")
                
            if opcion == "1":
                ver_inventario()
                
            elif opcion == "2":
                producto_a_vender = input("Introduce el nombre del producto a vender: ")
                cantidad_a_vender = int(input("Introduce la cantidad a vender: "))
                vender_producto(producto_a_vender, cantidad_a_vender)
            
            elif opcion == "3":
                actualizar_diccionario()
                
            elif opcion == "4":
                reposicion_diccionario()
                print("Se ha sobreescrito la lista")
            
            elif opcion == "5":
                print("Saliendo del programa...")
                break
            
            else:
                print("Opción no válida. Por favor, selecciona una opción del 1 al 4.")
    
    menu()
    
except:
    print("El sistema fallo")
    
    