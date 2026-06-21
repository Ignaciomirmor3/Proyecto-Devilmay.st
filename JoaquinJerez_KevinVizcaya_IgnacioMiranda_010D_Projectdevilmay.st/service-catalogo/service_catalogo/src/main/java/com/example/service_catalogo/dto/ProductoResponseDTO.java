package com.example.service_catalogo.dto;

import com.example.service_catalogo.model.Genero;
import com.example.service_catalogo.model.TipoPrenda;
import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Long id_producto;
    private String nombreProducto;
    private String descripcionProducto;
    private Integer precioProducto;
    private String tallaProducto;
    private String estadoInventario;
    private String urlImagen;
    private Genero genero;
    private TipoPrenda tipoPrenda;
}
