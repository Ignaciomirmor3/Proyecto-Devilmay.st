package com.example.service_devoluciones.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.example.service_devoluciones.dto.DespachoDTO;
import com.example.service_devoluciones.dto.EstadoDespachoDTO;
import com.example.service_devoluciones.dto.ReembolsoDTO;
import com.example.service_devoluciones.model.Devolucion;
import com.example.service_devoluciones.repository.DevolucionRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class) // Simulando objetos con Mockito
public class DevolucionServiceTest {

    @Mock
    private DevolucionRepository devolucionRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private DevolucionService devolucionService;


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
    @DisplayName("Debe guardar devolucion cuando el despacho haya sido entregado")
    void crearDevolucion(){
        
        // La devolución simulada de entrada al método
        Devolucion devolucionEntrada = new Devolucion();
        devolucionEntrada.setNro_Orden(5L);

        // El Estado simulado (Entregado) de Logística
        EstadoDespachoDTO estadoDespachoMock = new EstadoDespachoDTO();
        estadoDespachoMock.setIdEstadoDespacho(3L);
        estadoDespachoMock.setNombreEstado("Entregado");

        // El despacho simulado que nos "devolvería" Logística
        DespachoDTO despachoMock = new DespachoDTO();
        despachoMock.setNroOrden(5L);
        despachoMock.setEstadoDespacho(estadoDespachoMock);

        // La devolución simulada que devuelve la Base de Datos al guardar
        Devolucion devolucionGuardada = new Devolucion();
        devolucionGuardada.setId_Devolucion(1L);
        devolucionGuardada.setNro_Orden(5L);

        // Mock de la cadena de WebClient
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(headerSpec);
        when(headerSpec.retrieve()).thenReturn(responseSpec);
        
        // Aquí entregamos el DTO falso en lugar de hacer la petición real 
        when(responseSpec.bodyToMono(DespachoDTO.class)).thenReturn(Mono.just(despachoMock));

        // Entrenamos al repositorio falso para que devuelva la devolución de respuesta
        when(devolucionRepository.save(any(Devolucion.class))).thenReturn(devolucionGuardada);


        // --- 2. EJECUCIÓN ---
        Devolucion resultado = devolucionService.crearDevolucion(devolucionEntrada);


        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado, "La devolución no debe ser nula");
        
        // Verificamos que se llamó al repositorio para guardar exactamente 1 vez 
        verify(devolucionRepository, times(1)).save(any(Devolucion.class));
    }

    @Test
    @DisplayName("Debe enriquecer los datos de reembolso en la devolución")
    void encontrarDevolucion(){

        // --- 1. PREPARACIÓN ---
        Long idDevolucion = 1L;

        // La devolución simulada de entrada al método
        Devolucion devolucionBase = new Devolucion();
        devolucionBase.setId_Devolucion(idDevolucion);
        devolucionBase.setNro_Orden(10L);

        // El despacho simulado que nos "devolvería" Reembolsos
        ReembolsoDTO reembolsoMock = new ReembolsoDTO(1L, 5000);

        // MÉTODO REAL
        // Mock del repositorio: al buscar por ID, devuelve la devolución base
        when(devolucionRepository.findById(idDevolucion)).thenReturn(Optional.of(devolucionBase));

        // Mock de la cadena de WebClient
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(headerSpec);
        when(headerSpec.retrieve()).thenReturn(responseSpec);

        // Aquí entregamos el DTO falso en lugar de hacer la petición real hacia Reembolsos
        // Estamos simulando la respuesta real del otro microservicio
        when(responseSpec.bodyToMono(ReembolsoDTO.class)).thenReturn(Mono.just(reembolsoMock));

        // --- 2. EJECUCIÓN ---
        Devolucion resultado = devolucionService.encontrarDevolucion(idDevolucion);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado, "La devolución no debe ser nula");
        assertNotNull(resultado.getDatos_Reembolso(), "Los datos del reembolso deben estar presentes");

        // Cast y comprobación de datos
        ReembolsoDTO datosRetornados = (ReembolsoDTO) resultado.getDatos_Reembolso();
        assertEquals(5000, datosRetornados.getMontoDevuelto(), "El monto debe coincidir");

        // Verificamos que se llamó al repositorio
        verify(devolucionRepository, times(1)).findById(idDevolucion);
    }

    @Test
    @DisplayName("Debe dar mensaje de error si falla el enriquecimiento")
    void encontrarDevolucion_CuandoReembolsoFalla() {
        // --- 1. PREPARACIÓN ---
        Long idDevolucion = 2L;

        Devolucion devolucionBase = new Devolucion();
        devolucionBase.setId_Devolucion(idDevolucion);

        when(devolucionRepository.findById(idDevolucion)).thenReturn(Optional.of(devolucionBase));

        // Entrenamos al WebClient para que falle en el último paso
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(headerSpec);
        when(headerSpec.retrieve()).thenReturn(responseSpec);
        
        // Simulamos un error (el microservicio Logística está apagado o da 404)
        when(responseSpec.bodyToMono(ReembolsoDTO.class)).thenThrow(new RuntimeException("Conexión fallida"));

        // --- 2. EJECUCIÓN ---
        Devolucion resultado = devolucionService.encontrarDevolucion(idDevolucion);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado);
        
        // Verificamos que el campo tenga el string exacto de error en lugar de un objeto ReembolsoDTO
        assertEquals("No hay solicitud de reembolso para esta orden.", resultado.getDatos_Reembolso());

        // Verificamos que NUNCA se haya guardado nada en la base de datos
        verify(devolucionRepository, never()).save(any(Devolucion.class));
    }
}
