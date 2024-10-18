#Aritméticos
# +, -, *, **, /, //, %
num1 = 3
num2 = 5
sum1 = num1 + num2
sum2 = 3+5
print('La suma de las variables da ', sum1)
print('La suma de los enteros da ', sum2)

pow = 5 ** 3 # 125
print(pow) # 125

cont = 0
cont += 1 #equivale a cont = cont + 1

# Binarios
# &, | or, ~ not, << izqda, >> drcha

#>>> 16 / 3
4.666666666666667
b = 16 / 3 # --> 5.33333333333333333 / float
print(b)
b1 = 16 // 3 # --> 5 / int
print(b1)
print(type(b)) # <class 'float'
print(type(b1)) # class 'int'

b1 = float(b1) 
print(type(b1)) # class 'float'


sum3 = (40 - 5*2) # 30
print(sum3)

# bool --> true / false
# Y and, O or, No not

# operadores relacionales
# ==, !=, >, <, >=, <=

print('primera línea\n segunda línea')
print(r'primera línea\n segunda línea')
print("""primera línea
      segunda línea""")

cadena = 'hola'
cadena = 'hola' + 'hola'
print(cadena) #-> holahola

cadena = 'adios' * 3 #cadena 
print(cadena) # --> "adiosadiosadios"

z = len(cadena)
print(z) # 15

print(cadena[0]) # --> a
print(cadena[-1]) # --> s

cadena1 = str(3) + str(3)
print(cadena1) # --> 33

cadena1 = int(cadena1)
cadena1 += 1
print(cadena1) # --> 34

cadena3 = cadena.upper()
print(cadena3) # --> "ADIOSADIOSADIOS"

rep = cadena3.find("AD") # -> 0 es la posición en la que se inicia la subcadena "AD" respecto de otra
print(rep) # --> 0
rep = cadena3.count("ADIOS")
print(rep) # --> 3




