package com.app.gtlrecipeassignment.ui.details

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.app.gtlrecipeassignment.R
import com.app.gtlrecipeassignment.databinding.ActivityRecipeDetailsBinding
import com.app.gtlrecipeassignment.ui.details.fragments.IngredientsFragment
import com.app.gtlrecipeassignment.ui.details.fragments.InstructionsFragment
import com.app.gtlrecipeassignment.ui.details.fragments.SummaryFragment
import com.app.gtlrecipeassignment.utils.NetworkCallStatus
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Moiz Khan on 31/12/21
 */

@AndroidEntryPoint
class RecipeDetailsActivity : AppCompatActivity() {


    companion object {
        const val RECIPE_ID = "recipe_id"
        fun open(activity: Activity, recipeId: Int) {
            val intent = Intent(activity, RecipeDetailsActivity::class.java)
            intent.putExtra(RECIPE_ID, recipeId)
            activity.startActivity(intent)
        }
    }

    private val recipeDetailsViewModel: RecipeDetailsViewModel by viewModels()
    private lateinit var binding: ActivityRecipeDetailsBinding

    @Inject
    lateinit var adapter: RecipeDetailsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details)
        val recipeID = intent?.getIntExtra(RECIPE_ID, 0)

        setFragmentsToAdapter()
        setViewPager()
        setObserver()
        recipeID?.let { id ->
            recipeDetailsViewModel.fetchRecipeDetails(id)
        }

    }

    private fun setViewPager() {
        binding.recipeDetailsPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.recipeDetailsPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.summary)
                1 -> getString(R.string.instructions)
                2 -> getString(R.string.ingredients)
                else -> ""
            }
        }.attach()
    }

    private fun setFragmentsToAdapter() {
        adapter.getFragments(
            listOf(
                SummaryFragment(),
                InstructionsFragment(),
                IngredientsFragment(),
            )
        )
    }

    private fun setObserver() {
        recipeDetailsViewModel.getRecipeDetailsObservable().observe(this, { response ->
            when (response.status) {
                NetworkCallStatus.SUCCESS -> binding.progressBar.visibility = View.GONE
                NetworkCallStatus.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
                NetworkCallStatus.LOADING -> binding.progressBar.visibility = View.VISIBLE
            }
        })
    }
}