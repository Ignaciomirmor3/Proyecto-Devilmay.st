package com.example.service_devoluciones.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Schema(description = "Modelo que representa una devolución")
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID principal autoincremental", example = "1")
    private Long id_Devolucion;

    @Column(nullable = false, unique = true)
    @Schema(description = "ID único, principio de ID compartida", example = "3")
    private Long nro_Orden;

    @Schema(description = "Motivo de devolución", example = "Insatisfecho con el producto")
    private String motivo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Estado_Devolucion")
    @Schema(description = "ID del estado de la devolución correspondiente en la tabla EstadoDevolucion", example = "2")
    private EstadoDevolucion estadoDevolucion;

    @Transient
    @Schema(description = "Datos completos de una solicitud de reembolso. Se cargan en tiempo de ejecución vía WebClient", accessMode = Schema.AccessMode.READ_ONLY)
    private Object datos_Reembolso;
}
