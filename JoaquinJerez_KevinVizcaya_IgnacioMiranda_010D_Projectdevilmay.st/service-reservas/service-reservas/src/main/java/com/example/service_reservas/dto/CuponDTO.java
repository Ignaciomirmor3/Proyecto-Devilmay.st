package com.example.service_reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

// Simulando objeto que viene de WebClient
public class CuponDTO {

    private String codigoCupon;
    private Integer porcentajeDescuento;
}
