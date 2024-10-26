# Clase para representar una fila de datos
class FilaDatos:
    def __init__(self, **kwargs):
        for key, value in kwargs.items():
            setattr(self, key, value)

    # Método para modificar el valor de un atributo
    def modificar_atributo(self, atributo, valor):
        setattr(self, atributo, valor)

    # Método para imprimir los datos de la fila
    def __str__(self):
        return ', '.join(f"{key}: {value}" for key, value in self.__dict__.items())

    # Método para comparar dos objetos por el valor de la segunda columna
    def __eq__(self, otro):
        return self.__dict__.get('segunda_columna') == otro.__dict__.get('segunda_columna')

    # Método para sumar los valores de los atributos de dos objetos
    def __add__(self, otro):
        resultado = {}
        for key in self.__dict__.keys():
            resultado[key] = getattr(self, key, 0) + getattr(otro, key, 0)
        return FilaDatos(**resultado)

    # Método para restar los valores de los atributos de dos objetos
    def __sub__(self, otro):
        resultado = {}
        for key in self.__dict__.keys():
            resultado[key] = getattr(self, key, 0) - getattr(otro, key, 0)
        return FilaDatos(**resultado)