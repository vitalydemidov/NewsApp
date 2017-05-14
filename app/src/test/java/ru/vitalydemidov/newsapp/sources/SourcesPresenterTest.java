package ru.vitalydemidov.newsapp.sources;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.data.source.NewsDataSource;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;
import ru.vitalydemidov.newsapp.util.schedulers.TrampolineSchedulerProvider;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the implementation of {@link SourcesPresenter}
 */
public class SourcesPresenterTest {

    private static final List<Source> SOURCES_WITH_DEFAULT_FILTERING = Arrays.asList(
            new Source("mock1 with default filtering"),
            new Source("mock2 with default filtering"),
            new Source("mock3 with default filtering")
    );


    private static final List<Source> SOURCES_WITH_NON_DEFAULT_FILTERING = Arrays.asList(
            new Source("mock1 with non default filtering"),
            new Source("mock2 with non default filtering"),
            new Source("mock3 with non default filtering")
    );



    private static final SourcesCategoryFiltering DEFAULT_CATEGORY_FILTERING
            = SourcesCategoryFiltering.CATEGORY_ALL;


    private static final SourcesLanguageFiltering DEFAULT_LANGUAGE_FILTERING
            = SourcesLanguageFiltering.LANGUAGE_ALL;


    private static final SourcesCountryFiltering DEFAULT_COUNTRY_FILTERING
            = SourcesCountryFiltering.COUNTRY_ALL;


    private static final SourcesCategoryFiltering NON_DEFAULT_CATEGORY_FILTERING
            = SourcesCategoryFiltering.CATEGORY_SPORT;


    private static final SourcesLanguageFiltering NON_DEFAULT_LANGUAGE_FILTERING
            = SourcesLanguageFiltering.LANGUAGE_ENGLISH;


    private static final SourcesCountryFiltering NON_DEFAULT_COUNTRY_FILTERING
            = SourcesCountryFiltering.COUNTRY_GERMANY;


    @Mock
    private SourcesContract.View mSourcesViewMock;


    @Mock
    private NewsDataSource mNewsRepositoryMock;


    private BaseSchedulerProvider mSchedulerProvider;


    private SourcesPresenter mSourcesPresenter;


    @Before
    public void setUp() {
        // Inject the mock dependencies
        MockitoAnnotations.initMocks(this);

        // Make the sure that all schedulers are immediate.
        mSchedulerProvider = new TrampolineSchedulerProvider();

        // Get a reference to the class under test
        mSourcesPresenter = new SourcesPresenter(mNewsRepositoryMock, mSchedulerProvider);

        // Attach View to Presenter
        mSourcesPresenter.attachView(mSourcesViewMock);
    }


    @After
    public void release() {
        // Detach View from Presenter
        mSourcesPresenter.detachView();
    }


    @Test
    public void openArticlesForSource_showsArticlesForCorrectSource() {
        // Stubbed source
        Source selectedSource = new Source("STUBBED_SOURCE");

        // When a source was selected
        mSourcesPresenter.openArticlesForSource(selectedSource);

        // Then articles for selected source is shown
        verify(mSourcesViewMock).showArticlesForSourceUi(same(selectedSource));
    }


    @Test
    public void loadSources_loadsCorrectSources_whenDefaultFilteringSet() {
        when(mNewsRepositoryMock.getSources(
                DEFAULT_CATEGORY_FILTERING.getTitle(),
                DEFAULT_LANGUAGE_FILTERING.getTitle(),
                DEFAULT_COUNTRY_FILTERING.getTitle())
        )
        .thenReturn(Observable.just(SOURCES_WITH_DEFAULT_FILTERING));

        // Load sources with default filtering
        mSourcesPresenter.loadSources();

        verify(mSourcesViewMock).showLoadingProgress();

        verify(mNewsRepositoryMock).getSources(
                DEFAULT_CATEGORY_FILTERING.getTitle(),
                DEFAULT_LANGUAGE_FILTERING.getTitle(),
                DEFAULT_COUNTRY_FILTERING.getTitle());

        verify(mSourcesViewMock).hideLoadingProgress();
        verify(mSourcesViewMock).showSources(SOURCES_WITH_DEFAULT_FILTERING);
    }


