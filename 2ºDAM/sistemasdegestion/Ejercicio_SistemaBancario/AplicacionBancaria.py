import tkinter as tk
from tkinter import messagebox
from Banco import Banco
from Cuenta import Cuenta

class AplicacionBancaria:
    def __init__(self, root):
        self.root = root
        self.root.title("Gestión Bancaria")
        self.banco = Banco()
        
        # Crear cuenta de prueba
        cuenta_inicial = Cuenta("1", 1000)
        self.banco.agregar_cuenta(cuenta_inicial)
        
        # Crear interfaz
        self.crear_widgets()
    
    def crear_widgets(self):
        # Campos de entrada
        tk.Label(self.root, text="Número de Cuenta:").grid(row=0, column=0, padx=5, pady=5)
        self.numero_cuenta = tk.Entry(self.root)
        self.numero_cuenta.grid(row=0, column=1, padx=5, pady=5)
        
        tk.Label(self.root, text="Cantidad:").grid(row=1, column=0, padx=5, pady=5)
        self.cantidad = tk.Entry(self.root)
        self.cantidad.grid(row=1, column=1, padx=5, pady=5)
        
        # Botones
        tk.Button(self.root, text="Ingresar", command=self.ingresar).grid(row=2, column=0, padx=5, pady=5)
        tk.Button(self.root, text="Retirar", command=self.retirar).grid(row=2, column=1, padx=5, pady=5)
        tk.Button(self.root, text="Ver Saldo", command=self.ver_saldo).grid(row=2, column=2, padx=5, pady=5)
        tk.Button(self.root, text="Nueva Cuenta", command=self.nueva_cuenta).grid(row=3, column=1, padx=5, pady=5)

    def obtener_datos(self):
        try:
            numero_cuenta = self.numero_cuenta.get()
            cantidad = float(self.cantidad.get() or 0)
            return numero_cuenta, cantidad
        except ValueError:
            messagebox.showerror("Error", "Por favor ingrese valores válidos")
            return None, None

    def ingresar(self):
        numero_cuenta, cantidad = self.obtener_datos()
        if numero_cuenta and cantidad:
            if self.banco.depositar(numero_cuenta, cantidad):
                messagebox.showinfo("Éxito", f"Se ingresaron ${cantidad} en la cuenta {numero_cuenta}")
            else:
                messagebox.showerror("Error", "No se pudo realizar el ingreso")

    def retirar(self):
        numero_cuenta, cantidad = self.obtener_datos()
        if numero_cuenta and cantidad:
            if self.banco.retirar(numero_cuenta, cantidad):
                messagebox.showinfo("Éxito", f"Se retiraron ${cantidad} de la cuenta {numero_cuenta}")
            else:
                messagebox.showerror("Error", "Fondos insuficientes o cuenta inválida")

    def ver_saldo(self):
        numero_cuenta = self.numero_cuenta.get()
        cuenta = self.banco.consultar_saldo(numero_cuenta)
        if cuenta:
            messagebox.showinfo("Saldo", f"Saldo de la cuenta {numero_cuenta}: ${cuenta.mostrar_saldo()}")
        else:
            messagebox.showerror("Error", "Cuenta no encontrada")

    def nueva_cuenta(self):
        numero_cuenta = self.numero_cuenta.get()
        if numero_cuenta:
            nueva_cuenta = Cuenta(numero_cuenta)
            if self.banco.agregar_cuenta(nueva_cuenta):
                messagebox.showinfo("Éxito", f"Cuenta {numero_cuenta} creada con éxito")
            else:
                messagebox.showerror("Error", "La cuenta ya existe")
        else:
            messagebox.showerror("Error", "Ingrese un número de cuenta válido")

if __name__ == "__main__":
    root = tk.Tk()
    app = AplicacionBancaria(root)
    root.mainloop()