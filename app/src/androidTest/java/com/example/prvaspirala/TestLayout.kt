package com.example.prvaspirala

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.hamcrest.CoreMatchers.equalTo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import org.hamcrest.Matchers.equalToIgnoringCase
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matcher
import android.view.View
import android.widget.TextView
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TestLayout {

    @Test
    fun test1() {

        // Start the HomeActivity
        val activityScenario = ActivityScenario.launch(HomeActivity::class.java)

        // Find the NavHostFragment and NavController
        var navController: NavController? = null
        activityScenario.onActivity { activity ->
            val navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
        }

        // Check that the details button is disabled
        activityScenario.onActivity { activity ->
            val navView = activity.findViewById<BottomNavigationView>(R.id.bottom_nav)
            assertThat(navView.menu.findItem(R.id.gameDetailsItem).isEnabled, equalTo(false))
        }

        // Close the activity
        activityScenario.close()
    }



    @Test
    fun test2() {

            // start the HomeActivity
            val scenario = ActivityScenario.launch(HomeActivity::class.java)

            // click on the first element in Recycler View
            onView(withId(R.id.game_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition<GameListAdapter.GameViewHolder>(0, click()))

            // click on home button
            onView(withId(R.id.homeItem)).perform(click())

            // check that the details button is enabled
            onView(withId(R.id.gameDetailsItem)).check(matches(isEnabled()))

            // click on details button
            onView(withId(R.id.gameDetailsItem)).perform(click())

            // check that the GameDetailsFragment is opened
            onView(withId(R.id.item_title_textview)).check(matches(isDisplayed()))

            // close the activity
            scenario.close()
    }




    @Test
    fun test3() {

        // start the HomeActivity
        val scenario = ActivityScenario.launch(HomeActivity::class.java)

        // click on the second element in Recycler View
        onView(withId(R.id.game_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<GameListAdapter.GameViewHolder>(1, click()))

        // taking the game title
        var gameTitle = ""
        onView(withId(R.id.item_title_textview)).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Get the text from TextView"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                gameTitle = tv.text.toString()
            }
        })

        // click on home button
        onView(withId(R.id.homeItem)).perform(click())

        // click on details button
        onView(withId(R.id.gameDetailsItem)).perform(click())

        // checking that the navigation led to a GameDetailsFragment with the correct last open game
        onView(withId(R.id.item_title_textview))
            .check(matches(allOf(isDisplayed(), withText(equalToIgnoringCase(gameTitle)))))

        scenario.close()
    }
}

