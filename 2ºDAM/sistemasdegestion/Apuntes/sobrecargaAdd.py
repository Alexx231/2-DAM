class Bono:
    def __init__(self,referencia,valor):
        self.referencia = referencia
        self.valor = valor
        
    def __add__(self,otro_bono):
        nueva_referencia = f"{self.referencia} y {otro_bono.referencia}"
        suma_valores = self.valor + otro_bono.valor
        return Bono(nueva_referencia, suma_valores)
    
    def __str__(self):
        return f"Referencias: {self.referencia}, Inversion: {self.valor:,.2f}€"
    
bono1 = Bono("837462", 4892374)
bono2 = Bono("873265235", 95867347)
bono3 = Bono("23654273", 893479837)

cartera = bono1 + bono2 + bono3

print(cartera)
