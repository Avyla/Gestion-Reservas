package com.univalle.laboratorioII.repository;

import com.univalle.laboratorioII.entity.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {

    // Contar reservas por usuario
    long countByUsuario_UsuarioId(Long usuarioId);

    // Contar reservas por sala
    long countBySala_SalaId(Long salaId);
}