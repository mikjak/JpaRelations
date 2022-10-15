package com.jakubmikula.jparelationships.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipeNotes;

    @OneToOne
    private Recipe recipe;

    public Notes(String recipeNotes, Recipe recipe) {
        this.recipeNotes = recipeNotes;
        this.recipe = recipe;
    }

    public Notes() {

    }
}
