package com.app.gtlrecipeassignment.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gtlrecipeassignment.models.RecipesResponse
import com.app.gtlrecipeassignment.repository.RecipesRepository
import com.app.gtlrecipeassignment.resources.Resource
import com.app.gtlrecipeassignment.utils.NetworkCallStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val recipesRepository: RecipesRepository) :
    ViewModel() {
    private var fetchedRecipes: MutableLiveData<Resource<RecipesResponse>> = MutableLiveData()

    fun searchForRecipe(query: String) {
        fetchedRecipes.value = Resource(NetworkCallStatus.LOADING, null, "")
        try {
            viewModelScope.launch {
                fetchedRecipes.value = recipesRepository.getRecipes(query)
            }
        } catch (ex: Exception) {
            fetchedRecipes.value = Resource(NetworkCallStatus.ERROR, null, ex.message)
        }
    }

    fun getRecipeListObservable(): MutableLiveData<Resource<RecipesResponse>> = fetchedRecipes
}