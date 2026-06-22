package com.example.service_catalogo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID Producto", nullable = false)
    private Long id_producto;

    @jakarta.validation.constraints.NotBlank(message = "El nombre no puede estar vacío")
    @Column(name="Nombre", nullable = false, length = 200)
    private String nombreProducto;
    
    @jakarta.validation.constraints.NotBlank(message = "La descripción no puede estar vacía")
    @Column(name="Descripción", nullable = false, length = 500)
    private String descripcionProducto;

    @jakarta.validation.constraints.NotNull(message = "El precio no puede ser nulo")
    @jakarta.validation.constraints.PositiveOrZero(message = "El precio debe ser cero o positivo")
    @Column(name="Precio", nullable = false )
    private Integer precioProducto;

    @jakarta.validation.constraints.NotBlank(message = "La talla no puede estar vacía")
    @Column(name="Talla", nullable = false, length = 5)
    private String tallaProducto;

    @jakarta.validation.constraints.NotBlank(message = "El estado no puede estar vacío")
    @Column(name="Estado",nullable = false, length = 50)
    private String estadoInventario;

    @jakarta.validation.constraints.NotBlank(message = "La URL de la imagen no puede estar vacía")
    @Column(name="URL Imagen", nullable = false, length = 800)
    private String urlImagen;

    // claves foraneas genero y tipoprenda
    // claves foraneas genero y tipoprenda obligatorias
    @jakarta.validation.constraints.NotNull(message = "El género es obligatorio")
    @ManyToOne(optional = false) 
    @JoinColumn(name = "id_genero", referencedColumnName = "ID", nullable = false)
    private Genero genero;

    @jakarta.validation.constraints.NotNull(message = "El tipo de prenda es obligatorio")
    @ManyToOne(optional = false) 
    @JoinColumn(name = "id_tipo", referencedColumnName = "ID Tipo", nullable = false)
    private TipoPrenda tipoPrenda;

}
