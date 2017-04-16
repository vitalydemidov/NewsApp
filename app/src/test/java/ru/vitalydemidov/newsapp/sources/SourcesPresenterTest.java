package ru.vitalydemidov.newsapp.sources;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.data.source.NewsRepository;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;
import ru.vitalydemidov.newsapp.util.schedulers.TrampolineSchedulerProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the implementation of {@link SourcesPresenter}
 */
public class SourcesPresenterTest {

    private static final List<Source> SOURCES = Arrays.asList(new Source(), new Source(), new Source());
    private static final SourcesCategoryFiltering TEST_CATEGORY_FILTERING = SourcesCategoryFiltering.CATEGORY_SPORT;
    private static final SourcesLanguageFiltering TEST_LANGUAGE_FILTERING = SourcesLanguageFiltering.LANGUAGE_ENGLISH;
    private static final SourcesCountryFiltering TEST_COUNTRY_FILTERING = SourcesCountryFiltering.COUNTRY_GERMANY;

    @Mock
    private SourcesContract.View mSourcesView;


    @Mock
    private NewsRepository mNewsRepository;


    private BaseSchedulerProvider mSchedulerProvider;


    private SourcesPresenter mSourcesPresenter;


    @Before
    public void setupSourcesPresenter() {
        // Inject the mock dependencies
        MockitoAnnotations.initMocks(this);

        // Make the sure that all schedulers are immediate.
        mSchedulerProvider = new TrampolineSchedulerProvider();

        // Get a reference to the class under test
        mSourcesPresenter = new SourcesPresenter(mSourcesView, mNewsRepository, mSchedulerProvider);
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


    @Test
    public void loadSourcesFromRepositoryAndLoadIntoView() {
        when(mNewsRepository.getSources(any(), any(), any()))
        .thenReturn(Observable.just(SOURCES));

        mSourcesPresenter.setCategoryFiltering(SourcesCategoryFiltering.CATEGORY_ALL);
        mSourcesPresenter.setLanguageFiltering(SourcesLanguageFiltering.LANGUAGE_ALL);
        mSourcesPresenter.setCountryFiltering(SourcesCountryFiltering.COUNTRY_ALL);
        mSourcesPresenter.loadSources();

        verify(mSourcesView).showLoadingProgress(true);

        verify(mNewsRepository).getSources(
                SourcesCategoryFiltering.CATEGORY_ALL.getTitle(),
                SourcesLanguageFiltering.LANGUAGE_ALL.getTitle(),
                SourcesCountryFiltering.COUNTRY_ALL.getTitle());

        verify(mSourcesView).showLoadingProgress(false);
        verify(mSourcesView).showSources(SOURCES);
    }


    @Test
    public void setCategoryFilteringAndCheck() {
        mSourcesPresenter.setCategoryFiltering(TEST_CATEGORY_FILTERING);
        assertThat(mSourcesPresenter.getCategoryFiltering(), equalTo(TEST_CATEGORY_FILTERING));
    }


    @Test
    public void setLanguageFilteringAndCheck() {
        mSourcesPresenter.setLanguageFiltering(TEST_LANGUAGE_FILTERING);
        assertThat(mSourcesPresenter.getLanguageFiltering(), equalTo(TEST_LANGUAGE_FILTERING));
    }


    @Test
    public void setCountryFilteringAndCheck() {
        mSourcesPresenter.setCountryFiltering(TEST_COUNTRY_FILTERING);
        assertThat(mSourcesPresenter.getCountryFiltering(), equalTo(TEST_COUNTRY_FILTERING));
    }

}