package com.app.gtlrecipeassignment.api

import com.app.gtlrecipeassignment.models.RecipeDetails
import com.app.gtlrecipeassignment.models.RecipesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/search")
    suspend fun getRecipes(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String
    ): RecipesResponse?

    @GET("recipes/{recipeID}/information")
    suspend fun getRecipeDetails(
        @Path("recipeID") recipeID: Int,
        @Query("apiKey") apiKey: String
    ): RecipeDetails?
}