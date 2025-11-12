package com.univalle.laboratorioII.service;

import com.univalle.laboratorioII.entity.dto.SalaDTO;
import com.univalle.laboratorioII.entity.SalaEntity;
import com.univalle.laboratorioII.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository salaRepository;

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

    public void deleteSala(Long id) {
        salaRepository.deleteById(id);
    }

    public SalaDTO findSalaById(Long id) {
        SalaEntity salaEntity = salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala not found"));
        return convertToDTO(salaEntity);
    }

    public SalaDTO updateSala(SalaDTO salaDTO) {
        SalaEntity salaEntity = salaRepository.findById(salaDTO.getSalaId())
                .orElseThrow(() -> new RuntimeException("Sala not found"));

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
