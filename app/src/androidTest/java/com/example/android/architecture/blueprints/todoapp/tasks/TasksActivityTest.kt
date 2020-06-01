package com.example.android.architecture.blueprints.todoapp.tasks


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView.ViewHolder
import com.example.android.architecture.blueprints.todoapp.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TasksActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(TasksActivity::class.java)

    @Test
    fun tasksActivityTest() {
        val floatingActionButton = onView(
                allOf(withId(R.id.fab_add_task), withContentDescription("Add todo"), isDisplayed()))
        floatingActionButton.perform(click())

        val appCompatEditText = onView(
                withId(R.id.add_task_title))
        appCompatEditText.perform(scrollTo(), replaceText("title"), closeSoftKeyboard())

        val floatingActionButton2 = onView(
                allOf(withId(R.id.fab_edit_task_done), withContentDescription("Floating action button"), isDisplayed()))
        floatingActionButton2.perform(click())

        val linearLayout = onView(
                allOf(withId(R.id.todo_item), isDisplayed()))
        linearLayout.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val floatingActionButton3 = onView(
                allOf(withId(R.id.fab_edit_task), isDisplayed()))
        floatingActionButton3.perform(click())

        val appCompatEditText2 = onView(
                allOf(withId(R.id.add_task_title), withText("title")))
        appCompatEditText2.perform(scrollTo(), replaceText("edit title"))

        val appCompatEditText3 = onView(
                allOf(withId(R.id.add_task_title), withText("edit title"), isDisplayed()))
        appCompatEditText3.perform(closeSoftKeyboard())

        val floatingActionButton4 = onView(
                allOf(withId(R.id.fab_edit_task_done), withContentDescription("Floating action button"), isDisplayed()))
        floatingActionButton4.perform(click())

        val textView = onView(
                allOf(withId(R.id.todo_title), withText("edit title"), isDisplayed()))
        textView.check(matches(withText("edit title")))
    }
}
