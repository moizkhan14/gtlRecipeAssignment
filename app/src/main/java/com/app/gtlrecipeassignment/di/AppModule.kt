package com.app.gtlrecipeassignment.di

import android.content.Context
import com.app.gtlrecipeassignment.api.ApiService
import com.app.gtlrecipeassignment.constants.AppConstants
import com.app.gtlrecipeassignment.localdb.RecipeDetailsDao
import com.app.gtlrecipeassignment.localdb.RecipeDetailsDatabase
import com.app.gtlrecipeassignment.repository.RecipesRepository
import com.app.gtlrecipeassignment.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Moiz Khan on 31/12/21
 * This module will build over application scope. This will create instances using Hilt.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .client(OkHttpClient())
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun getRecipeRepository(
        recipeService: ApiService,
        recipeDetailsDao: RecipeDetailsDao
    ): RecipesRepository =
        RepositoryImpl(recipeService, recipeDetailsDao)

    @Singleton
    @Provides
    fun getRecipeDetailsDatabase(@ApplicationContext context: Context): RecipeDetailsDatabase {
        return RecipeDetailsDatabase.getDatabaseInstance(context)
    }

    @Singleton
    @Provides
    fun getRecipeDetailsDao(db: RecipeDetailsDatabase): RecipeDetailsDao {
        return db.getDao()
    }
}