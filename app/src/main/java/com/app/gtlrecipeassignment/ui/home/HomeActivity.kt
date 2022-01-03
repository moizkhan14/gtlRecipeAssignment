package com.app.gtlrecipeassignment.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.gtlrecipeassignment.R
import com.app.gtlrecipeassignment.databinding.ActivityHomeBinding
import com.app.gtlrecipeassignment.interfaces.ClickInterface
import com.app.gtlrecipeassignment.models.Recipe
import com.app.gtlrecipeassignment.ui.details.RecipeDetailsActivity
import com.app.gtlrecipeassignment.utils.AppUtils
import com.app.gtlrecipeassignment.utils.NetworkCallStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Moiz Khan on 31/12/21
 */
@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), ClickInterface {

    private val homeViewModel: HomeViewModel by viewModels()
    lateinit var binding: ActivityHomeBinding
    @Inject
    lateinit var adapter: RecipesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        setObserver()
        registerClicks()
        setRecyclerView()
    }

    private fun registerClicks() {
        binding.searchImageView.setOnClickListener {
            searchForRecipes()
        }
    }

    private fun searchForRecipes() {
        AppUtils.hideKeyboard(this, binding.root)
        if (binding.searchEditText.text.toString().isNotEmpty())
            homeViewModel.searchForRecipe(binding.searchEditText.text.toString())
        else
            binding.searchEditText.error = getString(R.string.search_empty_error)
    }

    private fun setObserver() {
        homeViewModel.getRecipeListObservable().observe(this, { response ->
            when (response.status) {
                NetworkCallStatus.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    response.data?.results?.let { recipes -> adapter.getRecipes(recipes) }
                }
                NetworkCallStatus.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                NetworkCallStatus.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun setRecyclerView() {
        binding.recipesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recipesRecyclerView.adapter = adapter
        adapter.registerClickListener(this)
    }

    override fun onClick(obj: Any) {
        if (obj is Recipe) {
            RecipeDetailsActivity.open(this@HomeActivity, obj.id)
        }
    }
}