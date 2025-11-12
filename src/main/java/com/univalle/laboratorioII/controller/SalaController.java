package com.univalle.laboratorioII.controller;

import com.univalle.laboratorioII.entity.dto.SalaDTO;
import com.univalle.laboratorioII.service.SalaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sala")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService salaService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<SalaDTO> findAllSalas() {
        return salaService.findAllSalas();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SalaDTO findSalaById(@PathVariable Long id) {
        return salaService.findSalaById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public SalaDTO createSala(@RequestBody SalaDTO salaDTO) {
        return salaService.createSala(salaDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SalaDTO updateSala(@PathVariable Long id, @RequestBody SalaDTO salaDTO) {
        salaDTO.setSalaId(id);
        return salaService.updateSala(salaDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSala(@PathVariable Long id) {
        salaService.deleteSala(id);
    }
}