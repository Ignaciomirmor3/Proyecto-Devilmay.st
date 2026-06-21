package com.example.service_comprobantes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.service_comprobantes.model.Boleta;

public interface BoletaRepository extends JpaRepository<Boleta, Long> {
}
