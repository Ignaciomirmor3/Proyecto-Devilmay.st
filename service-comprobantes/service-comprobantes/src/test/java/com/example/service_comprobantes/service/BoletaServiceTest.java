package com.example.service_comprobantes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.service_comprobantes.model.Boleta;
import com.example.service_comprobantes.repository.BoletaRepository;
import com.example.service_comprobantes.service.dto.EstadoOrdenDTO;
import com.example.service_comprobantes.service.dto.OrdenDTO;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class) // Simulando objetos con Mockito
public class BoletaServiceTest {

    @Mock
    private BoletaRepository boletaRepository; // Repositorio falso

    @Mock
    private WebClient.Builder webClientBuilder; // WebClient falso

    @InjectMocks
    private BoletaService boletaService; // Se injecta en el repositorio real


    // Los Mocks necesarios para que WebClient funcione
    @Mock
    private WebClient webClient;
    @Mock
    private WebClient.RequestHeadersUriSpec uriSpec;
    @Mock
    private WebClient.RequestHeadersSpec headerSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;


    @Test
    @DisplayName("Debe guardar boleta cuando la orden esté pagada")
    void crearBoleta_Pagada() {
        
        // --- 1. PREPARACIÓN ---
        // La boleta simulada de entrada al método
        Boleta boletaEntrada = new Boleta();
        boletaEntrada.setNro_Orden(3L);

        // El Estado simulado (Pagado) de Reservas
        EstadoOrdenDTO estadoOrdenMock = new EstadoOrdenDTO();
        estadoOrdenMock.setIdEstadoOrden(2L);
        estadoOrdenMock.setNombreEstado("Pagado");

        // La orden simulada que "devolvería" Reservas
        OrdenDTO ordenMock = new OrdenDTO();
        ordenMock.setNroOrden(3L);
        ordenMock.setCodigoCupon("VERANO2026");
        ordenMock.setEstadoOrden(estadoOrdenMock);

        // La boleta simulada que devuelve la Base de Datos al guardar
        Boleta boletaGuardada = new Boleta();
        boletaGuardada.setNro_Boleta(1L);
        boletaGuardada.setNro_Orden(3L);
        boletaGuardada.setCodigo_Cupon("VERANO2026");

        // Mock de la cadena de WebClient
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(headerSpec);
        when(headerSpec.retrieve()).thenReturn(responseSpec);
        
        // Aquí entregamos el DTO falso en lugar de hacer la petición real hacia Reservas
        // Estamos simulando la respuesta real del otro microservicio
        when(responseSpec.bodyToMono(OrdenDTO.class)).thenReturn(Mono.just(ordenMock));

        // MÉTODO REAL
        // Entrenamos al repositorio falso para que devuelva la boleta de respuesta
        when(boletaRepository.save(any(Boleta.class))).thenReturn(boletaGuardada);


        // --- 2. EJECUCIÓN ---
        Boleta resultado = boletaService.crearBoleta(boletaEntrada);


        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado, "La boleta no debe ser nula");
        assertEquals("VERANO2026", resultado.getCodigo_Cupon(), "El cupón debe ser el mismo que viene de Reservas");
        
        // Verificamos que se llamó al repositorio para guardar exactamente 1 vez 
        verify(boletaRepository, times(1)).save(any(Boleta.class));
    }
}

