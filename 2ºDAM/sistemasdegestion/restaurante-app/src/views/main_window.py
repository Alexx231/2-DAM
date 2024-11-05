import tkinter as tk
from tkinter import ttk

class MainWindow:
    def __init__(self, root):
        self.root = root
        self.root.title("Gestión de Pedidos en el Restaurante")
        self.root.geometry("800x600")

        self.create_widgets()

    def create_widgets(self):
        self.menu_frame = ttk.Frame(self.root)
        self.menu_frame.pack(fill=tk.BOTH, expand=True)

        self.label = ttk.Label(self.menu_frame, text="Bienvenido al Restaurante", font=("Helvetica", 16))
        self.label.pack(pady=20)

        self.start_button = ttk.Button(self.menu_frame, text="Iniciar Pedido", command=self.start_order)
        self.start_button.pack(pady=10)

        self.quit_button = ttk.Button(self.menu_frame, text="Salir", command=self.root.quit)
        self.quit_button.pack(pady=10)

    def start_order(self):
        # Aquí se llamaría a la función para abrir la ventana de pedidos
        pass

if __name__ == "__main__":
    root = tk.Tk()
    app = MainWindow(root)
    root.mainloop()