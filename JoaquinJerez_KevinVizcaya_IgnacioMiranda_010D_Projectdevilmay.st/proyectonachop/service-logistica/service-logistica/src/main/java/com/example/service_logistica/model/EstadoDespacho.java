package com.example.service_logistica.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoDespacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Estado_Despacho;

    private String nombre_Estado;
}
