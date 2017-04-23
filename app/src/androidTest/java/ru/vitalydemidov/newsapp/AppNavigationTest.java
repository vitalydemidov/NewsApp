package ru.vitalydemidov.newsapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.vitalydemidov.newsapp.sources.SourcesActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.close;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
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
                .check(matches(isClosed()));

        // Open Drawer
        onView(withId(R.id.drawer_layout))
                .perform(open());

        // Check if drawer is open
        onView(withId(R.id.drawer_layout))
                .check(matches(isOpen()));
    }


    @Test
    public void clickOnAndroidHomeIcon_ClosesNavigation() {
        // Check that left Drawer is closed at startup
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed()));

        // Open Drawer
        onView(withId(R.id.drawer_layout))
                .perform(open());

        // Check if Drawer is open
        onView(withId(R.id.drawer_layout))
                .check(matches(isOpen()));

        // Close Drawer
        onView(withId(R.id.drawer_layout))
                .perform(close());

        // Check if Drawer is closed via back button
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed()));
    }


    @Test
    public void clickNavigationViewItem_ChangesToolbarTitle() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed()))
                .perform(open())
                .check(matches(isOpen()));

        // Select 'Sport' category in NavigationView
        onView(withId(R.id.navigation_view))
                .perform(navigateTo(R.id.navigation_view_category_sport));

        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText(R.string.navigation_view_category_sport)));

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed()));
    }

}
