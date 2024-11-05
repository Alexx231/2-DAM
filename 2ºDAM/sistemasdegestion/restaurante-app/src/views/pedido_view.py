import tkinter as tk
from tkinter import messagebox
from src.models.pedido import Pedido

class PedidoView:
    def __init__(self, master):
        self.master = master
        self.master.title("Gestión de Pedidos")
        
        self.pedido = Pedido()
        
        self.label = tk.Label(master, text="Detalles del Pedido")
        self.label.pack()

        self.text_area = tk.Text(master, height=10, width=50)
        self.text_area.pack()

        self.btn_add_item = tk.Button(master, text="Añadir Plato", command=self.add_item)
        self.btn_add_item.pack()

        self.btn_calculate_total = tk.Button(master, text="Calcular Total", command=self.calculate_total)
        self.btn_calculate_total.pack()

    def add_item(self):
        # Aquí se puede implementar la lógica para añadir un plato al pedido
        pass

    def calculate_total(self):
        total = self.pedido.calcular_total()
        messagebox.showinfo("Total del Pedido", f"El total con IVA es: {total:.2f}€")

    def mostrar_resumen(self):
        resumen = self.pedido.generar_resumen()
        self.text_area.delete(1.0, tk.END)
        self.text_area.insert(tk.END, resumen)