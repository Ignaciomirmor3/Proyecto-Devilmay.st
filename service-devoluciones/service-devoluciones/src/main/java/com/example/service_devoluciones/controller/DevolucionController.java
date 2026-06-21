package com.example.service_devoluciones.controller;

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

import com.example.service_devoluciones.model.Devolucion;
import com.example.service_devoluciones.service.DevolucionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/devoluciones")
@RequiredArgsConstructor

@CrossOrigin(origins = "*")
@Tag(name = "Devoluciones", description = "Operaciones relacionadas con la gestión de las solicitudes de devolución")
public class DevolucionController {

    private final DevolucionService devolucionService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La lista se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay solicitudes registradas en la base de datos")
    })
    @Operation(summary = "Obtiene todas las solicitudes", description = "Se obtiene una lista de todas las solicitudes registradas")
    @GetMapping
    public ResponseEntity<List<Devolucion>> Listar(){
        List<Devolucion> devoluciones = devolucionService.listarDevoluciones();
        if (devoluciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(devoluciones);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La solicitud se ha guardado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error: Revisar el formato de la respuesta e intentar de nuevo")
    })
    @Operation(summary = "Crea una nueva solicitud", description = "Guarda una devolución en la base de datos db_devoluciones")
    @PostMapping
    public ResponseEntity<?> Crear(@RequestBody Devolucion devolucion){
        try {
            return ResponseEntity.ok(devolucionService.crearDevolucion(devolucion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La solicitud se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "La solicitud no ha sido encontrada")
    })
    @Operation(summary = "Obtiene una solicitud por ID principal")
    @GetMapping("/{id}")
    public ResponseEntity<Devolucion> EncontrarPorId(@PathVariable Long id){
        Devolucion existente = devolucionService.encontrarDevolucion(id);
        if (existente != null){
            return ResponseEntity.ok(existente);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La solicitud se ha actualizado exitosamente"),
        @ApiResponse(responseCode = "204", description = "La solicitud no se ha encontrado o hubo un error al editar")
    })
    @Operation(summary = "Actualiza una solicitud", description = "Editar el estado de la solicitud para dejar registros en el tiempo")
    @PutMapping("/{id}")
    public ResponseEntity<Devolucion> Actualizar(@PathVariable Long id, @RequestBody Devolucion devolucion){
        Devolucion actualizada = devolucionService.encontrarDevolucion(id);
        if (actualizada != null){
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.noContent().build();
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "La solicitud se ha eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "La solicitud no ha sido encontrada")
    })
    @Operation(summary = "Elimina una solicitud por el ID principal")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> Eliminar(@PathVariable Long id){
        try {
            devolucionService.eliminarDevolucion(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }




    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La lista se ha obtenido exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay solicitudes registradas en la base de datos con ese filtro")
    })
    @Operation(summary = "Filtrar las solicitudes", description = "Se obtiene una lista de devoluciones en base a los filtros aplicados")
    @GetMapping("/filtrar")
    public ResponseEntity<?> Filtrar(
        @RequestParam(required = false) Long idDevolucion){

            List<Devolucion> lista = devolucionService.filtrarDevoluciones(idDevolucion);

            if (lista.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
    }
}
