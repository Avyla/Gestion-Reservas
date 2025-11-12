package com.univalle.laboratorioII.repository;

import com.univalle.laboratorioII.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
}
