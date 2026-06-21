package com.example.service_inventario.service;

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

import com.example.service_inventario.exception.RecursoNoEncontradoException;
import com.example.service_inventario.model.EstadoInventario;
import com.example.service_inventario.model.Stock;
import com.example.service_inventario.repository.StockRepository;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockService stockService;

    @Test
    @DisplayName("Debería permitir reservar un producto que se encuentra DISPONIBLE")
    void reservarProducto_Disponible_Exitoso() {
        // --- 1. PREPARACIÓN ---
        Stock stock = new Stock();
        stock.setIdProducto(1L);
        stock.setEstadoInventario(EstadoInventario.DISPONIBLE);

        when(stockRepository.findByIdProducto(1L)).thenReturn(Optional.of(stock));
        when(stockRepository.save(any(Stock.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // --- 2. EJECUCIÓN ---
        Stock resultado = stockService.reservar(1L);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado, "El stock retornado no debe ser nulo");
        assertEquals(EstadoInventario.RESERVADO, resultado.getEstadoInventario(), "El estado debió cambiar a RESERVADO");
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    @DisplayName("No debería permitir reservar un producto si su estado es VENDIDO")
    void reservarProducto_Vendido_LanzaExcepcion() {
        // --- 1. PREPARACIÓN ---
        Stock stock = new Stock();
        stock.setIdProducto(1L);
        stock.setEstadoInventario(EstadoInventario.VENDIDO);

        when(stockRepository.findByIdProducto(1L)).thenReturn(Optional.of(stock));

        // --- 2 & 3. EJECUCIÓN Y VERIFICACIÓN ---
        IllegalStateException excepcion = assertThrows(IllegalStateException.class, () -> {
            stockService.reservar(1L);
        });
        
        assertNotNull(excepcion);
        assertEquals("No se puede reservar el producto porque no esta disponible", excepcion.getMessage());
        verify(stockRepository, never()).save(any(Stock.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al intentar reservar un producto que no existe en inventario")
    void reservarProducto_NoEncontrado() {
        // --- 1. PREPARACIÓN ---
        when(stockRepository.findByIdProducto(99L)).thenReturn(Optional.empty());

        // --- 2 & 3. EJECUCIÓN Y VERIFICACIÓN ---
        RecursoNoEncontradoException excepcion = assertThrows(RecursoNoEncontradoException.class, () -> {
            stockService.reservar(99L);
        });

        assertEquals("Producto no encontrado en inventario", excepcion.getMessage());
        verify(stockRepository, never()).save(any(Stock.class));
    }
}
