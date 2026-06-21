package com.example.service_catalogo.service;

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

import com.example.service_catalogo.dto.ProductoRequestDTO;
import com.example.service_catalogo.dto.ProductoResponseDTO;
import com.example.service_catalogo.exception.RecursoNoEncontradoException;
import com.example.service_catalogo.model.Genero;
import com.example.service_catalogo.model.Producto;
import com.example.service_catalogo.model.TipoPrenda;
import com.example.service_catalogo.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    @DisplayName("Debe guardar un producto en el catalogo exitosamente")
    void guardarProducto_Exitoso() {
        // --- 1. PREPARACIÓN ---
        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setNombreProducto("Chaqueta Cuero");
        request.setDescripcionProducto("Chaqueta elegante");
        request.setPrecioProducto(50000);
        request.setTallaProducto("M");
        request.setEstadoInventario("DISPONIBLE");
        request.setUrlImagen("http://imagen.com");
        request.setGenero(Genero.UNISEX);
        request.setTipoPrenda(TipoPrenda.CHAQUETA);

        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> {
            Producto p = invocation.getArgument(0);
            p.setId_producto(10L);
            return p;
        });

        // --- 2. EJECUCIÓN ---
        ProductoResponseDTO resultado = productoService.guardar(request);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado, "El producto devuelto no debe ser nulo");
        assertEquals(10L, resultado.getId_producto(), "El ID debe haber sido asignado (10)");
        assertEquals("Chaqueta Cuero", resultado.getNombreProducto(), "El nombre debe coincidir");
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("Debe buscar un producto por ID y retornarlo exitosamente")
    void buscarProductoPorId_Exitoso() {
        // --- 1. PREPARACIÓN ---
        Producto p = new Producto();
        p.setId_producto(5L);
        p.setNombreProducto("Polera Grafica");

        when(productoRepository.findById(5L)).thenReturn(Optional.of(p));

        // --- 2. EJECUCIÓN ---
        ProductoResponseDTO resultado = productoService.buscarPorId(5L);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado, "El producto no debe ser nulo");
        assertEquals(5L, resultado.getId_producto(), "El ID debe ser 5");
        assertEquals("Polera Grafica", resultado.getNombreProducto());
        verify(productoRepository, times(1)).findById(5L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al buscar un producto que no existe")
    void buscarProductoPorId_NoEncontrado() {
        // --- 1. PREPARACIÓN ---
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        // --- 2 & 3. EJECUCIÓN Y VERIFICACIÓN ---
        RecursoNoEncontradoException excepcion = assertThrows(RecursoNoEncontradoException.class, () -> {
            productoService.buscarPorId(99L);
        });

        assertEquals("Producto no encontrado", excepcion.getMessage(), "El mensaje de error debe coincidir");
        verify(productoRepository, times(1)).findById(99L);
    }
}
