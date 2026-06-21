package com.example.service_logistica.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

@Schema(description = "Modelo que representa un despacho")
public class Despacho {

    @Id
    @Schema(description = "ID único, principio de ID compartida", example = "1")
    private Long nro_Orden;

    @Schema(description = "Dirección de envío del cliente", example = "Luis Pasteur, 275")
    private String direccion_Envio;

    @Schema(description = "Comuna de la dirección de envío", example = "Padre Hurtado")
    private String comuna;

    // Llaves foráneas
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Estado_Despacho")
    @Schema(description = "ID del estado del despacho correspondiente en la tabla EstadoDespacho", example = "2")
    private EstadoDespacho estadoDespacho;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Metodo_Entrega")
    @Schema(description = "ID del método de entrega correspondiente en la tabla MetodoEntrega", example = "1")
    private MetodoEntrega metodoEntrega;
}
