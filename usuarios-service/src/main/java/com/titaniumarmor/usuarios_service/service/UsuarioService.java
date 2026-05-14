package com.titaniumarmor.usuarios_service.service;

import com.titaniumarmor.usuarios_service.dto.UsuarioDTO;
import com.titaniumarmor.usuarios_service.exception.BadRequestException;
import com.titaniumarmor.usuarios_service.exception.ResourceNotFoundException;
import com.titaniumarmor.usuarios_service.model.Usuario;
import com.titaniumarmor.usuarios_service.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> listar() {
        log.info("Consultando lista completa de usuarios");
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        log.info("Buscando usuario con ID: {}", id);
        return usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Usuario con ID {} no encontrado", id);
                    return new ResourceNotFoundException("Usuario no encontrado");
                });
    }

    public Usuario guardar(UsuarioDTO dto) {
        log.info("Intentando registrar nuevo usuario con email: {}", dto.getEmail());

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            log.warn("Fallo al crear usuario: El email {} ya está en uso", dto.getEmail());
            throw new BadRequestException("Email ya registrado");
        }

        Usuario usuario = dto.toModel();
        Usuario guardado = usuarioRepository.save(usuario);
        
        log.info("Usuario creado exitosamente con ID: {}", guardado.getId());
        return guardado;
    }

    public Usuario actualizar(Long id, UsuarioDTO dto) {
        log.info("Iniciando actualización para usuario ID: {}", id);

        Usuario usuario = buscarPorId(id);

        // Lógica inteligente para el email:
        // Si el email del DTO es diferente al actual, verificamos que el nuevo no esté ocupado
        if (!usuario.getEmail().equalsIgnoreCase(dto.getEmail())) {
            log.info("Se detectó cambio de email de {} a {}", usuario.getEmail(), dto.getEmail());
            if (usuarioRepository.existsByEmail(dto.getEmail())) {
                log.error("No se puede actualizar: El nuevo email {} ya pertenece a otro usuario", dto.getEmail());
                throw new BadRequestException("Email ya registrado");
            }
        }

        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setDireccion(dto.getDireccion());

        Usuario actualizado = usuarioRepository.save(usuario);
        log.info("Usuario ID: {} actualizado correctamente", id);
        
        return actualizado;
    }

    public void eliminar(Long id) {
        log.info("Intentando eliminar usuario ID: {}", id);
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
        log.info("Usuario ID: {} eliminado del sistema", id);
    }

    public boolean exists(Long id) {
        boolean existe = usuarioRepository.existsById(id);
        log.info("Verificación de existencia para usuario ID {}: {}", id, existe);
        return existe;
    }

    public Usuario buscarPorEmail(String email) {
        log.info("Buscando usuario por email: {}", email);
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }
}
