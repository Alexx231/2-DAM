def generar_grafico_ventas(ventas):
    import matplotlib.pyplot as plt

    platos = list(ventas.keys())
    cantidades = list(ventas.values())

    plt.bar(platos, cantidades, color='skyblue')
    plt.xlabel('Platos')
    plt.ylabel('Cantidad Vendida')
    plt.title('Ventas de Platos en el Restaurante')
    plt.xticks(rotation=45)
    plt.tight_layout()
    plt.show()

def generar_grafico_pastel(ventas):
    import matplotlib.pyplot as plt

    platos = list(ventas.keys())
    cantidades = list(ventas.values())

    plt.pie(cantidades, labels=platos, autopct='%1.1f%%', startangle=140)
    plt.title('Distribución de Ventas de Platos')
    plt.axis('equal')
    plt.show()