# README.md

# Gestión de Pedidos en un Restaurante

Este proyecto es una aplicación en Python para gestionar pedidos en un restaurante. Utiliza clases para representar los elementos del menú y los pedidos, una base de datos SQLite para almacenar la información, y una interfaz gráfica construida con Tkinter.

## Estructura del Proyecto

```
restaurante-app
├── src
│   ├── models
│   │   ├── __init__.py
│   │   ├── menu_item.py
│   │   └── pedido.py
│   ├── database
│   │   ├── __init__.py
│   │   ├── db_config.py
│   │   └── queries.py
│   ├── views
│   │   ├── __init__.py
│   │   ├── main_window.py
│   │   ├── menu_view.py
│   │   └── pedido_view.py
│   ├── controllers
│   │   ├── __init__.py
│   │   ├── menu_controller.py
│   │   └── pedido_controller.py
│   └── utils
│       ├── __init__.py
│       └── graficos.py
├── data
│   └── restaurante.db
├── tests
│   ├── __init__.py
│   ├── test_menu.py
│   └── test_pedido.py
├── requirements.txt
├── main.py
└── README.md
```

## Instalación

1. Clona el repositorio:
   ```
   git clone <url-del-repositorio>
   cd restaurante-app
   ```

2. Instala las dependencias:
   ```
   pip install -r requirements.txt
   ```

## Uso

1. Ejecuta la aplicación:
   ```
   python main.py
   ```

2. Utiliza la interfaz gráfica para gestionar el menú y los pedidos.

## Contribuciones

Las contribuciones son bienvenidas. Si deseas contribuir, por favor abre un issue o envía un pull request.

## Licencia

Este proyecto está bajo la Licencia MIT.