package com.app.gtlrecipeassignment.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.app.gtlrecipeassignment.models.RecipeDetails
/**
 * Created by Moiz Khan on 03/01/22
 */
@Dao
interface RecipeDetailsDao {

    @Query("SELECT * FROM details WHERE id = :recipeId")
    fun getRecipeDetails(recipeId: Int): RecipeDetails?

    @Insert(onConflict = REPLACE)
    fun insertRecipeDetails(recipeDetails: RecipeDetails)
}