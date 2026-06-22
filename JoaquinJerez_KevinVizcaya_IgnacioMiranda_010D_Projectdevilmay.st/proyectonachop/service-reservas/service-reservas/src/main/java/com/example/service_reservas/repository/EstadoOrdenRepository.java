package com.example.service_reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.service_reservas.model.EstadoOrden;

@Repository
public interface EstadoOrdenRepository extends JpaRepository<EstadoOrden, Long> {

}
