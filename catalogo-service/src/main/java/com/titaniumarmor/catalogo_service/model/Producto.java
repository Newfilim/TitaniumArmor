package com.titaniumarmor.catalogo_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    private Double precio;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;
    
    //MUCHOS PRODUCTOS PUEDEN PERTENECER A UNA CATEGORIA
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
