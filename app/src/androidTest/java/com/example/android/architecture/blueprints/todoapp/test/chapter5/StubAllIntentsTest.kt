package com.example.android.architecture.blueprints.todoapp.test.chapter5

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openContextualActionModeOverflowMenu
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.matcher.IntentMatchers.isInternal
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import com.example.android.architecture.blueprints.todoapp.test.chapter1.data.TestData
import com.example.android.architecture.blueprints.todoapp.test.chapter3.*
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Demonstrates how to stub all external [Intent]s.
 */
class StubAllIntentsTest {

    @get:Rule
    var intentsTestRule = IntentsTestRule(TasksActivity::class.java)

    private var toDoTitle = ""
    private var toDoDescription = ""

    // ViewInteractions used in tests
    private val addFab = viewWithId(R.id.fab_add_task)
    private val taskTitleField = viewWithId(R.id.add_task_title)
    private val taskDescriptionField = viewWithId(R.id.add_task_description)
    private val editDoneFab = viewWithId(R.id.fab_edit_task_done)
    private val shareMenuItem =
            onView(allOf(withId(R.id.title), withText(R.string.share)))

    @Before
    fun setUp() {
        toDoTitle = TestData.getToDoTitle()
        toDoDescription = TestData.getToDoDescription()
    }

    @Before
    fun stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal()))
                .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
    }

    //Exercise 16.2 - Verify the share intent functionality based on the intent type and action
    @Test
    fun stubsShareIntent() {
        val allExpectedIntent = (allOf(hasAction(Intent.ACTION_SEND),(hasType("text/plain"))))
        // adding new TO-DO
        addFab.click()
        taskTitleField.type(toDoTitle).closeKeyboard()
        taskDescriptionField.type(toDoDescription).closeKeyboard()
        editDoneFab.click()
        // verifying new TO-DO with title is shown in the TO-DO list
        viewWithText(toDoTitle).checkDisplayed()
        openContextualActionModeOverflowMenu()
        shareMenuItem.click()
        // Validate sent intent action.
        intended(allExpectedIntent)
        viewWithText(toDoTitle).click()
    }
}
