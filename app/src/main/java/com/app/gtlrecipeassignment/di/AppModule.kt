package com.app.gtlrecipeassignment.di

import android.content.Context
import com.app.gtlrecipeassignment.api.ApiService
import com.app.gtlrecipeassignment.constants.AppConstants
import com.app.gtlrecipeassignment.repository.RecipesRepository
import com.app.gtlrecipeassignment.repository.RepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

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
    fun getApiService(retrofit: Retrofit) : ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides fun getRecipeRepository(recipeService: ApiService
    ): RecipesRepository =
        RepositoryImpl(recipeService)
}