package com.example.service_carrito.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.service_carrito.model.Carrito;
import com.example.service_carrito.repository.CarritoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<Carrito> listarTodos() {
        return carritoRepository.findAll();
    }

    public Optional<Carrito> buscarPorId(Long id) {
        return carritoRepository.findById(id);
    }

    public Carrito guardar(Carrito carrito) {
        log.info("Guardando carrito para sesión/usuario id: {}", carrito.getId_carrito());
        Carrito guardado = carritoRepository.save(carrito);
        
        // Comunicación síncrona con service-catalogo
        if (guardado.getIdProducto() != null) {
            try {
                String catalogoUrl = "http://localhost:8081/api/v1/productos/" + guardado.getIdProducto() + "/estado?estado=RESERVADO";
                log.info("Notificando a catálogo: {}", catalogoUrl);
                // Usamos WebClient PUT
                webClientBuilder.build()
                        .put()
                        .uri(catalogoUrl)
                        .retrieve()
                        .bodyToMono(Void.class)
                        .subscribe();
            } catch (Exception e) {
                log.error("Error al comunicar con service-catalogo: {}", e.getMessage());
            }
        }
        
        return guardado;
    }

    public void eliminar(Long id) {
        carritoRepository.deleteById(id);
    }
    
    //Logica Financiera
    public Integer calcularTotalAPagar() {
        List<Carrito> prendasEnCarrito = carritoRepository.findAll();
        
        // aquí se implementará la comunicación con service-catalogo (WebClient/Feign)
        // para buscar el precio de cada 'idProducto' y sumarlos.
        // Por ahora, retornamos 0 para dejar la estructura lista.
        
        return 0; 
    }
}