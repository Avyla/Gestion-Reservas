package com.univalle.laboratorioII.service;

import com.univalle.laboratorioII.entity.ReservaEntity;
import com.univalle.laboratorioII.entity.SalaEntity;
import com.univalle.laboratorioII.entity.UsuarioEntity;
import com.univalle.laboratorioII.entity.dto.ReservaDTO;
import com.univalle.laboratorioII.repository.ReservaRepository;
import com.univalle.laboratorioII.repository.SalaRepository;
import com.univalle.laboratorioII.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final SalaRepository salaRepository;
    private final UsuarioRepository usuarioRepository;

    public List<ReservaDTO> findAllReservas() {
        List<ReservaEntity> reservas = reservaRepository.findAll();
        return reservas.stream()
                .map(this::converToDTO)
                .toList();
    }

    public ReservaDTO createReserva(ReservaDTO reservaDTO) {
        SalaEntity sala = salaRepository.findById(reservaDTO.getSalaId())
                .orElseThrow(() -> new RuntimeException("Sala not found"));

        UsuarioEntity usuario = usuarioRepository.findById(reservaDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario not found"));

        ReservaEntity reservaEntity = ReservaEntity.builder()
                .fechaReserva(reservaDTO.getFecha())
                .horaInicio(reservaDTO.getHoraInicio())
                .horaFin(reservaDTO.getHoraFin())
                .sala(sala)
                .usuario(usuario)
                .build();

        ReservaEntity savedReserva = reservaRepository.save(reservaEntity);
        return converToDTO(savedReserva);
    }

    public void deleteReserva(Long id) {
        reservaRepository.deleteById(id);
    }

    public ReservaDTO findReservaById(Long id) {
        ReservaEntity reservaEntity = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva not found"));
        return converToDTO(reservaEntity);
    }

    public ReservaDTO updateReserva(ReservaDTO reservaDTO) {
        ReservaEntity reservaEntity = reservaRepository.findById(reservaDTO.getReservaId())
                .orElseThrow(() -> new RuntimeException("Reserva not found"));

        SalaEntity sala = salaRepository.findById(reservaDTO.getSalaId())
                .orElseThrow(() -> new RuntimeException("Sala not found"));

        UsuarioEntity usuario = usuarioRepository.findById(reservaDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario not found"));

        reservaEntity.setFechaReserva(reservaDTO.getFecha());
        reservaEntity.setHoraInicio(reservaDTO.getHoraInicio());
        reservaEntity.setHoraFin(reservaDTO.getHoraFin());
        reservaEntity.setSala(sala);
        reservaEntity.setUsuario(usuario);

        ReservaEntity updatedReserva = reservaRepository.save(reservaEntity);
        return converToDTO(updatedReserva);
    }

    private ReservaDTO converToDTO(ReservaEntity reservaEntity) {
        return ReservaDTO.builder()
                .reservaId(reservaEntity.getReservaId())
                .fecha(reservaEntity.getFechaReserva())
                .horaInicio(reservaEntity.getHoraInicio())
                .horaFin(reservaEntity.getHoraFin())
                .salaId(reservaEntity.getSala().getSalaId())
                .nombreSala(reservaEntity.getSala().getNombre())
                .usuarioId(reservaEntity.getUsuario().getUsuarioId())
                .nameUsuario(reservaEntity.getUsuario().getNombre())
                .build();
    }
}