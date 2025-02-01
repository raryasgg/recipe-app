package com.example.recipe_app.service;

import com.example.recipe_app.model.Recipe;
import com.example.recipe_app.repository.RecipeRepository;
import com.example.recipe_app.exception.RecipeNotFoundException;
import com.example.recipe_app.exception.RecipeServiceException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RecipeService {

    private static final Logger logger = LoggerFactory.getLogger(RecipeService.class);

    private final RecipeRepository recipeRepository;
    private final RestTemplate restTemplate;

    @Value("${dataset.url}")
    private String datasetUrl;

    public RecipeService(RecipeRepository recipeRepository, RestTemplate restTemplate) {
        this.recipeRepository = recipeRepository;
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "recipeService", fallbackMethod = "fallbackForLoadRecipes")
    @Retry(name = "recipeService")
    public List<Recipe> loadRecipesFromDataset() {
        logger.info("Loading recipes from dataset...");

        try {
            RecipesResponse response = restTemplate.getForObject(datasetUrl, RecipesResponse.class);
            if (response != null && response.getRecipes() != null) {
                logger.info("Successfully loaded recipes.");
                return recipeRepository.saveAll(response.getRecipes());
            } else {
                logger.error("No recipes found in the response.");
                throw new RecipeServiceException("No recipes found in the response");
            }
        } catch (Exception e) {
            logger.error("Error while fetching recipes from external API: {}", e.getMessage());
            throw new RecipeServiceException("Failed to load recipes from dataset");
        }
    }

    // Fallback method for circuit breaker
    public List<Recipe> fallbackForLoadRecipes(Throwable throwable) {
        logger.error("Fallback method triggered due to: {}", throwable.getMessage());
        return recipeRepository.findAll(); // Return cached data from the database
    }

    public List<Recipe> searchRecipes(String query) {
        logger.info("Searching recipes with query: {}", query);
        return recipeRepository.findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(query, query);
    }

    public Recipe findRecipeById(Long id) {
        logger.info("Fetching recipe with ID: {}", id);
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with ID: " + id));
    }

    private static class RecipesResponse {
        private List<Recipe> recipes;

        public List<Recipe> getRecipes() {
            return recipes;
        }

        public void setRecipes(List<Recipe> recipes) {
            this.recipes = recipes;
        }
    }
}
