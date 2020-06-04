package com.example.android.architecture.blueprints.todoapp.test.chapter14

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.junit.Assert.assertFalse
import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class examplesForFailTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Before
    fun launchTasksActivity() {
        ActivityScenario.launch(TasksActivity::class.java)
    }

    /**
     * Specifically fails the test using JUnit assertion.
     */

    @Test
    fun exampleTestToGenerateJunitAssertionErros() {
        onView(ViewMatchers.withId(R.id.fab_add_task)).perform(ViewActions.click())
        val selector = uiDevice.findObject(UiSelector().resourceId("com.example.android.architecture.blueprints.todoapp.mock:id/add_task_title"))
        assertFalse("element with selector $selector is present on the screen when it should not", selector.exists())
    }

    /**
     * Specifically fails the test using Truth assertion.
     */

    @Test
    fun exampleTestToGenerateTruthAssertionErros() {
        onView(ViewMatchers.withId(R.id.fab_add_task)).perform(ViewActions.click())
        val selector = uiDevice.findObject(UiSelector().resourceId("com.example.android.architecture.blueprints.todoapp.mock:id/add_task_title"))
        assertThat(selector.exists()).isFalse()
    }
}
