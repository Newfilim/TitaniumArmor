package com.titaniumarmor.inventario_service.controller;

import com.titaniumarmor.inventario_service.dto.InventarioDTO;
import com.titaniumarmor.inventario_service.model.Inventario;
import com.titaniumarmor.inventario_service.service.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<Inventario>> listar() {

        return ResponseEntity.ok(
                inventarioService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                inventarioService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<InventarioDTO> guardar(
            @Valid @RequestBody InventarioDTO dto
    ) {

        Inventario inventario =
                inventarioService.guardar(dto);

        return ResponseEntity.ok(
                InventarioDTO.fromModel(inventario)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventarioDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody InventarioDTO dto
    ) {

        Inventario inventario =
                inventarioService.actualizar(id, dto);

        return ResponseEntity.ok(
                InventarioDTO.fromModel(inventario)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {

        inventarioService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/producto/{productoId}/stock")
    public ResponseEntity<Integer> stock(
            @PathVariable Long productoId
    ) {

        return ResponseEntity.ok(
                inventarioService.obtenerStock(productoId)
        );
    }

    @GetMapping("/producto/{productoId}/disponible")
    public ResponseEntity<Boolean> disponible(
            @PathVariable Long productoId
    ) {

        return ResponseEntity.ok(
                inventarioService.disponible(productoId)
        );
    }
    @PutMapping("/producto/{productoId}/descontar/{cantidad}")
    public ResponseEntity<Void> descontar(
            @PathVariable Long productoId,
            @PathVariable Integer cantidad
    ) {
        inventarioService.descontarStock(productoId, cantidad);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/producto/{productoId}/agregar/{cantidad}")
    public ResponseEntity<Void> agregar(
            @PathVariable Long productoId,
            @PathVariable Integer cantidad
    ) {
        inventarioService.agregarStock(productoId, cantidad);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Inventario>> stockBajo() {

        return ResponseEntity.ok(
                inventarioService.stockBajo()
        );
    }
}