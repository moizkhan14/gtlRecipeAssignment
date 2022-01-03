package com.app.gtlrecipeassignment.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//This data class will be used in api response and also as entity for room db.
@Entity(tableName = "details")
data class RecipeDetails(
    @PrimaryKey
    val id: Int = 0,
    val image: String? = null,
    val instructions: String? = null,
    val summary: String? = null,
    val title: String? = null,
    val extendedIngredients: List<Ingredients>? = null,
    val date: Date = Date(),
)
