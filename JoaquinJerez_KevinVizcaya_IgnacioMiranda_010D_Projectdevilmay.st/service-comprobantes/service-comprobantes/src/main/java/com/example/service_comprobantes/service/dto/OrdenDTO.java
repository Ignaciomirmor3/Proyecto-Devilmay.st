package com.example.service_comprobantes.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

// Simulando objeto que viene de WebClient
public class OrdenDTO {

    private Long nroOrden;
    private String codigoCupon;
    private EstadoOrdenDTO estadoOrden;
}
