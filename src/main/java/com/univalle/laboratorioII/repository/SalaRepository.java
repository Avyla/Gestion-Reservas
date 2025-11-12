package com.univalle.laboratorioII.repository;

import com.univalle.laboratorioII.entity.SalaEntity;
import com.univalle.laboratorioII.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaRepository extends JpaRepository<SalaEntity, Long> {



}
