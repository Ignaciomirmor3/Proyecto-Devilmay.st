package com.example.service_reembolsos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.service_reembolsos.dto.ReembolsoRequestDTO;
import com.example.service_reembolsos.dto.ReembolsoResponseDTO;
import com.example.service_reembolsos.exception.RecursoDuplicadoException;
import com.example.service_reembolsos.model.Reembolso;
import com.example.service_reembolsos.repository.ReembolsoRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class ReembolsoServiceTest {

    @Mock
    private ReembolsoRepository reembolsoRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private Mono<Object> monoObject;

    @InjectMocks
    private ReembolsoService reembolsoService;

    @Test
    @DisplayName("Debería solicitar un reembolso correctamente tras validar en service-devoluciones")
    void crearReembolso_Exitoso() {
        // --- 1. PREPARACIÓN ---
        ReembolsoRequestDTO request = new ReembolsoRequestDTO();
        request.setIdDevolucion(10L);
        request.setMontoDevuelto(5000.0);
        request.setBancoDestino("Banco Estado");

        when(reembolsoRepository.existsById(10L)).thenReturn(false);

        // Mock WebClient chain (simulando respuesta exitosa del otro microservicio)
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Object.class)).thenReturn(monoObject);
        when(monoObject.block()).thenReturn(new Object());

        when(reembolsoRepository.save(any(Reembolso.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // --- 2. EJECUCIÓN ---
        ReembolsoResponseDTO resultado = reembolsoService.crear(request);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado, "El reembolso no debe ser nulo");
        assertEquals(10L, resultado.getIdDevolucion(), "El ID de devolución debe coincidir");
        assertEquals(5000.0, resultado.getMontoDevuelto(), "El monto devuelto debe ser exacto");
        assertEquals("Banco Estado", resultado.getBancoDestino(), "El banco debe coincidir");
        verify(reembolsoRepository, times(1)).save(any(Reembolso.class));
    }

    @Test
    @DisplayName("Debería lanzar RecursoDuplicadoException si ya existe reembolso")
    void crearReembolso_Duplicado() {
        // --- 1. PREPARACIÓN ---
        ReembolsoRequestDTO request = new ReembolsoRequestDTO();
        request.setIdDevolucion(10L);

        when(reembolsoRepository.existsById(10L)).thenReturn(true);

        // --- 2 & 3. EJECUCIÓN Y VERIFICACIÓN ---
        RecursoDuplicadoException excepcion = assertThrows(RecursoDuplicadoException.class, () -> {
            reembolsoService.crear(request);
        });

        assertNotNull(excepcion);
        verify(reembolsoRepository, never()).save(any(Reembolso.class));
    }

    @Test
    @DisplayName("Debe actualizar correctamente el monto y el banco de destino")
    void actualizarReembolso_Exitoso() {
        // --- 1. PREPARACIÓN ---
        Reembolso reembolsoBase = new Reembolso();
        reembolsoBase.setIdDevolucion(5L);
        reembolsoBase.setMontoDevuelto(1000.0);
        reembolsoBase.setBancoDestino("Banco Viejo");

        ReembolsoRequestDTO request = new ReembolsoRequestDTO();
        request.setMontoDevuelto(2500.0);
        request.setBancoDestino("Banco Nuevo");

        when(reembolsoRepository.findById(5L)).thenReturn(Optional.of(reembolsoBase));
        when(reembolsoRepository.save(any(Reembolso.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // --- 2. EJECUCIÓN ---
        ReembolsoResponseDTO resultado = reembolsoService.actualizar(5L, request);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado);
        assertEquals(2500.0, resultado.getMontoDevuelto(), "El monto debe estar actualizado");
        assertEquals("Banco Nuevo", resultado.getBancoDestino(), "El banco debe estar actualizado");
        verify(reembolsoRepository, times(1)).save(any(Reembolso.class));
    }
}
