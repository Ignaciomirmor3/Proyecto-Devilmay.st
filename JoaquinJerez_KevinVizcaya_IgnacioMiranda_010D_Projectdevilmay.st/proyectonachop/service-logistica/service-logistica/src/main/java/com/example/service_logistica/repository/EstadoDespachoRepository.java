package com.example.service_logistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.service_logistica.model.EstadoDespacho;

@Repository
public interface EstadoDespachoRepository extends JpaRepository<EstadoDespacho, Long>{

}
