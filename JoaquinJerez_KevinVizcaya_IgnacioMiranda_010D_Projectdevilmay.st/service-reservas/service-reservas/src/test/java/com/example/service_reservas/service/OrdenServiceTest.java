package com.example.service_reservas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.service_reservas.dto.CuponDTO;
import com.example.service_reservas.model.Orden;
import com.example.service_reservas.repository.OrdenRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class) // Simulando objetos con Mockito
public class OrdenServiceTest {

    @Mock
    private OrdenRepository ordenRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private OrdenService ordenService;


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
    @DisplayName("Debe aplicar el descuento del cupón correctamente al total")
    void crearOrden_ConCuponValido() {
        
        // --- 1. PREPARACIÓN ---
        // La orden simulada de entrada al método
        Orden ordenEntrada = new Orden();
        ordenEntrada.setId_Carrito(1L); // Agregamos un ID para que busque el carrito
        ordenEntrada.setPrecio_Total(10000); 
        ordenEntrada.setCodigo_Cupon("VERANO20"); 

        // El cupón simulado de Comprobantes
        CuponDTO cuponMock = new CuponDTO();
        cuponMock.setCodigoCupon("VERANO20");
        cuponMock.setPorcentajeDescuento(20); 

        // La orden simulada que devuelve la Base de Datos al guardar
        Orden ordenGuardada = new Orden();
        ordenGuardada.setNro_Orden(1L);
        ordenGuardada.setPrecio_Total(8000); 

        // Mock de la cadena de WebClient
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);


        // --- RUTA 1: SIMULAR EL CARRITO (200 OK) ---
        WebClient.RequestHeadersSpec headerSpecCarrito = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecCarrito = mock(WebClient.ResponseSpec.class);
        
        when(uriSpec.uri(contains("carritos"))).thenReturn(headerSpecCarrito);
        when(headerSpecCarrito.retrieve()).thenReturn(responseSpecCarrito);
        // Verificar si carrito existe, devolviendo un 200 OK
        when(responseSpecCarrito.toBodilessEntity()).thenReturn(Mono.just(ResponseEntity.ok().build()));


        // --- RUTA 2: SIMULAR EL CUPÓN (DTO con 20%) ---
        WebClient.RequestHeadersSpec headerSpecCupon = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecCupon = mock(WebClient.ResponseSpec.class);
        
        when(uriSpec.uri(contains("promociones"))).thenReturn(headerSpecCupon);
        when(headerSpecCupon.retrieve()).thenReturn(responseSpecCupon);
        // Aquí entregamos el DTO falso en lugar de hacer la petición real hacia Comprobantes
        // Estamos simulando la respuesta real del otro microservicio
        when(responseSpecCupon.bodyToMono(CuponDTO.class)).thenReturn(Mono.just(cuponMock));

        
        // MÉTODO REAL
        // Entrenamos al repositorio falso para que devuelva la orden de respuesta
        when(ordenRepository.save(any(Orden.class))).thenReturn(ordenGuardada);

