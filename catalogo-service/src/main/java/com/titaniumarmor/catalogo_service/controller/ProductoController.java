package com.titaniumarmor.catalogo_service.controller;

import com.titaniumarmor.catalogo_service.dto.ProductoDTO;
import com.titaniumarmor.catalogo_service.model.Producto;
import com.titaniumarmor.catalogo_service.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;


    //OBTENER TODOS LOS PRODUCTOS
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {

        return ResponseEntity.ok(
                productoService.listar()
        );
    }
    //BUSCAR UN PRODUCTO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                productoService.buscarPorId(id)
        );
    }
    //CREAR UN PRODUCTO
    @PostMapping
    public ResponseEntity<Producto> crear(
            @Valid @RequestBody ProductoDTO dto
    ) {

        return ResponseEntity.ok(
                productoService.crear(dto)
        );
    }
    //ACTUALIZAR UN PRODUCTO POR SU ID
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDTO dto
    ) {

        return ResponseEntity.ok(
                productoService.actualizar(id, dto)
        );
    }
    //ELIMINAR UN PRODUCTO POR ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {

        productoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
    //BUSCAR PRODUCTOS DE UNA MARCA
    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Producto>> buscarPorMarca(
            @PathVariable String marca
    ) {

        return ResponseEntity.ok(
                productoService.buscarPorMarca(marca)
        );
    }

    //REVISAR EL PRECIO DE UN PRODUCTO
    @GetMapping("/precio")
public ResponseEntity<List<Producto>> buscarPorPrecio(
        @RequestParam Double min,
        @RequestParam Double max
) {

    return ResponseEntity.ok(
            productoService.buscarPorPrecio(
                    min,
                    max
            )
    );
}

//OBTENER UN PRODUCTO POR CATEGORIA
@GetMapping("/categoria/{categoriaId}")
public ResponseEntity<List<Producto>> buscarPorCategoria(
        @PathVariable Long categoriaId
) {

    return ResponseEntity.ok(
            productoService.buscarPorCategoria(
                    categoriaId
            )
    );
}
//OBTENER EL TOTAL DE PRODUCTOS
@GetMapping("/total")
public ResponseEntity<Long> totalProductos() {

    return ResponseEntity.ok(
            productoService.totalProductos()
    );
}

//confirmar existencia para inventario
@GetMapping("/{id}/exists")
public ResponseEntity<Boolean> exists(
        @PathVariable Long id
) {

    return ResponseEntity.ok(
            productoService.exists(id)
    );
}
}