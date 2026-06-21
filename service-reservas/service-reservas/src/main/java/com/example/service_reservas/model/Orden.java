package com.example.service_reservas.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
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

@Schema(description = "Modelo que representa una orden")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID principal autoincremental", example = "1")
    private Long nro_Orden;

    // Referencia al otro microservicio
    @Column(nullable = false, unique = true)
    @Schema(description = "ID único, principio de ID compartida", example = "3")
    private Long id_Carrito;

    @Column(nullable = false)
    @Schema(description = "Precio total de la compra traída desde carrito", example = "5000")
    private Integer precio_Total;

    @Column(nullable = true)
    @Schema(description = "Codigo único que el usuario usa para aplicar descuento", example = "VERANO2026")
    private String codigo_Cupon;

    @Column(name = "fecha_Hora")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Schema(description = "Fecha de la creación de la orden", example = "15/03/2024 15:45:23")
    private LocalDateTime fecha_Hora = LocalDateTime.now();

    @Schema(description = "Nombre completo del cliente", example = "Pepito Sandoval")
    private String nombre_Cliente;
    @Schema(description = "RUN o RUT del cliente", example = "12.354.678-k")
    private String rut_Cliente;

    @Schema(description = "Instagram del cliente", example = "@pepito.1234")
    private String instagram_Cliente;
    @Schema(description = "Teléfono de contacto del cliente", example = "123456789")
    private Integer telefono_Respaldo;
    @Schema(description = "Correo de respaldo del cliente", example = "pepito1234@gmail.com")
    private String correo_Respaldo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Metodo_Pago")
    @Schema(description = "ID del método de pago correspondiente en la tabla MetodoPago", example = "2")
    private MetodoPago metodoPago;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Estado_Orden")
    @Schema(description = "ID del estado de la orden correspondiente en la tabla EstadoOrden", example = "2")
    private EstadoOrden estadoOrden;

    // No se guardará en la base de datos, solo se usará para mostrar los datos de logística al obtener la orden
    @Transient
    @Schema(description = "Datos completos del despacho. Se cargan en tiempo de ejecución vía WebClient", accessMode = Schema.AccessMode.READ_ONLY)
    private Object datos_Despacho; 

    @Transient
    @Schema(description = "Datos completos de la boleta. Se cargan en tiempo de ejecución vía WebClient", accessMode = Schema.AccessMode.READ_ONLY)
    private Object datos_Boleta; 
}
