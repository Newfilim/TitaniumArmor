package com.titaniumarmor.catalogo_service.repository;

import com.titaniumarmor.catalogo_service.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository
        extends JpaRepository<Categoria, Long> {
}