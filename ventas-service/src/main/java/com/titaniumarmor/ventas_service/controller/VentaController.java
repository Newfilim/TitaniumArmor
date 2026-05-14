package com.titaniumarmor.ventas_service.controller;

import com.titaniumarmor.ventas_service.dto.VentaDTO;
import com.titaniumarmor.ventas_service.model.Venta;
import com.titaniumarmor.ventas_service.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
@Slf4j
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<Venta>> listar() {

        return ResponseEntity.ok(
                ventaService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                ventaService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Venta> guardar(
            @Valid
            @RequestBody VentaDTO dto
    ) {

        return ResponseEntity.ok(
                ventaService.guardar(dto)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizar(
            @PathVariable Long id,
            @RequestBody VentaDTO dto
    ) {

        return ResponseEntity.ok(
                ventaService.actualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {

        ventaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Venta>>
    buscarPorUsuario(
            @PathVariable Long usuarioId
    ) {

        return ResponseEntity.ok(
                ventaService.buscarPorUsuario(
                        usuarioId
                )
        );
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<Venta>>
    buscarPorFechas(

            @RequestParam
            @DateTimeFormat(
                    iso =
                    DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime inicio,

            @RequestParam
            @DateTimeFormat(
                    iso =
                    DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime fin
    ) {

        return ResponseEntity.ok(
                ventaService.buscarPorFechas(
                        inicio,
                        fin
                )
        );
    }

    @GetMapping("/{id}/exists")
public ResponseEntity<Boolean> exists(
        @PathVariable Long id
) {

    boolean existe =
            ventaService.exists(id);

    return ResponseEntity.ok(existe);
}

@GetMapping("/usuario/buscar-por-email")
public ResponseEntity<List<Venta>> buscarPorEmail(
        @RequestParam String email
) {
    log.info("Petición recibida para buscar ventas del email: {}", email);
    return ResponseEntity.ok(ventaService.buscarPorEmailUsuario(email));
}
}