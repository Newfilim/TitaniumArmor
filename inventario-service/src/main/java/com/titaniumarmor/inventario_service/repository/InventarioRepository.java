package com.titaniumarmor.inventario_service.repository;

import com.titaniumarmor.inventario_service.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventarioRepository
        extends JpaRepository<Inventario, Long> {

    Optional<Inventario> findByProductoId(Long productoId);

    List<Inventario> findByStockLessThanEqual(Integer stock);
}