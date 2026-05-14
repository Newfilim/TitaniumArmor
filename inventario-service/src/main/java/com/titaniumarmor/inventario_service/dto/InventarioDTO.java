package com.titaniumarmor.inventario_service.dto;

import com.titaniumarmor.inventario_service.model.Inventario;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventarioDTO {

    private Long id;

    @NotNull
    private Long productoId;

    @NotNull
    private Integer stock;

    @NotNull
    private Integer stockMinimo;

    private String ubicacion;

    public Inventario toModel() {

        return Inventario.builder()
                .id(id)
                .productoId(productoId)
                .stock(stock)
                .stockMinimo(stockMinimo)
                .ubicacion(ubicacion)
                .build();
    }

    public static InventarioDTO fromModel(Inventario inventario) {

        return InventarioDTO.builder()
                .id(inventario.getId())
                .productoId(inventario.getProductoId())
                .stock(inventario.getStock())
                .stockMinimo(inventario.getStockMinimo())
                .ubicacion(inventario.getUbicacion())
                .build();
    }
}