    @Test
    public void loadSources_loadsCorrectSources_whenNonDefaultFilteringSet() {
        when(mNewsRepositoryMock.getSources(
                NON_DEFAULT_CATEGORY_FILTERING.getTitle(),
                NON_DEFAULT_LANGUAGE_FILTERING.getTitle(),
                NON_DEFAULT_COUNTRY_FILTERING.getTitle())
        )
        .thenReturn(Observable.just(SOURCES_WITH_NON_DEFAULT_FILTERING));

        FilteringContainer nonDefaultFiltering = new FilteringContainer();
        nonDefaultFiltering.setCategoryFiltering(NON_DEFAULT_CATEGORY_FILTERING);
        nonDefaultFiltering.setLanguageFiltering(NON_DEFAULT_LANGUAGE_FILTERING);
        nonDefaultFiltering.setCountryFiltering(NON_DEFAULT_COUNTRY_FILTERING);

        // Set non default filtering
        mSourcesPresenter.setFiltering(nonDefaultFiltering);

        mSourcesPresenter.loadSources();

        verify(mSourcesViewMock).showLoadingProgress();

        verify(mNewsRepositoryMock).getSources(
                NON_DEFAULT_CATEGORY_FILTERING.getTitle(),
                NON_DEFAULT_LANGUAGE_FILTERING.getTitle(),
                NON_DEFAULT_COUNTRY_FILTERING.getTitle());

        verify(mSourcesViewMock).hideLoadingProgress();
        verify(mSourcesViewMock).showSources(SOURCES_WITH_NON_DEFAULT_FILTERING);
    }


    @Test
    public void loadSources_getsError_whenErrorOccurs() {
        when(mNewsRepositoryMock.getSources(any(), any(), any()))
                .thenReturn(Observable.error(new RuntimeException()));

        mSourcesPresenter.loadSources();

        verify(mSourcesViewMock).showLoadingError();

        verify(mNewsRepositoryMock).getSources(any(), any(), any());

        verify(mSourcesViewMock).hideLoadingProgress();
        verify(mSourcesViewMock).showLoadingError();
    }


    @Test
    public void loadSources_doesNotLoadSources_whenViewIsDetached() {
        // Detach View from Presenter
        mSourcesPresenter.detachView();

        mSourcesPresenter.loadSources();

        verify(mSourcesViewMock, times(0)).showLoadingError();

        verify(mNewsRepositoryMock, times(0)).getSources(any(), any(), any());

        verify(mSourcesViewMock, times(0)).hideLoadingProgress();
        verify(mSourcesViewMock, times(0)).showLoadingError();
    }


    @Test
    public void setFiltering_whenNonDefaultFilteringSet() {
        FilteringContainer nonDefaultFiltering = new FilteringContainer();
        nonDefaultFiltering.setCategoryFiltering(NON_DEFAULT_CATEGORY_FILTERING);
        nonDefaultFiltering.setLanguageFiltering(NON_DEFAULT_LANGUAGE_FILTERING);
        nonDefaultFiltering.setCountryFiltering(NON_DEFAULT_COUNTRY_FILTERING);

        mSourcesPresenter.setFiltering(nonDefaultFiltering);
        assertThat(mSourcesPresenter.getFiltering(), equalTo(nonDefaultFiltering));
    }


    @Test
    public void getFiltering_returnsCorrectDefaultFiltering() {
        assertThat(mSourcesPresenter.getFiltering().getCategoryFiltering(),
                equalTo(DEFAULT_CATEGORY_FILTERING));
        assertThat(mSourcesPresenter.getFiltering().getLanguageFiltering(),
                equalTo(DEFAULT_LANGUAGE_FILTERING));
        assertThat(mSourcesPresenter.getFiltering().getCountryFiltering(),
                equalTo(DEFAULT_COUNTRY_FILTERING));
    }

}