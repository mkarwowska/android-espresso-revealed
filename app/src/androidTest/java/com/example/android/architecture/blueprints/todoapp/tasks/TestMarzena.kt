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
import android.view.View
import android.view.ViewGroup
import com.example.android.architecture.blueprints.todoapp.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestMarzena {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(TasksActivity::class.java)

    @Test
    fun testMarzena() {
        val floatingActionButton = onView(
                allOf(withId(R.id.fab_add_task), withContentDescription("Add todo"),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withClassName(`is`("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()))
        floatingActionButton.perform(click())

        val appCompatEditText = onView(
                allOf(withId(R.id.add_task_title),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.add_task_scroll_view),
                                        0),
                                0)))
        appCompatEditText.perform(scrollTo(), replaceText("Title"), closeSoftKeyboard())

        val floatingActionButton2 = onView(
                allOf(withId(R.id.fab_edit_task_done), withContentDescription("Floating action button"),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withClassName(`is`("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()))
        floatingActionButton2.perform(click())

        val recyclerView = onView(
                allOf(withId(R.id.tasks_list),
                        childAtPosition(
                                withId(R.id.tasksLL),
                                1)))
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val floatingActionButton3 = onView(
                allOf(withId(R.id.fab_edit_task),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withClassName(`is`("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()))
        floatingActionButton3.perform(click())

        val appCompatEditText2 = onView(
                allOf(withId(R.id.add_task_title), withText("Title"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.add_task_scroll_view),
                                        0),
                                0)))
        appCompatEditText2.perform(scrollTo(), replaceText("edit Title"))

        val appCompatEditText3 = onView(
                allOf(withId(R.id.add_task_title), withText("edit Title"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.add_task_scroll_view),
                                        0),
                                0),
                        isDisplayed()))
        appCompatEditText3.perform(closeSoftKeyboard())

        val floatingActionButton4 = onView(
                allOf(withId(R.id.fab_edit_task_done), withContentDescription("Floating action button"),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withClassName(`is`("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()))
        floatingActionButton4.perform(click())

        val textView = onView(
                allOf(withId(R.id.todo_title), withText("edit Title"),
                        childAtPosition(
                                allOf(withId(R.id.todo_item),
                                        childAtPosition(
                                                withId(R.id.tasks_list),
                                                0)),
                                1),
                        isDisplayed()))
        textView.check(matches(withText("edit Title")))

        val textView2 = onView(
                allOf(withId(R.id.todo_title), withText("edit Title"),
                        childAtPosition(
                                allOf(withId(R.id.todo_item),
                                        childAtPosition(
                                                withId(R.id.tasks_list),
                                                0)),
                                1),
                        isDisplayed()))
        textView2.check(matches(withText("edit Title")))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
