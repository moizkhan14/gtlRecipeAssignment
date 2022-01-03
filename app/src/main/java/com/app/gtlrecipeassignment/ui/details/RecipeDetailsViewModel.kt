package com.app.gtlrecipeassignment.ui.details

import androidx.lifecycle.*
import com.app.gtlrecipeassignment.localdb.RecipeDetailsDao
import com.app.gtlrecipeassignment.models.Ingredients
import com.app.gtlrecipeassignment.models.RecipeDetails
import com.app.gtlrecipeassignment.repository.RecipesRepository
import com.app.gtlrecipeassignment.resources.Resource
import com.app.gtlrecipeassignment.utils.NetworkCallStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * Created by Moiz Khan on 31/12/21
 */
@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository,
    private val recipeDetailsDao: RecipeDetailsDao
) :
    ViewModel() {

    private var recipeDetails: MutableLiveData<Resource<RecipeDetails>> = MutableLiveData()
    var recipeSummary: MutableLiveData<String> = MutableLiveData()
    var recipeInstructions: MutableLiveData<String> = MutableLiveData()
    var recipeIngredients: MutableLiveData<List<Ingredients>> = MutableLiveData()

    fun fetchRecipeDetails(recipeId: Int) {
        recipeDetails.value = Resource(NetworkCallStatus.LOADING, null, "")
        try {

            viewModelScope.launch {
                // Here we will check is data stored in db is "stored with in 7 days?". If it is, then we will restore it
                // and pass it to observer else call api.
                val recipe = lookForDataInLocal(recipeId)
                recipeDetails.value = if (recipe != null)
                    Resource(NetworkCallStatus.SUCCESS, recipe, "")
                else
                    recipesRepository.getRecipeDetails(recipeId)

                recipeDetails.value?.let { value ->
                    value.data?.let { recipeDetail ->
                        viewModelScope.launch(Dispatchers.IO) {
                            // Saving data to local storage.
                            recipeDetailsDao.insertRecipeDetails(recipeDetail)
                        }
                        recipeDetail.summary?.let { summary ->
                            recipeSummary.value = summary
                        }
                        recipeDetail.instructions?.let { instructions ->
                            recipeInstructions.value = instructions
                        }
                        recipeDetail.extendedIngredients?.let { ingredients ->
                            recipeIngredients.value = ingredients
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            recipeDetails.value = Resource(NetworkCallStatus.ERROR, null, ex.message)
        }
    }

    private suspend fun lookForDataInLocal(recipeId: Int): RecipeDetails? =
        withContext(Dispatchers.IO) {
            val detailsFromDb = recipeDetailsDao.getRecipeDetails(recipeId)
            if (detailsFromDb != null) {
                val calendar = Calendar.getInstance(Locale.getDefault())
                calendar.time = detailsFromDb.date
                calendar.add(Calendar.DAY_OF_YEAR, 7)
                if (detailsFromDb.date.before(calendar.time)) {
                    return@withContext detailsFromDb
                }
            }
            return@withContext null
        }

    fun getRecipeDetailsObservable(): MutableLiveData<Resource<RecipeDetails>> = recipeDetails

}