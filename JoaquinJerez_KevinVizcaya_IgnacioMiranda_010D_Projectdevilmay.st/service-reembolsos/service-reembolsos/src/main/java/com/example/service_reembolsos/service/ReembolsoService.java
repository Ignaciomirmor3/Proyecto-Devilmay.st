package com.example.service_reembolsos.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import com.example.service_reembolsos.dto.ReembolsoRequestDTO;
import com.example.service_reembolsos.dto.ReembolsoResponseDTO;
import com.example.service_reembolsos.exception.RecursoDuplicadoException;
import com.example.service_reembolsos.exception.RecursoNoEncontradoException;
import com.example.service_reembolsos.model.Reembolso;
import com.example.service_reembolsos.repository.ReembolsoRepository;

@Service
public class ReembolsoService {

    private static final Logger log = LoggerFactory.getLogger(ReembolsoService.class);

    @Autowired
    private ReembolsoRepository reembolsoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public ReembolsoResponseDTO crear(ReembolsoRequestDTO request) {
        if (reembolsoRepository.existsById(request.getIdDevolucion())) {
            throw new RecursoDuplicadoException("Ya existe una solicitud de reembolso para la devolución " + request.getIdDevolucion());
        }

        // Consultar service-devoluciones
        String devolucionesUrl = "http://localhost:8090/api/v1/devoluciones/" + request.getIdDevolucion();
        try {
            log.info("Validando devolucion en {}", devolucionesUrl);
            webClientBuilder.build()
                .get()
                .uri(devolucionesUrl)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
        } catch (WebClientRequestException e) {
            log.error("Error conectando a service-devoluciones: {}", e.getMessage());
            throw new IllegalStateException("El servicio de devoluciones no se encuentra disponible");
        } catch (Exception e) {
            log.error("Error validando devolucion: {}", e.getMessage());
            throw new IllegalArgumentException("La devolución " + request.getIdDevolucion() + " no existe o no es válida.");
        }

        Reembolso reembolso = new Reembolso();
        reembolso.setIdDevolucion(request.getIdDevolucion());
        reembolso.setMontoDevuelto(request.getMontoDevuelto());
        reembolso.setBancoDestino(request.getBancoDestino());
        reembolso.setFechaTransaccion(LocalDateTime.now());

        Reembolso guardado = reembolsoRepository.save(reembolso);
        return mapToDTO(guardado);
    }

    public List<ReembolsoResponseDTO> listarTodos() {
        return reembolsoRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ReembolsoResponseDTO buscarPorId(Long id) {
        Reembolso r = reembolsoRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Reembolso no encontrado"));
        return mapToDTO(r);
    }

    public ReembolsoResponseDTO actualizar(Long id, ReembolsoRequestDTO request) {
        Reembolso r = reembolsoRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Reembolso no encontrado"));
        r.setMontoDevuelto(request.getMontoDevuelto());
        r.setBancoDestino(request.getBancoDestino());
        Reembolso guardado = reembolsoRepository.save(r);
        return mapToDTO(guardado);
    }

    public void eliminar(Long id) {
        if (!reembolsoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Reembolso no encontrado");
        }
        reembolsoRepository.deleteById(id);
    }

    private ReembolsoResponseDTO mapToDTO(Reembolso r) {
        ReembolsoResponseDTO dto = new ReembolsoResponseDTO();
        dto.setIdDevolucion(r.getIdDevolucion());
        dto.setMontoDevuelto(r.getMontoDevuelto());
        dto.setBancoDestino(r.getBancoDestino());
        dto.setFechaTransaccion(r.getFechaTransaccion());
        return dto;
    }
}
