package com.example.service_reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.service_reservas.model.EstadoOrden;

public interface EstadoOrdenRepository extends JpaRepository<EstadoOrden, Long> {

}
