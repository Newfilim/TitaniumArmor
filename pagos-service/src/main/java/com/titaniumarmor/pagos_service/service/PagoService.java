package com.titaniumarmor.pagos_service.service;

import com.titaniumarmor.pagos_service.dto.PagoDTO;
import com.titaniumarmor.pagos_service.dto.VentaRespuestaDTO;
import com.titaniumarmor.pagos_service.exception.ResourceNotFoundException;
import com.titaniumarmor.pagos_service.model.Pago;
import com.titaniumarmor.pagos_service.repository.PagoRepository;
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
public class PagoService {

    private final PagoRepository pagoRepository;
    private final WebClient webClient;

    @Value("${api.venta.exists}")
    private String ventaUrl; // Asegúrate de que esta URL apunte a http://localhost:8084/ventas/%d

    public List<Pago> listar() {
        log.info("Listando pagos");
        return pagoRepository.findAll();
    }

    public Pago buscarPorId(Long id) {
        log.info("Buscando pago id={}", id);
        return pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));
    }

    public Pago guardar(PagoDTO dto) {
        log.info("Iniciando proceso de pago para ventaId={}", dto.getVentaId());

        // 1. Obtener la venta completa (reemplaza la validación de existencia simple)
        VentaRespuestaDTO venta = webClient.get()
                .uri(String.format(ventaUrl, dto.getVentaId()))
                .retrieve()
                .bodyToMono(VentaRespuestaDTO.class)
                .block();

        if (venta == null) {
            log.error("Venta {} no encontrada", dto.getVentaId());
            throw new ResourceNotFoundException("Venta no existe");
        }

        // 2. Construir el Pago con datos automáticos
        Pago pago = Pago.builder()
                .ventaId(dto.getVentaId())
                .monto(venta.total())         // Obtenido automáticamente de Ventas
                .metodoPago(dto.getMetodoPago())
                .estado("APROBADO")           // Estado automático
                .fecha(LocalDateTime.now())
                .build();

        Pago guardado = pagoRepository.save(pago);
        log.info("Pago registrado con éxito ID: {}. Monto: ${}", guardado.getId(), guardado.getMonto());

        // 3. Notificar a Ventas para completar el ciclo
        actualizarEstadoVenta(dto.getVentaId(), "COMPLETADA");

        return guardado;
    }

    private void actualizarEstadoVenta(Long ventaId, String nuevoEstado) {
        try {
            webClient.put()
                    .uri(String.format(ventaUrl, ventaId))
                    .bodyValue(Map.of("estado", nuevoEstado))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            log.info("Sincronización exitosa: Venta {} marcada como {}", ventaId, nuevoEstado);
        } catch (Exception e) {
            log.error("Error al sincronizar estado con Ventas: {}", e.getMessage());
        }
    }

    // Los métodos actualizar, eliminar, buscarPorEstado y buscarPorFechas se mantienen igual...
    public Pago actualizar(Long id, PagoDTO dto) {
        Pago pago = buscarPorId(id);
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setEstado(dto.getEstado());
        pago.setMonto(dto.getMonto());
        log.info("Pago actualizado id={}", pago.getId());
        return pagoRepository.save(pago);
    }

    public void eliminar(Long id) {
        Pago pago = buscarPorId(id);
        pagoRepository.delete(pago);
        log.info("Pago eliminado id={}", id);
    }

    public List<Pago> buscarPorEstado(String estado) {
        log.info("Buscando pagos estado={}", estado);
        return pagoRepository.findByEstado(estado);
    }

    public List<Pago> buscarPorFechas(LocalDateTime inicio, LocalDateTime fin) {
        log.info("Buscando pagos por fechas");
        return pagoRepository.findByFechaBetween(inicio, fin);
    }
}