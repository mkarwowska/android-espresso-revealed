package com.example.android.architecture.blueprints.todoapp.test.chapter8

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openContextualActionModeOverflowMenu
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.*
import android.support.v7.widget.LinearLayoutCompat
import android.widget.ImageButton
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import com.example.android.architecture.blueprints.todoapp.test.chapter3.click
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Demonstrates [UiWatcher] functionality in test.
 */
@RunWith(AndroidJUnit4::class)
class UiAutomatorUiWatcherTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(TasksActivity::class.java)

    @Before
    // Register dialog watcher.
    fun before() = registerShareAppDialogWatcher()
    //fun before() = registerStatisticsDialogWatcher()

    @After
    fun after() = removeShareAppDialogWatcher()
    //fun after() = uiDevice.removeWatcher("StatisticsDialog")

    @Test
    fun dismissesStatisticsDialogUsingWatcher() {

        val toolbar =
                "com.example.android.architecture.blueprints.todoapp.mock:id/toolbar"
        val menuDrawer =
                "com.example.android.architecture.blueprints.todoapp.mock:id/design_navigation_view"

        // Open menu drawer.
        uiDevice.findObject(
                UiSelector().resourceId(toolbar))
                .getChild(UiSelector().className(ImageButton::class.java.name))
                .click()

        // Open Statistics section.
        uiDevice.findObject(
                UiSelector()
                        .resourceId(menuDrawer)
                        .childSelector(
                                UiSelector()
                                        .className(LinearLayoutCompat::class.java.name).instance(1)))
                .click()

        /**
         * Locate Statistics label based on the view id.
         * At this moment watcher kicks in and dismissed dialog by clicking on OK button.
         */
        val statistics: UiObject = uiDevice.findObject(UiSelector()
                .resourceId("com.example.android.architecture.blueprints.todoapp.mock:id/statistics"))

        // Assert expected text is shown.
        assertTrue("Expected statistics label: \"You have no tasks.\" but got: ${statistics.text}",
                statistics.text == "You have no tasks.")
    }

    // Exercise 20.3 and 20.4
    @Test
    fun openGmailFromShareSection() {
        val shareMenuItem = onView(allOf(withText("Share"), withId(R.id.title)))

        // Open a contextual menu in the TO-DO list toolbar
        openContextualActionModeOverflowMenu()

        // Click on share button
        shareMenuItem.click()

        // UiWatcher is start now
        val gmailWelcomeText: UiObject = uiDevice.findObject(UiSelector().text("Welcome to Gmail"))

        // Assert expected text is shown.
        assertTrue("Expected welcome gmail text: \"Welcome to Gmail\" but got: ${gmailWelcomeText.text}",
                gmailWelcomeText.text == "Welcome to Gmail")

    }

    /**
     * Register Statistics dialog watcher that will monitor dialog presence.
     * Dialog will be dismissed when appeared by clicking on OK button.
     */
    private fun registerStatisticsDialogWatcher() {
        uiDevice.registerWatcher("StatisticsDialog", statisticsDialogWatcher)

        // Run registered watcher.
        uiDevice.runWatchers()
    }

    private fun registerShareAppDialogWatcher() {
        uiDevice.registerWatcher("ShareDialog", appToShareChooserDialogWatcher)

        // Run registered watcher.
        uiDevice.runWatchers()
    }

    /**
     * Remove previously registered Statistics dialog.
     */
    private fun removeStatisticsDialogWatcher() {
        uiDevice.removeWatcher("StatisticsDialog")
    }

    private fun removeShareAppDialogWatcher() {
        uiDevice.removeWatcher("ShareDialog")
    }

    companion object {
        private val instrumentation = InstrumentationRegistry.getInstrumentation()
        private val uiDevice: UiDevice = UiDevice.getInstance(instrumentation)

        val statisticsDialogWatcher = UiWatcher {
            val okDialogButton = uiDevice.findObject(By.res("android:id/button1"))
            if (null != okDialogButton) {
                okDialogButton.click()
                return@UiWatcher true
            }
            false
        }

        val appToShareChooserDialogWatcher = UiWatcher {
            val gmailAppText = uiDevice.findObject(UiSelector().text("Gmail"))
            if (null != gmailAppText) {
                gmailAppText.click()
                return@UiWatcher true
            }
            false
        }
    }

}
