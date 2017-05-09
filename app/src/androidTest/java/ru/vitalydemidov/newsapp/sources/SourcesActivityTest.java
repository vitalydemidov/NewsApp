package ru.vitalydemidov.newsapp.sources;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.vitalydemidov.newsapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.close;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SourcesActivityTest {

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
    public void drawerActions_OpensNavigation() {
        onView(withId(R.id.drawer_layout))      // Find Drawer
                .check(matches(isClosed()))     // Check that left Drawer is closed at startup
                .perform(open())                // Open Drawer
                .check(matches(isOpen()));      // Check if Drawer is open
    }


    @Test
    public void drawerActions_ClosesNavigation() {
        onView(withId(R.id.drawer_layout))      // Find Drawer
                .check(matches(isClosed()));    // Check that left Drawer is closed at startup

        onView(withContentDescription(R.string.navigation_drawer_open))     // Find Home icon
                .perform(click());              // Click on Home icon

        onView(withId(R.id.drawer_layout))      // Find Drawer
                .check(matches(isOpen()));      // Check if Drawer is open

        pressBack();                            // Press back button and close Drawer

        onView(withId(R.id.drawer_layout))      // Find Drawer
                .check(matches(isClosed()));    // Check if Drawer is closed
    }


    @Test
    public void clickOnAndroidHomeIcon_OpensNavigation() {
        onView(withId(R.id.drawer_layout))      // Find Drawer
                .check(matches(isClosed()));    // Check that left Drawer is closed at startup

        onView(withContentDescription(R.string.navigation_drawer_open))     // Find Home icon
                .perform(click());              // Click on Home icon

        onView(withId(R.id.drawer_layout))      // Find Drawer
                .check(matches(isOpen()));      // Check if Drawer is open
    }


    @Test
    public void clickBackButton_ClosesNavigation() {
        onView(withId(R.id.drawer_layout))      // Find Drawer
                .check(matches(isClosed()))     // Check that left Drawer is closed at startup
                .perform(open())                // Open Drawer
                .check(matches(isOpen()))       // Check if Drawer is open
                .perform(close())               // Close Drawer
                .check(matches(isClosed()));    // Check if Drawer is closed
    }


    @Test
    public void clickNavigationViewItem_ChangesToolbarTitle() {
        onView(withId(R.id.drawer_layout))      // Find Drawer
                .check(matches(isClosed()))     // Check that left Drawer is closed at startup
                .perform(open())                // Open Drawer
                .check(matches(isOpen()));      // Check if Drawer is open

        // Select 'Sport' category in NavigationView
        onView(withId(R.id.navigation_view))
                .perform(navigateTo(R.id.navigation_view_category_sport));

        // Check that Toolbar title was changed
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText(R.string.navigation_view_category_sport)));
    }

}
