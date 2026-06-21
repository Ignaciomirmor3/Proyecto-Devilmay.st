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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service_reservas.model.Orden;
import com.example.service_reservas.service.OrdenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v3/reservas/ordenes")
@RequiredArgsConstructor

@CrossOrigin(origins = "*")
@Tag(name = "Ordenes", description = "Operaciones relacionadas con la gestión de las ordenes de los clientes")
public class OrdenController {

    private final OrdenService ordenService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La lista se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay ordenes registradas en la base de datos")
    })
    @Operation(summary = "Obtiene todas las ordenes", description = "Se obtiene una lista de todas las ordenes registradas")
    @GetMapping
    public ResponseEntity<List<Orden>> Listar(){
        List<Orden> ordenes = ordenService.listarOrdenes();
        if (ordenes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ordenes);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La orden se ha guardado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error: Revisar el formato de la respuesta e intentar de nuevo")
    })
    @Operation(summary = "Crea una nueva orden", description = "Guarda una orden en la base de datos db_reservas")
    @PostMapping
    public ResponseEntity<?> Crear(@RequestBody Orden orden){
        try {
            return ResponseEntity.ok(ordenService.crearOrden(orden));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La orden se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "La orden no ha sido encontrada")
    })
    @Operation(summary = "Obtiene una orden por ID principal")
    @GetMapping("/{id}")
    public ResponseEntity<Orden> EncontrarPorId(@PathVariable Long id){
        Orden existente = ordenService.encontrarOrden(id);
        if (existente != null){
            return ResponseEntity.ok(existente);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La orden se ha actualizado exitosamente"),
        @ApiResponse(responseCode = "204", description = "La orden no se ha encontrado o hubo un error al editar")
    })
    @Operation(summary = "Actualiza una orden", description = "Editar el estado de la orden para dejar registros en el tiempo")
    @PutMapping("/{id}")
    public ResponseEntity<Orden> Actualizar(@PathVariable Long id, @RequestBody Orden orden){
        Orden actualizada = ordenService.actualizarOrden(id, orden);
        if (actualizada != null){
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.noContent().build();
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "La orden se ha eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "La orden no ha sido encontrada")
    })
    @Operation(summary = "Elimina una orden por el ID principal")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> Eliminar(@PathVariable Long id){
        try {
            ordenService.eliminarOrden(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }



    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La lista se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay ordenes registradas en la base de datos con esos filtros")
    })
    @Operation(summary = "Filtrar las ordenes", description = "Se obtiene una lista de ordenes en base a los filtros aplicados")
    @GetMapping("/filtrar")
    public ResponseEntity<?> Filtrar(
        @RequestParam(required = false) Long idOrden, 
        @RequestParam(required = false) Long idPago){

            List<Orden> lista = ordenService.filtrarOrdenYPago(idOrden, idPago);

            if (lista.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
        }
}
