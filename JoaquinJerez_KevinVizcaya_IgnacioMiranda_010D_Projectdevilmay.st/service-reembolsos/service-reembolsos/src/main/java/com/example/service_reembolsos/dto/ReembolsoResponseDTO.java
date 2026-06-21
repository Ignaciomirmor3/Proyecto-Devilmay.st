package com.example.service_reembolsos.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReembolsoResponseDTO {
    private Long idDevolucion;
    private Double montoDevuelto;
    private String bancoDestino;
    private LocalDateTime fechaTransaccion;
}
