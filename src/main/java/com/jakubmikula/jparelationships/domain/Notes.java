package com.jakubmikula.jparelationships.domain;

import javax.persistence.*;

@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipeNotes;

    @OneToOne
    private Recipe recipe;
}
