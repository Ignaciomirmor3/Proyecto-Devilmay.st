package com.example.service_promociones.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cupon")
@Data
public class Cupon {
    @Id
    @Column(name = "codigo_cupon", length = 50, nullable = false)
    private String codigoCupon;

    @Column(name = "porcentaje_descuento", nullable = false)
    private Integer porcentajeDescuento;

    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDate fechaExpiracion;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @PrePersist
    public void prePersist() {
        if(this.codigoCupon != null) {
            this.codigoCupon = this.codigoCupon.toUpperCase().trim();
        }
    }
}
