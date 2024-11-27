# o matrices asociativas
# colecciones clave:valor -> primer valor (key), segundo (value)
# pares k_v separados por : parejas separados por , y todo {}

dic1 = {'piso1':'Juan', 'piso2':'Ana', 'piso3':'Luis'}
print(dic1['piso1']) # Juan

# no repite ninguna clave
# key -> es inmutable
# Key -> ok numeros, booleans, tuplas o cadenas -> NO lista
# valor -> num, cadena, lista o tupla

#añadir items
dic1['piso4'] = 'Maria'
print(dic1) # {'piso1': 'Juan', 'piso2': 'Ana', 'piso3': 'Luis', 'piso4': 'Maria'}

# se accede a los valores por su clave
print(dic1['piso4']) # -> Maria

# acceso a todas las claves: método keys()
print(dic1.keys()) # dict_keys(['piso1', 'piso2', 'piso3', 'piso4'])

# acceso a todos los valores : método values()
print(dic1.values()) # dict_values(['Juan', 'Ana', 'Luis', 'Maria'])

# acceso a pares: métodos items()
print(dic1.items()) # dict_items([('piso1', 'Juan'), ('piso2', 'Ana'), ('piso3', 'Luis'), ('piso4', 'Maria')])

print(type(dic1)) # -> <class 'dict'>





