package com.example.service_logistica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.service_logistica.model.Despacho;

@Repository
public interface DespachoRepository extends JpaRepository<Despacho, Long> {

    @Query("""
        SELECT d FROM Despacho d WHERE
        (:idDespacho IS NULL OR d.estadoDespacho.id_Estado_Despacho = :idDespacho) AND
        (:idEntrega IS NULL OR d.metodoEntrega.id_Metodo_Entrega = :idEntrega)
        """)
    List<Despacho> findByDespachoYEntrega(
        @Param("idDespacho") Long idDespacho, 
        @Param("idEntrega") Long idEntrega
    );
}
