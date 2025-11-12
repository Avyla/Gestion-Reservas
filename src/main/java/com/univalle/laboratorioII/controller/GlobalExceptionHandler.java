package com.univalle.laboratorioII.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                               RedirectAttributes redirectAttributes,
                                               HttpServletRequest request) {

        String message = ex.getMessage() != null ? ex.getMessage().toLowerCase() : "";
        String rootCauseMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage().toLowerCase() : "";
        String fullMessage = message + " " + rootCauseMessage;

        // Detectar el tipo de error y personalizar el mensaje
        if (fullMessage.contains("usuarios") && fullMessage.contains("reserva")) {
            redirectAttributes.addFlashAttribute("error",
                    "⚠️ No se puede eliminar este usuario porque tiene reservas asociadas. " +
                            "Por favor, elimine primero todas las reservas del usuario antes de continuar.");
            return "redirect:/view/usuario";

        } else if (fullMessage.contains("sala") && fullMessage.contains("reserva")) {
            redirectAttributes.addFlashAttribute("error",
                    "⚠️ No se puede eliminar esta sala porque tiene reservas activas. " +
                            "Elimine primero todas las reservas de la sala antes de continuar.");
            return "redirect:/view/sala";

        } else if (fullMessage.contains("sala") && fullMessage.contains("recurso")) {
            redirectAttributes.addFlashAttribute("error",
                    "⚠️ No se puede eliminar esta sala porque tiene recursos adicionales asignados. " +
                            "Elimine primero todos los recursos de la sala antes de continuar.");
            return "redirect:/view/sala";

        } else {
            // Mensaje genérico más amigable
            redirectAttributes.addFlashAttribute("error",
                    "⚠️ No se puede completar esta operación porque existen datos relacionados. " +
                            "Verifique las dependencias y elimínelas primero.");

            // Intentar redirigir a la página anterior
            String referer = request.getHeader("Referer");
            if (referer != null && !referer.isEmpty()) {
                return "redirect:" + referer.substring(referer.indexOf("/view"));
            }
            return "redirect:/";
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex,
                                         RedirectAttributes redirectAttributes,
                                         HttpServletRequest request) {
        String referer = request.getHeader("Referer");

        // Mejorar el mensaje de error
        String errorMessage = ex.getMessage();
        if (errorMessage != null && errorMessage.toLowerCase().contains("not found")) {
            redirectAttributes.addFlashAttribute("error",
                    "❌ Registro no encontrado. Es posible que ya haya sido eliminado.");
        } else {
            redirectAttributes.addFlashAttribute("error",
                    "❌ Ha ocurrido un error: " + (errorMessage != null ? errorMessage : "Error desconocido"));
        }

        if (referer != null && !referer.isEmpty()) {
            try {
                return "redirect:" + referer.substring(referer.indexOf("/view"));
            } catch (Exception e) {
                return "redirect:/";
            }
        }
        return "redirect:/";
    }
}