package com.univalle.laboratorioII.controller.view;

import com.univalle.laboratorioII.entity.dto.SalaDTO;
import com.univalle.laboratorioII.service.SalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/view/sala")
@RequiredArgsConstructor
public class SalaViewController {

    private final SalaService salaService;

    @GetMapping
    public String listarSalas(Model model) {
        List<SalaDTO> salas = salaService.findAllSalas();
        model.addAttribute("salas", salas);
        model.addAttribute("title", "Gestión de Salas");
        return "sala/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("sala", new SalaDTO());
        model.addAttribute("title", "Nueva Sala");
        model.addAttribute("isEdit", false);
        return "sala/formulario";
    }

    @PostMapping("/guardar")
    public String guardarSala(@Valid @ModelAttribute("sala") SalaDTO salaDTO,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("title", salaDTO.getSalaId() != null ? "Editar Sala" : "Nueva Sala");
            model.addAttribute("isEdit", salaDTO.getSalaId() != null);
            return "sala/formulario";
        }

        try {
            if (salaDTO.getSalaId() != null) {
                salaService.updateSala(salaDTO);
                redirectAttributes.addFlashAttribute("success", "Sala actualizada exitosamente");
            } else {
                salaService.createSala(salaDTO);
                redirectAttributes.addFlashAttribute("success", "Sala creada exitosamente");
            }
            return "redirect:/view/sala";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar la sala: " + e.getMessage());
            model.addAttribute("title", salaDTO.getSalaId() != null ? "Editar Sala" : "Nueva Sala");
            model.addAttribute("isEdit", salaDTO.getSalaId() != null);
            return "sala/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            SalaDTO sala = salaService.findSalaById(id);
            model.addAttribute("sala", sala);
            model.addAttribute("title", "Editar Sala");
            model.addAttribute("isEdit", true);
            return "sala/formulario";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Sala no encontrada");
            return "redirect:/view/sala";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarSala(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            salaService.deleteSala(id);
            redirectAttributes.addFlashAttribute("success", "Sala eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la sala: " + e.getMessage());
        }
        return "redirect:/view/sala";
    }
}