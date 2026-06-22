package com.example.service_calificaciones.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service_calificaciones.model.Resena;
import com.example.service_calificaciones.repository.ResenaRepository;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    public List<Resena> listarTodas() {
        return resenaRepository.findAll();
    }

    public Optional<Resena> buscarPorID(Long id){
        return resenaRepository.findById(id);
    }

    public Resena guardar(Resena resena){
        return resenaRepository.save(resena);
    }

    public List<Resena> listarPorEstrellas(Integer calificacion){
        return resenaRepository.findByCalificacion(calificacion);
    }

}
