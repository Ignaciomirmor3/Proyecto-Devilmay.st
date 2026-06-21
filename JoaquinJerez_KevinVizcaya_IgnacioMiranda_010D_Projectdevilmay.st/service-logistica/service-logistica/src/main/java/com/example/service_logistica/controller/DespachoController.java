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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service_logistica.model.Despacho;
import com.example.service_logistica.service.DespachoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v4/logistica/despacho")
@RequiredArgsConstructor

@CrossOrigin(origins = "*")
@Tag(name = "Despachos", description = "Operaciones relacionadas con la gestión de los despachos de las ordenes")
public class DespachoController {

    private final DespachoService despachoService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La lista se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay despachos registrados en la base de datos")
    })
    @Operation(summary = "Obtiene todos los despachos", description = "Se obtiene una lista de todos los despachos registrados")
    @GetMapping
    public ResponseEntity<List<Despacho>> Listar(){
        List<Despacho> despachos = despachoService.listarDespachos();
        if (despachos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(despachos);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El despacho se ha guardado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error: Revisar el formato de la respuesta e intentar de nuevo")
    })
    @Operation(summary = "Crea un nuevo despacho", description = "Guarda un despacho en la base de datos db_logistica")
    @PostMapping
    public ResponseEntity<?> Crear(@RequestBody Despacho despacho){
        try {
            return ResponseEntity.ok(despachoService.crearDespacho(despacho));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El despacho se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "El despacho no ha sido encontrado")
    })
    @Operation(summary = "Obtiene un despacho por ID principal")
    @GetMapping("/{id}")
    public ResponseEntity<Despacho> EncontrarPorId(@PathVariable Long id){
        return despachoService.encontrarDespacho(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El despacho se ha actualizado exitosamente"),
        @ApiResponse(responseCode = "204", description = "El despacho no se ha encontrado o hubo un error al editar")
    })
    @Operation(summary = "Actualiza un despacho", description = "Editar el estado de un despacho para dejar registros en el tiempo")
    @PutMapping("/{id}")
    public ResponseEntity<Despacho> Actualizar(@PathVariable Long id, @RequestBody Despacho despacho) {
        Despacho actualizado = despachoService.actualizarDespacho(id, despacho);
        if (actualizado != null){
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.noContent().build();
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "El despacho se ha eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "El despacho no ha sido encontrado")
    })
    @Operation(summary = "Elimina un despacho por el ID principal")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> Eliminar(@PathVariable Long id) {
        try {
            despachoService.eliminarDespacho(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }



    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La lista se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay despachos registrados en la base de datos con esos filtros")
    })
    @Operation(summary = "Filtrar los despachos", description = "Se obtiene una lista de despachos en base a los filtros aplicados")
    @GetMapping("/filtrar")
    public ResponseEntity<?> Filtrar(
        @RequestParam(required = false) Long idDespacho, 
        @RequestParam(required = false) Long idEntrega){

            List<Despacho> lista = despachoService.filtrarDespachoYEntrega(idDespacho, idEntrega);

            if (lista.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
        }
}