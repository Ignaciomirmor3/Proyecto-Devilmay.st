package com.example.service_devoluciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

// Simulando objeto que viene de WebClient
public class ReembolsoDTO {

    private Long idDevolucion;
    private Integer montoDevuelto;
}
