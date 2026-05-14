package com.titaniumarmor.catalogo_service.service;

import com.titaniumarmor.catalogo_service.dto.ProductoDTO;
import com.titaniumarmor.catalogo_service.exception.ResourceNotFoundException;
import com.titaniumarmor.catalogo_service.model.Categoria;
import com.titaniumarmor.catalogo_service.model.Producto;
import com.titaniumarmor.catalogo_service.repository.CategoriaRepository;
import com.titaniumarmor.catalogo_service.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private static final Logger logger =
            LoggerFactory.getLogger(ProductoService.class);

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    //LISTAR PRODUCTOS
    public List<Producto> listar() {

        logger.info("Listando productos");

        return productoRepository.findAll();
    }

    public Producto buscarPorId(Long id) {

        logger.info("Buscando producto id={}", id);

        return productoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Producto no encontrado"
                        ));
    }

    //CREAR PRODUCTOS
    public Producto crear(ProductoDTO dto) {

        logger.info("Creando producto {}", dto.getNombre());

        Categoria categoria =
                categoriaRepository.findById(dto.getCategoriaId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Categoria no encontrada"
                                ));

        Producto producto = new Producto();

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setMarca(dto.getMarca());
        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }


    //ACTUALIZAR UN PRODUCTO POR ID
    public Producto actualizar(
            Long id,
            ProductoDTO dto
    ) {

        logger.info("Actualizando producto id={}", id);

        Producto producto =
                buscarPorId(id);

        Categoria categoria =
                categoriaRepository.findById(dto.getCategoriaId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Categoria no encontrada"
                                ));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setMarca(dto.getMarca());
        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }


    //ELIMINAR UN PRODUCTO POR ID
    public void eliminar(Long id) {

        logger.info("Eliminando producto id={}", id);

        Producto producto =
                buscarPorId(id);

        productoRepository.delete(producto);
    }


    //LISTAR PRODUCTOS DE UNA MARCA
    public List<Producto> buscarPorMarca(
            String marca
    ) {

        logger.info(
                "Buscando productos marca={}",
                marca
        );

        return productoRepository.findByMarca(marca);
    }

    //BUSCAR PRODUCTO POR RANGO DE PRECIOS
    public List<Producto> buscarPorPrecio(
        Double min,
        Double max
) {

    logger.info(
            "Buscando productos entre {} y {}",
            min,
            max
    );

    return productoRepository.findByPrecioBetween(
            min,
            max
    );
}

//LISTAR PRODUCTOS DE UNA CATEGORIA
public List<Producto> buscarPorCategoria(
        Long categoriaId
) {

    logger.info(
            "Buscando productos categoria={}",
            categoriaId
    );

    return productoRepository.findByCategoriaId(
            categoriaId
    );
}

//VER CUANTOS PRODUCTOS HAY
public Long totalProductos() {

    logger.info("Contando productos");

    return productoRepository.count();
}

//validar existencia
public boolean exists(Long id) {

    return productoRepository.existsById(id);
}
}