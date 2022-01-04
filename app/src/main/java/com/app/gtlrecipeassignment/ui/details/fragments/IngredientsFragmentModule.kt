package com.app.gtlrecipeassignment.ui.details.fragments

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class IngredientsFragmentModule {
    @FragmentScoped
    @Provides
    fun getAdapter(): IngredientsRecyclerAdapter {
        return IngredientsRecyclerAdapter()
    }
}