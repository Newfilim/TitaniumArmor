package com.titaniumarmor.ventas_service.repository;

import com.titaniumarmor.ventas_service.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleVentaRepository
        extends JpaRepository<DetalleVenta, Long> {
}