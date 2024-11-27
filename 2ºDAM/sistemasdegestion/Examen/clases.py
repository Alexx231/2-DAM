class Silla:
    color = "verde"
    material = "madera"
    precio = 225

# instanciar la clase
silla1 = Silla()
print(silla1.color, silla1.material, silla1.precio)  # imprime: verde madera 225

'''
método constructor _init_: permite inicializar los atributos de un objeto cuando se crea
'''
class Mesilla:
    def __init__(self) -> None:
        pass
# esta es una implementación válida para crear la estructura de la Clase antes de haber decidido los atributos o el comportamiento que quieres para cada instancia
# indica que el constructor debe ser definido, está a falta de especificar qué debe hacer

class Mesa:
    def __init__(self, color, material, precio):
        self.color = color
        self.material = material
        self.precio = precio
        

mesa1 = Mesa("gris","aluminio",2750) # al crear el objeto mesa1, los atributos se inicializan con los valores proporcionados
print(mesa1.material)  # imprime: aluminio

# métodos de una Clase
class Persona:
    def __init__(self, nombre, edad, ciudad):
        self.nombre = nombre
        self.edad = edad
        self.ciudad = ciudad

    def cambiar_ciudad(self, nueva_ciudad): # método que permite modificar el atributo ciudad del objeto de la Clase Persona
        self.ciudad = nueva_ciudad

persona1 = Persona("Juan",27, "Madriz")
print(persona1.ciudad)  # imprime: Madriz
persona1.cambiar_ciudad("Madrid")
print(persona1.ciudad)  # imprime: Madrid

# método _str_: define cómo se imprimen los objetos de una Clase mediante print() -> permite personalizar el formato de salida

class Coche:
    def __init__(self, marca, modelo, precio):
        self.marca = marca
        self.modelo = modelo
        self.precio = precio

    def __str__(self):
        return f"Este coche, de la marca {self.marca}, es el modelo {self.modelo} y tiene un precio de {self.precio: .2f}€"
    
cochazo1 = Coche("BMW", "850i M", 137975.88)

print(cochazo1) # -> Este coche, de la marca BMW, es el modelo 850i M y tiene un precio de  137975.88€

#Sobrecarga del Operador __add__
# métodos especiales para operaciones

class Cuenta:
    def __init__(self, titular, cantidad):
        self.titular = titular
        self.cantidad = cantidad

    def __add__(self, otra_cuenta):
        # Crea una cuenta nueva, combinando las cantidades y los titulares
        total_cantidad = self.cantidad + otra_cuenta.cantidad
        nuevo_titular = f"{self.titular} y {otra_cuenta.titular}"
        return Cuenta(nuevo_titular, total_cantidad)
    
    def __str__(self):
        return f"Titular(es): {self.titular}, Cantidad: {self.cantidad:.2f}€"
    
'''
Utilizamos "dunder methods" {double underscore}, con DOS guiones bajos antes y después del nombre -> __add__
'''
# Crear dos cuentas y sumarlas

cuenta1 = Cuenta('12345678J', 300000.00)
cuenta2 = Cuenta('98765432J', 400000.00)
print(cuenta1 + cuenta2) # -> Titulares: 12345678J y 98765432J, Pasivo: 700000.00€
    
print(cuenta1.cantidad) # -> 300000.00
print(cuenta2.cantidad) # -> 400000.00
# __add__ no modifica el objeto original, sino que devuelve un nuevo objeto con la suma

class Taburete: 
    def __init__(self, color, precio):
        self.color = color
        self.precio = precio

    def __add__(self, otro_taburete):
        # Realiza la suma de los precios correctamente
        return f"Colores de los taburetes: {self.color} y {otro_taburete.color}; Precios combinados: {self.precio + otro_taburete.precio}€"

taburete1 = Taburete("rojo", 75)
taburete2 = Taburete("azul", 105)

print(taburete1 + taburete2)  # Salida: Colores: rojo y azul, Precios combinados: 180





'''
Ejercicio: imprime tu cartera de Bonos (referencia, valor)
'''

# __add__ creando nuevas instancias
class Bono:
    def __init__(self, referencia, valor):
        self.referencia = referencia
        self.valor = valor

    def __add__(self, otro_bono):
        # creas una nueva referencia, que combine las existentes
        nueva_referencia = f"{self.referencia} y {otro_bono.referencia}"
        # creas un nuevo objeto con la suma de los valores
        suma_valores = self.valor + otro_bono.valor
        # retornas una nueva instancia de Bono que contenga los valores combinados
        return Bono(nueva_referencia, suma_valores)
    
    def __str__(self):
        # método para representar el objeto como cadena
        return f"Referencias: {self.referencia}, Inversión: {self.valor: .2f}€"
    
# crea las instancias de Clase
bono1 = Bono('1234J', 300000.00)
bono2 = Bono('5678J', 400000.00)
bono3 = Bono('9876W', 600000.00)
print(bono1) # -> Referencias: 1234J, Inversión: 300000.00

# utiliza el método __add__ encadenado
cartera = bono1 + bono2 + bono3

# imprime tu cartera de bonos
print(cartera) # -> Referencias: 1234J y 5678J y 9876W, Inversión: 1300000.00€


