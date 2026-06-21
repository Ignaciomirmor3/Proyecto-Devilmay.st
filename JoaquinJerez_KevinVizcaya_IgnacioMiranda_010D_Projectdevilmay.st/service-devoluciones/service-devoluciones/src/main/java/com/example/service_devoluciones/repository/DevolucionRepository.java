package com.example.service_devoluciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.service_devoluciones.model.Devolucion;

public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {

    @Query("""
        SELECT o FROM Devolucion o WHERE
        (:idDevolucion IS NULL OR o.estadoDevolucion.id_Estado_Devolucion = :idDevolucion)
        """)
    List<Devolucion> findByDevolucion(@Param("idDevolucion") Long idDevolucion);
}
