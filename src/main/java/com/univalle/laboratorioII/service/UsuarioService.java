package com.univalle.laboratorioII.service;

import com.univalle.laboratorioII.entity.dto.UsuarioDTO;
import com.univalle.laboratorioII.entity.UsuarioEntity;
import com.univalle.laboratorioII.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

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

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioDTO findUsuarioById(Long id) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario not found"));
        return convertToDTO(usuarioEntity);
    }

    public UsuarioDTO updateUsuario(UsuarioDTO usuarioDTO) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(usuarioDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario not found"));

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
