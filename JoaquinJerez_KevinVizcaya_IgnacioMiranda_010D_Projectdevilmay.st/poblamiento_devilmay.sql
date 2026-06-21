-- ==========================================
-- SCRIPT DE POBLAMIENTO DEVILMAY.ST V5
-- Base de Datos MySQL
-- ==========================================

-- 1. POBLAMIENTO DE CATÁLOGO (db_catalogo)
USE db_catalogo;

INSERT INTO producto (nombre_producto, descripcion_producto, precio_producto, talla_producto, genero, tipo_prenda, url_imagen) VALUES
('Chaqueta de Cuero V', 'Chaqueta de cuero auténtico edición V, unidad única y exclusiva.', 150000, 'L', 'MASCULINO', 'EXTERIOR', 'http://img.devilmay.st/chaqueta_v.png'),
('Abrigo Carmesí Dante', 'Abrigo largo rojo inspirado en Dante, diseño artesanal único.', 250000, 'XL', 'MASCULINO', 'EXTERIOR', 'http://img.devilmay.st/abrigo_dante.png'),
('Botas de Combate Nero', 'Botas de cuero con refuerzo metálico, talla única 42.', 85000, '42', 'MASCULINO', 'CALZADO', 'http://img.devilmay.st/botas_nero.png'),
('Corset Gótico Lady', 'Corset negro con detalles de encaje blanco.', 70000, 'M', 'FEMENINO', 'SUPERIOR', 'http://img.devilmay.st/corset_lady.png'),
('Pantalón Táctico', 'Pantalón de combate con múltiples bolsillos tácticos.', 90000, 'M', 'UNISEX', 'INFERIOR', 'http://img.devilmay.st/pantalon_tactico.png');

-- 2. POBLAMIENTO DE INVENTARIO (db_inventario)
USE db_inventario;

-- Nota: Solo existe una unidad de cada producto.
INSERT INTO stock (id_producto, estado_inventario) VALUES
(1, 'DISPONIBLE'),
(2, 'DISPONIBLE'),
(3, 'DISPONIBLE'),
(4, 'DISPONIBLE'),
(5, 'DISPONIBLE');

-- 3. POBLAMIENTO DE PROMOCIONES (db_promociones)
USE db_promociones;

INSERT INTO cupon (codigo, porcentaje_descuento, fecha_expiracion, is_active) VALUES
('DEVILMAY20', 20, '2026-12-31 23:59:59', 1),
('JACKPOT50', 50, '2026-12-31 23:59:59', 1),
('VERANO10', 10, '2025-12-31 23:59:59', 0); -- Cupón inactivo/vencido
