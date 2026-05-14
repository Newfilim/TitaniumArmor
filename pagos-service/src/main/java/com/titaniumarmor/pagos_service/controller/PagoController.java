package com.titaniumarmor.pagos_service.controller;

import com.titaniumarmor.pagos_service.dto.PagoDTO;
import com.titaniumarmor.pagos_service.model.Pago;
import com.titaniumarmor.pagos_service.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<Pago>> listar() {

        return ResponseEntity.ok(
                pagoService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                pagoService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Pago> guardar(
            @Valid
            @RequestBody PagoDTO dto
    ) {

        return ResponseEntity.ok(
                pagoService.guardar(dto)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizar(
            @PathVariable Long id,
            @RequestBody PagoDTO dto
    ) {

        return ResponseEntity.ok(
                pagoService.actualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {

        pagoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pago>>
    buscarPorEstado(
            @PathVariable String estado
    ) {

        return ResponseEntity.ok(
                pagoService.buscarPorEstado(
                        estado
                )
        );
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<Pago>>
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
                pagoService.buscarPorFechas(
                        inicio,
                        fin
                )
        );
    }
}
