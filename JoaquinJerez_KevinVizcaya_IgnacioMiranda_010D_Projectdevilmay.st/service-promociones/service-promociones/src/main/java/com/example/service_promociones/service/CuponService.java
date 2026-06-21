package com.example.service_promociones.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service_promociones.dto.CuponRequestDTO;
import com.example.service_promociones.dto.CuponResponseDTO;
import com.example.service_promociones.dto.CuponValidacionResponseDTO;
import com.example.service_promociones.exception.RecursoDuplicadoException;
import com.example.service_promociones.exception.RecursoNoEncontradoException;
import com.example.service_promociones.model.Cupon;
import com.example.service_promociones.repository.CuponRepository;

@Service
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    public CuponResponseDTO crear(CuponRequestDTO request) {
        String codigo = request.getCodigoCupon().toUpperCase().trim();
        if (cuponRepository.existsById(codigo)) {
            throw new RecursoDuplicadoException("El cupon ya existe.");
        }
        Cupon cupon = new Cupon();
        cupon.setCodigoCupon(codigo);
        cupon.setPorcentajeDescuento(request.getPorcentajeDescuento());
        cupon.setFechaExpiracion(request.getFechaExpiracion());
        Cupon guardado = cuponRepository.save(cupon);
        return mapToDTO(guardado);
    }

    public List<CuponResponseDTO> listarTodos() {
        return cuponRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public CuponResponseDTO buscarPorCodigo(String codigo) {
        Cupon cupon = cuponRepository.findById(codigo.toUpperCase().trim())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cupon no encontrado"));
        return mapToDTO(cupon);
    }

    public CuponResponseDTO actualizar(String codigo, CuponRequestDTO request) {
        Cupon cupon = cuponRepository.findById(codigo.toUpperCase().trim())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cupon no encontrado"));
        cupon.setPorcentajeDescuento(request.getPorcentajeDescuento());
        cupon.setFechaExpiracion(request.getFechaExpiracion());
        Cupon guardado = cuponRepository.save(cupon);
        return mapToDTO(guardado);
    }

    public void eliminar(String codigo) {
        if (!cuponRepository.existsById(codigo.toUpperCase().trim())) {
            throw new RecursoNoEncontradoException("Cupon no encontrado");
        }
        cuponRepository.deleteById(codigo.toUpperCase().trim());
    }

    public CuponValidacionResponseDTO validarCupon(String codigo) {
        return cuponRepository.findById(codigo.toUpperCase().trim())
                .map(cupon -> {
                    boolean valido = cupon.getActivo() && !cupon.getFechaExpiracion().isBefore(LocalDate.now());
                    return new CuponValidacionResponseDTO(valido, valido ? cupon.getPorcentajeDescuento() : 0, valido ? "Cup�n v�lido" : "Cup�n expirado o inactivo");
                })
                .orElse(new CuponValidacionResponseDTO(false, 0, "Cupon no encontrado"));
    }

    private CuponResponseDTO mapToDTO(Cupon cupon) {
        CuponResponseDTO dto = new CuponResponseDTO();
        dto.setCodigoCupon(cupon.getCodigoCupon());
        dto.setPorcentajeDescuento(cupon.getPorcentajeDescuento());
        dto.setFechaExpiracion(cupon.getFechaExpiracion());
        dto.setActivo(cupon.getActivo());
        return dto;
    }
}
