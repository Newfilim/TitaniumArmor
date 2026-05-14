package com.titaniumarmor.inventario_service.service;
import com.titaniumarmor.inventario_service.dto.InventarioDTO;
import com.titaniumarmor.inventario_service.exception.BadRequestException;
import com.titaniumarmor.inventario_service.exception.ResourceNotFoundException;
import com.titaniumarmor.inventario_service.model.Inventario;
import com.titaniumarmor.inventario_service.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final WebClient webClient;

    @Value("${api.catalogo.exists}")
    private String productoExistsUrl;

    public List<Inventario> listar() {

        log.info("Listando inventarios");

        return inventarioRepository.findAll();
    }

    public Inventario buscarPorId(Long id) {

        log.info("Buscando inventario id={}", id);

        return inventarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventario no encontrado"));
    }

    public Inventario guardar(InventarioDTO dto) {

        log.info(
                "Creando inventario productoId={}",
                dto.getProductoId()
        );

        Boolean productoExiste =
                webClient.get()
                        .uri(
                                String.format(
                                        productoExistsUrl,
                                        dto.getProductoId()
                                )
                        )
                        .retrieve()
                        .bodyToMono(Boolean.class)
                        .block();

        if (Boolean.FALSE.equals(productoExiste)) {

            throw new ResourceNotFoundException(
                    "Producto no existe"
            );
        }

        Inventario inventario =
                dto.toModel();

        return inventarioRepository.save(inventario);
    }

    public Inventario actualizar(
            Long id,
            InventarioDTO dto
    ) {

        Inventario inventario =
                buscarPorId(id);

        inventario.setProductoId(dto.getProductoId());
        inventario.setStock(dto.getStock());
        inventario.setStockMinimo(dto.getStockMinimo());
        inventario.setUbicacion(dto.getUbicacion());

        log.info(
                "Actualizando inventario id={}",
                id
        );

        return inventarioRepository.save(inventario);
    }

    public void eliminar(Long id) {

        Inventario inventario =
                buscarPorId(id);

        log.info(
                "Eliminando inventario id={}",
                id
        );

        inventarioRepository.delete(inventario);
    }

    public Integer obtenerStock(Long productoId) {

        Inventario inventario =
                inventarioRepository
                        .findByProductoId(productoId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventario no encontrado"
                                ));

        return inventario.getStock();
    }

    public Boolean disponible(Long productoId) {

        return obtenerStock(productoId) > 0;
    }

public void descontarStock(Long productoId, Integer cantidad) {
        log.info("Intentando descontar {} unidades para el productoId={}", cantidad, productoId);

        Inventario inventario = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> {
                    log.error("No se encontró inventario para el productoId={}", productoId);
                    return new ResourceNotFoundException("Inventario no encontrado");
                });

        // VALIDACIÓN DE SEGURIDAD
        if (inventario.getStock() < cantidad) {
            log.warn("Intento de descuento fallido: Stock insuficiente (Disponible: {}, Solicitado: {})", 
                     inventario.getStock(), cantidad);
            throw new BadRequestException("No hay stock suficiente para realizar la venta");
        }

        int stockAnterior = inventario.getStock();
        inventario.setStock(stockAnterior - cantidad);
        
        inventarioRepository.save(inventario);

        log.info("Stock actualizado para producto {}: {} -> {}", 
                 productoId, stockAnterior, inventario.getStock());
    }

public void agregarStock(Long productoId, Integer cantidad) {
        log.info("Agregando {} unidades al productoId={}", cantidad, productoId);

        Inventario inventario = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado"));

        inventario.setStock(inventario.getStock() + cantidad);
        inventarioRepository.save(inventario);
        
        log.info("Nuevo stock para producto {}: {}", productoId, inventario.getStock());
    }


    public List<Inventario> stockBajo() {

        return inventarioRepository
                .findByStockLessThanEqual(5);
    }
}
