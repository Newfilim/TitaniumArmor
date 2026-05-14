package com.titaniumarmor.ventas_service.repository;

import com.titaniumarmor.ventas_service.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository
        extends JpaRepository<Venta, Long> {

    List<Venta> findByUsuarioId(Long usuarioId);

    List<Venta> findByFechaBetween(
            LocalDateTime inicio,
            LocalDateTime fin
    );
}