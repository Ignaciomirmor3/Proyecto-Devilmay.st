package com.example.service_reembolsos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ReembolsoRequestDTO {
    @NotNull(message = "El id de devolucion no puede ser nulo")
    private Long idDevolucion;

    @NotNull(message = "El monto devuelto no puede ser nulo")
    @Positive(message = "El monto debe ser mayor a 0")
    private Double montoDevuelto;

    @NotBlank(message = "El banco de destino no puede estar vacio")
    private String bancoDestino;
}
