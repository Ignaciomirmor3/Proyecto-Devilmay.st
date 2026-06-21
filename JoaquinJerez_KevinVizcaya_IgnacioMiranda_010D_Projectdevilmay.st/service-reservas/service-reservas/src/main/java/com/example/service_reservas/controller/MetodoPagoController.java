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

import com.example.service_reservas.model.MetodoPago;
import com.example.service_reservas.service.MetodoPagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v3/reservas/metodopago")
@RequiredArgsConstructor

@CrossOrigin(origins = "*")
@Tag(name = "Métodos de pago", description = "Operaciones relacionadas con la gestión de métodos de pago de las ordenes")
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La lista se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay métodos registrados en la base de datos")
    })
    @Operation(summary = "Obtiene todos los métodos", description = "Se obtiene una lista de todos los métodos registrados")
    @GetMapping
    public ResponseEntity<List<MetodoPago>> Listar(){
        List<MetodoPago> metodo = metodoPagoService.listarMetodos();
        if (metodo.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(metodo);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El método se ha guardado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error: Revisar el formato de la respuesta e intentar de nuevo")
    })
    @Operation(summary = "Crea un nuevo método", description = "Guarda un método de orden en la tabla MetodoPago de la base de datos db_reservas")
    @PostMapping
    public ResponseEntity<MetodoPago> Crear(@RequestBody MetodoPago metodo){
        return ResponseEntity.ok(metodoPagoService.crearMetodo(metodo));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El método se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "El método no ha sido encontrado")
    })
    @Operation(summary = "Obtiene un método por ID principal")
    @GetMapping("/{id}")
    public ResponseEntity<MetodoPago> EncontrarPorId(@PathVariable Long id){
        return metodoPagoService.encontrarMetodo(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El método se ha actualizado exitosamente"),
        @ApiResponse(responseCode = "204", description = "El método no se ha encontrado o hubo un error al editar")
    })
    @Operation(summary = "Actualiza un método", description = "Editar el nombre del método de pago en los registros")
    @PutMapping("/{id}")
    public ResponseEntity<MetodoPago> Actualizar(@PathVariable Long id, @RequestBody MetodoPago metodo) {
        MetodoPago actualizado = metodoPagoService.actualizarMetodo(id, metodo);
        if (actualizado != null){
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.noContent().build();
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "El método se ha eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "El método no ha sido encontrado")
    })
    @Operation(summary = "Elimina un método por el ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> Eliminar(@PathVariable Long id) {
        try {
            metodoPagoService.eliminarMetodo(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
