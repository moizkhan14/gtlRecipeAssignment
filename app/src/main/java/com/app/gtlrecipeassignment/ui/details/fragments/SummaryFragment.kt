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
import com.app.gtlrecipeassignment.databinding.FragmentSummaryBinding
import com.app.gtlrecipeassignment.ui.details.RecipeDetailsViewModel

/**
 * Created by Moiz Khan on 03/01/22
 */
class SummaryFragment : Fragment() {

    private val recipeDetailViewModel: RecipeDetailsViewModel by activityViewModels()
    private lateinit var binding: FragmentSummaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_summary, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeDetailViewModel.recipeSummary.observe(
            viewLifecycleOwner,
            Observer<String> { summary ->
                binding.summaryTextView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(summary, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    Html.fromHtml(summary)
                }
            })
    }
}