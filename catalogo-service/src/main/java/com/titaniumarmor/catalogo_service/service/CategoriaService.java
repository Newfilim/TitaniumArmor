package com.titaniumarmor.catalogo_service.service;

import com.titaniumarmor.catalogo_service.dto.CategoriaDTO;
import com.titaniumarmor.catalogo_service.exception.ResourceNotFoundException;
import com.titaniumarmor.catalogo_service.model.Categoria;
import com.titaniumarmor.catalogo_service.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private static final Logger logger =
            LoggerFactory.getLogger(CategoriaService.class);

    private final CategoriaRepository categoriaRepository;
    //LISTAR CATEGORIAS
    public List<Categoria> listar() {

        logger.info("Listando categorias");

        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {

        logger.info("Buscando categoria id={}", id);

        return categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoria no encontrada"
                        ));
    }

    //CREAR CATEGORIAS
    public Categoria crear(CategoriaDTO dto) {

        logger.info(
                "Creando categoria {}",
                dto.getNombre()
        );

        Categoria categoria =
                new Categoria();

        categoria.setNombre(dto.getNombre());

        return categoriaRepository.save(categoria);
    }


    //ACTUALIZAR CATEGORIA POR ID
    public Categoria actualizar(
            Long id,
            CategoriaDTO dto
    ) {

        logger.info(
                "Actualizando categoria id={}",
                id
        );

        Categoria categoria =
                buscarPorId(id);

        categoria.setNombre(dto.getNombre());

        return categoriaRepository.save(categoria);
    }

    //ELIMINAR CATEGORIA POR ID
    public void eliminar(Long id) {

        logger.info(
                "Eliminando categoria id={}",
                id
        );

        Categoria categoria =
                buscarPorId(id);

        categoriaRepository.delete(categoria);
    }
}