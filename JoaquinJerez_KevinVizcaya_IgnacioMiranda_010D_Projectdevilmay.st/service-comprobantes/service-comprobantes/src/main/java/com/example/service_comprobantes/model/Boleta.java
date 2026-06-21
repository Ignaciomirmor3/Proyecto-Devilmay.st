package com.example.service_comprobantes.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Schema(description = "Modelo que representa una boleta electrónica")
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID principal autoincremental", example = "1")
    private Long nro_Boleta;

    @Column(nullable = false, unique = true)
    @Schema(description = "ID único, principio de ID compartida", example = "3")
    private Long nro_Orden;

    @Column(nullable = true)
    @Schema(description = "Codigo único que el usuario usa para aplicar descuento", example = "VERANO2026")
    private String codigo_Cupon;
    
    @Column(name = "fecha_Pago")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Schema(description = "Fecha de la transacción", example = "15/03/2024 15:45:23")
    private LocalDateTime fecha_Pago = LocalDateTime.now();
}
