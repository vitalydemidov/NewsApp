package ru.vitalydemidov.newsapp.sources;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.data.source.NewsRepository;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the implementation of {@link SourcesPresenter}
 */
public class SourcesFragmentTest {

    @Mock
    private SourcesContract.View mSourcesView;


    @Mock
    private NewsRepository mNewsRepository;


    private SourcesPresenter mSourcesPresenter;


    @Before
    public void setupSourcesPresenter() {
        // Inject the mock dependencies
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mSourcesPresenter = new SourcesPresenter(mSourcesView, mNewsRepository);
    }


    @Test
    public void clickOnSource_ShowsArticlesForSourceUi() {
        // Stubbed source
        Source selectedSource = new Source();

        // When a source was selected
        mSourcesPresenter.openArticlesForSource(selectedSource);

        // Then articles for selected source is shown
        verify(mSourcesView).showArticlesForSourceUi(same(selectedSource));
    }

}