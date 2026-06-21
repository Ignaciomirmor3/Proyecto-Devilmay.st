package com.example.service_reembolsos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.service_reembolsos.model.Reembolso;

@Repository
public interface ReembolsoRepository extends JpaRepository<Reembolso, Long> {
}
