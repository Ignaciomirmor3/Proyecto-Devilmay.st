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
        log.info("Verificando disponibilidad en Inventario para el producto id: {}", carrito.getIdProducto());
        
        Optional<Carrito> existente = carritoRepository.findByIdProducto(carrito.getIdProducto());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("El producto ya se encuentra en un carrito. Solo se permite 1 unidad por prenda exclusiva.");
        }

        // Consultar Inventario
        String inventarioUrl = "http://localhost:8082/api/v1/inventario/producto/" + carrito.getIdProducto();
        try {
            InventarioStatusDTO status = webClientBuilder.build()
                    .get()
                    .uri(inventarioUrl)
                    .retrieve()
                    .bodyToMono(InventarioStatusDTO.class)
                    .block();
            
            if (status == null || !"DISPONIBLE".equals(status.estadoInventario())) {
                throw new IllegalStateException("El producto no esta DISPONIBLE en el inventario.");
            }
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al consultar Inventario: {}", e.getMessage());
            throw new IllegalStateException("Error al conectar con el servicio de Inventario.");
        }

        Carrito guardado = carritoRepository.save(carrito);
        
        // Notificar a Inventario para reservar
        try {
            String reservarUrl = "http://localhost:8082/api/v1/inventario/producto/" + guardado.getIdProducto() + "/reservar";
            log.info("Reservando en inventario: {}", reservarUrl);
            webClientBuilder.build()
                    .put()
                    .uri(reservarUrl)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception e) {
            log.error("Error al reservar en service-inventario: {}", e.getMessage());
        }
        
        return guardado;
    }

    public void eliminar(Long id) {
        Optional<Carrito> item = carritoRepository.findById(id);
        if (item.isPresent()) {
            carritoRepository.deleteById(id);
            // Liberar en inventario
            try {
                String liberarUrl = "http://localhost:8082/api/v1/inventario/producto/" + item.get().getIdProducto() + "/liberar";
                log.info("Liberando en inventario: {}", liberarUrl);
                webClientBuilder.build()
                        .put()
                        .uri(liberarUrl)
                        .retrieve()
                        .bodyToMono(Void.class)
                        .block();
            } catch (Exception e) {
                log.error("Error al liberar en service-inventario: {}", e.getMessage());
            }
        }
    }
    
    public Integer calcularTotalAPagar(String codigoCupon) {
        List<Carrito> prendasEnCarrito = carritoRepository.findAll();
        
        if (prendasEnCarrito.isEmpty()) {
            return 0;
        }

        int total = 0;
        
        // 1. Consultar service-catalogo por el precio de cada prenda
        for (Carrito item : prendasEnCarrito) {
            try {
                String catalogoUrl = "http://localhost:8081/api/v1/productos/" + item.getIdProducto();
                log.info("Consultando precio en catálogo: {}", catalogoUrl);
                
                ProductoCatalogoDTO producto = webClientBuilder.build()
                        .get()
                        .uri(catalogoUrl)
                        .retrieve()
                        .bodyToMono(ProductoCatalogoDTO.class)
                        .block(); 

                if (producto != null && producto.precioProducto() != null) {
                    total += producto.precioProducto();
                }
            } catch (Exception e) {
                log.error("Error al obtener el precio del producto {}: {}", item.getIdProducto(), e.getMessage());
            }
        }

        // 2. Aplicar descuento consultando a service-promociones si se proporcionó un cupón
        if (codigoCupon != null && !codigoCupon.trim().isEmpty() && total > 0) {
            try {
                String promocionesUrl = "http://localhost:8086/api/v1/promociones/" + codigoCupon + "/validar";
                log.info("Validando cupón: {}", promocionesUrl);
                
                CuponValidacionDTO validacion = webClientBuilder.build()
                        .get()
                        .uri(promocionesUrl)
                        .retrieve()
                        .bodyToMono(CuponValidacionDTO.class)
                        .block();

                if (validacion != null && validacion.valido() && validacion.porcentajeDescuento() != null) {
                    log.info("Cupón válido. Descuento aplicado: {}%", validacion.porcentajeDescuento());
                    int descuento = (total * validacion.porcentajeDescuento()) / 100;
                    total -= descuento;
                } else {
                    log.warn("El cupón no es válido o ha expirado.");
                }
            } catch (Exception e) {
                log.error("Error al validar el cupón {}: {}", codigoCupon, e.getMessage());
            }
        }
        
        return total; 
    }

    public record InventarioStatusDTO(String estadoInventario) {}
    public record ProductoCatalogoDTO(Integer precioProducto) {}
    public record CuponValidacionDTO(boolean valido, Integer porcentajeDescuento, String mensaje) {}
}