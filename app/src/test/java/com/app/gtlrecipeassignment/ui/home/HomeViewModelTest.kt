package com.app.gtlrecipeassignment.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.gtlrecipeassignment.repository.RecipesRepository
import com.app.gtlrecipeassignment.resources.Resource
import com.app.gtlrecipeassignment.testUtils.RecipesTest
import com.app.gtlrecipeassignment.testUtils.getOrAwaitValue
import com.app.gtlrecipeassignment.utils.NetworkCallStatus
import com.example.android.kotlincoroutines.main.utils.CoroutineScopeRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = CoroutineScopeRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private val recipeRepository: RecipesRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        viewModel = HomeViewModel(recipeRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testEmptySearchForRecipe() {
        coEvery {
            recipeRepository.getRecipes(any())
        } returns Resource(NetworkCallStatus.SUCCESS, RecipesTest.getRecipes(), "")
        runBlockingTest {
            viewModel.searchForRecipe("")
        }
        val value = viewModel.getRecipeListObservable().getOrAwaitValue()

        assertEquals(value.status, NetworkCallStatus.SUCCESS)
        Assert.assertNotNull(value.message)
        Assert.assertNotNull(value.data?.results)
        Assert.assertEquals(value.data?.results?.size, 3)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSearchForRecipe() {
        coEvery {
            recipeRepository.getRecipes(any())
        } returns Resource(NetworkCallStatus.SUCCESS, RecipesTest.getRecipes(), "")
        runBlockingTest {
            viewModel.searchForRecipe("pasta")
        }
        val value = viewModel.getRecipeListObservable().getOrAwaitValue()

        assertEquals(value.status, NetworkCallStatus.SUCCESS)
        Assert.assertNotNull(value.message)
        Assert.assertNotNull(value.data?.results)
        Assert.assertEquals(value.data?.results?.size, 3)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun testInvalidDataSearchForRecipe() {
        coEvery {
            recipeRepository.getRecipes(any())
        } returns Resource(NetworkCallStatus.SUCCESS, RecipesTest.getEmptyListOfRecipes(), "")
        runBlockingTest {
            viewModel.searchForRecipe("zxcv")
        }
        val value = viewModel.getRecipeListObservable().getOrAwaitValue()

        assertEquals(value.status, NetworkCallStatus.SUCCESS)
        Assert.assertNotNull(value.message)
        Assert.assertNotNull(value.data?.results)
        Assert.assertEquals(value.data?.results?.size, 0)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testErrorSearchForRecipe() {
        coEvery {
            recipeRepository.getRecipes(any())
        } returns Resource(NetworkCallStatus.ERROR, RecipesTest.getEmptyListOfRecipes(), "Error in fetching recipes.")
        runBlockingTest {
            viewModel.searchForRecipe("zxcv")
        }
        val value = viewModel.getRecipeListObservable().getOrAwaitValue()

        assertEquals(value.status, NetworkCallStatus.ERROR)
        Assert.assertNotNull(value.message)
        Assert.assertNotNull(value.data?.results)
        Assert.assertEquals(value.data?.results?.size, 0)
    }

}