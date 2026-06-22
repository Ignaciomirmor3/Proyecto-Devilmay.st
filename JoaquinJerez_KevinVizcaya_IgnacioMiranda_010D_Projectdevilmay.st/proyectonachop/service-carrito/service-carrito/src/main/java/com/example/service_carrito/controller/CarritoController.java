package com.example.service_carrito.controller;

import com.example.service_carrito.dto.CarritoDTO;
import lombok.extern.slf4j.Slf4j;

import com.example.service_carrito.model.Carrito;
import com.example.service_carrito.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/carritos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    public List<Carrito> listar() {
        return carritoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrito> verDetalle(@PathVariable Long id) {
        return carritoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Carrito> crear(@jakarta.validation.Valid @RequestBody CarritoDTO carritoDTO) {
        log.info("Llamada a POST /api/v1/carritos para idProducto: {}", carritoDTO.getIdProducto());
        Carrito carrito = new Carrito();
        carrito.setIdProducto(carritoDTO.getIdProducto());
        carrito.setCantidad(carritoDTO.getCantidad());
        return ResponseEntity.ok(carritoService.guardar(carrito));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        carritoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    //Para calcular el total a pagar
    @GetMapping("/total")
    public ResponseEntity<Integer> calcularTotal() {
        return ResponseEntity.ok(carritoService.calcularTotalAPagar());
    }
}
