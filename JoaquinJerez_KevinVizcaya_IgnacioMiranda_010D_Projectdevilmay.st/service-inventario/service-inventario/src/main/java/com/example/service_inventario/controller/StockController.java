package com.example.service_inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.service_inventario.model.Stock;
import com.example.service_inventario.service.StockService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/inventario")
@Tag(name = "Inventario", description = "API para gestionar el stock y estados de exclusividad")
public class StockController {

    @Autowired
    private StockService stockService;

    @Operation(summary = "Consultar el estado de un producto")
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<Stock> consultarEstado(@PathVariable Long idProducto) {
        return stockService.consultarEstado(idProducto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Reservar un producto (DISPONIBLE -> RESERVADO)")
    @PutMapping("/producto/{idProducto}/reservar")
    public ResponseEntity<Stock> reservar(@PathVariable Long idProducto) {
        return ResponseEntity.ok(stockService.reservar(idProducto));
    }

    @Operation(summary = "Liberar un producto reservado (RESERVADO -> DISPONIBLE)")
    @PutMapping("/producto/{idProducto}/liberar")
    public ResponseEntity<Stock> liberar(@PathVariable Long idProducto) {
        return ResponseEntity.ok(stockService.liberar(idProducto));
    }

    @Operation(summary = "Vender un producto reservado (RESERVADO -> VENDIDO)")
    @PutMapping("/producto/{idProducto}/vender")
    public ResponseEntity<Stock> vender(@PathVariable Long idProducto) {
        return ResponseEntity.ok(stockService.vender(idProducto));
    }
}
