package com.example.service_carrito.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service_carrito.dto.CarritoDTO;
import com.example.service_carrito.model.Carrito;
import com.example.service_carrito.service.CarritoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name = "Carrito", description = "API para la gestion de Carrito")
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
        return ResponseEntity.ok(carritoService.guardar(carrito));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        carritoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    //Para calcular el total a pagar (y aplicar cupones de service-promociones)
    @GetMapping("/total")
    public ResponseEntity<Integer> calcularTotal(
            @org.springframework.web.bind.annotation.RequestParam(required = false) String cupon) {
        return ResponseEntity.ok(carritoService.calcularTotalAPagar(cupon));
    }
}

