package com.example.recipe_app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.recipe_app.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	 List<Recipe> findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(String nameQuery, String cuisineQuery);
}