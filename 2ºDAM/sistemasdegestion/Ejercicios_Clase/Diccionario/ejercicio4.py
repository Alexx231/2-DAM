# Actividad 1
inventario = {
    "manzanas": {"precio": 0.5, "cantidad": 100},
    "naranjas": {"precio": 0.75, "cantidad": 80},
    "platanos": {"precio": 0.3, "cantidad": 150},
    "peras": {"precio": 0.6, "cantidad": 60},
    "batidos": {"precio": 1.6, "cantidad": 65},
    "carnes": {"precio": 0.9, "cantidad": 20}
}

inventario["melones"] = {"precio": 1.0, "cantidad": 150}

# Actividad 2

inventario["naranjas"]["precio"] = 2.0

# Actividad 3

vender_producto = lambda producto, cantidad: inventario[producto].update({"cantidad": inventario[producto]["cantidad"] - cantidad}) if producto in inventario and inventario[producto]["cantidad"] >= cantidad else print("No hay suficiente stock o el producto no existe.")

vender_producto("carnes", 20)

# Actividad 4
for producto, detalles in inventario.items():
    print(producto, detalles)
