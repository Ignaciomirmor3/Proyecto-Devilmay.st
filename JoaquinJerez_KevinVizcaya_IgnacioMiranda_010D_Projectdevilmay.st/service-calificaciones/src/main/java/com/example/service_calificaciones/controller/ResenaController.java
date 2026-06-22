package com.example.service_calificaciones.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service_calificaciones.model.Resena;
import com.example.service_calificaciones.service.ResenaService;


@RestController
@RequestMapping("/api/v1/calificaciones")
@Tag(name = "Calificaciones", description = "Endpoints para gestionar reseñas y calificaciones de productos")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @GetMapping
    @Operation(summary = "Obtener todas las calificaciones")
    public List<Resena> listar(){
        return resenaService.listarTodas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una calificación por su ID")
    public ResponseEntity<Resena> verResena(@PathVariable Long id){
        return resenaService.buscarPorID(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva reseña/calificación")
    public ResponseEntity<Resena> crear(@RequestBody Resena resena){
        return ResponseEntity.ok(resenaService.guardar(resena));
    }

    @GetMapping("/estrellas/{calificacion}")
    @Operation(summary = "Buscar reseñas filtradas por cantidad de estrellas (1 a 5)")
    public ResponseEntity<List<Resena>> buscarPorCalificacion(@PathVariable Integer calificacion){
        return ResponseEntity.ok(resenaService.listarPorEstrellas(calificacion));
    }

}
