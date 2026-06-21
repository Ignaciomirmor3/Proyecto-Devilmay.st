package com.example.service_comprobantes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service_comprobantes.model.Boleta;
import com.example.service_comprobantes.service.BoletaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/comprobantes")
@RequiredArgsConstructor

@CrossOrigin(origins = "*")
@Tag(name = "Boletas", description = "Operaciones relacionadas con la gestión de las boletas")
public class BoletaController {

    private final BoletaService boletaService;
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La lista se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay boletas registradas en la base de datos")
    })
    @Operation(summary = "Obtiene todas las boletas", description = "Se obtiene una lista de todas las boletas registradas")
    @GetMapping
    public ResponseEntity<List<Boleta>> Listar(){
        List<Boleta> boletas = boletaService.listarBoletas();
        if (boletas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(boletas);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La boleta se ha guardado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error: Revisar el formato de la respuesta e intentar de nuevo")
    })
    @Operation(summary = "Crea una nueva boleta", description = "Guarda una boleta nueva en la base de datos db_comprobantes")
    @PostMapping
    public ResponseEntity<?> Crear(@RequestBody Boleta boleta){
        try {
            return ResponseEntity.ok(boletaService.crearBoleta(boleta));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La boleta se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "La boleta no ha sido encontrada")
    })
    @Operation(summary = "Obtiene una boleta por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Boleta> EncontrarPorId(@PathVariable Long id){
        return boletaService.encontrarBoleta(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "La boleta se ha eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "La boleta no ha sido encontrada")
    })
    @Operation(summary = "Elimina una boleta por el ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> Eliminar(@PathVariable Long id) {
        try {
            boletaService.eliminarBoleta(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
