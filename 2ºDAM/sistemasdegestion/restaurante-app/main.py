import sqlite3
from src.database.db_config import DATABASE
from src.views.main_window import MainWindow

def initialize_database():
    conn = sqlite3.connect(DATABASE)
    cursor = conn.cursor()
    # Aquí puedes agregar la lógica para crear tablas si no existen
    conn.commit()
    conn.close()

def main():
    initialize_database()
    app = MainWindow()
    app.run()

if __name__ == "__main__":
    main()