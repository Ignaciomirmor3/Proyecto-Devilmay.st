package com.example.service_carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.service_carrito.model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    java.util.Optional<Carrito> findByIdProducto(Long idProducto);
}
