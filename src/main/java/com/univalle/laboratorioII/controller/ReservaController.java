package com.univalle.laboratorioII.controller;

import com.univalle.laboratorioII.entity.dto.ReservaDTO;
import com.univalle.laboratorioII.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reserva")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ReservaDTO> findAllReservas() {
        return reservaService.findAllReservas();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservaDTO findReservaById(@PathVariable Long id) {
        return reservaService.findReservaById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaDTO createReserva(@RequestBody ReservaDTO reservaDTO) {
        return reservaService.createReserva(reservaDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservaDTO updateReserva(@PathVariable Long id, @RequestBody ReservaDTO reservaDTO) {
        reservaDTO.setReservaId(id);
        return reservaService.updateReserva(reservaDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
    }
}