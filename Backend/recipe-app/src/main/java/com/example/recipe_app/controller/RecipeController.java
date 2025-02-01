package com.example.recipe_app.controller;

import com.example.recipe_app.model.Recipe;
import com.example.recipe_app.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/recipes")
@Validated
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(summary = "Load recipes from the dataset", description = "Load a list of recipes from the predefined dataset into the application.")
    @PostMapping("/load")
    public List<Recipe> loadRecipes() {
        return recipeService.loadRecipesFromDataset();
    }

    @Operation(summary = "Search recipes by name or cuisine", description = "Search all recipes based on a free-text search on the recipe name and cuisine.")
    @GetMapping("/search")
    public List<Recipe> searchRecipes(
            @RequestParam @Parameter(description = "Search query for recipe name or cuisine")
            @NotEmpty(message = "Search query cannot be empty") String query) {
        return recipeService.searchRecipes(query);
    }

    @Operation(summary = "Find a specific recipe by ID", description = "Retrieve a recipe by its unique ID.")
    @GetMapping("/{id}")
    public Recipe getRecipeById(
            @PathVariable @Parameter(description = "ID of the recipe to retrieve")
            @NotNull(message = "Recipe ID cannot be null") Long id) {
        return recipeService.findRecipeById(id);
    }
}
