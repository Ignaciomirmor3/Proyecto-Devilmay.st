package com.example.service_logistica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.service_logistica.model.Despacho;
import com.example.service_logistica.repository.DespachoRepository;

import jakarta.transaction.Transactional;

@Service
public class DespachoService {

    @Autowired
    private DespachoRepository despachoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<Despacho> listarDespachos(){
        return despachoRepository.findAll();
    }

    @Transactional
    public Despacho crearDespacho(Despacho despacho){
        // VERIFICAR SI ID_CARRITO EXISTE EN SERVICE-CARRITO
        validarExistenciaOrden(despacho.getNro_Orden());

        return despachoRepository.save(despacho);
    }

    public Optional<Despacho> encontrarDespacho(Long id){
        return despachoRepository.findById(id);
    }

    @Transactional
    public Despacho actualizarDespacho(Long id, Despacho despacho) {
        Despacho existente = despachoRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setEstadoDespacho(despacho.getEstadoDespacho());
            return despachoRepository.save(existente);
        }
        return null;
    }

    @Transactional
    public void eliminarDespacho(Long id) {
        despachoRepository.deleteById(id);
    }




    public List<Despacho> filtrarDespachoYEntrega(Long idDespacho, Long idEntrega){
        return despachoRepository.findByDespachoYEntrega(idDespacho, idEntrega);
    }




    // Validar que la ID de la orden existe
    private void validarExistenciaOrden(Long id){
        try {
            webClientBuilder
            .build()
            .get()
            .uri("http://localhost:8084/api/v2/reservas/ordenes/" + id)
            .retrieve()
            .toBodilessEntity() // No devuelve cuerpo, solo código del servidor
            .block();
        } catch (Exception e) {
            // Lanza excepción para que @Transactional haga rollback
            throw new RuntimeException("Error de validación: La orden no existe o el servicio no responde.");
        }
    }
}
