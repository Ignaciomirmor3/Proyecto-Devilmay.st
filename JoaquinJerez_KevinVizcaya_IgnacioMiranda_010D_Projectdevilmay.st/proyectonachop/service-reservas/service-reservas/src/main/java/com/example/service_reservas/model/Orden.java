package com.example.service_reservas.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nro_Orden;

    // Referencia al otro microservicio
    private Long id_Carrito;

    private Integer precio_Total;

    @Column(name = "fecha_Hora")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fecha_Hora = LocalDateTime.now();

    private String nombre_Cliente;
    private String rut_Cliente;

    private String instagram_Cliente;
    private Integer telefono_Respaldo;
    private String correo_Respaldo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Metodo_Pago")
    private MetodoPago metodoPago;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Estado_Orden")
    private EstadoOrden estadoOrden;

    // No se guardará en la base de datos, solo se usará para mostrar los datos de logística al obtener la orden
    @Transient
    private Object datosDespacho; 
}
