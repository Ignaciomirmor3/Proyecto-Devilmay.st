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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service_reservas.model.Orden;
import com.example.service_reservas.service.OrdenService;

@RestController
@RequestMapping("/api/v2/reservas/ordenes")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @GetMapping
    public ResponseEntity<List<Orden>> Listar(){
        List<Orden> ordenes = ordenService.listarOrdenes();
        if (ordenes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ordenes);
    }

    @PostMapping
    public ResponseEntity<?> Crear(@RequestBody Orden orden){
        try {
            return ResponseEntity.ok(ordenService.crearOrden(orden));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orden> EncontrarPorId(@PathVariable Long id){
        Orden existente = ordenService.encontrarOrden(id);
        if (existente != null){
            return ResponseEntity.ok(existente);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orden> actualizarOrden(@PathVariable Long id, @RequestBody Orden orden){
        Orden actualizada = ordenService.actualizarOrden(id, orden);
        if (actualizada != null){
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarOrden(@PathVariable Long id){
        try {
            ordenService.borrarOrden(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }



    
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
