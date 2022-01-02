package com.app.gtlrecipeassignment.ui.home

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class HomeActivityModule {

   @ActivityScoped
    @Provides
    fun getAdapter():RecipesRecyclerAdapter{
        return RecipesRecyclerAdapter()
    }
}