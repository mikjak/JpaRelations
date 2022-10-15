package com.jakubmikula.jparelationships.controllers;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jakubmikula.jparelationships.domain.Category;
import com.jakubmikula.jparelationships.domain.Ingredient;
import com.jakubmikula.jparelationships.domain.Recipe;
import com.jakubmikula.jparelationships.repositories.CategoryRepository;
import com.jakubmikula.jparelationships.repositories.RecipeRepository;
import com.jakubmikula.jparelationships.repositories.UnitOfMeasureRepository;

@Controller
public class IndexController {
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeRepository recipeRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage() {
//
//        Optional<Category> category = categoryRepository.findByDescription("Poland");
//        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Teaspoon");
        Optional<Recipe> recipe = recipeRepository.findByDescription("The Best Guacamole.");

//        System.out.println("Cat id = " + category.get().getId());
//        System.out.println("UoM id = " + unitOfMeasure.get().getId());
        System.out.println("Recipe id = " + recipe.get().getId());
        System.out.println("Directions: " + recipe.get().getDirections());
        System.out.println("Notes: " + recipe.get().getNotes().getRecipeNotes());

        Set<Category> categories = recipe.get().getCategories();
        for(Category cat : categories) {
            System.out.println("Category: " + cat.getDescription() + "\n");
        }

        Set<Ingredient> ingredients = recipe.get().getIngredients();
        ingredients.forEach(System.out::println);

        return "index";
    }
}
