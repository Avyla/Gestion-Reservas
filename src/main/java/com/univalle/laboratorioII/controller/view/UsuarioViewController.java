package com.univalle.laboratorioII.controller.view;

import com.univalle.laboratorioII.entity.dto.UsuarioDTO;
import com.univalle.laboratorioII.entity.enums.RolEnum;
import com.univalle.laboratorioII.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/view/usuario")
@RequiredArgsConstructor
public class UsuarioViewController {

    private final UsuarioService usuarioService;

    @GetMapping
    public String listarUsuarios(Model model) {
        List<UsuarioDTO> usuarios = usuarioService.findAllUsuarios();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("title", "Gestión de Usuarios");
        return "usuario/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        model.addAttribute("roles", RolEnum.values());
        model.addAttribute("title", "Nuevo Usuario");
        model.addAttribute("isEdit", false);
        return "usuario/formulario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@Valid @ModelAttribute("usuario") UsuarioDTO usuarioDTO,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("roles", RolEnum.values());
            model.addAttribute("title", usuarioDTO.getUsuarioId() != null ? "Editar Usuario" : "Nuevo Usuario");
            model.addAttribute("isEdit", usuarioDTO.getUsuarioId() != null);
            return "usuario/formulario";
        }

        try {
            if (usuarioDTO.getUsuarioId() != null) {
                usuarioService.updateUsuario(usuarioDTO);
                redirectAttributes.addFlashAttribute("success", "Usuario actualizado exitosamente");
            } else {
                usuarioService.createUsuario(usuarioDTO);
                redirectAttributes.addFlashAttribute("success", "Usuario creado exitosamente");
            }
            return "redirect:/view/usuario";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el usuario: " + e.getMessage());
            model.addAttribute("roles", RolEnum.values());
            model.addAttribute("title", usuarioDTO.getUsuarioId() != null ? "Editar Usuario" : "Nuevo Usuario");
            model.addAttribute("isEdit", usuarioDTO.getUsuarioId() != null);
            return "usuario/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            UsuarioDTO usuario = usuarioService.findUsuarioById(id);
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", RolEnum.values());
            model.addAttribute("title", "Editar Usuario");
            model.addAttribute("isEdit", true);
            return "usuario/formulario";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/view/usuario";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.deleteUsuario(id);
            redirectAttributes.addFlashAttribute("success", "Usuario eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el usuario: " + e.getMessage());
        }
        return "redirect:/view/usuario";
    }
}