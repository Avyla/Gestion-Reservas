package com.univalle.laboratorioII.entity.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {

    private Long reservaId;

    @NotNull(message = "La fecha de reserva es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalDateTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalDateTime horaFin;

    @NotNull(message = "Debe seleccionar una sala")
    private Long salaId;

    private String nombreSala;

    @NotNull(message = "Debe seleccionar un usuario")
    private Long usuarioId;

    private String nameUsuario;
}