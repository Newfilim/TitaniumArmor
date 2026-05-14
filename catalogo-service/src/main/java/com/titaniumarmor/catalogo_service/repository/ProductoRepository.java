package com.titaniumarmor.catalogo_service.repository;

import com.titaniumarmor.catalogo_service.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository
        extends JpaRepository<Producto, Long> {
    //BUSCAR POR MARCA
    List<Producto> findByMarca(String marca);
    //BUSCAR POR RANGO DE PRECIOS
    List<Producto> findByPrecioBetween(
            Double min,
            Double max
    );
    //BUSCAR POR LA ID DE LA CATEGORIA
    List<Producto> findByCategoriaId(Long categoriaId);

    //confirmar si existe para inventario
    boolean existsById(Long id);
}