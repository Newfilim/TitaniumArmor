package com.titaniumarmor.catalogo_service.controller;

import com.titaniumarmor.catalogo_service.dto.CategoriaDTO;
import com.titaniumarmor.catalogo_service.model.Categoria;
import com.titaniumarmor.catalogo_service.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;
    //OBTENER TODAS
    @GetMapping
    public ResponseEntity<List<Categoria>> listar() {

        return ResponseEntity.ok(
                categoriaService.listar()
        );
    }
    //OBTENER UNA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                categoriaService.buscarPorId(id)
        );
    }
    //CREAR UNA CATEGORIA NUEVA
    @PostMapping
    public ResponseEntity<Categoria> crear(
            @Valid @RequestBody CategoriaDTO dto
    ) {

        return ResponseEntity.ok(
                categoriaService.crear(dto)
        );
    }
    //ACTUALIZAR POR ID
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaDTO dto
    ) {

        return ResponseEntity.ok(
                categoriaService.actualizar(id, dto)
        );
    }
    //ELIMINAR UNA CATEGORIA POR ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {

        categoriaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}