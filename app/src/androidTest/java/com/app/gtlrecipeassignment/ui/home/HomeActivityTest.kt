package com.app.gtlrecipeassignment.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.app.gtlrecipeassignment.R
import com.app.gtlrecipeassignment.utils.RecipesTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun isHomeActivityVisible() {
        onView(withId(R.id.recipesRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun isNavigatedToDetailsActivityOnItemClick() {
        activityRule.scenario.onActivity {
            val recyclerView = it.findViewById<RecyclerView>(R.id.recipesRecyclerView)
            val adapter = RecipesRecyclerAdapter()
            adapter.getRecipes(RecipesTest.getRecipes().results)
            adapter.registerClickListener(it)
            recyclerView.adapter = adapter
        }

        onView(withId(R.id.recipesRecyclerView)).perform(
            actionOnItemAtPosition<RecipesRecyclerAdapter.RecipeItemViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.recipeDetailsPager)).check(matches(isDisplayed()))
    }

}