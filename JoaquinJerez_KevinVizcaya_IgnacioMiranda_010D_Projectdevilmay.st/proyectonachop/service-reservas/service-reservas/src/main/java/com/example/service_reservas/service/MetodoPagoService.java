package com.example.service_reservas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service_reservas.model.MetodoPago;
import com.example.service_reservas.repository.MetodoPagoRepository;

@Service
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;



    public List<MetodoPago> listarMetodos(){
        return metodoPagoRepository.findAll();
    }

    public MetodoPago crearMetodo(MetodoPago metodo){
        return metodoPagoRepository.save(metodo);
    }

    public Optional<MetodoPago> encontrarMetodo(Long id){
        return metodoPagoRepository.findById(id);
    }

    public MetodoPago actualizarMetodo(Long id, MetodoPago metodo) {
        MetodoPago existente = metodoPagoRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre_Metodo(metodo.getNombre_Metodo());
            return metodoPagoRepository.save(existente);
        }
        return null;
    }

    public void eliminarMetodo(Long id) {
        metodoPagoRepository.deleteById(id);
    }
}
