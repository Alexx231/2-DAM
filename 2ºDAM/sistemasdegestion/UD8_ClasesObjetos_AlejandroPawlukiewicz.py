# Parte 1
print("-----------")

print("PARTE 1")

print("-----------")


class Empleado:
    
    def __init__(self, nombre, cargo, salario):
        
        self.nombre = nombre
        self.cargo = cargo
        self.__salario = float(salario)
        
    def get_salario(self):
        return self.__salario

    def informacion(self):
        return f"El empleado {self.nombre}, su cargo es {self.cargo} y su salario es {self.__salario}€"

    def calcular_salario_anual(self, salario):
        salario_anual = self.__salario * 12
        return f"El salario anual del empleado {self.nombre} es {salario_anual}€"

# Parte 2

    def ver_salario(self):
        return f"El empleado {self.nombre} tiene un salario de {self.__salario}€"
    
    def modificar_salario(self, nuevo_salario):
        self.__salario = float(nuevo_salario)
        return f"El nuevo salario del empleado {self.nombre} es {self.__salario}€"
    
    def trabajar(self):
        return f"{self.nombre} está trabajando."


empleado1 = Empleado("Juan","Carretillero",1200)
empleado2 = Empleado("Luis","Encargado",1600)

print(empleado1.informacion())
print("-----------")
print(empleado2.informacion())
print("-----------")
print(empleado1.calcular_salario_anual(empleado1))
print("-----------")
print(empleado2.calcular_salario_anual(empleado2))
print("-----------")
print(empleado1.modificar_salario(1300))
print("-----------")
print(empleado1.informacion())
print("-----------")

print("PARTE 2")

print("-----------")

class CuentaBancaria:
    
    def __init__(self,titular,saldo):
        self.titular = titular
        self.__saldo = float(saldo)
        
    def depositar(self, cantidad):
        if cantidad > 0:
            self.__saldo += float(cantidad)
            return f"Depósito exitoso. El nuevo saldo es {self.__saldo}€"
        else:
            return "La cantidad a depositar debe ser positiva."

    def retirar(self, cantidad):
        if 0 < cantidad <= self.__saldo:
            self.__saldo -= float(cantidad)
            return f"Retiro exitoso. El nuevo saldo es {self.__saldo}€"
        elif cantidad > self.__saldo:
            return "Fondos insuficientes."
        else:
            return "La cantidad a retirar debe ser positiva."
        
    def ver_saldo(self):
        return f"El saldo actual es {self.__saldo}"
    
cuenta = CuentaBancaria("Alejandro", 1000)

print(cuenta.ver_saldo())
print("-----------")
print(cuenta.depositar(500))
print("-----------")
print(cuenta.ver_saldo())
print("-----------")
print(cuenta.retirar(300))
print("-----------")
print(cuenta.ver_saldo())
print("-----------")
print(cuenta.retirar(1500))
print("-----------")
print(cuenta.ver_saldo())
print("-----------")

# Parte 3 la bonificacion mensual es de 500€
print("PARTE 3")

print("-----------")

class Gerente(Empleado):
    def __init__(self, nombre, cargo, salario, bonificacion):
        super().__init__(nombre, cargo, salario)
        self.bonificacion = float(bonificacion)
        
    def salario_total(self, antiguedad):
        antiguedad = 1 <= antiguedad <= 5
        salario_total = self.get_salario() * 12 + (self.bonificacion * antiguedad)
        return f"El salario con la bonificacion incluida del gerente {self.nombre} es {salario_total}"
    
gerente1 = Gerente("Alex", "Gerente", 1800, 500)

print(gerente1.salario_total(3))
print("-----------")


class Desarrollador(Empleado):
    def trabajar(self):
        return f"{self.nombre} está escribiendo código."

class Diseñador(Empleado):
    def trabajar(self):
        return f"{self.nombre} está diseñando una interfaz."

def demostrar_trabajo(empleado):
    print(empleado.trabajar())
    
desarrollador1 = Desarrollador("Ana", "Desarrolladora", 2000)
diseñador1 = Diseñador("Maria", "Diseñadora", 1800)

demostrar_trabajo(empleado1)
print("-----------")
demostrar_trabajo(gerente1)
print("-----------")
demostrar_trabajo(desarrollador1)
print("-----------")
demostrar_trabajo(diseñador1)
print("-----------")

# Parte 4

print("PARTE 4")

print("-----------")

class Motor:
    
    def arrancar(self):
        return "El motor esta arrancando"
        
class Coche:
    def __init__(self, marca):
        self.marca = marca
        self.motor = Motor()
    
    def conducir(self):
        return f"El coche es de la marca {self.marca} y {self.motor.arrancar()}"

coche1 = Coche("Seat Ibiza")

print(coche1.conducir())
print("-----------")

# Parte 5

print("PARTE 5")

print("-----------")

class Producto:
    
    def __init__(self, nombre, precio):
        self.nombre = nombre
        self.precio = float(precio)

    def __add__(self, otro_producto):
        return self.precio + otro_producto.precio

    def __str__(self):
        return f"Producto: {self.nombre}, Precio: {self.precio}€"

producto1 = Producto("Manzana", 1.20)
producto2 = Producto("Naranja", 0.80)

print(producto1)
print("-----------")
print(producto2)
print("-----------")

suma_precios = producto1 + producto2
print(f"La suma de los precios es: {suma_precios}€")

print("-----------")