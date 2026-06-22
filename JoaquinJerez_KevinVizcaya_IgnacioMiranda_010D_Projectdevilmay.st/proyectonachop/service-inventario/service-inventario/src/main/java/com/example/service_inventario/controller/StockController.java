package com.example.service_inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.service_inventario.model.Stock;
import com.example.service_inventario.service.StockService;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping
    public ResponseEntity<Stock> crear(@jakarta.validation.Valid @RequestBody Stock stock){ //NO estoy seguro todavia si debe estar esto aca...
        return ResponseEntity.ok(stockService.guardar(stock));
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<java.util.List<Stock>> buscarPorProducto(@PathVariable Long idProducto){
        return ResponseEntity.ok(stockService.buscarPorProducto(idProducto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> actualizar(@PathVariable Long id, @jakarta.validation.Valid @RequestBody Stock stock){
        Stock actualizado = stockService.actualizar(id, stock);
        if(actualizado != null){
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        stockService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
