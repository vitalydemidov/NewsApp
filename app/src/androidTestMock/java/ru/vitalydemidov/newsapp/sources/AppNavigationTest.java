package ru.vitalydemidov.newsapp.sources;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.vitalydemidov.newsapp.R;
import ru.vitalydemidov.newsapp.data.Source;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static ru.vitalydemidov.newsapp.articles.ArticlesActivity.EXTRA_SOURCE;
import static ru.vitalydemidov.newsapp.data.source.remote.NewsRemoteDataSource.SOURCES;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AppNavigationTest {

    private static final String PACKAGE_NAME = "ru.vitalydemidov.newsapp.articles";


    @Rule
    public IntentsTestRule<SourcesActivity> mIntentsTestRule =
            new IntentsTestRule<>(SourcesActivity.class);


    @Test
    public void verifySourceSentToArticleActivity() {
        int position = 1;
        Source expectedSource = SOURCES.get(position);

        onView(withId(R.id.sources_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));

        intended(allOf(
//                    hasComponent(hasShortClassName(".ArticlesActivity")),
//                    toPackage(PACKAGE_NAME),
                    hasExtra(Matchers.equalTo(EXTRA_SOURCE), Matchers.equalTo(expectedSource))
                )
        );

    }

}
