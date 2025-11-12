package com.univalle.laboratorioII.controller;

import com.univalle.laboratorioII.entity.dto.UsuarioDTO;
import com.univalle.laboratorioII.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioDTO> findAllUsuarios() {
        return usuarioService.findAllUsuarios();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTO findUsuarioById(@PathVariable Long id) {
         return usuarioService.findUsuarioById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.createUsuario(usuarioDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTO updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO){
        usuarioDTO.setUsuarioId(id);
        return usuarioService.updateUsuario(usuarioDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
    }

}
