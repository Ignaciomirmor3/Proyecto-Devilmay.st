package com.example.service_reservas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api/v2/reservas/estadoorden")
public class EstadoOrdenController {

    @Autowired
    private EstadoOrdenService estadoOrdenService;



    @GetMapping
    public ResponseEntity<List<EstadoOrden>> Listar(){
        List<EstadoOrden> estado = estadoOrdenService.listarEstados();
        if (estado.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estado);
    }

    @PostMapping
    public ResponseEntity<EstadoOrden> Crear(@RequestBody EstadoOrden estado){
        return ResponseEntity.ok(estadoOrdenService.crearEstado(estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoOrden> EncontrarPorId(@PathVariable Long id){
        return estadoOrdenService.encontrarEstado(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoOrden> Actualizar(@PathVariable Long id, @RequestBody EstadoOrden estado) {
        EstadoOrden actualizado = estadoOrdenService.actualizarEstado(id, estado);
        if (actualizado != null){
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.noContent().build();
    }

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
