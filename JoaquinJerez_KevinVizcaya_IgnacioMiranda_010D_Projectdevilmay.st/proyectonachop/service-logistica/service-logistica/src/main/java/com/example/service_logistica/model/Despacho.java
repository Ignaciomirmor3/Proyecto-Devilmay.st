package com.example.service_logistica.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Despacho {

    @Id
    private Long nro_Orden;

    private String direccion_Envio;
    private String comuna;

    // Llaves foráneas
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Estado_Despacho")
    private EstadoDespacho estadoDespacho;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Metodo_Entrega")
    private MetodoEntrega metodoEntrega;
}
