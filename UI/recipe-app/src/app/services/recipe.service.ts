import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from './../environments/environment';
import { Recipe } from './../models/recipe';

@Injectable({
    providedIn: 'root',
})
export class RecipeService {
    private baseUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {}

    searchRecipes(query: string): Observable<Recipe[]> {
        return this.http.get<Recipe[]>(`${this.baseUrl}/search?query=${query}`);
    }

    getRecipeById(id: number): Observable<Recipe> {
        return this.http.get<Recipe>(`${this.baseUrl}/${id}`);
    }
}
