from models.menu_item import MenuItem
from database.queries import add_menu_item, update_menu_item, get_menu_items

def add_item(nombre, precio, categoria):
    item = MenuItem(nombre, precio, categoria)
    add_menu_item(item)

def update_item(item_id, nombre, precio, categoria):
    item = MenuItem(nombre, precio, categoria)
    update_menu_item(item_id, item)

def list_items():
    return get_menu_items()