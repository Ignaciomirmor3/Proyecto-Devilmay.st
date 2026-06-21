package com.example.service_devoluciones.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

@Schema(description = "Modelo que representa el estado de una devolución")
public class EstadoDevolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID principal autoincremental", example = "1")
    private Long id_Estado_Devolucion;
    
    @Schema(description = "Nombre del estado que la devolución tendrá en el tiempo", example = "Entregado")
    private String nombre_Estado;
}
