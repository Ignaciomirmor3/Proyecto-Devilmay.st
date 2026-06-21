package com.example.service_logistica.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Schema(description = "Modelo que representa el estado de un despacho")
public class EstadoDespacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID principal autoincremental", example = "1")
    private Long id_Estado_Despacho;

    @Schema(description = "Nombre del estado que el despacho tendrá en el tiempo", example = "En camino")
    private String nombre_Estado;
}
