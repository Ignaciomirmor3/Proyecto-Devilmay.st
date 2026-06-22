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

import com.example.service_reservas.model.MetodoPago;
import com.example.service_reservas.service.MetodoPagoService;

@RestController
@RequestMapping("/api/v2/reservas/metodopago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;



    @GetMapping
    public ResponseEntity<List<MetodoPago>> Listar(){
        List<MetodoPago> metodo = metodoPagoService.listarMetodos();
        if (metodo.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(metodo);
    }

    @PostMapping
    public ResponseEntity<MetodoPago> Crear(@RequestBody MetodoPago metodo){
        return ResponseEntity.ok(metodoPagoService.crearMetodo(metodo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPago> EncontrarPorId(@PathVariable Long id){
        return metodoPagoService.encontrarMetodo(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPago> Actualizar(@PathVariable Long id, @RequestBody MetodoPago metodo) {
        MetodoPago actualizado = metodoPagoService.actualizarMetodo(id, metodo);
        if (actualizado != null){
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.noContent().build();
    }

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
