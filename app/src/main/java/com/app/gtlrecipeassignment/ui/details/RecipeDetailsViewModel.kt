package com.app.gtlrecipeassignment.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gtlrecipeassignment.models.Ingredients
import com.app.gtlrecipeassignment.models.RecipeDetails
import com.app.gtlrecipeassignment.repository.RecipesRepository
import com.app.gtlrecipeassignment.resources.Resource
import com.app.gtlrecipeassignment.utils.NetworkCallStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Moiz Khan on 31/12/21
 */
@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
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
                recipeDetails.value =
                    recipesRepository.getRecipeDetails(recipeId)

                recipeDetails.value?.let { value ->
                    value.data?.let { recipeDetail ->
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

    fun getRecipeDetailsObservable(): MutableLiveData<Resource<RecipeDetails>> = recipeDetails

}