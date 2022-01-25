package com.nutritec.backend.Nutri.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Nutricionista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String matricula;

    @Column(length = 200, nullable = false)
    private String nome;

    @Column(length = 10, nullable = false)
    private int idade;

    @Column(length = 200, nullable = false)
    private String especialidade; 

    private String planosAceitos;
}
