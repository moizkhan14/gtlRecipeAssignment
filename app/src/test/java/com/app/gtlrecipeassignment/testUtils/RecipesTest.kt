package com.app.gtlrecipeassignment.testUtils

import com.app.gtlrecipeassignment.models.Recipe
import com.app.gtlrecipeassignment.models.RecipesResponse

object RecipesTest {
    fun getRecipes(): RecipesResponse = RecipesResponse(
        mutableListOf<Recipe>(
            Recipe(
                1,
                "Easy Vegetable Beef Soup",
                "vegetable-beef-stew-715447.jpg"
            ),
            Recipe(
                2,
                "Penne Pasta with Broccoli and Cheese",
                "Penne-Pasta-with-Broccoli-and-Cheese-655575.jpg"
            ),
            Recipe(3, "Penne Arrabiata", "Penne-Arrabiata-655573.jpg")
        )
    )

    fun getEmptyListOfRecipes(): RecipesResponse = RecipesResponse(listOf())
}