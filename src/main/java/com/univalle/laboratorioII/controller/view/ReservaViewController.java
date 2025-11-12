package com.univalle.laboratorioII.controller.view;

import com.univalle.laboratorioII.entity.dto.ReservaDTO;
import com.univalle.laboratorioII.entity.dto.SalaDTO;
import com.univalle.laboratorioII.entity.dto.UsuarioDTO;
import com.univalle.laboratorioII.service.ReservaService;
import com.univalle.laboratorioII.service.SalaService;
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
@RequestMapping("/view/reserva")
@RequiredArgsConstructor
public class ReservaViewController {

    private final ReservaService reservaService;
    private final SalaService salaService;
    private final UsuarioService usuarioService;

    @GetMapping
    public String listarReservas(Model model) {
        List<ReservaDTO> reservas = reservaService.findAllReservas();
        model.addAttribute("reservas", reservas);
        model.addAttribute("title", "Gestión de Reservas");
        return "reserva/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        List<SalaDTO> salas = salaService.findAllSalas();
        List<UsuarioDTO> usuarios = usuarioService.findAllUsuarios();

        model.addAttribute("reserva", new ReservaDTO());
        model.addAttribute("salas", salas);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("title", "Nueva Reserva");
        model.addAttribute("isEdit", false);
        return "reserva/formulario";
    }

    @PostMapping("/guardar")
    public String guardarReserva(@Valid @ModelAttribute("reserva") ReservaDTO reservaDTO,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<SalaDTO> salas = salaService.findAllSalas();
            List<UsuarioDTO> usuarios = usuarioService.findAllUsuarios();

            model.addAttribute("salas", salas);
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("title", reservaDTO.getReservaId() != null ? "Editar Reserva" : "Nueva Reserva");
            model.addAttribute("isEdit", reservaDTO.getReservaId() != null);
            return "reserva/formulario";
        }

        try {
            if (reservaDTO.getReservaId() != null) {
                reservaService.updateReserva(reservaDTO);
                redirectAttributes.addFlashAttribute("success", "Reserva actualizada exitosamente");
            } else {
                reservaService.createReserva(reservaDTO);
                redirectAttributes.addFlashAttribute("success", "Reserva creada exitosamente");
            }
            return "redirect:/view/reserva";
        } catch (Exception e) {
            List<SalaDTO> salas = salaService.findAllSalas();
            List<UsuarioDTO> usuarios = usuarioService.findAllUsuarios();

            model.addAttribute("error", "Error al guardar la reserva: " + e.getMessage());
            model.addAttribute("salas", salas);
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("title", reservaDTO.getReservaId() != null ? "Editar Reserva" : "Nueva Reserva");
            model.addAttribute("isEdit", reservaDTO.getReservaId() != null);
            return "reserva/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            ReservaDTO reserva = reservaService.findReservaById(id);
            List<SalaDTO> salas = salaService.findAllSalas();
            List<UsuarioDTO> usuarios = usuarioService.findAllUsuarios();

            model.addAttribute("reserva", reserva);
            model.addAttribute("salas", salas);
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("title", "Editar Reserva");
            model.addAttribute("isEdit", true);
            return "reserva/formulario";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Reserva no encontrada");
            return "redirect:/view/reserva";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarReserva(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reservaService.deleteReserva(id);
            redirectAttributes.addFlashAttribute("success", "Reserva eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la reserva: " + e.getMessage());
        }
        return "redirect:/view/reserva";
    }
}