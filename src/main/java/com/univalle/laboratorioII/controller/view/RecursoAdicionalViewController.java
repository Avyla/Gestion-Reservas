package com.univalle.laboratorioII.controller.view;

import com.univalle.laboratorioII.entity.dto.RecursoAdicionalDTO;
import com.univalle.laboratorioII.entity.dto.SalaDTO;
import com.univalle.laboratorioII.service.RecursoAdicionalService;
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
@RequestMapping("/view/recurso")
@RequiredArgsConstructor
public class RecursoAdicionalViewController {

    private final RecursoAdicionalService recursoAdicionalService;
    private final SalaService salaService;

    @GetMapping
    public String listarRecursos(Model model) {
        List<RecursoAdicionalDTO> recursos = recursoAdicionalService.findAllRecursosAdicionales();
        model.addAttribute("recursos", recursos);
        model.addAttribute("title", "Gestión de Recursos Adicionales");
        return "recurso/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        List<SalaDTO> salas = salaService.findAllSalas();

        model.addAttribute("recurso", new RecursoAdicionalDTO());
        model.addAttribute("salas", salas);
        model.addAttribute("title", "Nuevo Recurso Adicional");
        model.addAttribute("isEdit", false);
        return "recurso/formulario";
    }

    @PostMapping("/guardar")
    public String guardarRecurso(@Valid @ModelAttribute("recurso") RecursoAdicionalDTO recursoDTO,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<SalaDTO> salas = salaService.findAllSalas();

            model.addAttribute("salas", salas);
            model.addAttribute("title", recursoDTO.getRecursoId() != null ? "Editar Recurso" : "Nuevo Recurso");
            model.addAttribute("isEdit", recursoDTO.getRecursoId() != null);
            return "recurso/formulario";
        }

        try {
            if (recursoDTO.getRecursoId() != null) {
                recursoAdicionalService.updateRecursoAdicional(recursoDTO);
                redirectAttributes.addFlashAttribute("success", "Recurso actualizado exitosamente");
            } else {
                recursoAdicionalService.createRecursoAdicional(recursoDTO);
                redirectAttributes.addFlashAttribute("success", "Recurso creado exitosamente");
            }
            return "redirect:/view/recurso";
        } catch (Exception e) {
            List<SalaDTO> salas = salaService.findAllSalas();

            model.addAttribute("error", "Error al guardar el recurso: " + e.getMessage());
            model.addAttribute("salas", salas);
            model.addAttribute("title", recursoDTO.getRecursoId() != null ? "Editar Recurso" : "Nuevo Recurso");
            model.addAttribute("isEdit", recursoDTO.getRecursoId() != null);
            return "recurso/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            RecursoAdicionalDTO recurso = recursoAdicionalService.findRecursoAdicionalById(id);
            List<SalaDTO> salas = salaService.findAllSalas();

            model.addAttribute("recurso", recurso);
            model.addAttribute("salas", salas);
            model.addAttribute("title", "Editar Recurso Adicional");
            model.addAttribute("isEdit", true);
            return "recurso/formulario";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Recurso no encontrado");
            return "redirect:/view/recurso";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarRecurso(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            recursoAdicionalService.deleteRecursoAdicional(id);
            redirectAttributes.addFlashAttribute("success", "Recurso eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el recurso: " + e.getMessage());
        }
        return "redirect:/view/recurso";
    }
}