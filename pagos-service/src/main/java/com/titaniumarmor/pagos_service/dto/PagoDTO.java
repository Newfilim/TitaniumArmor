package com.titaniumarmor.pagos_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagoDTO {

    @NotNull(message = "La ventaId es obligatoria")
    private Long ventaId;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    private Double monto; 

    private String estado;
}