package com.example.service_carrito.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarritoDTO {
    
    private Long id;

    @NotNull(message = "El ID del producto no puede ser nulo")
    private Long idProducto;

}
