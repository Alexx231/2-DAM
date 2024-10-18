class Persona:
    def __initi__(self, nombre, edad, ciudad):
        self.nombre = nombre
        self.edad = edad
        self.ciudad = ciudad
    
    def cambiar_ciudad(self, nueva_ciudad) :
        self.ciudad = nueva_ciudad
        
persona1 = Persona("Juan", 27, "Madrid")
print(persona1.ciudad)
persona1.cambiar_ciudad("Barcelona")
print(persona1.ciudad)