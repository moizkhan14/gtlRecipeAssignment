package com.app.gtlrecipeassignment.repository

import com.app.gtlrecipeassignment.models.RecipeDetails
import com.app.gtlrecipeassignment.models.RecipesResponse
import com.app.gtlrecipeassignment.resources.Resource

/**
 * Created by Moiz Khan on 31/12/21
 */
interface RecipesRepository {
    suspend fun getRecipes(query: String): Resource<RecipesResponse>
    suspend fun getRecipeDetails(recipeId: Int): Resource<RecipeDetails>
}