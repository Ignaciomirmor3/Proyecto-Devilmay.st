package com.example.service_catalogo.model;

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
@Table(name = "tipo_prenda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoPrenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID Tipo", nullable = false)
    private Long id_tipo;

    @Column(name="Nombre", nullable = false, length = 200)
    private String nombreTipo;
}
