"""Módulo que define las clases principales para la gestión de restaurante."""

class MenuItem:
    """Clase que representa un ítem del menú del restaurante."""
    
    def __init__(self, nombre, precio, categoria):
        """
        Inicializa un nuevo ítem del menú.
        
        Args:
            nombre (str): Nombre del plato
            precio (float): Precio del plato
            categoria (str): Categoría a la que pertenece el plato
        """
        self.nombre = nombre
        self.precio = precio
        self.categoria = categoria

class Pedido:
    """Clase que gestiona los pedidos del restaurante."""
    
    def __init__(self):
        """
        Inicializa un nuevo pedido.
        
        Attributes:
            items (list): Lista de items pedidos
            total (float): Total del pedido sin IVA
            total_con_iva (float): Total del pedido con IVA
        """
        self.items = []  # Lista para almacenar los items del pedido
        self.total = 0   # Total sin IVA
        
    def agregar_item(self, item, cantidad):
        """
        Añade un ítem al pedido.
        
        Args:
            item (MenuItem): Ítem del menú a agregar
            cantidad (int): Cantidad del ítem solicitado
        """
        self.items.append({'item': item, 'cantidad': cantidad})
        self.calcular_total()  # Actualizar el total
        
    def calcular_total(self):
        """
        Calcula el total del pedido incluyendo IVA.
        
        El IVA aplicado es del 21% sobre el total del pedido.
        """
        # Calcular suma de (precio * cantidad) para cada ítem
        self.total = sum(item['item'].precio * item['cantidad'] for item in self.items)
        # Aplicar IVA del 21%
        self.total_con_iva = self.total * 1.21