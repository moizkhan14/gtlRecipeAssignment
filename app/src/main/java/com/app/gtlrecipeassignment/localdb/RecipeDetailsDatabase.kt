package com.app.gtlrecipeassignment.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.gtlrecipeassignment.models.RecipeDetails

/**
 * Created by Moiz Khan on 03/01/22
 */
@Database(entities = [RecipeDetails::class], version = 1, exportSchema = false)
@TypeConverters(DataTypeConverters::class)
abstract class RecipeDetailsDatabase : RoomDatabase() {

    abstract fun getDao(): RecipeDetailsDao

    companion object {
        @Volatile
        private var DB_INSTANCE: RecipeDetailsDatabase? = null

        fun getDatabaseInstance(context: Context): RecipeDetailsDatabase {
            return DB_INSTANCE ?: synchronized(this) {
                DB_INSTANCE ?: Room.databaseBuilder(
                    context,
                    RecipeDetailsDatabase::class.java,
                    "RECIPE_DETAILS"
                ).build().also { instance -> DB_INSTANCE = instance }
            }
        }
    }
}