# Tuplas agrupan elementos: inmutable
tuplaRGB = ('rojo','verde','azul')
#ojo la coma en tuplas de UN elemento
tuplaR = ('rojo',)
print(tuplaR) # -> ('rojo',)

# convertir lista a tupla
a = list('hola')
print(type(a)) # <class 'list'>
# convertir lista a tupla
a = tuple(a)
print(type(a)) # <class 'tuple'>

#tupla vacía
tplanum3 = ()
print(tplanum3) # -> ()

#anidar tuplas
tplanum1 = (1,2,3)
tplanum2 = (tplanum1,4,5,6) 
print(tplanum2) # -> ((1, 2, 3), 4, 5, 6)

#acceso a valores de tupla anidada
print(tplanum2[0]) # -> (1, 2, 3) es la tupla anidada
print(tplanum2[0][0]) # -> 1
print(tplanum2[0][1]) # -> 2
print(tplanum2[0][2]) # -> 3
print(tplanum2[1:4])# -> (4,5,6)
print(tplanum2[1]) # -> 4
print(tplanum2[2]) # -> 5
print(tplanum2[3]) # -> 6

