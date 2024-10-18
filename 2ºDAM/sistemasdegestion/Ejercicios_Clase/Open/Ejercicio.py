try:
    fichero = open("mi_fichero.txt", "r")
    lineas = fichero.readlines()
    print(lineas)
except FileNotFoundError:

    print("El fichero no existe. Creando uno nuevo...")
    fichero = open("mi_fichero.txt", "w")
    fichero.write("Este es un nuevo fichero.")
finally:
    fichero.close()