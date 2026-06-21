package com.example.service_devoluciones.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.service_devoluciones.dto.DespachoDTO;
import com.example.service_devoluciones.dto.ReembolsoDTO;
import com.example.service_devoluciones.model.Devolucion;
import com.example.service_devoluciones.repository.DevolucionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DevolucionService {

    private final DevolucionRepository devolucionRepository;

    private final WebClient.Builder webClientBuilder;

    public List<Devolucion> listarDevoluciones(){
        return devolucionRepository.findAll();
    }

    @Transactional
    public Devolucion crearDevolucion(Devolucion devolucion){
        String nombreEstado = obtenerEstadoDespacho(devolucion);

        if (!nombreEstado.equalsIgnoreCase("Entregado")){
            throw new RuntimeException("Error de validación: no se puede devolver un pedido que aún no ha sido entregado.");
        }
        
        return devolucionRepository.save(devolucion);
    }

    public Devolucion encontrarDevolucion(Long id){
        Devolucion devolucionCompleta = devolucionRepository.findById(id).orElse(null);
        if (devolucionCompleta != null){
            return enriquecerReembolso(devolucionCompleta);
        }
        return null;
    }

    @Transactional
    public Devolucion actualizarDevolucion(Long id, Devolucion devolucion){
        Devolucion existente = devolucionRepository.findById(id).orElse(null);
        if (existente != null){
            existente.setEstadoDevolucion(devolucion.getEstadoDevolucion());
            return devolucionRepository.save(existente);
        }
        return null;
    }

    @Transactional
    public void eliminarDevolucion(Long id){
        devolucionRepository.deleteById(id);
    }




    public List<Devolucion> filtrarDevoluciones(Long idDevolucion){
        return devolucionRepository.findByDevolucion(idDevolucion);
    }




    // método interno: obtener el estado del despacho de la orden
    private String obtenerEstadoDespacho(Devolucion devolucion) {
        try {
            // Transforma el JSON en un diccionario
            DespachoDTO respuestaJson = webClientBuilder.build()
                .get()
                .uri("http://localhost:8085/api/v4/logistica/despacho/" + devolucion.getNro_Orden())
                .retrieve()
                .bodyToMono(DespachoDTO.class) 
                .block();
                
            return respuestaJson.getEstadoDespacho().getNombreEstado();
            
        } catch (Exception e) {
            throw new RuntimeException("Error de validación: El despacho no existe o el servicio no responde.");
        }
    }

    // Método interno: enriquecerReembolso (Principio Dont Repeat Yourself (DRY))
    private Devolucion enriquecerReembolso(Devolucion devolucion){
        try {
            ReembolsoDTO datosDelReembolso = webClientBuilder
            .build()
            .get()
            .uri("http://localhost:8087/api/v1/reembolsos" + devolucion.getId_Devolucion())
            .retrieve()
            .bodyToMono(ReembolsoDTO.class)
            .block();

            devolucion.setDatos_Reembolso(datosDelReembolso);
        } catch (Exception e) {
            devolucion.setDatos_Reembolso("No hay solicitud de reembolso para esta orden.");
        }
        return devolucion;
    }

    
}
