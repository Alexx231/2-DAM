def create_connection(db_file):
    """Crea una conexión a la base de datos SQLite."""
    import sqlite3
    conn = None
    try:
        conn = sqlite3.connect(db_file)
        return conn
    except sqlite3.Error as e:
        print(e)
    return conn

def create_menu_item(conn, menu_item):
    """Inserta un nuevo elemento del menú en la tabla."""
    sql = ''' INSERT INTO menu(nombre, precio, categoria)
              VALUES(?,?,?) '''
    cur = conn.cursor()
    cur.execute(sql, menu_item)
    conn.commit()
    return cur.lastrowid

def update_menu_item(conn, menu_item):
    """Actualiza un elemento del menú existente."""
    sql = ''' UPDATE menu
              SET precio = ?, categoria = ?
              WHERE nombre = ? '''
    cur = conn.cursor()
    cur.execute(sql, menu_item)
    conn.commit()

def get_menu_items(conn):
    """Devuelve todos los elementos del menú."""
    cur = conn.cursor()
    cur.execute("SELECT * FROM menu")
    return cur.fetchall()

def create_order(conn, order):
    """Inserta un nuevo pedido en la tabla."""
    sql = ''' INSERT INTO pedidos(items, total)
              VALUES(?,?) '''
    cur = conn.cursor()
    cur.execute(sql, order)
    conn.commit()
    return cur.lastrowid

def get_orders(conn):
    """Devuelve todos los pedidos."""
    cur = conn.cursor()
    cur.execute("SELECT * FROM pedidos")
    return cur.fetchall()