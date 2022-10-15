package com.jakubmikula.jparelationships.bootstrap;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.jakubmikula.jparelationships.domain.*;
import com.jakubmikula.jparelationships.repositories.CategoryRepository;
import com.jakubmikula.jparelationships.repositories.RecipeRepository;
import com.jakubmikula.jparelationships.repositories.UnitOfMeasureRepository;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
    }

    List<Recipe> getRecipes() {
        Optional<UnitOfMeasure> teaSpoonOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        if(!teaSpoonOptional.isPresent()) {
            throw new RuntimeException("Expected Teaspoon unit not found");
        }
        UnitOfMeasure teaSpoon = teaSpoonOptional.get();        
        
        Optional<UnitOfMeasure> cupOptional = unitOfMeasureRepository.findByDescription("Cup");
        if(!cupOptional.isPresent()) {
            throw new RuntimeException("Expected Cup unit not found");
        }
        UnitOfMeasure cup = cupOptional.get();

        List<Recipe> recipes = new ArrayList<>();

        Recipe guacamoleRecipe = new Recipe();

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
        if (!mexicanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Mexican category not found.");
        }
        Category mexicanCategory = mexicanCategoryOptional.get();
        Set<Category> categories = new HashSet<>();
        categories.add(mexicanCategory);

        guacamoleRecipe.setCategories(categories);

        Ingredient avocadosIng = new Ingredient("2 ripe avocados", BigDecimal.valueOf(2L), guacamoleRecipe, teaSpoon);
        Ingredient freshLimeIng = new Ingredient("1 tablespoon fresh lime or lemon juice", BigDecimal.valueOf(1L), guacamoleRecipe, cup);
        Ingredient redOnionIng = new Ingredient("2 to 4 tablespoons minced red onion or thinly sliced green onion", BigDecimal.valueOf(4L), guacamoleRecipe, cup);
        Set<Ingredient> guacamoleIngredients = new HashSet<>(Set.of(avocadosIng, freshLimeIng, redOnionIng));

        guacamoleRecipe.setDescription("The Best Guacamole.");
        guacamoleRecipe.setIngredients(guacamoleIngredients);
        guacamoleRecipe.setDirections("""
                1. Cut the avocado:
                Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife
                and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.
                
                2. Mash the avocado flesh:
                Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.
                """
        );
        guacamoleRecipe.setCookTime(10);
        guacamoleRecipe.setPrepTime(30);

        Notes notes1 = new Notes("""
                Chilling tomatoes hurts their flavor.
                So, if you want to add chopped tomato to your guacamole, add it just before serving.
                """, guacamoleRecipe);

        guacamoleRecipe.setNotes(notes1);
        guacamoleRecipe.setServings(2);

        recipes.add(guacamoleRecipe);

        return recipes;
    }
}
