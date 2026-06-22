package com.example.service_logistica.controller;

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

import com.example.service_logistica.model.EstadoDespacho;
import com.example.service_logistica.service.EstadoDespachoService;

@RestController
@RequestMapping("/api/v3/logistica/estadodespacho")
public class EstadoDespachoController {

    @Autowired
    private EstadoDespachoService estadoDespachoService;



    @GetMapping
    public ResponseEntity<List<EstadoDespacho>> Listar(){
        List<EstadoDespacho> estado = estadoDespachoService.listarEstados();
        if (estado.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estado);
    }

    @PostMapping
    public ResponseEntity<EstadoDespacho> Crear(@RequestBody EstadoDespacho estado){
        return ResponseEntity.ok(estadoDespachoService.crearEstado(estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoDespacho> EncontrarPorId(@PathVariable Long id){
        return estadoDespachoService.encontrarEstado(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoDespacho> Actualizar(@PathVariable Long id, @RequestBody EstadoDespacho estado) {
        EstadoDespacho actualizado = estadoDespachoService.actualizarEstado(id, estado);
        if (actualizado != null){
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.noContent().build();
    }

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
