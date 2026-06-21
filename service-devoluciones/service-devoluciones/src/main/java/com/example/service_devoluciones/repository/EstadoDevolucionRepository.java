package com.example.service_devoluciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.service_devoluciones.model.EstadoDevolucion;

public interface EstadoDevolucionRepository extends JpaRepository<EstadoDevolucion, Long> {

}
