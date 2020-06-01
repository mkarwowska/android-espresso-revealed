package com.example.android.architecture.blueprints.todoapp.tasks


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
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
class TasksActivityTest2 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(TasksActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.CAMERA")

    @Test
    fun tasksActivityTest2() {
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
        appCompatEditText.perform(scrollTo(), replaceText("title"), closeSoftKeyboard())

        val appCompatImageButton = onView(
                allOf(withId(R.id.makePhoto),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                        0),
                                0)))
        appCompatImageButton.perform(scrollTo(), click())

        val button = onView(
                allOf(withId(R.id.picture), withText("Take photo"),
                        childAtPosition(
                                allOf(withId(R.id.camera_preview),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()))
        button.perform(click())

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

        val textView = onView(
                allOf(withId(R.id.filteringLabel), withText("All TO-DOs"),
                        childAtPosition(
                                allOf(withId(R.id.tasksLL),
                                        childAtPosition(
                                                withId(R.id.tasksContainer),
                                                0)),
                                0),
                        isDisplayed()))
        textView.check(matches(withText("All TO-DOs")))
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
