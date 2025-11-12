package com.univalle.laboratorioII.service;

import com.univalle.laboratorioII.entity.RecursoAdicionalEntity;
import com.univalle.laboratorioII.entity.SalaEntity;
import com.univalle.laboratorioII.entity.dto.RecursoAdicionalDTO;
import com.univalle.laboratorioII.repository.RecursoAdicionalRepository;
import com.univalle.laboratorioII.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecursoAdicionalService {

    private final RecursoAdicionalRepository recursoAdicionalRepository;
    private final SalaRepository salaRepository;

    public List<RecursoAdicionalDTO> findAllRecursosAdicionales() {
        return recursoAdicionalRepository.findAll()
                .stream()
                .map(this::converToDTO)
                .toList();
    }

    public RecursoAdicionalDTO createRecursoAdicional(RecursoAdicionalDTO recursoAdicionalDTO) {
        SalaEntity sala = salaRepository.findById(recursoAdicionalDTO.getSalaId())
                .orElseThrow(() -> new RuntimeException("Sala not found"));

        RecursoAdicionalEntity recursoAdicionalEntity = RecursoAdicionalEntity.builder()
                .nombre(recursoAdicionalDTO.getNombre())
                .descripcion(recursoAdicionalDTO.getDescripcion())
                .sala(sala)
                .build();

        RecursoAdicionalEntity savedRecurso = recursoAdicionalRepository.save(recursoAdicionalEntity);
        return converToDTO(savedRecurso);
    }

    public void deleteRecursoAdicional(Long id) {
        recursoAdicionalRepository.deleteById(id);
    }

    public RecursoAdicionalDTO findRecursoAdicionalById(Long id) {
        RecursoAdicionalEntity recursoAdicionalEntity = recursoAdicionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso Adicional not found"));
        return converToDTO(recursoAdicionalEntity);
    }

    public RecursoAdicionalDTO updateRecursoAdicional(RecursoAdicionalDTO recursoAdicionalDTO) {
        RecursoAdicionalEntity recursoAdicionalEntity = recursoAdicionalRepository.findById(recursoAdicionalDTO.getRecursoId())
                .orElseThrow(() -> new RuntimeException("Recurso Adicional not found"));

        SalaEntity sala = salaRepository.findById(recursoAdicionalDTO.getSalaId())
                .orElseThrow(() -> new RuntimeException("Sala not found"));

        recursoAdicionalEntity.setNombre(recursoAdicionalDTO.getNombre());
        recursoAdicionalEntity.setDescripcion(recursoAdicionalDTO.getDescripcion());
        recursoAdicionalEntity.setSala(sala);

        RecursoAdicionalEntity updatedRecurso = recursoAdicionalRepository.save(recursoAdicionalEntity);
        return converToDTO(updatedRecurso);
    }

    private RecursoAdicionalDTO converToDTO(RecursoAdicionalEntity recursoAdicionalEntity) {
        return RecursoAdicionalDTO.builder()
                .recursoId(recursoAdicionalEntity.getRecursoId())
                .nombre(recursoAdicionalEntity.getNombre())
                .descripcion(recursoAdicionalEntity.getDescripcion())
                .salaId(recursoAdicionalEntity.getSala().getSalaId())
                .nombreSala(recursoAdicionalEntity.getSala().getNombre())
                .build();
    }
}