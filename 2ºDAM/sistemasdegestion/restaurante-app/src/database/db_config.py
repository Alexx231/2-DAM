import sqlite3

def connect_db():
    conn = sqlite3.connect('data/restaurante.db')
    return conn

def close_db(conn):
    conn.close()