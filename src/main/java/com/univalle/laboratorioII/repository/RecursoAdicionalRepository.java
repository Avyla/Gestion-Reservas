package com.univalle.laboratorioII.repository;

import com.univalle.laboratorioII.entity.RecursoAdicionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecursoAdicionalRepository extends JpaRepository<RecursoAdicionalEntity, Long> {

    // Contar recursos por sala
    long countBySala_SalaId(Long salaId);
}