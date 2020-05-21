package com.example.android.architecture.blueprints.todoapp.test.chapter1.actions

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.v7.widget.RecyclerView
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.test.BaseTest
import com.example.android.architecture.blueprints.todoapp.test.chapter2.customactions.CustomClickAction
import com.example.android.architecture.blueprints.todoapp.test.chapter2.customactions.CustomRecyclerViewActions
import com.example.android.architecture.blueprints.todoapp.test.chapter2.customactions.CustomRecyclerViewActions.ClickTodoCheckBoxWithTitleViewAction
import com.example.android.architecture.blueprints.todoapp.test.chapter4.conditionwatchers.ConditionWatchers.waitForElement
import com.example.android.architecture.blueprints.todoapp.test.chapter4.conditionwatchers.ConditionWatchers.waitForElementIsGone
import org.junit.Test

/**
 * Demonstrates [RecyclerView] actions usage.
 */
class RecyclerViewActionsTest : BaseTest() {
    private val todoSavedSnackbar = Espresso.onView(ViewMatchers.withText(R.string.successfully_saved_task_message))

    @Test
    @Throws(Exception::class)
    fun addNewToDos() {
        generateToDos(12)
        Espresso.onView(ViewMatchers.withId(R.id.tasks_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, ViewActions.scrollTo()))
        Espresso.onView(ViewMatchers.withId(R.id.tasks_list))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        Espresso.onView(ViewMatchers.withId(R.id.tasks_list))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(11))
        Espresso.onView(ViewMatchers.withId(R.id.tasks_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(11, ViewActions.click()))
        Espresso.pressBack()
        Espresso.onView(ViewMatchers.withId(R.id.tasks_list))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2))
    }

    @Test
    @Throws(Exception::class)
    fun addNewToDosChained() {
        generateToDos(12)
        Espresso.onView(ViewMatchers.withId(R.id.tasks_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, ViewActions.scrollTo()))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(11))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(11, ViewActions.click()))
        Espresso.pressBack()
        Espresso.onView(ViewMatchers.withId(R.id.tasks_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2))
    }

    @Test
    @Throws(Exception::class)
    fun completeToDo() {
        generateToDos(10)
        Espresso.onView(ViewMatchers.withId(R.id.tasks_list)).perform(ClickTodoCheckBoxWithTitleViewAction.clickTodoCheckBoxWithTitle("item 2"))
        Espresso.onView(ViewMatchers.withId(R.id.tasks_list))
                .perform(CustomRecyclerViewActions.ScrollToLastHolder.scrollToLastHolder())
    }

    /**
     * Helper function that adds needed TO-DOs amount.
     * @param count - amount of TO-DOs to add.
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun generateToDos(count: Int) {
        for (i in 1..count) {
            waitForElementIsGone(todoSavedSnackbar, 3000)
            // Adding new TO-DO.
            Espresso.onView(ViewMatchers.withId(R.id.fab_add_task)).perform(CustomClickAction.clickElementWithVisibility(20))
            Espresso.onView(ViewMatchers.withId(R.id.add_task_title))
                    .perform(ViewActions.typeText("item $i"), ViewActions.closeSoftKeyboard())
            Espresso.onView(ViewMatchers.withId(R.id.fab_edit_task_done)).perform(ViewActions.click())
            waitForElement(todoSavedSnackbar, 3000)
        }
        waitForElementIsGone(todoSavedSnackbar, 3000)
    }
}