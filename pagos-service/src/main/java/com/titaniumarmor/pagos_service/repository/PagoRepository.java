package com.titaniumarmor.pagos_service.repository;

import com.titaniumarmor.pagos_service.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PagoRepository
        extends JpaRepository<Pago, Long> {

    List<Pago> findByEstado(
            String estado
    );

    List<Pago> findByFechaBetween(
            LocalDateTime inicio,
            LocalDateTime fin
    );
}