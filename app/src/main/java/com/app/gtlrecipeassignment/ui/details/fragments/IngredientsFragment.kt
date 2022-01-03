package com.app.gtlrecipeassignment.ui.details.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.gtlrecipeassignment.R
import com.app.gtlrecipeassignment.databinding.FragmentIngredientsBinding
import com.app.gtlrecipeassignment.models.Ingredients
import com.app.gtlrecipeassignment.ui.details.RecipeDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Moiz Khan on 03/01/22
 */
@AndroidEntryPoint
class IngredientsFragment : Fragment() {

    private val recipeDetailViewModel: RecipeDetailsViewModel by activityViewModels()
    private lateinit var binding: FragmentIngredientsBinding
    @Inject
    lateinit var adapter: IngredientsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_ingredients, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()
        recipeDetailViewModel.recipeIngredients.observe(
            viewLifecycleOwner,
            Observer<List<Ingredients>> { ingredients ->
                adapter.getIngredients(ingredients)
            })
    }

    private fun setRecycler() {
        binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.ingredientsRecyclerView.adapter = adapter
    }
}