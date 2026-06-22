package com.example.service_catalogo.controller;

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

import com.example.service_catalogo.model.TipoPrenda;
import com.example.service_catalogo.service.ProductoService;

@RestController
@RequestMapping("/api/v1/tipos-prenda")
public class TipoPrendaController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<TipoPrenda> listar() {
        return productoService.listarPrendas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoPrenda> verDetalle(@PathVariable Long id) {
        return productoService.buscarTipoPrendaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoPrenda> crear(@jakarta.validation.Valid @RequestBody TipoPrenda tipoPrenda) {
        return ResponseEntity.ok(productoService.guardarTipoPrenda(tipoPrenda));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoPrenda> actualizar(@PathVariable Long id, @jakarta.validation.Valid @RequestBody TipoPrenda tipoPrenda) {
        TipoPrenda actualizado = productoService.actualizarTipoPrenda(id, tipoPrenda);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarTipoPrenda(id);
        return ResponseEntity.noContent().build();
    }
}
