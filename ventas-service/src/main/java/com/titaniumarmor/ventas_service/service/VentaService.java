package com.titaniumarmor.ventas_service.service;

import com.titaniumarmor.ventas_service.dto.ProductoDTO;
import com.titaniumarmor.ventas_service.dto.VentaDTO;
import com.titaniumarmor.ventas_service.exception.BadRequestException;
import com.titaniumarmor.ventas_service.exception.ResourceNotFoundException;
import com.titaniumarmor.ventas_service.model.DetalleVenta;
import com.titaniumarmor.ventas_service.model.Venta;
import com.titaniumarmor.ventas_service.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class VentaService {

    private final VentaRepository ventaRepository;
    private final WebClient webClient;

    @Value("${api.usuario.exists}")
    private String usuarioExistsUrl;

    @Value("${api.catalogo.exists}")
    private String productoUrl;

    @Value("${api.inventario.disponible}")
    private String inventarioDisponibleUrl;

    @Value("${api.inventario.descontar}")
    private String inventarioDescontarUrl;

    @Value("${api.usuario.email}")
    private String usuarioEmailUrl;

    public List<Venta> listar() {
        log.info("Listando todas las ventas registradas");
        return ventaRepository.findAll();
    }

    public Venta buscarPorId(Long id) {
        log.info("Buscando venta con ID: {}", id);
        return ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada"));
    }

    public Venta guardar(VentaDTO dto) {
        log.info("Iniciando creación de venta para usuarioId={}", dto.getUsuarioId());

        validarUsuario(dto.getUsuarioId());

        Venta venta = new Venta();
        venta.setUsuarioId(dto.getUsuarioId());
        venta.setFecha(LocalDateTime.now());
        venta.setEstado("PENDIENTE");

        List<DetalleVenta> detalles = dto.getDetalles().stream()
                .map(detalleDTO -> {
                    Long prodId = detalleDTO.getProductoId();
                    Integer cant = detalleDTO.getCantidad();

                    // Obtenemos precio real del catálogo
                    Double precioReal = obtenerPrecioReal(prodId);
                    
                    // Validamos y descontamos stock real
                    validarStock(prodId, cant);
                    descontarStock(prodId, cant);

                    Double subtotal = precioReal * cant;

                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setProductoId(prodId);
                    detalle.setCantidad(cant);
                    detalle.setPrecio(precioReal);
                    detalle.setSubtotal(subtotal);
                    detalle.setVenta(venta);

                    return detalle;
                }).toList();

        double total = detalles.stream().mapToDouble(DetalleVenta::getSubtotal).sum();
        venta.setTotal(total);
        venta.setDetalles(detalles);

        Venta guardada = ventaRepository.save(venta);
        log.info("Venta creada exitosamente. ID: {}, Total: {}", guardada.getId(), total);
        return guardada;
    }

    public Venta actualizar(Long id, VentaDTO dto) {
        log.info("Actualizando estado de la venta ID: {}", id);
        Venta venta = buscarPorId(id);
        venta.setEstado(dto.getEstado());
        return ventaRepository.save(venta);
    }

    public void eliminar(Long id) {
        log.info("Eliminando venta ID: {}", id);
        Venta venta = buscarPorId(id);
        ventaRepository.delete(venta);
    }

    public List<Venta> buscarPorUsuario(Long usuarioId) {
        log.info("Buscando ventas para el usuario ID: {}", usuarioId);
        return ventaRepository.findByUsuarioId(usuarioId);
    }

    public List<Venta> buscarPorFechas(LocalDateTime inicio, LocalDateTime fin) {
        log.info("Buscando ventas entre {} y {}", inicio, fin);
        return ventaRepository.findByFechaBetween(inicio, fin);
    }

    public boolean exists(Long id) {
        return ventaRepository.existsById(id);
    }

    // --- MÉTODOS PRIVADOS ---

    private void validarUsuario(Long usuarioId) {
        Boolean existe = webClient.get()
                .uri(String.format(usuarioExistsUrl, usuarioId))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (Boolean.FALSE.equals(existe)) {
            throw new ResourceNotFoundException("Usuario no existe");
        }
    }

private Double obtenerPrecioReal(Long productoId) {
    // WebClient ahora mapea directamente a tu ProductoDTO
    ProductoDTO producto = webClient.get()
            .uri(String.format(productoUrl, productoId)) 
            .retrieve()
            .bodyToMono(ProductoDTO.class)
            .block();

    if (producto == null || producto.getPrecio() == null) {
        log.error("No se pudo obtener el precio para el producto ID: {}", productoId);
        throw new ResourceNotFoundException("Producto o precio no encontrado en el catálogo");
    }

    return producto.getPrecio();
}

    private void validarStock(Long productoId, Integer cantidad) {
        Boolean disponible = webClient.get()
                .uri(String.format(inventarioDisponibleUrl, productoId))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (Boolean.FALSE.equals(disponible)) {
            throw new BadRequestException("Producto sin stock suficiente");
        }
    }

    private void descontarStock(Long productoId, Integer cantidad) {
        webClient.put()
                .uri(String.format(inventarioDescontarUrl + "/%d", productoId, cantidad))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public List<Venta> buscarPorEmailUsuario(String email) {
    log.info("Iniciando búsqueda de ventas para el email: {}", email);

    // 1. Llamar a Usuarios-Service usando el RequestParam
    // El String.format pondrá el email después del 'email='
    Map<String, Object> usuario = webClient.get()
            .uri(String.format(usuarioEmailUrl, email))
            .retrieve()
            .bodyToMono(new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {})
            .block();

    if (usuario == null || usuario.get("id") == null) {
        throw new ResourceNotFoundException("No se encontró el usuario con email: " + email);
    }

    // 2. Extraer el ID (casteo seguro)
    Long usuarioId = Long.valueOf(usuario.get("id").toString());
    
    log.info("Email {} corresponde al ID {}. Buscando sus ventas...", email, usuarioId);

    // 3. Retornar las ventas de ese ID
    return ventaRepository.findByUsuarioId(usuarioId);
}
}