INSERT INTO clientes (nombre, apellido, nit, telefono, create_at) values ('Andres', 'lopez', '1234','12345678','2022-01-01');
INSERT INTO clientes (nombre, apellido, nit, telefono, create_at) values ('Andres1', 'lopez1', '12341','12345671','2022-02-01');
INSERT INTO clientes (nombre, apellido, nit, telefono, create_at) values ('Andres2', 'lopez2', '12342','12345672','2022-03-01');
INSERT INTO clientes (nombre, apellido, nit, telefono, create_at) values ('Andres3', 'lopez3', '12343','12345673','2022-04-01');
INSERT INTO clientes (nombre, apellido, nit, telefono, create_at) values ('Andres4', 'lopez4', '12344','12345674','2022-04-02');
INSERT INTO clientes (nombre, apellido, nit, telefono, create_at) values ('Andres5', 'lopez5', '12345','12345675','2022-05-01');
INSERT INTO clientes (nombre, apellido, nit, telefono, create_at) values ('Andres6', 'lopez6', '12346','12345676','2022-06-01');
INSERT INTO clientes (nombre, apellido, nit, telefono, create_at) values ('Andres7', 'lopez7', '12347','12345677','2022-07-01');


INSERT INTO productos (nombre, descripcion, precio, create_at) VALUES('Panasonic Pantalla LCD','Panasonic', 259990, NOW());
INSERT INTO productos (nombre, descripcion, precio, create_at) VALUES('Sony Camara digital DSC-W320B', 'Panasonic', 123490, NOW());
INSERT INTO productos (nombre, descripcion, precio, create_at) VALUES('Apple iPod shuffle','Panasonic', 1499990, NOW());
INSERT INTO productos (nombre, descripcion, precio, create_at) VALUES('Sony Notebook Z110','Panasonic', 37990, NOW());
INSERT INTO productos (nombre, descripcion, precio, create_at) VALUES('Hewlett Packard Multifuncional F2280','Panasonic', 69990, NOW());
INSERT INTO productos (nombre, descripcion, precio, create_at) VALUES('Bianchi Bicicleta Aro 26','Panasonic', 69990, NOW());
INSERT INTO productos (nombre, descripcion, precio, create_at) VALUES('Mica Comoda 5 Cajones','Panasonic', 299990, NOW());


/* Creamos algunas facturas */
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura equipos de oficina', null, 1, NOW());

INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(3, 2, 6);

