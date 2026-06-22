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
(3, 3, 'EN_TRANSITO'),
(4, 4, 'VENDIDO'),
(5, 5, 'DEVUELTO');


-- ==========================================
-- BASE DE DATOS: db_promociones
-- ==========================================
USE db_promociones;

INSERT INTO cupon (codigo_cupon, porcentaje_descuento, fecha_expiracion, activo) VALUES
('VERANO2026', 20.0, '2026-12-31', 1),
('DESCUENTO10', 10.0, '2026-12-31', 1),
('PRIMERACOMPRA', 15.0, '2026-12-31', 1);


-- ==========================================
-- BASE DE DATOS: db_logistica
-- ==========================================
USE db_logistica;

INSERT INTO `estado_despacho` (`id_estado_despacho`, `nombre_estado`) VALUES 
(1, 'En Camino'),
(2, 'Entregado');

INSERT INTO `metodo_entrega` (`id_metodo_entrega`, `nombre_metodo`) VALUES 
(1, 'Starken'),
(2, 'Presencial');

INSERT INTO `despacho` (`id_estado_despacho`, `id_metodo_entrega`, `nro_orden`, `comuna`, `direccion_envio`) VALUES 
('2', '1', '1', 'Padre Hurtado', 'Camino San Alberto Hurtado 123'),
('2', '2', '2', 'Maipú', 'Av. Pajaritos 4560'),
('2', '1', '3', 'Peñaflor', 'Vicuna Mackenna 789'),
('2', '1', '4', 'Cerrillos', 'Av. Los Cerrillos 321'),
('2', '2', '5', 'Estación Central', 'Alameda 3920'),
('2', '1', '6', 'Providencia', 'Av. Providencia 1500'),
('2', '2', '7', 'Santiago', 'Moneda 1040'),
('1', '1', '8', 'Padre Hurtado', 'Primera Avenida 555'),
('1', '1', '9', 'Maipú', '5 de Abril 200'),
('1', '2', '10', 'Talagante', 'Arturo Prat 880'),
('1', '1', '11', 'Ñuñoa', 'Irarrazaval 3000'),
('1', '1', '12', 'Macul', 'Av. Macul 2500'),
('1', '2', '13', 'Padre Hurtado', 'San Ignacio 444'),
('1', '1', '14', 'Peñaflor', 'Manuel Rodriguez 100'),
('1', '1', '15', 'Maipú', 'Olimpo 800'),
('1', '2', '16', 'Santiago', 'Huérfanos 1200'),
('1', '1', '17', 'Cerrillos', 'Camino a Melipilla 7000'),
('1', '1', '18', 'Estación Central', 'Ecuador 4500'),
('1', '2', '19', 'Providencia', 'Pedro de Valdivia 300'),
('1', '1', '20', 'Padre Hurtado', 'Los Silos 900');


-- ==========================================
-- BASE DE DATOS: db_reservas
-- ==========================================
USE db_reservas;

INSERT INTO `estado_orden` (`id_estado_orden`, `nombre_estado`) VALUES 
(1, 'Pendiente'),
(2, 'Pagado'),
(3, 'Cancelado');

INSERT INTO `metodo_pago` (`id_metodo_pago`, `nombre_metodo`) VALUES 
(1, 'Efectivo'),
(2, 'Transferencia');

