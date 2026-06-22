package com.example.service_calificaciones.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Modelo que representa a las reseñas de productos.")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental", example = "1")
    @Column(name = "ID Reseña", nullable = false)
    private Long id_resena;

    @Schema(description = "ID del respectivo producto", example = "2")
    @Column(name = "ID Producto", nullable = false, unique = true)
    private Long idProducto;

    @Schema(description = "Calificación del producto en estrellas", example = "5")
    @Column(name = "Calificación", nullable = false)
    private Integer calificacion;

    @Schema(description = "Comentarios dejado por el cliente", example = "la polera me quedó excelente")
    @Column(name = "Comentario Cliente", nullable = false)
    private String comentarioCliente;

    @Schema(description = "Fecha en que se publicó la reseña", example = "04/02/2026")
    @JsonFormat(pattern = "DD-MM-YYYY")
    @Column(name = "Fecha de Publicación", nullable = false)
    private LocalDate fechaPublicacion = LocalDate.now();

}
