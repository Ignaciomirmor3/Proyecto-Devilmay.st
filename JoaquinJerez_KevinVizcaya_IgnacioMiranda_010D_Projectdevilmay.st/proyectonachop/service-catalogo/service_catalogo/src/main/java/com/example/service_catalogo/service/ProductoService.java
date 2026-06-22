package com.example.service_catalogo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service_catalogo.model.Genero;
import com.example.service_catalogo.model.Producto;
import com.example.service_catalogo.model.TipoPrenda;
import com.example.service_catalogo.repository.GeneroRepository;
import com.example.service_catalogo.repository.ProductoRepository;
import com.example.service_catalogo.repository.TipoPrendaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private GeneroRepository generoRepository;
    
    @Autowired
    private TipoPrendaRepository tipoPrendaRepository;
    
    //Producto

    public List<Producto> listarTodos(){
        
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarPorId(Long id){
        return productoRepository.findById(id);
    }

    

    public Producto guardar(Producto producto){
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto productoActualizado) {
        // Buscamos el producto existente por su ID. Usamos orElse(null) para que no falle si no existe.
        Producto existente = productoRepository.findById(id).orElse(null);
        
        if (existente != null) {
            // Actualizamos todos los campos con los datos nuevos
            existente.setNombreProducto(productoActualizado.getNombreProducto());
            existente.setDescripcionProducto(productoActualizado.getDescripcionProducto());
            existente.setPrecioProducto(productoActualizado.getPrecioProducto());
            existente.setTallaProducto(productoActualizado.getTallaProducto());
            existente.setEstadoInventario(productoActualizado.getEstadoInventario());
            existente.setUrlImagen(productoActualizado.getUrlImagen());
            existente.setGenero(productoActualizado.getGenero());
            existente.setTipoPrenda(productoActualizado.getTipoPrenda());
            
            // Guardamos y retornamos el producto modificado
            return productoRepository.save(existente);
        }
        
        // Si no existe, retornamos null (el controller se encargará de mandar un 404 Not Found)
        return null;
    }

    public Producto actualizarEstado(Long id, String nuevoEstado) {
        Producto existente = productoRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setEstadoInventario(nuevoEstado);
            return productoRepository.save(existente);
        }
        return null;
    }

    public void eliminar(Long id){
        productoRepository.deleteById(id);
    }

    //Genero


    public List<Genero> listarGeneros(){
        return generoRepository.findAll();
    }

    public Genero guardarGenero(Genero genero){
        return generoRepository.save(genero);
    }

    public Optional<Genero> buscarGeneroPorId(Long id) {
        return generoRepository.findById(id);
    }

    public Genero actualizarGenero(Long id, Genero generoActualizado) {
        Genero existente = generoRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombreGenero(generoActualizado.getNombreGenero());
            return generoRepository.save(existente);
        }
        return null;
    }

    public void eliminarGenero(Long id) {
        generoRepository.deleteById(id);
    }

    //Tipo Prenda

    public List<TipoPrenda> listarPrendas(){
        return tipoPrendaRepository.findAll();
    }

    public TipoPrenda guardarTipoPrenda(TipoPrenda tipoPrenda){
        return tipoPrendaRepository.save(tipoPrenda);
    }

        public Optional<TipoPrenda> buscarTipoPrendaPorId(Long id) {
        return tipoPrendaRepository.findById(id);
    }

    public TipoPrenda actualizarTipoPrenda(Long id, TipoPrenda tipoActualizado) {
        TipoPrenda existente = tipoPrendaRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombreTipo(tipoActualizado.getNombreTipo());
            return tipoPrendaRepository.save(existente);
        }
        return null;
    }

    public void eliminarTipoPrenda(Long id) {
        tipoPrendaRepository.deleteById(id);
    }
}
