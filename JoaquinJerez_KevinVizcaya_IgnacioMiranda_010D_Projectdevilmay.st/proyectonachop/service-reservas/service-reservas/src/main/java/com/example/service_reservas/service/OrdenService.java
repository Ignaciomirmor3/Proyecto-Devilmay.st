package com.example.service_reservas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.service_reservas.model.Orden;
import com.example.service_reservas.repository.OrdenRepository;

import jakarta.transaction.Transactional;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<Orden> listarOrdenes(){
        return ordenRepository.findAll();
    }

    @Transactional
    public Orden crearOrden(Orden orden){
        // VERIFICAR SI ID_CARRITO EXISTE EN SERVICE-CARRITO
        validarExistenciaCarrito(orden.getId_Carrito());
        
        return ordenRepository.save(orden);
    }

    public Orden encontrarOrden(Long id){
        Orden ordenCompleta = ordenRepository.findById(id).orElse(null);
        if (ordenCompleta != null){
            return enriquecerDespacho(ordenCompleta);
        }
        return null;
    }

    @Transactional
    public Orden actualizarOrden(Long id, Orden orden){
        Orden existente = ordenRepository.findById(id).orElse(null);
        if (existente != null){
            existente.setEstadoOrden(orden.getEstadoOrden());
            return ordenRepository.save(existente);
        }
        return null;
    }

    @Transactional
    public void borrarOrden(Long id){
        ordenRepository.deleteById(id);
    }




    public List<Orden> filtrarOrdenYPago(Long idOrden, Long idPago){
        return ordenRepository.findByOrdenYPago(idOrden, idPago);
    }




    // Método interno (Principio Dont Repeat Yourself (DRY))
    private Orden enriquecerDespacho(Orden orden){
        try {
            // llamando a logística y la info se guarda como objeto
            Object datosDelDespacho = webClientBuilder
            .build()
            .get()
            .uri("http://localhost:8085/api/v2/logistica/despacho/" + orden.getNro_Orden())
            .retrieve()
            .bodyToMono(Object.class)
            .block();

            // Seteamos en datosDespacho
            orden.setDatosDespacho(datosDelDespacho);
        } catch (Exception e) {
            orden.setDatosDespacho("Error al cargar datos del despacho.");
        }
        return orden;
    }

    // Validar que la ID del carrito existe
    private void validarExistenciaCarrito(Long id){
        try {
            webClientBuilder
            .build()
            .get()
            .uri("http://localhost:8083/api/v1/carritos/" + id)
            .retrieve()
            .toBodilessEntity() // No devuelve cuerpo, solo código del servidor
            .block();
        } catch (Exception e) {
            // Lanza excepción para que @Transactional haga rollback
            throw new RuntimeException("Error de validación: El carrito no existe o el servicio no responde.");
        }
    }
}
