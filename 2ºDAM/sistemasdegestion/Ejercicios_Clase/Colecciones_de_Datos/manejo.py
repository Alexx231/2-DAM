#Problema 1

lista = ["platano", "manzana", "Melon", "lechuga", "carne", "salsa"]

print("Lista antes del append: " ,lista)

print("---------------------")

lista.append(input("Introduce un nuevo producto: "))

print("---------------------")

print("Lista despues del append: " ,lista)

print("---------------------")

#Problema 2

lista.sort()

print("Lista ordenada alfabeticamente: " ,lista)

print("---------------------")

#Problema 3

del lista[1]

print("Lista con segundo producto borrado: " ,lista)

print("---------------------")

#Problema 4

nuevo_producto = input("Añade un nuevo producto: ")

lista.insert(1,nuevo_producto)

print("Lista con el producto insertado en el segundo lugar: " ,lista)

print("---------------------")

#Problema 5

lista_m = [producto for producto in lista if producto.startswith('m') or producto.startswith('M')]

print("Lista con productos con m: " ,lista_m)

print("---------------------")