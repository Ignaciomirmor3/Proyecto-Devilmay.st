package com.example.service_catalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.service_catalogo.dto.ProductoRequestDTO;
import com.example.service_catalogo.dto.ProductoResponseDTO;
import com.example.service_catalogo.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Productos", description = "API para la gestion de Productos")
@RequestMapping("/api/v1/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    
    @Operation(summary = "Obtener lista de recursos")
    @GetMapping
    public List<ProductoResponseDTO> listar(
            @RequestParam(required = false) com.example.service_catalogo.model.Genero genero, 
            @RequestParam(required = false) com.example.service_catalogo.model.TipoPrenda tipoPrenda){
        if (genero != null && tipoPrenda != null) {
            return productoService.buscarPorGeneroYTipo(genero, tipoPrenda);
        } else if (genero != null) {
            return productoService.buscarPorGenero(genero);
        } else if (tipoPrenda != null) {
            return productoService.buscarPorTipoPrenda(tipoPrenda);
        }
        return productoService.listarTodos();
    }

    @Operation(summary = "Obtener recurso por parametro")
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> verDetalle(@PathVariable Long id){
        return productoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo recurso")
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@jakarta.validation.Valid @RequestBody ProductoRequestDTO productoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.guardar(productoDTO));
    }

    @Operation(summary = "Actualizar un recurso existente")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id, @jakarta.validation.Valid @RequestBody ProductoRequestDTO productoDTO) {
        ProductoResponseDTO actualizado = productoService.actualizar(id, productoDTO);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Actualizar un recurso existente")
    @PutMapping("/{id}/estado")
    public ResponseEntity<ProductoResponseDTO> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        ProductoResponseDTO actualizado = productoService.actualizarEstado(id, estado);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar un recurso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }  

}

