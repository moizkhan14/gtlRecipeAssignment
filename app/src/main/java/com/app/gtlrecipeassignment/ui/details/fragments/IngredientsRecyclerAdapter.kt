package com.app.gtlrecipeassignment.ui.details.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.gtlrecipeassignment.R
import com.app.gtlrecipeassignment.databinding.ItemIngredientsRecyclerBinding
import com.app.gtlrecipeassignment.models.Ingredients

/**
 * Created by Moiz Khan on 03/01/22
 */
class IngredientsRecyclerAdapter :
    RecyclerView.Adapter<IngredientsRecyclerAdapter.IngredientsViewHolder>() {

    private var ingredients: List<Ingredients> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(
            ItemIngredientsRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient =
            "${ingredients[position].originalString}\n${holder.binding.root.context.getString(R.string.quantity)} ${ingredients[position].amount} ${ingredients[position].unit}"
        holder.binding.ingredientsTextView.text = ingredient
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    fun getIngredients(ingredients: List<Ingredients>) {
        this.ingredients = ingredients
        notifyDataSetChanged()
    }

    class IngredientsViewHolder(val binding: ItemIngredientsRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root)

}