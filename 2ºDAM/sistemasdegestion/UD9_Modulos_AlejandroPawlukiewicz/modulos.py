class MenuItem:
    def __init__(self, nombre, precio, categoria):
        self.nombre = nombre
        self.precio = precio
        self.categoria = categoria

class Pedido:
    def __init__(self):
        self.items = []
        self.total = 0

    def agregar_item(self, item, cantidad):
        self.items.append({'item': item, 'cantidad': cantidad})
        self.calcular_total()

    def calcular_total(self):
        self.total = sum(item['item'].precio * item['cantidad'] for item in self.items)
        self.total_con_iva = self.total * 1.21  # IVA 21%