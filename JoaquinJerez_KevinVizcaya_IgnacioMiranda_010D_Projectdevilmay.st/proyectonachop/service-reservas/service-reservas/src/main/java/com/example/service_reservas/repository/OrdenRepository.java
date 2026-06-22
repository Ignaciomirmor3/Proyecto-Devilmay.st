package com.example.service_reservas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.service_reservas.model.Orden;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long>{

    @Query("""
        SELECT o FROM Orden o WHERE
        (:idOrden IS NULL OR o.estadoOrden.id_Estado_Orden = :idOrden) AND
        (:idPago IS NULL OR o.metodoPago.id_Metodo_Pago = :idPago)
        """)
    List<Orden> findByOrdenYPago(
        @Param("idOrden") Long idOrden, 
        @Param("idPago") Long idPago
    );
}
