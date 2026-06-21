package com.example.service_reembolsos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service_reembolsos.dto.ReembolsoRequestDTO;
import com.example.service_reembolsos.dto.ReembolsoResponseDTO;
import com.example.service_reembolsos.service.ReembolsoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Reembolsos", description = "API para la gestion de Reembolsos")
@RequestMapping("/api/v1/reembolsos")
public class ReembolsoController {

    @Autowired
    private ReembolsoService reembolsoService;

    @PostMapping
    public ResponseEntity<ReembolsoResponseDTO> crear(@Valid @RequestBody ReembolsoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reembolsoService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ReembolsoResponseDTO>> listar() {
        return ResponseEntity.ok(reembolsoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReembolsoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(reembolsoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReembolsoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ReembolsoRequestDTO request) {
        return ResponseEntity.ok(reembolsoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reembolsoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

