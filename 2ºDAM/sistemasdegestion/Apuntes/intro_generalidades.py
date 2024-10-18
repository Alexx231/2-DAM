num = 5
num1, num2 = 5, 7
cad = "hola"
dec = 5.2
bool= True
complex= 5 + 2j

print (cad + str(num)) #hola5; sin cast nos da, claro, error

#cadenas son ordenadas
print (cad[3]) # --> a

cad1 = "cadena"
print(cad1[3]) # --> e

#cadenas son inmutables, no puedes cambiar un único carácter
# no puedes cad1[0]= "K"

char = cad1[-1]
print('El último caracter de cad1 es:',char) # --> El último caracter de cad1 es: a

char1 = cad1[-2]
print('El penúltimo caracter de cad1 es:',char1) # --> El penúltimo ... es: n

char2 = cad1[1:4]
print(char2) # --> ade (es hasta 4-1)

char3 = cad1[:4]
print(char3) # cade (el límite es -1)

char4 =cad1[1:]
print('La sección desde la pos 1 hasta el final de la cadena es:', char4) # --> adena

name = "Alejandro"
edad = 27.
print(f"Mi nombre es {name} y tengo {edad} años") # --> f-strings Mi nombre es Alejandro y tengo 27 años

print("Mi nombre es {} y tengo {} años" .format(name, edad)) # puedo sustituir las {} por variables indicadas es el .format

edad1 = 27.25
print("Mi nombre es {} y tengo {e:1.1f} años" .format(name, e = edad1)) # y puedo dar formato a las variables, aquí 1 sólo decimal --> 27.2

# esto último igual para f-strings

# input
print("Introduce tu nombre")
tunombre = input()
print("Hola " + tunombre) # --> Hola <tu nombre>

print("Introduce tu edad")
tuedad = int(input())
print(tunombre + ", tu edad es " +str(tuedad)+ " años")

#print("Ahora introduce tu apellido")--> esta sintaxis evita el print de la pregunta y captura el valor en la variable, todo en uno
tuapellido = str(input('Ahora, introduce tu apellido\n'))
print(tunombre + ", tu apellido es " + tuapellido )
