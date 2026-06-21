package com.example.service_promociones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CuponValidacionResponseDTO {
    private boolean valido;
    private Integer porcentajeDescuento;
    private String mensaje;
}
