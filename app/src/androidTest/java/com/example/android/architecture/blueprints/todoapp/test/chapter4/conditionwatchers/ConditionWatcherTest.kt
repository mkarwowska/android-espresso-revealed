package com.example.android.architecture.blueprints.todoapp.test.chapter4.conditionwatchers

import android.preference.PreferenceActivity
import android.support.test.espresso.DataInteraction
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.widget.ImageButton
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.test.BaseTest
import com.example.android.architecture.blueprints.todoapp.test.chapter3.*
import com.example.android.architecture.blueprints.todoapp.test.chapter4.conditionwatchers.ConditionWatchers.isDrawerNotDisplayed
import com.example.android.architecture.blueprints.todoapp.test.chapter4.conditionwatchers.ConditionWatchers.todoListSnackbarGone
import com.example.android.architecture.blueprints.todoapp.test.chapter4.conditionwatchers.ConditionWatchers.waitForElement
import com.example.android.architecture.blueprints.todoapp.test.chapter4.conditionwatchers.ConditionWatchers.waitForElementIsGone
import com.example.android.architecture.blueprints.todoapp.test.helpers.CommonElements.openDrawer
import junit.framework.Assert.assertTrue
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.*
import org.hamcrest.core.AllOf
import org.junit.Test


/**
 * ConditionWatcher test sample.
 */
class ConditionWatcherTest : BaseTest() {

    private val addTaskFab = onView(withId(R.id.fab_add_task))
    private val drawerMenu = onView(withId(R.id.nav_view))
    private val settingsOption = onView(withText("Settings"))


    @Test
    fun waitForElementCondition() {
        waitForElement(addTaskFab, 400).click()
    }

    @Test
    fun navigateToSettingsSectionUsingConditionWatcher() {
        openDrawer()
        waitForElement(drawerMenu, 400)
        settingsOption.click()
        waitForElementIsGone(drawerMenu, 400)
        assertTrue("Drawer is displayed after navigation to main menu", isDrawerNotDisplayed())
    }
}
