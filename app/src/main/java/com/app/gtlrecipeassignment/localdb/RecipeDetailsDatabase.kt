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
        private var DB_INSTANCE: RecipeDetailsDatabase? = null

        fun getDatabaseInstance(context: Context): RecipeDetailsDatabase {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context,
                    RecipeDetailsDatabase::class.java,
                    "RECIPE_DETAILS"
                ).build()
            }
            return DB_INSTANCE!!
        }
    }
}