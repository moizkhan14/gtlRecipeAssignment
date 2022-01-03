package com.app.gtlrecipeassignment.ui.details.fragments

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.app.gtlrecipeassignment.R
import com.app.gtlrecipeassignment.databinding.FragmentInstructionsBinding
import com.app.gtlrecipeassignment.ui.details.RecipeDetailsViewModel

/**
 * Created by Moiz Khan on 03/01/22
 */
class InstructionsFragment : Fragment() {

    private val recipeDetailViewModel: RecipeDetailsViewModel by activityViewModels()
    private lateinit var binding: FragmentInstructionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_instructions, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeDetailViewModel.recipeInstructions.observe(
            viewLifecycleOwner,
            Observer<String> { instruction ->
                binding.instructionTextView.text =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Html.fromHtml(instruction, Html.FROM_HTML_MODE_COMPACT)
                    } else {
                        Html.fromHtml(instruction)
                    }
            })

    }
}