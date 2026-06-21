package com.example.service_devoluciones.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.service_devoluciones.model.EstadoDevolucion;
import com.example.service_devoluciones.repository.EstadoDevolucionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstadoDevolucionService {

    private final EstadoDevolucionRepository estadoDevolucionRepository;



    public List<EstadoDevolucion> listarEstados(){
        return estadoDevolucionRepository.findAll();
    }

    public EstadoDevolucion crearEstado(EstadoDevolucion estado){
        return estadoDevolucionRepository.save(estado);
    }

    public Optional<EstadoDevolucion> encontrarEstado(Long id){
        return estadoDevolucionRepository.findById(id);
    }

    public EstadoDevolucion actualizarEstado(Long id, EstadoDevolucion estado) {
        EstadoDevolucion existente = estadoDevolucionRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre_Estado(estado.getNombre_Estado());
            return estadoDevolucionRepository.save(existente);
        }
        return null;
    }

    public void eliminarEstado(Long id) {
        estadoDevolucionRepository.deleteById(id);
    }
}
