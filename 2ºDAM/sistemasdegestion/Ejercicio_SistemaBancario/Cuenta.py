class Cuenta:
    def __init__(self, numero_cuenta, saldo_inicial=0):
        self.numero_cuenta = numero_cuenta
        self.saldo = saldo_inicial
    
    def ingresar(self, cantidad):
        if cantidad > 0:
            self.saldo += cantidad
            return True
        return False
    
    def retirar(self, cantidad):
        if cantidad > 0 and self.saldo >= cantidad:
            self.saldo -= cantidad
            return True
        return False
    
    def mostrar_saldo(self):
        return self.saldo