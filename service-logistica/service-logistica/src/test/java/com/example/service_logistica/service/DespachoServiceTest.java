package com.example.service_logistica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.service_logistica.model.Despacho;
import com.example.service_logistica.repository.DespachoRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class) // Simulando objetos con Mockito
public class DespachoServiceTest {

    @Mock
    private DespachoRepository despachoRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private DespachoService despachoService;


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
    @DisplayName("Debe guardar despacho cuando la orden exista")
    void crearDespacho_CuandoOrdenExiste() {
        // --- 1. PREPARACIÓN ---
        // E despacho simulado de entrada al método
        Despacho despachoEntrada = new Despacho();
        despachoEntrada.setNro_Orden(3L);

        Despacho despachoGuardado = new Despacho();
        despachoGuardado.setNro_Orden(3L);

        // Mock de la cadena de WebClient
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(headerSpec);
        when(headerSpec.retrieve()).thenReturn(responseSpec);
        
        // Simulamos que la API respondió un 200 OK sin cuerpo
        when(responseSpec.toBodilessEntity()).thenReturn(Mono.just(ResponseEntity.ok().build()));

        // MÉTODO REAL
        // Entrenamos al repositorio falso para que devuelva el despacho de respuesta
        when(despachoRepository.save(any(Despacho.class))).thenReturn(despachoGuardado);

        // --- 2. EJECUCIÓN ---
        Despacho resultado = despachoService.crearDespacho(despachoEntrada);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado);
        
        verify(despachoRepository, times(1)).save(any(Despacho.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando Reservas no responde o da 404")
    void crearDespacho_CuandoOrdenNoExiste() {

        // --- 1. PREPARACIÓN ---
        // El despacho simulado de entrada al método
        Despacho despachoEntrada = new Despacho();
        despachoEntrada.setNro_Orden(99L); // Una orden que "no existe"

        // Mock de la cadena de WebClient
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(headerSpec);
        when(headerSpec.retrieve()).thenReturn(responseSpec);
        
        // Hacemos que lanzar la petición arroje un error de conexión
        when(responseSpec.toBodilessEntity()).thenThrow(new RuntimeException("Conexión fallida"));

        // 2 y 3. Ejecución y Verificación al mismo tiempo
        // Usamos assertThrows para capturar la excepción que el catch va a lanzar
        // Lambda espera que se lance una RuntimeException para atraparla
        RuntimeException excepcionLanzada = assertThrows(RuntimeException.class, () -> {
            despachoService.crearDespacho(despachoEntrada);
        });

        // Verificamos que el mensaje sea exactamente el del RuntimeException
        assertEquals("Error de validación: La orden no existe o el servicio no responde.", excepcionLanzada.getMessage());
        
        // Verificamos que NUNCA se haya llamado al repositorio
        verify(despachoRepository, never()).save(any(Despacho.class));
    }
}
