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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service_logistica.model.Despacho;
import com.example.service_logistica.service.DespachoService;

@RestController
@RequestMapping("/api/v3/logistica/despacho")
public class DespachoController {

    @Autowired
    private DespachoService despachoService;


    
    @GetMapping
    public ResponseEntity<List<Despacho>> Listar(){
        List<Despacho> despachos = despachoService.listarDespachos();
        if (despachos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(despachos);
    }

    @PostMapping
    public ResponseEntity<?> Crear(@RequestBody Despacho despacho){
        try {
            return ResponseEntity.ok(despachoService.crearDespacho(despacho));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despacho> EncontrarPorId(@PathVariable Long id){
        return despachoService.encontrarDespacho(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Despacho> Actualizar(@PathVariable Long id, @RequestBody Despacho despacho) {
        Despacho actualizado = despachoService.actualizarDespacho(id, despacho);
        if (actualizado != null){
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> Eliminar(@PathVariable Long id) {
        try {
            despachoService.eliminarDespacho(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }



    

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