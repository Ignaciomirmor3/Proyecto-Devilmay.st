package com.example.service_catalogo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.service_catalogo.model.TipoPrenda;

@Repository
public interface TipoPrendaRepository extends JpaRepository<TipoPrenda, Long>{

}