INSERT INTO `orden` (`precio_total`, `telefono_respaldo`, `fecha_hora`, `id_carrito`, `id_estado_orden`, `id_metodo_pago`, `nro_orden`, `correo_respaldo`, `instagram_cliente`, `nombre_cliente`, `rut_cliente`, `codigo_cupon`) VALUES 
('15000', '987654321', '2026-05-26 10:30:00.000000', '1', '2', '2', NULL, 'juan.perez@gmail.com', '@juanito_p', 'Juan Perez', '19.456.789-0', NULL),
('25500', '912345678', '2026-05-26 11:15:00.000000', '2', '2', '1', NULL, 'maria.g@hotmail.com', '@mary.g', 'Maria Gonzalez', '18.123.456-7', 'VERANO2026'),
('8900', '998877665', '2026-05-26 12:40:00.000000', '3', '2', '2', NULL, 'carlos.soto@yahoo.com', '@csoto_99', 'Carlos Soto', '20.555.666-8', NULL),
('42000', '955443322', '2026-05-26 14:20:00.000000', '4', '2', '2', NULL, 'ana.mendez@gmail.com', '@anita.m', 'Ana Mendez', '17.888.999-K', 'DESCUENTO10'),
('12500', '944332211', '2026-05-26 15:05:00.000000', '5', '2', '1', NULL, 'luis.rojas@gmail.com', '@lrojas', 'Luis Rojas', '16.333.222-1', NULL),
('31000', '933221100', '2026-05-26 16:10:00.000000', '6', '2', '2', NULL, 'cami.silva@hotmail.com', '@cami_silva', 'Camila Silva', '21.111.222-3', NULL),
('18000', '922110099', '2026-05-26 17:30:00.000000', '7', '2', '1', NULL, 'diego.tapia@gmail.com', '@diegot', 'Diego Tapia', '19.999.888-4', 'PRIMERACOMPRA'),
('9500', '911009988', '2026-05-27 09:15:00.000000', '8', '1', '2', NULL, 'valeria.c@gmail.com', '@vale.c', 'Valeria Castro', '20.444.333-5', NULL),
('55000', '900998877', '2026-05-27 10:45:00.000000', '9', '2', '2', NULL, 'jorge.munoz@yahoo.com', '@jorge_m', 'Jorge Muñoz', '15.777.666-6', NULL),
('22000', '988776655', '2026-05-27 11:20:00.000000', '10', '1', '1', NULL, 'sofia.vargas@gmail.com', '@sofi.vargas', 'Sofia Vargas', '21.987.654-3', 'VERANO2026'),
('13500', '977665544', '2026-05-27 12:10:00.000000', '11', '2', '2', NULL, 'matias.l@hotmail.com', '@matias_l', 'Matias Lopez', '19.333.444-2', NULL),
('48000', '966554433', '2026-05-27 13:50:00.000000', '12', '3', '2', NULL, 'fran.pinto@gmail.com', '@franpinto', 'Francisca Pinto', '18.555.444-1', NULL),
('7200', '955443322', '2026-05-27 14:30:00.000000', '13', '2', '1', NULL, 'seba.diaz@gmail.com', '@seba_diaz', 'Sebastian Diaz', '20.123.987-6', 'DESCUENTO10'),
('29000', '944332211', '2026-05-27 15:45:00.000000', '14', '2', '2', NULL, 'dani.herrera@yahoo.com', '@dani.h', 'Daniela Herrera', '17.654.321-0', NULL),
('16800', '933221100', '2026-05-27 16:20:00.000000', '15', '1', '1', NULL, 'nicolas.cruz@gmail.com', '@nico_cruz', 'Nicolas Cruz', '19.888.777-5', NULL),
('35000', '922110099', '2026-05-27 17:15:00.000000', '16', '2', '2', NULL, 'cata.morales@hotmail.com', '@cata_m', 'Catalina Morales', '21.444.555-K', 'PRIMERACOMPRA'),
('11000', '911009988', '2026-05-28 09:30:00.000000', '17', '2', '1', NULL, 'pedro.navarro@gmail.com', '@pedrito.n', 'Pedro Navarro', '16.999.111-2', NULL),
('64000', '900998877', '2026-05-28 10:10:00.000000', '18', '3', '2', NULL, 'andrea.rios@gmail.com', '@andrea_rios', 'Andrea Rios', '18.222.333-4', NULL),
('19500', '998877665', '2026-05-28 11:45:00.000000', '19', '2', '2', NULL, 'tomas.flores@yahoo.com', '@tomas.f', 'Tomas Flores', '20.777.888-9', 'VERANO2026'),
('27000', '987654321', '2026-05-28 12:30:00.000000', '20', '1', '1', NULL, 'paz.araya@gmail.com', '@paz_araya', 'Paz Araya', '19.111.999-7', NULL);


-- ==========================================
-- BASE DE DATOS: db_seguridad (Auth)
-- ==========================================
USE db_seguridad;

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

INSERT INTO `estado_devolucion` (`id_estado_devolucion`, `nombre_estado`) VALUES 
(1, 'Solicitada'),
(2, 'Aprobada'),
(3, 'Rechazada');

INSERT INTO `devolucion` (`id_devolucion`, `nro_orden`, `motivo`, `id_estado_devolucion`) VALUES 
(1, '1', 'Producto llegó dañado', '1'),
(2, '2', 'Talla incorrecta', '2'),
(3, '3', 'Me arrepentí de la compra', '3'),
(4, '4', 'Color no corresponde a la foto', '1'),
(5, '5', 'Faltaban piezas en la caja', '2'),
(6, '6', 'Producto defectuoso', '1'),
(7, '7', 'No cumplió mis expectativas', '2');


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

INSERT INTO `boleta` (`nro_boleta`, `nro_orden`, `codigo_cupon`, `fecha_pago`) VALUES 
(1, '1', NULL, '2026-05-26 10:30:00.000000'),
(2, '2', 'VERANO2026', '2026-05-26 11:15:00.000000'),
(3, '3', NULL, '2026-05-26 12:40:00.000000'),
(4, '4', 'DESCUENTO10', '2026-05-26 14:20:00.000000'),
(5, '5', NULL, '2026-05-26 15:05:00.000000'),
(6, '6', NULL, '2026-05-26 16:10:00.000000'),
(7, '7', 'PRIMERACOMPRA', '2026-05-26 17:30:00.000000'),
(8, '9', NULL, '2026-05-27 10:45:00.000000'),
(9, '11', NULL, '2026-05-27 12:10:00.000000'),
(10, '13', 'DESCUENTO10', '2026-05-27 14:30:00.000000'),
(11, '14', NULL, '2026-05-27 15:45:00.000000'),
(12, '16', 'PRIMERACOMPRA', '2026-05-27 17:15:00.000000'),
(13, '17', NULL, '2026-05-28 09:30:00.000000'),
(14, '19', 'VERANO2026', '2026-05-28 11:45:00.000000');

-- Fin del script
