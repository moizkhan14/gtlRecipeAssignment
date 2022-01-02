package com.app.gtlrecipeassignment.repository

import com.app.gtlrecipeassignment.api.ApiService
import com.app.gtlrecipeassignment.constants.AppConstants
import com.app.gtlrecipeassignment.models.RecipeDetails
import com.app.gtlrecipeassignment.models.RecipesResponse
import com.app.gtlrecipeassignment.resources.Resource
import com.app.gtlrecipeassignment.utils.NetworkCallStatus
import kotlinx.coroutines.*
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ApiService) : RecipesRepository {
    override suspend fun getRecipes(query: String): Resource<RecipesResponse> =
        withContext(Dispatchers.IO) {
            try {
                val recipes = apiService.getRecipes(query, AppConstants.API_KEY)
                if (recipes?.results != null) {
                    return@withContext Resource(NetworkCallStatus.SUCCESS, recipes, null)
                } else {
                    return@withContext Resource(NetworkCallStatus.ERROR, null, "An error occured")
                }
            } catch (exc: Exception) {
                return@withContext Resource(NetworkCallStatus.ERROR, null, exc.message)
            }
        }


    override suspend fun getRecipeDetails(recipeId: Int): Resource<RecipeDetails> =
        withContext(Dispatchers.IO) {
            try {
                val recipeInfo = apiService.getRecipeDetails(recipeId, AppConstants.API_KEY)
                if (recipeInfo != null) {

                    return@withContext Resource(NetworkCallStatus.SUCCESS, recipeInfo, null)
                } else {
                    return@withContext Resource(NetworkCallStatus.ERROR, null, "An error occured")
                }
            } catch (exc: Exception) {
                return@withContext Resource(NetworkCallStatus.ERROR, null, exc.message)
            }
        }
}