package com.example.service_inventario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service_inventario.model.Stock;
import com.example.service_inventario.repository.StockRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> listarTodos(){
        return stockRepository.findAll();
    }

    public Stock guardar(Stock stock){
        return stockRepository.save(stock);
    }

    public Optional<Stock> buscarPorId(Long id){
        return stockRepository.findById(id);
    }

    public List<Stock> buscarPorProducto(Long idProducto){
        return stockRepository.findByIdProducto(idProducto);
    }

    public Stock actualizar(Long id, Stock stockActualizado){
        Stock existente = stockRepository.findById(id).orElse(null);

        if(existente != null){
            existente.setNombreEstado(stockActualizado.getNombreEstado());

            return stockRepository.save(existente);
        }
        
        return null;
    }

    public void eliminar(Long id_estado){
        stockRepository.deleteById(id_estado);
    }
    
}
