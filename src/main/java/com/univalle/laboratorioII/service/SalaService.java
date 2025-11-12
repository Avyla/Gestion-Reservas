package com.univalle.laboratorioII.service;

import com.univalle.laboratorioII.entity.dto.SalaDTO;
import com.univalle.laboratorioII.entity.SalaEntity;
import com.univalle.laboratorioII.repository.SalaRepository;
import com.univalle.laboratorioII.repository.ReservaRepository;
import com.univalle.laboratorioII.repository.RecursoAdicionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository salaRepository;
    private final ReservaRepository reservaRepository;
    private final RecursoAdicionalRepository recursoAdicionalRepository;

    public List<SalaDTO> findAllSalas() {
        List<SalaEntity> salas = salaRepository.findAll();
        return salas.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public SalaDTO createSala(SalaDTO salaDTO) {
        SalaEntity salaEntity = SalaEntity.builder()
                .nombre(salaDTO.getNombre())
                .capacidad(salaDTO.getCapacidad())
                .ubicacion(salaDTO.getUbicacion())
                .estado(salaDTO.isEstado())
                .build();

        SalaEntity savedSala = salaRepository.save(salaEntity);
        return convertToDTO(savedSala);
    }

    @Transactional
    public void deleteSala(Long id) {
        // Verificar si existe
        SalaEntity sala = salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        // Verificar reservas asociadas
        long reservasCount = reservaRepository.countBySala_SalaId(id);
        if (reservasCount > 0) {
            throw new RuntimeException(
                    "No se puede eliminar la sala porque tiene " + reservasCount +
                            " reserva(s) asociada(s). Elimine primero las reservas."
            );
        }

        // Verificar recursos asociados
        long recursosCount = recursoAdicionalRepository.countBySala_SalaId(id);
        if (recursosCount > 0) {
            throw new RuntimeException(
                    "No se puede eliminar la sala porque tiene " + recursosCount +
                            " recurso(s) adicional(es) asignado(s). Elimine primero los recursos."
            );
        }

        // Si no tiene dependencias, eliminar
        salaRepository.deleteById(id);
    }

    public SalaDTO findSalaById(Long id) {
        SalaEntity salaEntity = salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));
        return convertToDTO(salaEntity);
    }

    public SalaDTO updateSala(SalaDTO salaDTO) {
        SalaEntity salaEntity = salaRepository.findById(salaDTO.getSalaId())
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        salaEntity.setNombre(salaDTO.getNombre());
        salaEntity.setCapacidad(salaDTO.getCapacidad());
        salaEntity.setUbicacion(salaDTO.getUbicacion());
        salaEntity.setEstado(salaDTO.isEstado());

        SalaEntity updatedSala = salaRepository.save(salaEntity);
        return convertToDTO(updatedSala);
    }

    private SalaDTO convertToDTO(SalaEntity salaEntity) {
        return SalaDTO.builder()
                .salaId(salaEntity.getSalaId())
                .nombre(salaEntity.getNombre())
                .capacidad(salaEntity.getCapacidad())
                .ubicacion(salaEntity.getUbicacion())
                .estado(salaEntity.isEstado())
                .build();
    }
}