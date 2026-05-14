package com.titaniumarmor.inventario_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productoId;

    private Integer stock;

    private Integer stockMinimo;

    private String ubicacion;
}