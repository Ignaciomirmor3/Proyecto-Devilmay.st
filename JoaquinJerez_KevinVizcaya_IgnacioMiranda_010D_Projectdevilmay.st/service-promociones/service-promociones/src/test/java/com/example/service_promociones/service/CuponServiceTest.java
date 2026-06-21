package com.example.service_promociones.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.service_promociones.dto.CuponRequestDTO;
import com.example.service_promociones.dto.CuponResponseDTO;
import com.example.service_promociones.dto.CuponValidacionResponseDTO;
import com.example.service_promociones.exception.RecursoDuplicadoException;
import com.example.service_promociones.exception.RecursoNoEncontradoException;
import com.example.service_promociones.model.Cupon;
import com.example.service_promociones.repository.CuponRepository;

@ExtendWith(MockitoExtension.class)
class CuponServiceTest {

    @Mock
    private CuponRepository cuponRepository;

    @InjectMocks
    private CuponService cuponService;

    @Test
    @DisplayName("Debe crear un cupon exitosamente si no existe")
    void crearCupon_Exitoso() {
        // --- 1. PREPARACIÓN ---
        CuponRequestDTO request = new CuponRequestDTO();
        request.setCodigoCupon("NUEVO20");
        request.setPorcentajeDescuento(20);
        request.setFechaExpiracion(LocalDate.now().plusDays(10));

        Cupon cuponGuardado = new Cupon();
        cuponGuardado.setCodigoCupon("NUEVO20");
        cuponGuardado.setPorcentajeDescuento(20);
        cuponGuardado.setFechaExpiracion(LocalDate.now().plusDays(10));
        cuponGuardado.setActivo(true);

        when(cuponRepository.existsById("NUEVO20")).thenReturn(false);
        when(cuponRepository.save(any(Cupon.class))).thenReturn(cuponGuardado);

        // --- 2. EJECUCIÓN ---
        CuponResponseDTO resultado = cuponService.crear(request);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado, "El cupón retornado no debe ser nulo");
        assertEquals("NUEVO20", resultado.getCodigoCupon(), "El código debe coincidir y estar en mayúsculas");
        assertEquals(20, resultado.getPorcentajeDescuento(), "El porcentaje debe ser 20");
        verify(cuponRepository, times(1)).save(any(Cupon.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al intentar crear un cupón duplicado")
    void crearCupon_Duplicado() {
        // --- 1. PREPARACIÓN ---
        CuponRequestDTO request = new CuponRequestDTO();
        request.setCodigoCupon("DUPLICADO50");

        when(cuponRepository.existsById("DUPLICADO50")).thenReturn(true);

        // --- 2 & 3. EJECUCIÓN Y VERIFICACIÓN ---
        RecursoDuplicadoException excepcion = assertThrows(RecursoDuplicadoException.class, () -> {
            cuponService.crear(request);
        });

        assertEquals("El cupon ya existe.", excepcion.getMessage());
        verify(cuponRepository, never()).save(any(Cupon.class));
    }

    @Test
    @DisplayName("Debería validar correctamente un cupón vigente")
    void validarCuponVigenteTest() {
        // --- 1. PREPARACIÓN ---
        Cupon cupon = new Cupon();
        cupon.setCodigoCupon("DEVILMAY20");
        cupon.setPorcentajeDescuento(20);
        cupon.setFechaExpiracion(LocalDate.now().plusDays(5));
        cupon.setActivo(true);

        when(cuponRepository.findById("DEVILMAY20")).thenReturn(Optional.of(cupon));

        // --- 2. EJECUCIÓN ---
        CuponValidacionResponseDTO resultado = cuponService.validarCupon("DEVILMAY20");

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado);
        assertTrue(resultado.isValido(), "El cupón debe ser marcado como válido");
        assertEquals(20, resultado.getPorcentajeDescuento(), "El descuento aplicado debe ser del 20%");
        verify(cuponRepository, times(1)).findById("DEVILMAY20");
    }

    @Test
    @DisplayName("Debería retornar inválido al validar un cupón expirado")
    void validarCuponExpiradoTest() {
        // --- 1. PREPARACIÓN ---
        Cupon cupon = new Cupon();
        cupon.setCodigoCupon("VENCIDO10");
        cupon.setPorcentajeDescuento(10);
        cupon.setFechaExpiracion(LocalDate.now().minusDays(1)); // Ayer
        cupon.setActivo(true);

        when(cuponRepository.findById("VENCIDO10")).thenReturn(Optional.of(cupon));

        // --- 2. EJECUCIÓN ---
        CuponValidacionResponseDTO resultado = cuponService.validarCupon("VENCIDO10");

        // --- 3. VERIFICACIÓN ---
        assertFalse(resultado.isValido(), "El cupón debe ser marcado como inválido");
        assertEquals(0, resultado.getPorcentajeDescuento(), "El descuento debe ser cero si expiró");
        assertTrue(resultado.getMensaje().contains("expirado"));
    }

    @Test
    @DisplayName("Debe actualizar los datos de un cupón existente")
    void actualizarCupon_Exitoso() {
        // --- 1. PREPARACIÓN ---
        Cupon cuponBase = new Cupon();
        cuponBase.setCodigoCupon("ACTUALIZAR10");
        cuponBase.setPorcentajeDescuento(10);

        CuponRequestDTO request = new CuponRequestDTO();
        request.setPorcentajeDescuento(15);
        request.setFechaExpiracion(LocalDate.now().plusDays(30));

        Cupon cuponGuardado = new Cupon();
        cuponGuardado.setCodigoCupon("ACTUALIZAR10");
        cuponGuardado.setPorcentajeDescuento(15);
        cuponGuardado.setFechaExpiracion(LocalDate.now().plusDays(30));
        cuponGuardado.setActivo(true);

        when(cuponRepository.findById("ACTUALIZAR10")).thenReturn(Optional.of(cuponBase));
        when(cuponRepository.save(any(Cupon.class))).thenReturn(cuponGuardado);

        // --- 2. EJECUCIÓN ---
        CuponResponseDTO resultado = cuponService.actualizar("ACTUALIZAR10", request);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado);
        assertEquals(15, resultado.getPorcentajeDescuento(), "El porcentaje debió actualizarse a 15%");
        verify(cuponRepository, times(1)).save(any(Cupon.class));
    }
}
