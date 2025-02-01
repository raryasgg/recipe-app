package com.example.recipe_app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.recipe_app.exception.RecipeNotFoundException;
import com.example.recipe_app.model.Recipe;
import com.example.recipe_app.repository.RecipeRepository;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    public void testFindRecipeById_Success() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Pasta");

        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        Recipe result = recipeService.findRecipeById(1L);

        assertNotNull(result);
        assertEquals("Pasta", result.getName());
    }

    @Test
    public void testFindRecipeById_NotFound() {
        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> recipeService.findRecipeById(1L));
    }
}
