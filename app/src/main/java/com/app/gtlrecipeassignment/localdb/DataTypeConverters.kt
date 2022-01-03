package com.app.gtlrecipeassignment.localdb

import androidx.room.TypeConverter
import com.app.gtlrecipeassignment.models.Ingredients
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

/**
 * Created by Moiz Khan on 03/01/22
 */
class DataTypeConverters {

    @TypeConverter
    fun convertMillisToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun convertDateToMillis(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun convertFetchedStringToIngredientsList(value: String?): List<Ingredients>? {
        val listType: Type = object : TypeToken<List<Ingredients>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun convertIngredientsListToString(ingredients: List<Ingredients>): String {
        return Gson().toJson(ingredients)
    }
/*
    @TypeConverter
    fun convertFetchedStringToIngredients(value: String?): Ingredients? {
        val listType: Type = object : TypeToken<Ingredients>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun convertIngredientsToString(ingredient: Ingredients): String {
        return Gson().toJson(ingredient)
    }*/
}