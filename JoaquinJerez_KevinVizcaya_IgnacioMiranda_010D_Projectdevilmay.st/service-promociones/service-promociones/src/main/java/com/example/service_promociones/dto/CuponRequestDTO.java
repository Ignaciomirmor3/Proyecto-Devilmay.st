package com.example.service_promociones.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CuponRequestDTO {
    @NotBlank(message = "El codigo no puede estar vacio")
    private String codigoCupon;

    @NotNull(message = "El porcentaje no puede ser nulo")
    @Min(value = 1, message = "El descuento minimo es 1%")
    @Max(value = 100, message = "El descuento miximo es 100%")
    private Integer porcentajeDescuento;

    @NotNull(message = "La fecha de expiracion es obligatoria")
    @jakarta.validation.constraints.FutureOrPresent(message = "La fecha de expiracion debe ser hoy o en el futuro")
    private LocalDate fechaExpiracion;
}
