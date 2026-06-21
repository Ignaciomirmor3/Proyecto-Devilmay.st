package com.example.service_reembolsos.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reembolso")
@Data
public class Reembolso {
    @Id
    @Column(name = "id_devolucion")
    private Long idDevolucion;

    @Column(name = "monto_devuelto", nullable = false)
    private Double montoDevuelto;

    @Column(name = "banco_destino", nullable = false, length = 100)
    private String bancoDestino;

    @Column(name = "fecha_transaccion")
    private LocalDateTime fechaTransaccion = LocalDateTime.now();

}
