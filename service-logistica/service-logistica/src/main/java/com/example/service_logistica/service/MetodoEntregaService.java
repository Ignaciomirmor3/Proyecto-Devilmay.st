package com.example.service_logistica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.service_logistica.model.MetodoEntrega;
import com.example.service_logistica.repository.MetodoEntregaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MetodoEntregaService {

    private final MetodoEntregaRepository metodoEntregaRepository;



    public List<MetodoEntrega> listarMetodos(){
        return metodoEntregaRepository.findAll();
    }

    public MetodoEntrega crearMetodo(MetodoEntrega metodo){
        return metodoEntregaRepository.save(metodo);
    }

    public Optional<MetodoEntrega> encontrarMetodo(Long id){
        return metodoEntregaRepository.findById(id);
    }

    public MetodoEntrega actualizarMetodo(Long id, MetodoEntrega metodo) {
        MetodoEntrega existente = metodoEntregaRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre_Metodo(metodo.getNombre_Metodo());
            return metodoEntregaRepository.save(existente);
        }
        return null;
    }

    public void eliminarMetodo(Long id) {
        metodoEntregaRepository.deleteById(id);
    }
}
