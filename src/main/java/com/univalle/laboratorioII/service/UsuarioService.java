package com.univalle.laboratorioII.service;

import com.univalle.laboratorioII.entity.dto.UsuarioDTO;
import com.univalle.laboratorioII.entity.UsuarioEntity;
import com.univalle.laboratorioII.repository.UsuarioRepository;
import com.univalle.laboratorioII.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ReservaRepository reservaRepository;

    public List<UsuarioDTO> findAllUsuarios() {
        List<UsuarioEntity> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {
        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .nombre(usuarioDTO.getNombre())
                .correo(usuarioDTO.getCorreo())
                .rol(usuarioDTO.getRol())
                .build();

        UsuarioEntity savedUsuario = usuarioRepository.save(usuarioEntity);
        return convertToDTO(savedUsuario);
    }

    @Transactional
    public void deleteUsuario(Long id) {
        // Verificar si existe
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si tiene reservas asociadas
        long reservasCount = reservaRepository.countByUsuario_UsuarioId(id);

        if (reservasCount > 0) {
            throw new RuntimeException(
                    "No se puede eliminar el usuario porque tiene " + reservasCount +
                            " reserva(s) asociada(s). Por favor, elimine primero las reservas."
            );
        }

        // Si no tiene dependencias, eliminar
        usuarioRepository.deleteById(id);
    }

    public UsuarioDTO findUsuarioById(Long id) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertToDTO(usuarioEntity);
    }

    public UsuarioDTO updateUsuario(UsuarioDTO usuarioDTO) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(usuarioDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioEntity.setNombre(usuarioDTO.getNombre());
        usuarioEntity.setCorreo(usuarioDTO.getCorreo());
        usuarioEntity.setRol(usuarioDTO.getRol());

        UsuarioEntity updatedUsuario = usuarioRepository.save(usuarioEntity);
        return convertToDTO(updatedUsuario);
    }

    private UsuarioDTO convertToDTO(UsuarioEntity usuarioEntity) {
        return UsuarioDTO.builder()
                .usuarioId(usuarioEntity.getUsuarioId())
                .nombre(usuarioEntity.getNombre())
                .correo(usuarioEntity.getCorreo())
                .rol(usuarioEntity.getRol())
                .build();
    }
}