package com.example.service_promociones.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CuponResponseDTO {
    private String codigoCupon;
    private Integer porcentajeDescuento;
    private LocalDate fechaExpiracion;
    private Boolean activo;
}
