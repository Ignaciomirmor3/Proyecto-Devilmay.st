package com.example.service_logistica.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service_logistica.model.EstadoDespacho;
import com.example.service_logistica.service.EstadoDespachoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v4/logistica/estadodespacho")
@RequiredArgsConstructor

@CrossOrigin(origins = "*")
@Tag(name = "Estados de los despachos", description = "Operaciones relacionadas con la gestión de estados de los despachos")
public class EstadoDespachoController {

    private final EstadoDespachoService estadoDespachoService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La lista se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay estados registrados en la base de datos")
    })
    @Operation(summary = "Obtiene todos los estados", description = "Se obtiene una lista de todos los estados registrados")
    @GetMapping
    public ResponseEntity<List<EstadoDespacho>> Listar(){
        List<EstadoDespacho> estado = estadoDespachoService.listarEstados();
        if (estado.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estado);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El estado se ha guardado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error: Revisar el formato de la respuesta e intentar de nuevo")
    })
    @Operation(summary = "Crea un nuevo estado", description = "Guarda un estado de despacho en la tabla EstadoDespacho de la base de datos db_logistica")
    @PostMapping
    public ResponseEntity<EstadoDespacho> Crear(@RequestBody EstadoDespacho estado){
        return ResponseEntity.ok(estadoDespachoService.crearEstado(estado));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El estado se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "El estado no ha sido encontrado")
    })
    @Operation(summary = "Obtiene un estado por ID principal")
    @GetMapping("/{id}")
    public ResponseEntity<EstadoDespacho> EncontrarPorId(@PathVariable Long id){
        return estadoDespachoService.encontrarEstado(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El estado se ha actualizado exitosamente"),
        @ApiResponse(responseCode = "204", description = "El estado no se ha encontrado o hubo un error al editar")
    })
    @Operation(summary = "Actualiza un estado", description = "Editar el nombre del estado de despacho en los registros")
    @PutMapping("/{id}")
    public ResponseEntity<EstadoDespacho> Actualizar(@PathVariable Long id, @RequestBody EstadoDespacho estado) {
        EstadoDespacho actualizado = estadoDespachoService.actualizarEstado(id, estado);
        if (actualizado != null){
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.noContent().build();
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "El estado se ha eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "El estado no ha sido encontrado")
    })
    @Operation(summary = "Elimina un estado por el ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> Eliminar(@PathVariable Long id) {
        try {
            estadoDespachoService.eliminarEstado(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
