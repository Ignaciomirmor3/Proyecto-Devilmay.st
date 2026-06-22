package com.example.service_inventario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="stock")
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable = false)
    private Long id_estado; 

    @jakarta.validation.constraints.NotNull(message = "El nombre del estado no puede ser nulo")
    @Column(name="Estado", nullable = false)
    private Integer nombreEstado;

    @jakarta.validation.constraints.NotNull(message = "El ID del producto no puede ser nulo")
    @Column(name="ID Producto", nullable = false)
    private Long idProducto;

}
