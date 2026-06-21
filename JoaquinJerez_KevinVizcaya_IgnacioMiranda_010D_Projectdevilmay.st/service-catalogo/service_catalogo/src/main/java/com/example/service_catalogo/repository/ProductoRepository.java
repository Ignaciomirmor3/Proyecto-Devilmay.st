package com.example.service_catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.service_catalogo.model.Producto;

import java.util.List;
import com.example.service_catalogo.model.Genero;
import com.example.service_catalogo.model.TipoPrenda;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    List<Producto> findByGenero(Genero genero);
    List<Producto> findByTipoPrenda(TipoPrenda tipoPrenda);
    List<Producto> findByGeneroAndTipoPrenda(Genero genero, TipoPrenda tipoPrenda);
}
