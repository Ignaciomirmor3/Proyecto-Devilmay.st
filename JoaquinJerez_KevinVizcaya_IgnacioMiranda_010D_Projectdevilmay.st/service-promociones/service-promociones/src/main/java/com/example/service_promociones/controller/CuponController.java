package com.example.service_promociones.controller;

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

import com.example.service_promociones.dto.CuponRequestDTO;
import com.example.service_promociones.dto.CuponResponseDTO;
import com.example.service_promociones.dto.CuponValidacionResponseDTO;
import com.example.service_promociones.service.CuponService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/cupones")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @PostMapping
    public ResponseEntity<CuponResponseDTO> crear(@Valid @RequestBody CuponRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuponService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<CuponResponseDTO>> listar() {
        return ResponseEntity.ok(cuponService.listarTodos());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<CuponResponseDTO> buscar(@PathVariable String codigo) {
        return ResponseEntity.ok(cuponService.buscarPorCodigo(codigo));
    }

    @GetMapping("/{codigo}/validar")
    public ResponseEntity<CuponValidacionResponseDTO> validar(@PathVariable String codigo) {
        return ResponseEntity.ok(cuponService.validarCupon(codigo));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<CuponResponseDTO> actualizar(@PathVariable String codigo, @Valid @RequestBody CuponRequestDTO request) {
        return ResponseEntity.ok(cuponService.actualizar(codigo, request));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable String codigo) {
        cuponService.eliminar(codigo);
        return ResponseEntity.noContent().build();
    }
}

