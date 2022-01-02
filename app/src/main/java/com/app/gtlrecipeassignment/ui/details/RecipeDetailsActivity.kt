package com.app.gtlrecipeassignment.ui.details

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.app.gtlrecipeassignment.R
import com.app.gtlrecipeassignment.databinding.ActivityRecipeDetailsBinding

class RecipeDetailsActivity : AppCompatActivity() {


    companion object{
        const val RECIPE_ID = "recipe_id"
        fun open(activity:Activity, recipeId:Int){
            val intent = Intent(activity,RecipeDetailsActivity::class.java)
            intent.putExtra(RECIPE_ID,recipeId)
            activity.startActivity(intent)
        }
    }

    private val recipeDetailsViewModel: RecipeDetailsViewModel by viewModels()
    lateinit var binding: ActivityRecipeDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details)
        val recipeID = intent.getIntExtra(RECIPE_ID,0)

    }
}