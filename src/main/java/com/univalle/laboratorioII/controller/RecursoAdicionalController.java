package com.univalle.laboratorioII.controller;

import com.univalle.laboratorioII.entity.dto.RecursoAdicionalDTO;
import com.univalle.laboratorioII.service.RecursoAdicionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recurso-adicional")
@RequiredArgsConstructor
public class RecursoAdicionalController {

    private final RecursoAdicionalService recursoAdicionalService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<RecursoAdicionalDTO> findAllRecursosAdicionales() {
        return recursoAdicionalService.findAllRecursosAdicionales();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecursoAdicionalDTO findRecursoAdicionalById(@PathVariable Long id) {
        return recursoAdicionalService.findRecursoAdicionalById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public RecursoAdicionalDTO createRecursoAdicional(@RequestBody RecursoAdicionalDTO recursoAdicionalDTO) {
        return recursoAdicionalService.createRecursoAdicional(recursoAdicionalDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecursoAdicionalDTO updateRecursoAdicional(@PathVariable Long id, @RequestBody RecursoAdicionalDTO recursoAdicionalDTO) {
        recursoAdicionalDTO.setRecursoId(id);
        return recursoAdicionalService.updateRecursoAdicional(recursoAdicionalDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecursoAdicional(@PathVariable Long id) {
        recursoAdicionalService.deleteRecursoAdicional(id);
    }
}