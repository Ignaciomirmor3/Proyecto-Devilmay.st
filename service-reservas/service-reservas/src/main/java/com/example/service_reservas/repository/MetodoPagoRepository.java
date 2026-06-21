package com.example.service_reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.service_reservas.model.MetodoPago;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {

}
