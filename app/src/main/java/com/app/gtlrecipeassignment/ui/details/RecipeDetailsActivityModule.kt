package com.app.gtlrecipeassignment.ui.details

import androidx.fragment.app.FragmentActivity
import com.app.gtlrecipeassignment.ui.details.fragments.IngredientsRecyclerAdapter
import com.app.gtlrecipeassignment.ui.home.RecipesRecyclerAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Singleton

/**
 * Created by Moiz Khan on 01/01/22
 */
@Module
@InstallIn(ActivityComponent::class)
class RecipeDetailsActivityModule {

    @ActivityScoped
    @Provides
    fun getPagerAdapter(@FragmentScoped fragmentActivity: FragmentActivity): RecipeDetailsPagerAdapter {
        return RecipeDetailsPagerAdapter(fragmentActivity)
    }
}