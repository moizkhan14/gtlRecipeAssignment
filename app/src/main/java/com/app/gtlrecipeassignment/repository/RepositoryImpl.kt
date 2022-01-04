package com.app.gtlrecipeassignment.repository

import com.app.gtlrecipeassignment.api.ApiService
import com.app.gtlrecipeassignment.constants.AppConstants
import com.app.gtlrecipeassignment.localdb.RecipeDetailsDao
import com.app.gtlrecipeassignment.models.RecipeDetails
import com.app.gtlrecipeassignment.models.RecipesResponse
import com.app.gtlrecipeassignment.resources.Resource
import com.app.gtlrecipeassignment.utils.NetworkCallStatus
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

/**
 * Created by Moiz Khan on 31/12/21
 */
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val recipeDetailsDao: RecipeDetailsDao
) : RecipesRepository {
    override suspend fun getRecipes(query: String): Resource<RecipesResponse> =
        withContext(Dispatchers.IO) {
            try {
                val recipes = apiService.getRecipes(query, AppConstants.API_KEY)
                if (recipes?.results != null) {
                    return@withContext Resource(NetworkCallStatus.SUCCESS, recipes, null)
                } else {
                    return@withContext Resource(
                        NetworkCallStatus.ERROR,
                        null,
                        "Error in fetching recipes."
                    )
                }
            } catch (ex: Exception) {
                return@withContext Resource(NetworkCallStatus.ERROR, null, ex.message)
            }
        }


    override suspend fun getRecipeDetails(recipeId: Int): Resource<RecipeDetails> =
        withContext(Dispatchers.IO) {
            try {
                val detailsFromDb = recipeDetailsDao.getRecipeDetails(recipeId)
                // Here we will check is data stored on db is stored with in 7 days. If it is then we will restore it
                // and pass it to observer else call api.
                if (detailsFromDb != null) {
                    val calendar = Calendar.getInstance(Locale.getDefault())
                    calendar.time = detailsFromDb.date
                    calendar.add(Calendar.DAY_OF_YEAR, 7)
                    if (detailsFromDb.date.before(calendar.time)) {
                        return@withContext Resource(NetworkCallStatus.SUCCESS, detailsFromDb, null)
                    }
                }

                val recipeDetails = apiService.getRecipeDetails(recipeId, AppConstants.API_KEY)
                if (recipeDetails != null) {
                    recipeDetailsDao.insertRecipeDetails(recipeDetails)
                    return@withContext Resource(NetworkCallStatus.SUCCESS, recipeDetails, null)
                } else {
                    return@withContext Resource(
                        NetworkCallStatus.ERROR,
                        null,
                        "Error in fetching details."
                    )
                }
            } catch (ex: Exception) {
                return@withContext Resource(NetworkCallStatus.ERROR, null, ex.message)
            }
        }
}