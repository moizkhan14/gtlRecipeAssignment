package com.app.gtlrecipeassignment.ui.details

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

/**
 * Created by Moiz Khan on 03/01/22
 */
class RecipeDetailsPagerAdapter(
    fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(fragmentActivity) {
    private lateinit var fragmentList: List<Fragment>
    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun getFragments(fragmentList: List<Fragment>) {
        this.fragmentList = fragmentList
    }
}