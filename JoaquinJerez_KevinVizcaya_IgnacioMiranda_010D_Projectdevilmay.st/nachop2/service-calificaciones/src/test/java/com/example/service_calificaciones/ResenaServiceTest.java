package com.example.service_calificaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.service_calificaciones.model.Resena;
import com.example.service_calificaciones.repository.ResenaRepository;
import com.example.service_calificaciones.service.ResenaService;

@ExtendWith(MockitoExtension.class)
public class ResenaServiceTest {
    @Mock
    private ResenaRepository resenaRepository;

    @InjectMocks
    private ResenaService resenaService;

    @Test
    @DisplayName("Debería guardar una reseña correctamente")

    void guardarResenaTest(){
        Resena resena = new Resena();
        resena.setCalificacion(4);
        resena.setComentarioCliente("buena calidad");
        resena.setFechaPublicacion(LocalDate.now());
        
        when(resenaRepository.save(any(Resena.class))).thenAnswer(invocation -> {
            Resena r = invocation.getArgument(0);
            r.setId_resena(1L);
            return r;
        });

        Resena resultado = resenaService.guardar(resena);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId_resena());
        assertEquals(4, resultado.getCalificacion());
        verify(resenaRepository, times(1)).save(resena);
    }
}
