# Aquí tienes el contenido para el archivo: /restaurante-app/restaurante-app/src/views/menu_view.py

import tkinter as tk
from tkinter import messagebox
from src.models.menu_item import MenuItem
from src.controllers.menu_controller import MenuController

class MenuView:
    def __init__(self, master):
        self.master = master
        self.master.title("Menú del Restaurante")
        
        self.menu_controller = MenuController()
        
        self.frame = tk.Frame(self.master)
        self.frame.pack(pady=10)

        self.menu_listbox = tk.Listbox(self.frame, width=50)
        self.menu_listbox.pack()

        self.load_menu()

        self.add_button = tk.Button(self.frame, text="Añadir Plato", command=self.add_menu_item)
        self.add_button.pack(pady=5)

        self.update_button = tk.Button(self.frame, text="Actualizar Plato", command=self.update_menu_item)
        self.update_button.pack(pady=5)

    def load_menu(self):
        self.menu_listbox.delete(0, tk.END)
        menu_items = self.menu_controller.get_menu_items()
        for item in menu_items:
            self.menu_listbox.insert(tk.END, f"{item.nombre} - ${item.precio:.2f} ({item.categoria})")

    def add_menu_item(self):
        # Aquí se puede implementar la lógica para añadir un nuevo plato
        pass

    def update_menu_item(self):
        selected_item_index = self.menu_listbox.curselection()
        if selected_item_index:
            selected_item = self.menu_listbox.get(selected_item_index)
            # Aquí se puede implementar la lógica para actualizar el plato seleccionado
        else:
            messagebox.showwarning("Advertencia", "Por favor, selecciona un plato para actualizar.")

# Código para ejecutar la vista si se ejecuta este archivo directamente
if __name__ == "__main__":
    root = tk.Tk()
    app = MenuView(root)
    root.mainloop()