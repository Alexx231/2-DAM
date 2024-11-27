# tipos de datos para almacenar colecciones de datos o sucesión de elementos
# una cadena es una secuencia de caracteres
# Listas, Tuplas, Diccionarios, Conjuntos
# listas
# secuencia de datos ordenada (array o vector)
# listas son mutables, es decir, son accesibles y se pueden modificar, suprim o añadir después de su creación
# pueden contener cualquier tipo de datos, inc listas y de tipos distintos entre sí

a = list('hola')
print(type(a)) # <class 'list'>
print(a) # ['h', 'o', 'l', 'a']
print(a[0]) # -> h
print(a[-1]) # -> a
print(a[1:3]) # ['o', 'l'] inicio : fin-1 -> 1:2
b = list('alejandro')
#inicio:fin(-1):secuenca(cada 2) -> (1:8:2)
print(b[1:8:2]) # ['l', 'j', 'n', 'r']
# desde posición hasta fin
print(b[3:]) # -> ['j', 'a', 'n', 'd', 'r', 'o']
# desde inicio hasta posición (-1=)
print(b[:5]) # ['a', 'l', 'e', 'j', 'a']

# concatenar listas
c = list('abc')
d = list('def')
e = list(c + d)
print(e) # ['a', 'b', 'c', 'd', 'e', 'f']

f = list(e * 2)
print(f) # ['a', 'b', 'c', 'd', 'e', 'f', 'a', 'b', 'c', 'd', 'e', 'f']

print(len(f)) # -> 12
# lista de listas (matriz)

x = [['a','b','c']+['d','e','f']]
print(x[0][0]) # --> a

x1 =[['g','h','i'],[1,2,3]]
print(x1[1][2]) # -> 3

c.append('d')
print(c) # -> ['a', 'b', 'c', 'd']
# insertar un elemento en una posición específica
c.insert(4,'a')# en posición 4, el item 'a'
print(c) # ['a', 'b', 'c', 'd', 'a']
print(c.count('a'))# ->2 cuenta las ocurrencias del item indicado
# eliminar un elemento
c.remove('a') # elimina la primera ocurrencia del item indicado
print(c) # ['b', 'c', 'd', 'a']
# eliminar un elemento en una posición específica
c.pop(2) # elimina el item en la posición 2
print(c) # ['b', 'c', 'a']
# ordena la lista
c.sort()
print(c) # ['a', 'b', 'c']

# otra lista heterogénea
x2 = ['uno', 2, False]
print(x2) # ->['uno', 2, False]