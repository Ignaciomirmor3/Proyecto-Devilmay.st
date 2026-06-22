package com.example.service_carrito.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarritoDTO {
    
    private Long id;

    @NotNull(message = "El ID del producto no puede ser nulo")
    private Long idProducto;

    @NotNull(message = "La cantidad no puede ser nula")
    @Max(value = 1, message = "Lo sentimos, este artículo es exclusivo y solo se puede agregar 1 unidad")
    private Integer cantidad;
}
