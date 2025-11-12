package com.univalle.laboratorioII.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sala")
public class SalaEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "sala_id")
    private Long salaId;

    private String nombre;
    private int capacidad;
    private int ubicacion;
    private boolean estado;


}
