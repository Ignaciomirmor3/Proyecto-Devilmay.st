package com.example.service_catalogo.dto;

import com.example.service_catalogo.model.Genero;
import com.example.service_catalogo.model.TipoPrenda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductoRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombreProducto;
    
    @NotBlank(message = "La descripción no puede estar vacia")
    private String descripcionProducto;

    @NotNull(message = "El precio no puede ser nulo")
    @PositiveOrZero(message = "El precio debe ser cero o positivo")
    private Integer precioProducto;

    @NotBlank(message = "La talla no puede estar vacia")
    private String tallaProducto;

    @NotBlank(message = "El estado no puede estar vacio")
    private String estadoInventario;

    @NotBlank(message = "La URL de la imagen no puede estar vacia")
    private String urlImagen;

    @NotNull(message = "El genero es obligatorio")
    private Genero genero;

    @NotNull(message = "El tipo de prenda es obligatorio")
    private TipoPrenda tipoPrenda;
}
