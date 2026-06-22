package com.example.service_calificaciones.model;

import java.sql.Date;

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
@Table(name = "Reseñas")
@NoArgsConstructor
@AllArgsConstructor
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID Reseña", nullable = false)
    private Long id_resena;

    @Column(name = "ID Producto", nullable = false)
    private Long idProducto;

    @Column(name = "Calificación", nullable = false)
    private Integer calificacion;

    @Column(name = "Comentario Cliente", nullable = false)
    private String comentarioCliente;

    @Column(name = "Fecha de Publicación", nullable = false)
    private Date fechaPublicacion;

}
