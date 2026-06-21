package com.example.service_reservas.controller;

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

import com.example.service_reservas.model.EstadoOrden;
import com.example.service_reservas.service.EstadoOrdenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v3/reservas/estadoorden")
@RequiredArgsConstructor

@CrossOrigin(origins = "*")
@Tag(name = "Estados de las ordenes", description = "Operaciones relacionadas con la gestión de estados de las ordenes")
public class EstadoOrdenController {

    private final EstadoOrdenService estadoOrdenService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La lista se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay estados registrados en la base de datos")
    })
    @Operation(summary = "Obtiene todos los estados", description = "Se obtiene una lista de todos los estados registrados")
    @GetMapping
    public ResponseEntity<List<EstadoOrden>> Listar(){
        List<EstadoOrden> estado = estadoOrdenService.listarEstados();
        if (estado.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estado);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El estado se ha guardado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error: Revisar el formato de la respuesta e intentar de nuevo")
    })
    @Operation(summary = "Crea un nuevo estado", description = "Guarda un estado de orden en la tabla EstadoOrden de la base de datos db_reservas")
    @PostMapping
    public ResponseEntity<EstadoOrden> Crear(@RequestBody EstadoOrden estado){
        return ResponseEntity.ok(estadoOrdenService.crearEstado(estado));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El estado se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "El estado no ha sido encontrado")
    })
    @Operation(summary = "Obtiene un estado por ID principal")
    @GetMapping("/{id}")
    public ResponseEntity<EstadoOrden> EncontrarPorId(@PathVariable Long id){
        return estadoOrdenService.encontrarEstado(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El estado se ha actualizado exitosamente"),
        @ApiResponse(responseCode = "204", description = "El estado no se ha encontrado o hubo un error al editar")
    })
    @Operation(summary = "Actualiza un estado", description = "Editar el nombre del estado de la orden en los registros")
    @PutMapping("/{id}")
    public ResponseEntity<EstadoOrden> Actualizar(@PathVariable Long id, @RequestBody EstadoOrden estado) {
        EstadoOrden actualizado = estadoOrdenService.actualizarEstado(id, estado);
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
            estadoOrdenService.eliminarEstado(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
