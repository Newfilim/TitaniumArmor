package com.titaniumarmor.catalogo_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDTO {
    //NO PUEDE ESTAR VACIO
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
}