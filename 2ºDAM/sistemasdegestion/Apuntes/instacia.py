class Mesa:
    def __init__(self, color, material, precio):
        self.color = color
        self.material = material
        self.precio = precio

mesa1 = Mesa("gris","aluminio",2750)
print(mesa1.material)