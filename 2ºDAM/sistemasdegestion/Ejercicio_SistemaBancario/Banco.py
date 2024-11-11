from Cuenta import Cuenta

class Banco:
    def __init__(self):
        self.cuentas = {}
    
    def agregar_cuenta(self, cuenta):
        if cuenta.numero_cuenta not in self.cuentas:
            self.cuentas[cuenta.numero_cuenta] = cuenta
            return True
        return False
    
    def consultar_saldo(self, numero_cuenta):
        return self.cuentas.get(numero_cuenta)
    
    def depositar(self, numero_cuenta, cantidad):
        cuenta = self.consultar_saldo(numero_cuenta)
        if cuenta:
            return cuenta.ingresar(cantidad)
        return False
    
    def retirar(self, numero_cuenta, cantidad):
        cuenta = self.consultar_saldo(numero_cuenta)
        if cuenta:
            return cuenta.retirar(cantidad)
        return False