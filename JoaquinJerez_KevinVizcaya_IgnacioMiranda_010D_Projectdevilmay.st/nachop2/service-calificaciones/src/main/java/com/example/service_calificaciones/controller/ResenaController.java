package com.example.service_calificaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service_calificaciones.model.Resena;
import com.example.service_calificaciones.service.ResenaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/calificaciones")
@Tag(name = "Reseñas", description = "Operaciones realizadas con la gestión de reseñas")
@CrossOrigin(origins = "*")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @Operation(summary = "Obtener todas las reseñas", description = "Retorna una lista de todas las reseñas de usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay reseñas registradas para mostrar")
    })
    @GetMapping
    public List<Resena> listar(){
        return resenaService.listarTodas();
    }

    @Operation(summary = "Obtener reseña por ID", description = "Retorna la reseña con el respectivo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña encontrada"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada / no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Resena> verResena(@PathVariable Long id){
        return resenaService.buscarPorID(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nueva reseña", description = "Guarda una nueva reseña en la base de datos db_calificaciones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña guardada exitosamente"),
        @ApiResponse(responseCode = "400", description = "ERROR: Formato inválido, inténtelo de nuevo.")
    })
    @PostMapping
    public ResponseEntity<Resena> crear(@RequestBody Resena resena){
        return ResponseEntity.ok(resenaService.guardar(resena));
    }

    @Operation(summary = "Listar por estrellas", description = "Retorna una lista de todas las reseñas con una calificación específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseñas encontradas"),
        @ApiResponse(responseCode = "204", description = "No existen reseñas con la cant. de estrellas ingresadas")
    })
    @GetMapping("/estrellas/{calificacion}")
    public ResponseEntity<List<Resena>> buscarPorCalificacion(@PathVariable Integer calificacion){
        return ResponseEntity.ok(resenaService.listarPorEstrellas(calificacion));
    }

}
