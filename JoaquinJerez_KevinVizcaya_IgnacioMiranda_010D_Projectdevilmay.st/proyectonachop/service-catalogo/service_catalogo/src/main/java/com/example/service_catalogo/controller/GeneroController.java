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

import com.example.service_catalogo.model.Genero;
import com.example.service_catalogo.service.ProductoService;

@RestController
@RequestMapping("/api/v1/genero")
public class GeneroController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Genero> listar() {
        return productoService.listarGeneros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genero> verDetalle(@PathVariable Long id) {
        return productoService.buscarGeneroPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Genero> crear(@jakarta.validation.Valid @RequestBody Genero genero) {
        return ResponseEntity.ok(productoService.guardarGenero(genero));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genero> actualizar(@PathVariable Long id, @jakarta.validation.Valid @RequestBody Genero genero) {
        Genero actualizado = productoService.actualizarGenero(id, genero);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarGenero(id);
        return ResponseEntity.noContent().build();
    }
}
