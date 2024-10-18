class Coche:
    def __init__(self,marca,modelo,precio):
        self.marca = marca
        self.modelo = modelo
        self.precio = precio
        
    def __str__(self):
        return (f"Este coche de la marca {self.marca} es el modelo {self.modelo} y tiene un precio de {self.precio:,.2f}€")
    
cochazo1 = Coche("BMW", "850i M", 137975.88)
print(cochazo1)
    