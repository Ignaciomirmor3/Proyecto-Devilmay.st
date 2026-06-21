package com.example.service_comprobantes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.service_comprobantes.model.Boleta;
import com.example.service_comprobantes.repository.BoletaRepository;
import com.example.service_comprobantes.service.dto.OrdenDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoletaService {

    private final BoletaRepository boletaRepository;

    private final WebClient.Builder webClientBuilder;



    public List<Boleta> listarBoletas(){
        return boletaRepository.findAll();
    }

    @Transactional
    public Boleta crearBoleta(Boleta boleta){
        
        // Map ordenJson = obtenerOrdenCompleta(boleta);
        OrdenDTO ordenJson = obtenerOrdenCompleta(boleta);
        String nombreEstado = ordenJson.getEstadoOrden().getNombreEstado(); 

        if (!nombreEstado.equalsIgnoreCase("Pagado")) {
            throw new RuntimeException("Error de validación: la orden no ha sido pagada.");
        }

        String cupon = ordenJson.getCodigoCupon();
        boleta.setCodigo_Cupon(cupon);

        return boletaRepository.save(boleta);
    }

    public Optional<Boleta> encontrarBoleta(Long id){
        return boletaRepository.findById(id);
    }

    @Transactional
    public void eliminarBoleta(Long id) {
        boletaRepository.deleteById(id);
    }




    // método interno: obtener la orden completa
    private OrdenDTO obtenerOrdenCompleta(Boleta boleta) {
        try {
            // Transforma el JSON en un diccionario
            // Se testea con el DTO
            return webClientBuilder.build()
                .get()
                .uri("http://localhost:8084/api/v3/reservas/ordenes/" + boleta.getNro_Orden())
                .retrieve()
                .bodyToMono(OrdenDTO.class)
                // .bodyToMono(Map.class) 
                .block();
        } catch (Exception e) {
            throw new RuntimeException("Error de validación: La orden no existe o el servicio no responde.");
        }
    }
}
