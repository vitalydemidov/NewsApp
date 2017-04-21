package ru.vitalydemidov.newsapp;

import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.vitalydemidov.newsapp.sources.SourcesActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AppNavigationTest {

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     *
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<SourcesActivity> mActivityTestRule =
            new ActivityTestRule<>(SourcesActivity.class);


    @Test
    public void clickOnAndroidHomeIcon_OpensNavigation() {
        // Check that left drawer is closed at startup
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START))); // Left Drawer should be closed.

        // Open Drawer
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());

        // Check if drawer is open
        onView(withId(R.id.drawer_layout))
                .check(matches(isOpen(Gravity.START))); // Left drawer is open open.
    }


    @Test
    public void clickOnAndroidHomeIcon_ClosesNavigation() {
        // Check that left Drawer is closed at startup
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)));

        // Open Drawer
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());

        // Check if Drawer is open
        onView(withId(R.id.drawer_layout))
                .check(matches(isOpen(Gravity.START)));

        // Close Drawer
        Espresso.pressBack();

        // Check if Drawer is closed via back button
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)));
    }

}