        // --- 2. EJECUCIÓN ---
        // Ahora el código pasará la validación del carrito y llegará al descuento
        Orden resultado = ordenService.crearOrden(ordenEntrada);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado, "La orden no debe ser nula");
        assertEquals(8000, resultado.getPrecio_Total(), "El precio total debe reflejar el 20% de descuento");
        verify(ordenRepository, times(1)).save(any(Orden.class));
    }

    @Test
    @DisplayName("Debe guardar orden exitosamente cuando carrito existe y NO hay cupón")
    void crearOrden_CarritoExisteSinCupon() {
        
        // --- 1. PREPARACIÓN ---
        // La orden simulada de entrada al método
        Orden ordenEntrada = new Orden();
        ordenEntrada.setId_Carrito(1L);
        ordenEntrada.setPrecio_Total(5000);
        ordenEntrada.setCodigo_Cupon(null); // CLAVE: No hay cupón

        // La orden simulada que devuelve la Base de Datos al guardar
        Orden ordenGuardada = new Orden();
        ordenGuardada.setNro_Orden(1L);
        ordenGuardada.setPrecio_Total(5000); // El precio se mantiene

        // Mock de la cadena de WebClient SOLO para carrito
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(contains("carritos"))).thenReturn(headerSpec);
        when(headerSpec.retrieve()).thenReturn(responseSpec);
        
        // Simulamos que la API respondió un 200 OK sin cuerpo
        when(responseSpec.toBodilessEntity()).thenReturn(Mono.just(ResponseEntity.ok().build()));

        // MÉTODO REAL
        // Entrenamos al repositorio falso para que devuelva la orden de respuesta
        when(ordenRepository.save(any(Orden.class))).thenReturn(ordenGuardada);

        // --- 2. EJECUCIÓN ---
        Orden resultado = ordenService.crearOrden(ordenEntrada);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado);
        assertEquals(5000, resultado.getPrecio_Total(), "El precio no debe cambiar");
        verify(ordenRepository, times(1)).save(any(Orden.class));
        
        // Verificamos que NUNCA intentó buscar un cupón
        verify(uriSpec, never()).uri(contains("promociones"));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el carrito no existe")
    void crearOrden_CarritoNoExiste() {
        // --- 1. PREPARACIÓN ---
        // La orden simulada de entrada al método
        Orden ordenEntrada = new Orden();
        ordenEntrada.setId_Carrito(99L); // Carrito falso

        // Mock de la cadena de WebClient
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(contains("carritos"))).thenReturn(headerSpec);
        when(headerSpec.retrieve()).thenReturn(responseSpec);
        
        // Hacemos que lanzar la petición arroje un error de conexión
        when(responseSpec.toBodilessEntity()).thenThrow(new RuntimeException("Carrito no encontrado"));

        // 2 y 3. Ejecución y Verificación al mismo tiempo
        // Usamos assertThrows para capturar la excepción que el catch va a lanzar
        // Lambda espera que se lance una RuntimeException para atraparla
        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            ordenService.crearOrden(ordenEntrada);
        });

        // Verificamos que el campo tenga el string exacto de error
        assertEquals("Error de validación: El carrito no existe o el servicio no responde.", excepcion.getMessage());
        
        // Verificamos que NUNCA se haya guardado nada en la base de datos
        verify(ordenRepository, never()).save(any(Orden.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el cupón es inválido o expiró")
    void crearOrden_ConCuponInvalidoOExpirado() {
        
        // --- 1. PREPARACIÓN ---
        // La orden simulada de entrada al método
        Orden ordenEntrada = new Orden();
        ordenEntrada.setId_Carrito(1L); 
        ordenEntrada.setPrecio_Total(10000); 
        ordenEntrada.setCodigo_Cupon("CUPON_VENCIDO"); // El cupón que hará fallar la API

        // Mock de la cadena de WebClient
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);


        // --- RUTA 1: CARRITO (carrito SÍ existe) ---
        WebClient.RequestHeadersSpec headerSpecCarrito = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecCarrito = mock(WebClient.ResponseSpec.class);
        
        when(uriSpec.uri(contains("carritos"))).thenReturn(headerSpecCarrito);
        when(headerSpecCarrito.retrieve()).thenReturn(responseSpecCarrito);

        when(responseSpecCarrito.toBodilessEntity()).thenReturn(Mono.just(ResponseEntity.ok().build()));

        
        // --- RUTA 2: CUPÓN (simulamos que Promociones rechaza el cupón) ---
        WebClient.RequestHeadersSpec headerSpecCupon = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecCupon = mock(WebClient.ResponseSpec.class);
        
        when(uriSpec.uri(contains("promociones"))).thenReturn(headerSpecCupon);
        when(headerSpecCupon.retrieve()).thenReturn(responseSpecCupon);
        // Simulamos que el WebClient tira error porque el cupón expiró
        when(responseSpecCupon.bodyToMono(CuponDTO.class)).thenThrow(new RuntimeException("Servicio Comprobantes 404"));

        
        // --- 2 & 3. EJECUCIÓN Y VERIFICACIÓN ---
        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            ordenService.crearOrden(ordenEntrada);
        });

        // Verificamos que el campo tenga el string exacto de error
        assertEquals("El cupón ingresado no es válido o ha expirado.", excepcion.getMessage());
        
        // Verificamos que NUNCA se haya guardado nada en la base de datos
        verify(ordenRepository, never()).save(any(Orden.class));
    }

    // ENRIQUECER BOLETA Y DESPACHO
    @Test
    @DisplayName("Debe enriquecer la orden con despacho y boleta exitosamente")
    void encontrarOrden_ConDatosCompletos() {
        
        // --- 1. PREPARACIÓN ---
        // La orden simulada de entrada al método
        Long idOrden = 1L;
        Orden ordenBase = new Orden();
        ordenBase.setNro_Orden(idOrden);

        // Los "objetos" simulados que devolverán las APIs
        Object despachoMock = "Datos de Despacho Simulados";
        Object boletaMock = "Datos de Boleta Simulados";

        // MÉTODO REAL
        // Mock del repositorio: al buscar por ID, devuelve la orden base
        when(ordenRepository.findById(idOrden)).thenReturn(Optional.of(ordenBase));

        // Tronco común del WebClient
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);

        // -- RUTA 1: LOGÍSTICA (Despacho) --
        WebClient.RequestHeadersSpec headerSpecDespacho = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecDespacho = mock(WebClient.ResponseSpec.class);
        
        when(uriSpec.uri(contains("logistica"))).thenReturn(headerSpecDespacho);
        when(headerSpecDespacho.retrieve()).thenReturn(responseSpecDespacho);
        when(responseSpecDespacho.bodyToMono(Object.class)).thenReturn(Mono.just(despachoMock));

        // -- RUTA 2: COMPROBANTES (Boleta) --
        WebClient.RequestHeadersSpec headerSpecBoleta = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecBoleta = mock(WebClient.ResponseSpec.class);
        
        when(uriSpec.uri(contains("comprobantes"))).thenReturn(headerSpecBoleta);
        when(headerSpecBoleta.retrieve()).thenReturn(responseSpecBoleta);
        when(responseSpecBoleta.bodyToMono(Object.class)).thenReturn(Mono.just(boletaMock));

        // --- 2. EJECUCIÓN ---
        Orden resultado = ordenService.encontrarOrden(idOrden);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado, "La orden no debe ser nula");
        
        // Verificamos que ambos datos se pegaron correctamente en el objeto
        assertEquals("Datos de Despacho Simulados", resultado.getDatos_Despacho());
        assertEquals("Datos de Boleta Simulados", resultado.getDatos_Boleta());
        
        verify(ordenRepository, times(1)).findById(idOrden);
    }

    @Test
    @DisplayName("Debe setear mensajes de error cuando fallan los microservicios")
    void encontrarOrden_CuandoServiciosFallan() {
        
        // --- 1. PREPARACIÓN ---
        // La orden simulada de entrada al método
        Long idOrden = 2L;
        Orden ordenBase = new Orden();
        ordenBase.setNro_Orden(idOrden);

        when(ordenRepository.findById(idOrden)).thenReturn(Optional.of(ordenBase));

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);

        // -- RUTA 1: LOGÍSTICA FALLA --
        WebClient.RequestHeadersSpec headerSpecDespacho = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecDespacho = mock(WebClient.ResponseSpec.class);
        
        when(uriSpec.uri(contains("logistica"))).thenReturn(headerSpecDespacho);
        when(headerSpecDespacho.retrieve()).thenReturn(responseSpecDespacho);
        when(responseSpecDespacho.bodyToMono(Object.class)).thenThrow(new RuntimeException("Servicio Logística 404"));

        // -- RUTA 2: COMPROBANTES FALLA --
        WebClient.RequestHeadersSpec headerSpecBoleta = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecBoleta = mock(WebClient.ResponseSpec.class);
        
        when(uriSpec.uri(contains("comprobantes"))).thenReturn(headerSpecBoleta);
        when(headerSpecBoleta.retrieve()).thenReturn(responseSpecBoleta);
        when(responseSpecBoleta.bodyToMono(Object.class)).thenThrow(new RuntimeException("Servicio Comprobantes 404"));

        // --- 2. EJECUCIÓN ---
        // El código intentará llamar a los 2.
        // Ambos fallarán, ambos catch se activarán, y la orden se devolverá de todos modos.
        Orden resultado = ordenService.encontrarOrden(idOrden);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado);
        
        // Verificamos que el campo tenga los strings exacto de error
        assertEquals("Error al cargar datos del despacho.", resultado.getDatos_Despacho());
        assertEquals("Error al cargar datos de la boleta.", resultado.getDatos_Boleta());
    }
}
