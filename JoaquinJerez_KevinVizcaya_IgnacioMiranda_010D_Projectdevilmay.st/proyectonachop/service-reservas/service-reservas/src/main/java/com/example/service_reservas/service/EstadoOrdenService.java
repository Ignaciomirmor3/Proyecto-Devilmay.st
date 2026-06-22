package com.example.service_reservas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service_reservas.model.EstadoOrden;
import com.example.service_reservas.repository.EstadoOrdenRepository;

@Service
public class EstadoOrdenService {

    @Autowired
    private EstadoOrdenRepository estadoOrdenRepository;



    public List<EstadoOrden> listarEstados(){
        return estadoOrdenRepository.findAll();
    }

    public EstadoOrden crearEstado(EstadoOrden estado){
        return estadoOrdenRepository.save(estado);
    }

    public Optional<EstadoOrden> encontrarEstado(Long id){
        return estadoOrdenRepository.findById(id);
    }

    public EstadoOrden actualizarEstado(Long id, EstadoOrden estado) {
        EstadoOrden existente = estadoOrdenRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre_Estado(estado.getNombre_Estado());
            return estadoOrdenRepository.save(existente);
        }
        return null;
    }

    public void eliminarEstado(Long id) {
        estadoOrdenRepository.deleteById(id);
    }
}
