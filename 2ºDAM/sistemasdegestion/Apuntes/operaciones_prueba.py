#Crear Lista
lista = ["desarrollo, aplicaciones, multiplataforma"]

#Buscar el valor en la lista 
resultado = any("a" in elemento for elemento in lista)

#Comprobar
print(resultado)

#2 Ejercicio

# Crear dos listas con los mismos elementos
lista1 = [1, 2, 3]
lista2 = [1, 2, 3]

# Comparar si lista1 y lista2 son el mismo objeto en memoria
print(lista1 is lista2)  # False

# Comparar si lista1 y lista2 tienen el mismo valor
print(lista1 == lista2)  # True

# Asignar lista1 a lista3
lista3 = lista1

# Comparar si lista1 y lista3 son el mismo objeto en memoria
print(lista1 is lista3)  # True

# Comparar si lista1 y lista3 tienen el mismo valor
print(lista1 == lista3)  # True