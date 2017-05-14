package ru.vitalydemidov.newsapp.articles;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.vitalydemidov.newsapp.R;
import ru.vitalydemidov.newsapp.data.Source;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ArticlesActivityTest {

    private static final Source MOCK_SOURCE = new Source("Mock source name");

    @Rule
    public ActivityTestRule<ArticlesActivity> mArticlesActivityTestRule =
            new ActivityTestRule<>(ArticlesActivity.class, true, false);


    @Before
    public void intentWithStubbedSource() {
        // Lazily start the Activity from the ActivityTestRule this time to inject the start Intent
        Intent startIntent = new Intent();
        startIntent.putExtra(ArticlesActivity.EXTRA_SOURCE, MOCK_SOURCE);
        mArticlesActivityTestRule.launchActivity(startIntent);
    }


    @Test
    public void articlesActivityOpenedByIntent_correctSourceReceived() {
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText(MOCK_SOURCE.getName())));
    }

}