class MenuItem:
    def __init__(self, nombre: str, precio: float, categoria: str):
        self.nombre = nombre
        self.precio = precio
        self.categoria = categoria

    def get_detalles(self):
        return f"{self.nombre} - {self.categoria}: ${self.precio:.2f}"

    def calcular_precio(self):
        return self.precio