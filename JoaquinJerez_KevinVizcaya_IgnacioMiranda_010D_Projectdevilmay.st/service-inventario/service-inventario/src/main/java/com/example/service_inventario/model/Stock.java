package com.example.service_inventario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private Long idStock; 

    @jakarta.validation.constraints.NotNull(message = "El ID del producto no puede ser nulo")
    @Column(name="ID Producto", nullable = false, unique = true)
    private Long idProducto;

    @Enumerated(EnumType.STRING)
    @jakarta.validation.constraints.NotNull(message = "El estado del inventario no puede ser nulo")
    @Column(name="Estado_Inventario", nullable = false)
    private EstadoInventario estadoInventario;

}
