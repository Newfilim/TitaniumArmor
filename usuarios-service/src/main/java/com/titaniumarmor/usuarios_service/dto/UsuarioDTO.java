package com.titaniumarmor.usuarios_service.dto;

import com.titaniumarmor.usuarios_service.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {

   private Long id;

   @NotBlank
   private String nombre;

   @NotBlank
   @Email
   private String email;

   @NotBlank
   private String password;

   @NotBlank
   private String direccion;

   public Usuario toModel() {
       return Usuario.builder()
               .id(id)
               .nombre(nombre)
               .email(email)
               .password(password)
               .direccion(direccion)
               .build();
   }

   public static UsuarioDTO fromModel(Usuario usuario) {
       return UsuarioDTO.builder()
               .id(usuario.getId())
               .nombre(usuario.getNombre())
               .email(usuario.getEmail())
               .password(usuario.getPassword())
               .direccion(usuario.getDireccion())
               .build();
   }
}
