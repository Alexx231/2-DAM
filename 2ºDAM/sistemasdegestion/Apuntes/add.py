class Cuenta:
    def __init__(self, titular, cantidad):
        self.titular = titular
        self.cantidad = cantidad
        
    def __add__(self,otra_cuenta):
        nueva_cantidad = self.cantidad + otra_cuenta.cantidad
        nuevo_titular = f"{self.titular} y {otra_cuenta.titular}"
        return Cuenta(nuevo_titular, nueva_cantidad)
    
    def __str__(self):
        return f"Titular(es): {self.titular}, Cantidad: {self.cantidad:.2f}€"
    

cuenta1 = Cuenta("123713J", 300000)
cuenta2 = Cuenta("4872364826384", 9873845)
cuenta_combinada = cuenta1 + cuenta2

print(cuenta_combinada)