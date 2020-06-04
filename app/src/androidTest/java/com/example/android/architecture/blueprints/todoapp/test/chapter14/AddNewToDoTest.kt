package com.example.android.architecture.blueprints.todoapp.test.chapter14

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddNewToDoTest {

    // Exercise 29 - page 280
    // Implement a test that launches ActivityScenario in the @Before method

    @Before
    fun launchTasksActivity() {
        ActivityScenario.launch(TasksActivity::class.java)
    }

    @Test
    fun addsNewToDo() {
        // adding new TO-DO
        onView(withId(R.id.fab_add_task)).perform(click())
        onView(withId(R.id.add_task_title))
                .perform(ViewActions.typeText("Example title"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.fab_edit_task_done)).perform(click())
        // verifying new TO-DO with title is shown in the TO-DO list
        onView(withText("Example title")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}