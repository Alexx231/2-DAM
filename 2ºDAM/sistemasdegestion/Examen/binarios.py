# usamos 'b' como segundo parámetro para manejar el archivo en modo binario
#esto general- para manejar archivos de imagen
# tratar ficheros binarios con módulo 'pickle' con un alias
import pickle as pk
import os

print(os.getcwd())# - C:\Users\ernes\Desktop\VSCode_python\campus_fp\UD\ud5\estudio

listaColores = ["- azul","- rojo","- verde"]
# usamos 'wb' para manejar el archivo en modo binario
fich3 = open("bicfp1.pckl","wb")


#para escribir, método .dump del módulo pickle
pk.dump(listaColores,fich3)

# y cierre del fichero
fich3.close()

# open ahora con método 'rb'
fich3 = open("bicfp1.pckl","rb")
# leemos el archivo con el método pk.load
leerListaCol = pk.load(fich3)

print(leerListaCol) # -> ['- azul', '- rojo', '- verde']

fich3.close()