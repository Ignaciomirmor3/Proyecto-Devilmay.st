package com.example.service_inventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.service_inventario.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long>{

    List<Stock> findByIdProducto(Long idProducto);

}
