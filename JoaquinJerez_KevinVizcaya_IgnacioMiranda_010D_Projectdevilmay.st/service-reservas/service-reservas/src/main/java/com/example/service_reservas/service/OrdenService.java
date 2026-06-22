package com.example.service_reservas.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.service_reservas.dto.CuponDTO;
import com.example.service_reservas.model.Orden;
import com.example.service_reservas.repository.OrdenRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdenService {

    private final OrdenRepository ordenRepository;

    private final WebClient.Builder webClientBuilder;

    public List<Orden> listarOrdenes(){
        return ordenRepository.findAll();
    }

    @Transactional
    public Orden crearOrden(Orden orden){
        validarExistenciaCarrito(orden.getId_Carrito());
        
        Integer total = orden.getPrecio_Total();

        if (orden.getCodigo_Cupon() != null) {
            Integer descuento = obtenerPorcentajeDescuento(orden.getCodigo_Cupon());
            total = (int) (total - (total * (descuento / 100.0)));
        }
        
        orden.setPrecio_Total(total);
        return ordenRepository.save(orden);
    }

    public Orden encontrarOrden(Long id){
        Orden ordenCompleta = ordenRepository.findById(id).orElse(null);
        if (ordenCompleta != null){
            enriquecerDespacho(ordenCompleta);
            enriquecerBoleta(ordenCompleta);
            return ordenCompleta;
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
    public void eliminarOrden(Long id){
        ordenRepository.deleteById(id);
    }




    public List<Orden> filtrarOrdenYPago(Long idOrden, Long idPago){
        return ordenRepository.findByOrdenYPago(idOrden, idPago);
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

    // Extraer el porcentaje y calcular el total a pagar
    private Integer obtenerPorcentajeDescuento(String codigoCupon) {
        try {
            // Transforma el JSON en un diccionario
            CuponDTO respuestaJson = webClientBuilder
                .build()
                .get()
                // URI DE PROMOCIONES
                .uri("http://localhost:8086/api/v1/cupones/" + codigoCupon)
                .retrieve()
                .bodyToMono(CuponDTO.class) 
                .block();
                
            // Extraemos el número directamente usando el nombre del atributo del JSON
            return respuestaJson.getPorcentajeDescuento();
            
        } catch (Exception e) {
            throw new RuntimeException("El cupón ingresado no es válido o ha expirado.");
        }
    }

    // Método interno: enriquecerDespacho (Principio Dont Repeat Yourself (DRY))
    private Orden enriquecerDespacho(Orden orden){
        try {
            // llamando a logística y la info se guarda como objeto
            Object datosDelDespacho = webClientBuilder
            .build()
            .get()
            .uri("http://localhost:8085/api/v4/logistica/despacho/" + orden.getNro_Orden())
            .retrieve()
            .bodyToMono(Object.class)
            .block();

            // Seteamos en datosDespacho
            orden.setDatos_Despacho(datosDelDespacho);
        } catch (Exception e) {
            orden.setDatos_Despacho("Error al cargar datos del despacho.");
        }
        return orden;
    }

    // Método interno: enriquecerBoleta
    private Orden enriquecerBoleta(Orden orden){
        try {
            // llamando a comprobantes y la info se guarda como objeto
            Object datosDeBoleta = webClientBuilder
            .build()
            .get()
            .uri("http://localhost:8088/api/v1/comprobantes/" + orden.getNro_Orden())
            .retrieve()
            .bodyToMono(Object.class)
            .block();

            // Seteamos en datosBoleta
            orden.setDatos_Boleta(datosDeBoleta);
        } catch (Exception e) {
            orden.setDatos_Boleta("Error al cargar datos de la boleta.");
        }
        return orden;
    }
}
