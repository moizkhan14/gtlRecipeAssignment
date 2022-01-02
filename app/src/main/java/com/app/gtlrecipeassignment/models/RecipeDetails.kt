package com.app.gtlrecipeassignment.models

import java.util.*

data class RecipeDetails(
    val id: Long = 0,
    val image: String? = null,
    val instructions: String? = null,
    val summary: String? = null,
    val title: String? = null,
    val date: Date = Date(),
)
