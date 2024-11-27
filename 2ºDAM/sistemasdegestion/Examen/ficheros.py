# ficheros de texto
# secuencia de bits almacenados en disp físico 
# funcion open -> devuelve una dirección de mem en la que está el archivo (puntero) (no el prop archivo ni los datos)
# sintaxis: open("nombreDelFichero.txt", "modo")
# ya que la funct open() devuelve un puntero, debemos guardarlo en una variable 

# puedes usar este código antes de open para pintar la ruta actual del directorio de trabajo
import os
print(os.getcwd())

# usamos una ruta relativa, para lo que resulta crítico encontrarse donde creemos estar

fich1= open("datos/cfp1.txt", "xt") #--> ojo ya que está creado y daría error @@@@@@@@@@@@@@@@@@

#también es posible utilizar una ruta absoluta, donde: prefijo "r" para indicar es cadena sin caracteres de escape y barras invertidas "\""

fich2 = open(r"C:\Users\ernes\Desktop\VSCode_python\campus_fp\UD\ud5\estudio\cfp2.txt", "wt")#--> ojo ya que está creado y daría error @@@@@@@@@@@@@@@@@@
#cfp2.txt se crea en el directorio, no en la carpeta /datos -->el objetivo es poder usar abajo directamente el método de os.remove que trabaja sobre el directorio de trabajp

# ahora podemos guardar texto en estos ficheros

text1 = "texto fichero estudio cfp1"
text2 = "texto fichero estudio cfp2"

fich1.write(text1)
fich2.write(text2)

#cerramos los ficheros para evitar problemas de memoria

fich1.close()
fich2.close()

# podemos añadir texto a un fichero ya creado; lo abrimos con el modo "append", para texto 'at' o binario 'ab'

fich1 = open("datos/cfp1.txt", "at")

#text1at1 = "Nuevo texto a incluir tras primer cierre"
#fich1.write(text1at1)
#fich1.close()

#esto agrega el texto sin formato alguno. Si se necesita podemos usar: \n (salto línea), \t (tabulación), \n---\n (guiones), \', \"

text1at1 = "\n\tNuevo texto a incluir tras primer cierre"
fich1.write(text1at1)
fich1.close()

# con este rdo: 
# texto fichero estudio cfp1
#	    Nuevo texto a incluir tras primer cierre

# para eliminar un fich, uso del módulo "os" y su método "remove"
#como ya hice un import de os arriba, directamente

os.remove("cfp2.txt")
# esto es ok ya que cfp2 se creó arriba en el directorio de trabajo (el que podemos ver con os.getcwd())
#caso de haberlo creado arriba en /datos podemos usar
# 1# os.remove(r"C:\Users\ernes\Desktop\VSCode_python\campus_fp\UD\ud5\estudio\datos\cfp1.txt")

# 2# os.chdir(r"C:\Users\ernes\Desktop\VSCode_python\campus_fp\UD\ud5\estudio\datos")
#haciendo de este el nuevo dir de trabajo y ya poder usar sobre el os.remove
