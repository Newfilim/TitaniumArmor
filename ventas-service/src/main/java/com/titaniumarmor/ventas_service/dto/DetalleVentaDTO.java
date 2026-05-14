package com.titaniumarmor.ventas_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaDTO {

    private Long id;

    @NotNull
    private Long productoId;

    @NotNull
    private Integer cantidad;

    @NotNull
    private Double precio;
}