package com.titaniumarmor.ventas_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaDTO {

    private Long id;

    @NotNull
    private Long usuarioId;

    private String estado;

    private Double total;

    private List<DetalleVentaDTO> detalles;
}