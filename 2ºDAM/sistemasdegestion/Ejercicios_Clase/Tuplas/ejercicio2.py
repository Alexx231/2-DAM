# Actividad 1

mi_tupla = ("lunes","martes","miercoles","jueves","viernes","sabado","domingo")

print(mi_tupla[2])

# Actividad 3

mi_lista = list(mi_tupla)

mi_lista.append("dianuevo")

mi_lista_nueva = ("domingondo","juernes")

mi_lista.extend(mi_lista_nueva)

mi_tupla = tuple(mi_lista)

print(mi_tupla)

