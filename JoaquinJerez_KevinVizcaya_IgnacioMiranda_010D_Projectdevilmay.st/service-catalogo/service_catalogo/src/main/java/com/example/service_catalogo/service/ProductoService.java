package com.example.service_catalogo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service_catalogo.dto.ProductoRequestDTO;
import com.example.service_catalogo.dto.ProductoResponseDTO;
import com.example.service_catalogo.model.Producto;
import com.example.service_catalogo.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoResponseDTO> listarTodos(){
        return productoRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ProductoResponseDTO> buscarPorGenero(com.example.service_catalogo.model.Genero genero){
        return productoRepository.findByGenero(genero).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ProductoResponseDTO> buscarPorTipoPrenda(com.example.service_catalogo.model.TipoPrenda tipoPrenda){
        return productoRepository.findByTipoPrenda(tipoPrenda).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ProductoResponseDTO> buscarPorGeneroYTipo(com.example.service_catalogo.model.Genero genero, com.example.service_catalogo.model.TipoPrenda tipoPrenda){
        return productoRepository.findByGeneroAndTipoPrenda(genero, tipoPrenda).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<ProductoResponseDTO> buscarPorId(Long id){
        return productoRepository.findById(id).map(this::mapToDTO);
    }

    public ProductoResponseDTO guardar(ProductoRequestDTO requestDTO){
        Producto producto = mapToEntity(requestDTO);
        Producto guardado = productoRepository.save(producto);
        return mapToDTO(guardado);
    }

    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO productoActualizado) {
        Producto existente = productoRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombreProducto(productoActualizado.getNombreProducto());
            existente.setDescripcionProducto(productoActualizado.getDescripcionProducto());
            existente.setPrecioProducto(productoActualizado.getPrecioProducto());
            existente.setTallaProducto(productoActualizado.getTallaProducto());
            existente.setEstadoInventario(productoActualizado.getEstadoInventario());
            existente.setUrlImagen(productoActualizado.getUrlImagen());
            existente.setGenero(productoActualizado.getGenero());
            existente.setTipoPrenda(productoActualizado.getTipoPrenda());
            
            return mapToDTO(productoRepository.save(existente));
        }
        return null;
    }

    public ProductoResponseDTO actualizarEstado(Long id, String nuevoEstado) {
        Producto existente = productoRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setEstadoInventario(nuevoEstado);
            return mapToDTO(productoRepository.save(existente));
        }
        return null;
    }

    public void eliminar(Long id){
        productoRepository.deleteById(id);
    }

    private ProductoResponseDTO mapToDTO(Producto p) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId_producto(p.getId_producto());
        dto.setNombreProducto(p.getNombreProducto());
        dto.setDescripcionProducto(p.getDescripcionProducto());
        dto.setPrecioProducto(p.getPrecioProducto());
        dto.setTallaProducto(p.getTallaProducto());
        dto.setEstadoInventario(p.getEstadoInventario());
        dto.setUrlImagen(p.getUrlImagen());
        dto.setGenero(p.getGenero());
        dto.setTipoPrenda(p.getTipoPrenda());
        return dto;
    }

    private Producto mapToEntity(ProductoRequestDTO dto) {
        Producto p = new Producto();
        p.setNombreProducto(dto.getNombreProducto());
        p.setDescripcionProducto(dto.getDescripcionProducto());
        p.setPrecioProducto(dto.getPrecioProducto());
        p.setTallaProducto(dto.getTallaProducto());
        p.setEstadoInventario(dto.getEstadoInventario());
        p.setUrlImagen(dto.getUrlImagen());
        p.setGenero(dto.getGenero());
        p.setTipoPrenda(dto.getTipoPrenda());
        return p;
    }
}
