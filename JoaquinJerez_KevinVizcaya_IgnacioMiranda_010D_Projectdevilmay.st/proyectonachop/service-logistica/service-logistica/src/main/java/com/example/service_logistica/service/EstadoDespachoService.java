package com.example.service_logistica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service_logistica.model.EstadoDespacho;
import com.example.service_logistica.repository.EstadoDespachoRepository;

@Service
public class EstadoDespachoService {

    @Autowired
    private EstadoDespachoRepository estadoDespachoRepository;

    

    public List<EstadoDespacho> listarEstados(){
        return estadoDespachoRepository.findAll();
    }

    public EstadoDespacho crearEstado(EstadoDespacho estado){
        return estadoDespachoRepository.save(estado);
    }

    public Optional<EstadoDespacho> encontrarEstado(Long id){
        return estadoDespachoRepository.findById(id);
    }

    public EstadoDespacho actualizarEstado(Long id, EstadoDespacho estado) {
        EstadoDespacho existente = estadoDespachoRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre_Estado(estado.getNombre_Estado());
            return estadoDespachoRepository.save(existente);
        }
        return null;
    }

    public void eliminarEstado(Long id) {
        estadoDespachoRepository.deleteById(id);
    }
}
