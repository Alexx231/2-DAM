CREATE DATABASE bookworld;
USE bookworld;

CREATE TABLE categoria (
  id_categoria int NOT NULL AUTO_INCREMENT,
  nombre varchar(50) NOT NULL,
  descripcion text,
  PRIMARY KEY (id_categoria)
);

CREATE TABLE cliente (
  id_cliente int NOT NULL AUTO_INCREMENT,
  nombre varchar(100) NOT NULL,
  apellidos varchar(100) NOT NULL,
  email varchar(100) NOT NULL,
  telefono varchar(15),
  direccion varchar(200),
  PRIMARY KEY (id_cliente),
  UNIQUE KEY (email)
);

CREATE TABLE proveedor (
  id_proveedor int NOT NULL AUTO_INCREMENT,
  nombre varchar(100) NOT NULL,
  contacto varchar(100),
  telefono varchar(15),
  email varchar(100),
  PRIMARY KEY (id_proveedor)
);

CREATE TABLE libro (
  isbn varchar(13) NOT NULL,
  titulo varchar(200) NOT NULL,
  autor varchar(100) NOT NULL,
  precio decimal(10,2) NOT NULL,
  stock int NOT NULL,
  categoria_id int,
  proveedor_id int,
  PRIMARY KEY (isbn),
  FOREIGN KEY (categoria_id) REFERENCES categoria (id_categoria),
  FOREIGN KEY (proveedor_id) REFERENCES proveedor (id_proveedor)
);

CREATE TABLE venta (
  id_venta int NOT NULL AUTO_INCREMENT,
  fecha datetime NOT NULL,
  total decimal(10,2) NOT NULL,
  cliente_id int,
  PRIMARY KEY (id_venta),
  FOREIGN KEY (cliente_id) REFERENCES cliente (id_cliente)
);

CREATE TABLE detalle_venta (
  id_detalle int NOT NULL AUTO_INCREMENT,
  venta_id int,
  libro_isbn varchar(13),
  cantidad int NOT NULL,
  precio_unitario decimal(10,2) NOT NULL,
  PRIMARY KEY (id_detalle),
  FOREIGN KEY (venta_id) REFERENCES venta (id_venta),
  FOREIGN KEY (libro_isbn) REFERENCES libro (isbn)
);

-- Datos de ejemplo
INSERT INTO cliente VALUES 
(1, 'Juan', 'Pérez', 'juan@email.com', '555-0101', 'Calle 1'),
(2, 'María', 'García', 'maria@email.com', '555-0202', 'Calle 2');

-- Ejecuta esto en MySQL para agregar algunas categorías básicas
INSERT INTO categoria (nombre, descripcion) VALUES 
('Ficción', 'Libros de ficción en general'),
('No Ficción', 'Libros basados en hechos reales'),
('Infantil', 'Literatura para niños'),
('Técnico', 'Libros técnicos y académicos');

-- Ejecuta esto en MySQL para agregar algunos proveedores básicos
INSERT INTO proveedor (nombre, contacto, telefono, email) VALUES 
('Proveedor A', 'Contacto A', '555-0101', 'contactoA@proveedor.com'),
('Proveedor B', 'Contacto B', '555-0202', 'contactoB@proveedor.com'),
('Proveedor C', 'Contacto C', '555-0303', 'contactoC@proveedor.com');

INSERT INTO venta VALUES 
(3, '2024-11-19 20:41:45', 23.00, 1);