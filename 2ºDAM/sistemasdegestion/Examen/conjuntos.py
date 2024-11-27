# estructura de datos para agrupar colección de datos sin orden (índices ni claves)

hierbas = {'cilandro','perejil','hierbabuena'}
print(hierbas) # -> {'perejil', 'hierbabuena', 'cilandro'}
especias = {'cilandro','perejil','pimienta'}
print(especias) # -> {'pimienta', 'perejil', 'cilandro'}

#operaciones entre conjuntos
# unión -> combina todos los elementos sin duplicidades
print(hierbas | especias) # -> {'perejil', 'pimienta', 'hierbabuena', 'cilandro'}
despensa = hierbas | especias
print(despensa) # -> {'pimienta', 'perejil', 'hierbabuena', 'cilandro'}

# intersección -> muestra los elementos que se encuentran en ambos conjuntos
print(hierbas & especias) # -> {'perejil', 'cilandro'}
stock = hierbas & especias
print(stock) # -> {'perejil', 'cilandro'}

# diferencia -> muestra los elementos que se encuentran en un conjunto pero no en el otro
print(hierbas - especias) # -> {'hierbabuena'}
compra = hierbas - especias
print(compra) # -> {'hierbabuena'}

# diferencia simétrica -> muestra los elementos que se encuentran en un conjunto
# # o en el otro, pero no en ambos
print(hierbas ^ especias) # -> {'hierbabuena', 'pimienta'}
compra1 = hierbas ^ especias
print(compra1) # -> {'hierbabuena', 'pimienta'}


# operador in: comprobar si un valor específico está en el conjunto
print('perejil' in hierbas) # -> True
print('atun' in especias) # -> False

# operador not in: comprobar si un valor específico no está en el conjunto
esta = 'atun' in especias
if esta:
    print("Ojo!; hay atún en el armario de especias")
else:
    print("ok!; no hay atún en el armario de especias")

print(type(esta)) # -> <class 'bool'>
print(type(especias)) # - <class 'set'>




