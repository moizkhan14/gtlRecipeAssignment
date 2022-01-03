package com.app.gtlrecipeassignment.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.gtlrecipeassignment.databinding.ItemRecipeRecyclerBinding
import com.app.gtlrecipeassignment.interfaces.ClickInterface
import com.app.gtlrecipeassignment.models.Recipe
import com.bumptech.glide.Glide

/**
 * Created by Moiz Khan on 31/12/21
 */
class RecipesRecyclerAdapter :
    RecyclerView.Adapter<RecipesRecyclerAdapter.RecipeItemViewHolder>() {

    private var recipes: List<Recipe> = mutableListOf()
    private lateinit var context: Context
    private lateinit var listener: ClickInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeItemViewHolder {
        context = parent.context
        return RecipeItemViewHolder(
            ItemRecipeRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecipeItemViewHolder, position: Int) {
        holder.binding.recipeTitleTextView.text = recipes[position].title
        Glide.with(holder.binding.root.context)
            .load("https://spoonacular.com/recipeImages/${recipes[position].image}")
            .into(holder.binding.recipeImageView)
        holder.binding.root.setOnClickListener {
            listener.onClick(recipes[position])
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun getRecipes(recipes: List<Recipe>) {
        this.recipes = recipes
        notifyDataSetChanged()
    }

    fun registerClickListener(listener: ClickInterface) {
        this.listener = listener
    }

    class RecipeItemViewHolder(val binding: ItemRecipeRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root)
}