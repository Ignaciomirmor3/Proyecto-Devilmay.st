package com.example.service_calificaciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.service_calificaciones.model.Resena;

@Repository
public interface ResenaRepository extends JpaRepository<Resena,Long>{

    List<Resena> findByCalificacion(Integer calificacion);

}
