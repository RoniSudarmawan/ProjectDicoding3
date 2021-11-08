package com.example.project3dicoding.userInterface.mainActivity

import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.project3dicoding.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest{
    private val dummyUsername = "RoniSudarmawan"

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        activityScenarioRule.scenario
    }

    @Test
    fun testUi(){
        onView(withId(R.id.searchView)).perform(click())
        onView(withId(R.id.searchView))
            .perform(typeText(dummyUsername), pressKey(KeyEvent.KEYCODE_ENTER), closeSoftKeyboard())

        Thread.sleep(1000)

    }
}