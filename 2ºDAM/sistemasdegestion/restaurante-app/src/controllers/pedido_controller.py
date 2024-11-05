from models.pedido import Pedido
from database.queries import agregar_pedido

class PedidoController:
    def __init__(self):
        self.pedido_actual = Pedido()

    def agregar_item(self, menu_item):
        self.pedido_actual.agregar_item(menu_item)

    def calcular_total(self):
        return self.pedido_actual.calcular_total()

    def procesar_pedido(self):
        total = self.calcular_total()
        agregar_pedido(self.pedido_actual, total)
        self.pedido_actual = Pedido()  # Reiniciar el pedido actual