package com.example.service_carrito.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.example.service_carrito.model.Carrito;
import com.example.service_carrito.repository.CarritoRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class CarritoServiceTest {

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private Mono<CarritoService.InventarioStatusDTO> monoStatus;

    @Mock
    private Mono<Void> monoVoid;

    @InjectMocks
    private CarritoService carritoService;

    @Test
    @DisplayName("Debe guardar un producto en el carrito si no existe y esta DISPONIBLE en inventario")
    void guardarCarrito_Exitoso() {
        // --- 1. PREPARACIÓN ---
        Carrito carrito = new Carrito();
        carrito.setIdProducto(100L);

        when(carritoRepository.findByIdProducto(100L)).thenReturn(Optional.empty());
        
        // Mocking WebClient GET Inventario
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(String.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CarritoService.InventarioStatusDTO.class)).thenReturn(monoStatus);
        when(monoStatus.block()).thenReturn(new CarritoService.InventarioStatusDTO("DISPONIBLE"));

        // Mocking WebClient PUT Reservar
        when(webClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(any(String.class))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(monoVoid);
        when(monoVoid.block()).thenReturn(null);

        when(carritoRepository.save(any(Carrito.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // --- 2. EJECUCIÓN ---
        Carrito resultado = carritoService.guardar(carrito);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado, "El carrito no debe ser nulo");
        assertEquals(100L, resultado.getIdProducto(), "El ID del producto debe coincidir");
        verify(carritoRepository, times(1)).save(any(Carrito.class));
    }
    
    @Test
    @DisplayName("Debe lanzar excepción si intenta agregar producto duplicado")
    void guardarCarrito_Duplicado() {
        // --- 1. PREPARACIÓN ---
        Carrito carrito = new Carrito();
        carrito.setIdProducto(100L);

        Carrito existente = new Carrito();
        existente.setIdProducto(100L);

        when(carritoRepository.findByIdProducto(100L)).thenReturn(Optional.of(existente));

        // --- 2 & 3. EJECUCIÓN Y VERIFICACIÓN ---
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            carritoService.guardar(carrito);
        });

        assertNotNull(excepcion);
        verify(carritoRepository, never()).save(any(Carrito.class));
    }
}
