package com.example.prvaspirala

import android.content.pm.ActivityInfo
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
import ba.etf.rma23.projekat.GameListAdapter
import ba.etf.rma23.projekat.MainActivity
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class OwnEspressoTests {


    /**
     * This test scenario is designed to verify the navigation and UI behavior in the HomeActivity.
     * The purpose of this test is to ensure that:
     * 1. The correct items are enabled or disabled in the bottom navigation view.
     * 2. Clicking on a game list item takes the user to the Game Details screen.
     * 3. Clicking on the home button in the bottom navigation view takes the user back to the home screen.
     * 4. The game details item in the bottom navigation view is enabled after navigating to the Game Details screen.
     * 5. The game details screen shows the item title text view.
     *
     * To ensure the test checks the specified behavior, we perform the following actions:
     * 1. Launch the HomeActivity and retrieve the NavController.
     * 2. Check if the details button is disabled in the bottom navigation view.
     * 3. Click on the first item in the game list and verify that the user is navigated to the Game Details screen.
     * 4. Click on the home button in the bottom navigation view and verify that the user is navigated back to the home screen.
     * 5. Check if the details button is enabled in the bottom navigation view.
     * 6. Click on the details button and verify that the user is navigated to the Game Details screen.
     * 7. Check if the game title is displayed on the Game Details screen.
     */
    @Test
    fun test1() {

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        var navController: NavController? = null
        activityScenario.onActivity { activity ->
            val navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
        }

        activityScenario.onActivity { activity ->
            val navView = activity.findViewById<BottomNavigationView>(R.id.bottom_nav)
            assertThat(navView.menu.findItem(R.id.gameDetailsItem).isEnabled, equalTo(false))
        }

        onView(withId(R.id.game_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<GameListAdapter.GameViewHolder>(0, click()))

        onView(withId(R.id.homeItem)).perform(click())

        onView(withId(R.id.gameDetailsItem)).check(matches(isEnabled()))

        onView(withId(R.id.gameDetailsItem)).perform(click())

        onView(withId(R.id.item_title_textview)).check(matches(isDisplayed()))

        activityScenario.close()
    }



    /**
     * This test scenario is designed to verify the navigation and UI behavior in the HomeActivity, specifically focusing on the game title consistency.
     * The purpose of this test is to ensure that:
     * 1. Clicking on a game list item takes the user to the Game Details screen with the correct game title.
     * 2. When navigating back to the home screen and then to the Game Details screen via the bottom navigation view, the game title remains consistent.
     *
     * To ensure the test checks the specified behavior, we perform the following actions:
     * 1. Launch the HomeActivity.
     * 2. Click on the second item in the game list and navigate to the Game Details screen.
     * 3. Retrieve the game title from the item_title_textview.
     * 4. Navigate back to the home screen by clicking on the home button in the bottom navigation view.
     * 5. Navigate to the Game Details screen by clicking on the details button in the bottom navigation view.
     * 6. Check if the item_title_textview displays the same game title that was retrieved earlier.
     */

    @Test
    fun test2() {

        val scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.game_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<GameListAdapter.GameViewHolder>(1, click()))


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


        onView(withId(R.id.homeItem)).perform(click())

        onView(withId(R.id.gameDetailsItem)).perform(click())

        onView(withId(R.id.item_title_textview))
            .check(matches(allOf(isDisplayed(), withText(equalToIgnoringCase(gameTitle)))))

        scenario.close()
    }

    /**
     * This test scenario is designed to verify the behavior of the HomeActivity when the screen orientation changes to landscape mode.
     * The purpose of this test is to ensure that:
     * 1. The home_fragment_container is still displayed after the orientation change.
     * 2. The game_details_fragment_container is still displayed after the orientation change.
     *
     * To ensure the test checks the specified behavior, we perform the following actions:
     * 1. Launch the HomeActivity.
     * 2. Change the screen orientation to landscape mode.
     * 3. Check if the home_fragment_container is displayed on the screen.
     * 4. Check if the game_details_fragment_container is displayed on the screen.
     */

    @Test
    fun test3() {

        ActivityScenario.launch(MainActivity::class.java)

        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }

            onView(withId(R.id.home_fragment_container)).check(matches(isDisplayed()))

            onView(withId(R.id.game_details_fragment_container)).check(matches(isDisplayed()))
        }
    }
}

