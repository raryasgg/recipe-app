import { Component } from '@angular/core';
import { RecipeService } from './services/recipe.service';
import { CommonModule } from '@angular/common'; 
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-root',
	imports: [CommonModule,FormsModule],
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    title = 'recipe-app';
    searchText = '';
    recipes: any[] = [];
    selectedRecipe: any = null;

    constructor(private recipeService: RecipeService) {}


    onSearchInput(): void {
        if (this.searchText.length >= 3) {
            this.recipeService.searchRecipes(this.searchText).subscribe(
                (data) => (this.recipes = data),
                (error) => console.error('Error fetching recipes', error)
            );
        } else {
            this.recipes = [];
        }
    }

    selectRecipe(recipe: any): void {
        this.recipeService.getRecipeById(recipe.id).subscribe(
            (data) => (this.selectedRecipe = data),
            (error) => console.error('Error fetching recipe details', error)
        );
        this.recipes = [];
        this.searchText = '';
    }
}
