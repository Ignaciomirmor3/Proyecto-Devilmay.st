-- Script de Poblamiento Inicial de Base de Datos para DevilMay.st V5
-- Instrucciones: Ejecutar cada bloque en su respectiva base de datos.

-- ==========================================
-- BASE DE DATOS: db_catalogo
-- ==========================================
USE db_catalogo;

INSERT INTO producto (id_producto, nombre_producto, descripcion_producto, precio_producto, talla_producto, url_imagen, genero, tipo_prenda) VALUES
(1, 'Polera Negra DevilMay', 'Polera 100% algodon corte oversized', 15000.00, 'L', 'https://img.com/polera1.jpg', 'UNISEX', 'SUPERIOR'),
(2, 'Chaqueta de Cuero Vintage', 'Chaqueta autentica cuero italiano', 85000.00, 'M', 'https://img.com/chaqueta.jpg', 'MASCULINO', 'SUPERIOR'),
(3, 'Pantalón Cargo Urbano', 'Pantalon cargo negro multibolsillos', 35000.00, '42', 'https://img.com/cargo.jpg', 'UNISEX', 'INFERIOR'),
(4, 'Zapatillas High Top', 'Zapatillas de lona caña alta blancas', 42000.00, '41', 'https://img.com/zapatillas.jpg', 'UNISEX', 'CALZADO'),
(5, 'Gorro Beanie Gris', 'Gorro tejido de lana suave', 12000.00, 'U', 'https://img.com/gorro.jpg', 'UNISEX', 'ACCESORIO');


-- ==========================================
-- BASE DE DATOS: db_inventario
-- ==========================================
USE db_inventario;

-- Nota: id_producto debe coincidir con los insertados en db_catalogo
INSERT INTO stock (id_stock, id_producto, estado_inventario) VALUES
(1, 1, 'DISPONIBLE'),
(2, 2, 'RESERVADO'),
(3, 3, 'DISPONIBLE'),
(4, 4, 'VENDIDO'),
(5, 5, 'DISPONIBLE');


-- ==========================================
-- BASE DE DATOS: db_promociones
-- ==========================================
USE db_promociones;

INSERT INTO cupon (codigo_cupon, porcentaje_descuento, fecha_expiracion, activo) VALUES
('DEVILMAY20', 20.0, '2026-12-31', 1),
('VERANO10', 10.0, '2026-03-01', 1),
('BLACKFRIDAY50', 50.0, '2025-11-30', 0);


-- ==========================================
-- BASE DE DATOS: db_logistica
-- ==========================================
USE db_logistica;

INSERT INTO metodo_entrega (id_metodo_entrega, descripcion, costo_adicional) VALUES
(1, 'Despacho a Domicilio Standard', 3500.00),
(2, 'Despacho Express (Mismo día)', 6000.00),
(3, 'Retiro en Tienda Física', 0.00);

INSERT INTO estado_despacho (id_estado_despacho, descripcion) VALUES
(1, 'PREPARANDO'),
(2, 'EN_CAMINO'),
(3, 'ENTREGADO'),
(4, 'FALLIDO');


-- ==========================================
-- BASE DE DATOS: db_reservas
-- ==========================================
USE db_reservas;

INSERT INTO estado_orden (id_estado_orden, descripcion) VALUES
(1, 'PENDIENTE_PAGO'),
(2, 'PAGADA'),
(3, 'CANCELADA');

INSERT INTO metodo_pago (id_metodo_pago, descripcion) VALUES
(1, 'Transferencia Bancaria'),
(2, 'Webpay Plus / Tarjeta'),
(3, 'MercadoPago');


-- ==========================================
-- BASE DE DATOS: db_seguridad (Auth)
-- ==========================================
USE db_seguridad;

-- Claves encriptadas BCrypt u otro dependiendo de su implementación, aquí ejemplo genérico
INSERT INTO usuario (id_usuario, nombre_usuario, password, rol) VALUES
(1, 'admin_joaquin', '12345', 'ADMIN'),
(2, 'admin_kevin', '12345', 'ADMIN'),
(3, 'admin_ignacio', '12345', 'ADMIN'),
(4, 'cliente1', 'password123', 'CLIENTE');


-- ==========================================
-- BASE DE DATOS: db_calificaciones
-- ==========================================
USE db_calificaciones;

INSERT INTO resena (id_resena, id_producto, calificacion, comentario_cliente, fecha_creacion) VALUES
(1, 1, 5, 'Excelente polera, muy buena calidad de la tela.', '2026-06-20'),
(2, 2, 4, 'Me quedó un poco grande pero el cuero es increíble.', '2026-06-19'),
(3, 4, 5, 'Zapatillas super cómodas, las recomiendo 100%.', '2026-06-21');


-- ==========================================
-- BASE DE DATOS: db_devoluciones
-- ==========================================
USE db_devoluciones;

INSERT INTO devolucion (id_devolucion, nro_orden, motivo, datos_reembolso, estado) VALUES
(1, 101, 'Me equivoqué de talla, pedí M y necesitaba L.', 'Cuenta Rut: 12345678-9', 'PENDIENTE'),
(2, 102, 'La prenda venía con un pequeño piquete.', 'Cuenta Corriente Banco Estado', 'APROBADA');


-- ==========================================
-- BASE DE DATOS: db_reembolsos
-- ==========================================
USE db_reembolsos;

INSERT INTO reembolso (id_reembolso, id_devolucion, monto_devuelto, banco_destino) VALUES
(1, 2, 15000.00, 'Banco Estado');


-- ==========================================
-- BASE DE DATOS: db_comprobantes
-- ==========================================
USE db_comprobantes;

INSERT INTO comprobante (id_comprobante, nro_orden, codigo_cupon, fecha_pago, total_pagado) VALUES
(1, 105, 'DEVILMAY20', '2026-06-21 10:30:00', 12000.00);

-- Fin del script
