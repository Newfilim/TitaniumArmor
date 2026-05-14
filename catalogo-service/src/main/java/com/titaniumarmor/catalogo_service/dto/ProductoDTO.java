package com.titaniumarmor.catalogo_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {
    //NO PUEDE ESTAR VACIO EL STRING
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;
    //NO PUEDE ESTAR VACIO NI SER NULL
    @NotNull(message = "El precio es obligatorio")
    private Double precio;
    
    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotNull(message = "La categoria es obligatoria")
    private Long categoriaId;
}
