package com.example.service_inventario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.example.service_inventario.model.EstadoInventario;
import com.example.service_inventario.model.Stock;
import com.example.service_inventario.repository.StockRepository;

import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public Stock crearOActualizarStock(Long idProducto, EstadoInventario estado) {
        Stock stock = stockRepository.findByIdProducto(idProducto).orElse(new Stock());
        stock.setIdProducto(idProducto);
        stock.setEstadoInventario(estado);
        return stockRepository.save(stock);
    }

    public Optional<Stock> consultarEstado(Long idProducto) {
        return stockRepository.findByIdProducto(idProducto);
    }

    public Stock reservar(Long idProducto) {
        Stock stock = stockRepository.findByIdProducto(idProducto)
            .orElseThrow(() -> new IllegalArgumentException("Stock no encontrado para idProducto: " + idProducto));
        
        if (stock.getEstadoInventario() == EstadoInventario.DISPONIBLE) {
            stock.setEstadoInventario(EstadoInventario.RESERVADO);
            log.info("Producto {} reservado.", idProducto);
            return stockRepository.save(stock);
        } else {
            throw new IllegalStateException("No se puede reservar el producto. Estado actual: " + stock.getEstadoInventario());
        }
    }

    public Stock liberar(Long idProducto) {
        Stock stock = stockRepository.findByIdProducto(idProducto)
            .orElseThrow(() -> new IllegalArgumentException("Stock no encontrado para idProducto: " + idProducto));
        
        if (stock.getEstadoInventario() == EstadoInventario.RESERVADO) {
            stock.setEstadoInventario(EstadoInventario.DISPONIBLE);
            log.info("Producto {} liberado.", idProducto);
            return stockRepository.save(stock);
        } else {
            throw new IllegalStateException("Solo un producto RESERVADO puede ser liberado.");
        }
    }

    public Stock vender(Long idProducto) {
        Stock stock = stockRepository.findByIdProducto(idProducto)
            .orElseThrow(() -> new IllegalArgumentException("Stock no encontrado para idProducto: " + idProducto));
        
        if (stock.getEstadoInventario() == EstadoInventario.RESERVADO) {
            stock.setEstadoInventario(EstadoInventario.VENDIDO);
            log.info("Producto {} vendido.", idProducto);
            return stockRepository.save(stock);
        } else {
            throw new IllegalStateException("Solo un producto RESERVADO puede ser VENDIDO.");
        }
    }
}
