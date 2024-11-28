-- DML--
DROP DATABASE IF EXISTS LIBRERIA;
CREATE DATABASE LIBRERIA;
USE LIBRERIA;

-- Tabla TEMA
CREATE TABLE TEMA (
    id_tema INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla LIBRO
CREATE TABLE LIBRO (
    id_libro INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    editorial VARCHAR(100),
    descripcion TEXT,
    pvp DECIMAL(10, 2) NOT NULL,
    id_tema INT NOT NULL,
    FOREIGN KEY (id_tema) REFERENCES TEMA(id_tema) ON DELETE CASCADE
);

-- Tabla CLIENTE
CREATE TABLE CLIENTE (
    nif CHAR(9) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    direccion VARCHAR(255)
);

-- Tabla PEDIDO
CREATE TABLE PEDIDO (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    nif_cliente CHAR(9),
    fecha DATE NOT NULL,
    FOREIGN KEY (nif_cliente) REFERENCES CLIENTE(nif) ON DELETE CASCADE
);

-- Tabla DETALLE
CREATE TABLE DETALLE (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_libro INT NOT NULL,
    unidades INT NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES PEDIDO(id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_libro) REFERENCES LIBRO(id_libro) ON DELETE CASCADE
);

-- DDL--

-- Temas
INSERT INTO TEMA (nombre) VALUES
('Ciencia Ficción'),
('Fantasía'),
('Historia'),
('Literatura Clásica');

-- Libros de ciencia ficción
INSERT INTO LIBRO (titulo, autor, editorial, descripcion, pvp, id_tema) VALUES
('Dune', 'Frank Herbert', 'Ace', 'Un clásico de la ciencia ficción.', 19.99, 1),
('Neuromante', 'William Gibson', 'Ace', 'El nacimiento del ciberpunk.', 15.99, 1),
('Fahrenheit 451', 'Ray Bradbury', 'Ballantine Books', 'Una distopía sobre la quema de libros.', 13.99, 1),
('1984', 'George Orwell', 'Harcourt', 'Una distopía sobre el control totalitario.', 16.99, 1),
('El fin de la infancia', 'Arthur C. Clarke', 'HarperVoyager', 'Un encuentro con una inteligencia alienígena superior.', 14.99, 1),
('La máquina del tiempo', 'H.G. Wells', 'Tor Classics', 'Viajes a través del tiempo.', 10.99, 1),
('El hombre en el castillo', 'Philip K. Dick', 'Vintage', 'Historia alternativa donde los nazis ganaron la Segunda Guerra Mundial.', 18.99, 1),
('Fundación', 'Isaac Asimov', 'Spectra', 'Un futuro donde la psicohistoria predice el colapso de la civilización.', 17.99, 1),
('Solaris', 'Stanisław Lem', 'Harcourt', 'La exploración de un planeta consciente.', 14.50, 1),
('La guerra de los mundos', 'H.G. Wells', 'Signet Classics', 'Una invasión alienígena en la Tierra.', 12.50, 1);

-- Libros de fantasía
INSERT INTO LIBRO (titulo, autor, editorial, descripcion, pvp, id_tema) VALUES
('El Señor de los Anillos', 'J.R.R. Tolkien', 'Minotauro', 'Una saga épica de fantasía.', 25.99, 2),
('Juego de Tronos', 'George R.R. Martin', 'Bantam', 'Primera parte de Canción de Hielo y Fuego.', 22.99, 2),
('El Hobbit', 'J.R.R. Tolkien', 'Minotauro', 'La precuela de El Señor de los Anillos.', 15.99, 2),
('La espada del destino', 'Andrzej Sapkowski', 'Alamut', 'Una de las historias del brujo Geralt de Rivia.', 20.99, 2),
('Harry Potter y la piedra filosofal', 'J.K. Rowling', 'Bloomsbury', 'El inicio de la saga del joven mago.', 18.99, 2),
('La historia interminable', 'Michael Ende', 'Alfaguara', 'Un viaje a un mundo imaginario.', 14.99, 2),
('Crónica del asesino de reyes: El nombre del viento', 'Patrick Rothfuss', 'DAW Books', 'La vida del arcanista Kvothe.', 19.99, 2),
('Las crónicas de Narnia: El león, la bruja y el armario', 'C.S. Lewis', 'HarperCollins', 'La primera historia del mundo de Narnia.', 13.99, 2),
('El último deseo', 'Andrzej Sapkowski', 'Alamut', 'Relatos del mundo de Geralt de Rivia.', 21.99, 2),
('La princesa prometida', 'William Goldman', 'Harcourt', 'Una novela de aventuras, amor y espadas.', 16.99, 2);

-- Libros de historia
INSERT INTO LIBRO (titulo, autor, editorial, descripcion, pvp, id_tema) VALUES
('Sapiens: De animales a dioses', 'Yuval Noah Harari', 'Debate', 'La historia de la humanidad.', 18.99, 3),
('Homo Deus', 'Yuval Noah Harari', 'Debate', 'El futuro de la humanidad.', 19.99, 3),
('Guns, Germs, and Steel', 'Jared Diamond', 'W.W. Norton', 'El impacto de los recursos en la historia.', 21.99, 3),
('El origen de las especies', 'Charles Darwin', 'Penguin Classics', 'Fundamentos de la evolución.', 16.99, 3),
('Los cañones de agosto', 'Barbara W. Tuchman', 'Ballantine Books', 'La Primera Guerra Mundial.', 14.99, 3),
('El declive de Occidente', 'Oswald Spengler', 'Alianza Editorial', 'Un análisis del ciclo de las civilizaciones.', 22.99, 3),
('Historia de Roma', 'Indro Montanelli', 'Debolsillo', 'Una visión breve y entretenida de Roma.', 12.99, 3),
('El ascenso y la caída del Tercer Reich', 'William L. Shirer', 'Simon & Schuster', 'Historia de la Alemania nazi.', 23.99, 3),
('Chernóbil: La tragedia', 'Serhii Plokhy', 'Alianza', 'Historia del desastre nuclear.', 17.99, 3),
('Historia de España contada para escépticos', 'Juan Eslava Galán', 'Planeta', 'Una visión ligera de la historia española.', 15.99, 3);

-- Libros de literatura clásica
INSERT INTO LIBRO (titulo, autor, editorial, descripcion, pvp, id_tema) VALUES
('Don Quijote de la Mancha', 'Miguel de Cervantes', 'Cátedra', 'La obra cumbre de la literatura española.', 17.99, 4),
('Crimen y castigo', 'Fiódor Dostoyevski', 'Penguin Classics', 'Una exploración de la psicología criminal.', 14.99, 4),
('Orgullo y prejuicio', 'Jane Austen', 'Penguin Classics', 'Un retrato de la sociedad inglesa.', 13.99, 4),
('Moby Dick', 'Herman Melville', 'Penguin Classics', 'La obsesión por cazar a la ballena blanca.', 16.99, 4),
('Los hermanos Karamázov', 'Fiódor Dostoyevski', 'Penguin Classics', 'Un estudio sobre el alma humana.', 18.99, 4),
('En busca del tiempo perdido', 'Marcel Proust', 'Gallimard', 'Una introspección sobre la memoria.', 22.99, 4),
('Cien años de soledad', 'Gabriel García Márquez', 'Sudamericana', 'El realismo mágico en su máximo esplendor.', 15.99, 4),
('Anna Karénina', 'Lev Tolstói', 'Penguin Classics', 'Una obra maestra de la literatura rusa.', 19.99, 4),
('La Divina Comedia', 'Dante Alighieri', 'Penguin Classics', 'Un viaje a través del Infierno, el Purgatorio y el Paraíso.', 25.99, 4),
('Hamlet', 'William Shakespeare', 'Oxford University Press', 'Un clásico del teatro universal.', 12.99, 4);

-- Clientes
INSERT INTO CLIENTE (NIF, nombre, apellido, direccion) VALUES
  ('X1234567Z', 'Gabriel García', 'Márquez', 'Calle de la Candelaria, 123, Bogotá, Colombia'),
  ('Y4567890A', 'Octavio Paz', 'Lozano', 'Avenida de las Fuentes, 56, Ciudad de México, México'),
  ('Z1234567B', 'Albert', 'Einstein', 'Princeton University, Princeton, New Jersey, Estados Unidos'),
  ('W4567890C', 'Marie', 'Curie', 'Rue Pierre Curie, 11, París, Francia'),
  ('V1234567D', 'Nelson', 'Mandela', 'Vilakazi Street, Soweto, Johannesburgo, Sudáfrica');

-- Pedidos
INSERT INTO PEDIDO (nif_cliente, fecha) VALUES
('X1234567Z', '2024-10-15'),
('X1234567Z', '2024-11-01'),
('Y4567890A', '2024-09-12'),
('Y4567890A', '2024-10-22'),
('Z1234567B', '2024-08-05'),
('Z1234567B', '2024-09-18'),
('Z1234567B', '2024-10-10'),
('W4567890C', '2024-11-05'),
('V1234567D', '2024-10-01');

-- Detalle de pedidos
INSERT INTO DETALLE (id_pedido, id_libro, unidades) VALUES
(1, 1, 2),
(1, 5, 1),
(2, 3, 3),
(2, 6, 2),
(3, 2, 1),
(3, 7, 3),
(4, 4, 4),
(4, 8, 1),
(5, 9, 2),
(5, 10, 1),
(6, 11, 1),
(6, 12, 2),
(7, 13, 3),
(7, 14, 1),
(8, 15, 2),
(9, 16, 4),
(9, 17, 2);