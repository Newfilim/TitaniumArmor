package com.titaniumarmor.usuarios_service.controller;

import com.titaniumarmor.usuarios_service.dto.UsuarioDTO;
import com.titaniumarmor.usuarios_service.model.Usuario;
import com.titaniumarmor.usuarios_service.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

   private final UsuarioService usuarioService;

   @GetMapping
   public ResponseEntity<List<Usuario>> listar() {

       return ResponseEntity.ok(usuarioService.listar());
   }

   @GetMapping("/{id}")
   public ResponseEntity<Usuario> buscarPorId(
           @PathVariable Long id
   ) {

       return ResponseEntity.ok(usuarioService.buscarPorId(id));
   }

   @GetMapping("/{id}/exists")
   public ResponseEntity<Boolean> exists(
           @PathVariable Long id
   ) {

       return ResponseEntity.ok(usuarioService.exists(id));
   }

   @GetMapping("/email")
   public ResponseEntity<Usuario> buscarPorEmail(
           @RequestParam String email
   ) {

       return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
   }

   @PostMapping
   public ResponseEntity<UsuarioDTO> guardar(
           @Valid @RequestBody UsuarioDTO dto
   ) {

       Usuario usuario = usuarioService.guardar(dto);

       return ResponseEntity.ok(
               UsuarioDTO.fromModel(usuario)
       );
   }

   @PutMapping("/{id}")
   public ResponseEntity<UsuarioDTO> actualizar(
           @PathVariable Long id,
           @Valid @RequestBody UsuarioDTO dto
   ) {

       Usuario usuario = usuarioService.actualizar(id, dto);

       return ResponseEntity.ok(
               UsuarioDTO.fromModel(usuario)
       );
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> eliminar(
           @PathVariable Long id
   ) {

       usuarioService.eliminar(id);

       return ResponseEntity.noContent().build();
   }
}
