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

import com.example.service_logistica.model.MetodoEntrega;
import com.example.service_logistica.service.MetodoEntregaService;

@RestController
@RequestMapping("/api/v3/logistica/metodoentrega")
public class MetodoEntregaController {

    @Autowired
    private MetodoEntregaService metodoEntregaService;



    @GetMapping
    public ResponseEntity<List<MetodoEntrega>> Listar(){
        List<MetodoEntrega> metodo = metodoEntregaService.listarMetodos();
        if (metodo.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(metodo);
    }

    @PostMapping
    public ResponseEntity<MetodoEntrega> Crear(@RequestBody MetodoEntrega metodo){
        return ResponseEntity.ok(metodoEntregaService.crearMetodo(metodo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoEntrega> EncontrarPorId(@PathVariable Long id){
        return metodoEntregaService.encontrarMetodo(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoEntrega> Actualizar(@PathVariable Long id, @RequestBody MetodoEntrega metodo) {
        MetodoEntrega actualizado = metodoEntregaService.actualizarMetodo(id, metodo);
        if (actualizado != null){
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> Eliminar(@PathVariable Long id) {
        try {
            metodoEntregaService.eliminarMetodo(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
