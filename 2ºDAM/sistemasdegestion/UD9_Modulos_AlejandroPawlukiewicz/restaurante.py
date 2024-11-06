import sqlite3

def crear_bd():
    conn = sqlite3.connect('restaurante.db')
    c = conn.cursor()
    
    # Crear tabla menú
    c.execute('''CREATE TABLE IF NOT EXISTS menu
                 (id INTEGER PRIMARY KEY,
                  nombre TEXT,
                  precio REAL,
                  categoria TEXT)''')
    
    # Crear tabla pedidos
    c.execute('''CREATE TABLE IF NOT EXISTS pedidos
                 (id INTEGER PRIMARY KEY,
                  fecha TEXT,
                  total REAL)''')
    
    conn.commit()
    conn.close()