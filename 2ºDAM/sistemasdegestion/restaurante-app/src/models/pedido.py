class Pedido:
    def __init__(self):
        self.items = []

    def agregar_item(self, item):
        self.items.append(item)

    def calcular_total(self):
        total = sum(item.precio for item in self.items)
        iva = total * 0.21  # Suponiendo un IVA del 21%
        return total + iva

    def resumen_pedido(self):
        resumen = "Resumen del Pedido:\n"
        for item in self.items:
            resumen += f"{item.nombre} - Precio: {item.precio} - Categoría: {item.categoria}\n"
        resumen += f"Total con IVA: {self.calcular_total()}"
        return resumen