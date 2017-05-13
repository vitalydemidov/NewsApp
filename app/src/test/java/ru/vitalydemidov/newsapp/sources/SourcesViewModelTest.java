package ru.vitalydemidov.newsapp.sources;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.data.source.NewsDataSource;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;
import ru.vitalydemidov.newsapp.util.schedulers.TrampolineSchedulerProvider;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class SourcesViewModelTest {

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
    private NewsDataSource mNewsRepositoryMock;


    private BaseSchedulerProvider mSchedulerProvider;


    private SourcesViewModel mSourcesViewModel;


    @Before
    public void setUp() {
        // Inject the mock dependencies
        MockitoAnnotations.initMocks(this);

        // Make the sure that all schedulers are immediate.
        mSchedulerProvider = new TrampolineSchedulerProvider();

        // Get a reference to the class under test
        mSourcesViewModel = new SourcesViewModel(mNewsRepositoryMock, mSchedulerProvider);
    }


    @Test
    public void testLoadSources_doesNotEmit_noSubscribers() {
        TestObserver<List<Source>> testObserver = new TestObserver<>();

        FilteringContainer filtering = new FilteringContainer();
        filtering.setCategoryFiltering(NON_DEFAULT_CATEGORY_FILTERING);
        filtering.setLanguageFiltering(NON_DEFAULT_LANGUAGE_FILTERING);
        filtering.setCountryFiltering(NON_DEFAULT_COUNTRY_FILTERING);

        mSourcesViewModel.setFiltering(filtering);

        testObserver.assertNoErrors();
        testObserver.assertNoValues();
    }


    @Test
    public void testLoadSources_emitsCorrectSourcesForDefaultFiltering() {
        Mockito.when(mNewsRepositoryMock.getSources(
                DEFAULT_CATEGORY_FILTERING.getTitle(),
                DEFAULT_LANGUAGE_FILTERING.getTitle(),
                DEFAULT_COUNTRY_FILTERING.getTitle()))
                .thenReturn(Observable.just(SOURCES_WITH_DEFAULT_FILTERING));

        TestObserver<List<Source>> testObserver = new TestObserver<>();

        mSourcesViewModel.loadSources()
                .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertValue(SOURCES_WITH_DEFAULT_FILTERING);
    }


    @Test
    public void testLoadSources_emitsCorrectSourcesForNonDefaultFiltering() {
        Mockito.when(mNewsRepositoryMock.getSources(
                NON_DEFAULT_CATEGORY_FILTERING.getTitle(),
                NON_DEFAULT_LANGUAGE_FILTERING.getTitle(),
                NON_DEFAULT_COUNTRY_FILTERING.getTitle()))
                .thenReturn(Observable.just(SOURCES_WITH_NON_DEFAULT_FILTERING));

        TestObserver<List<Source>> testObserver = new TestObserver<>();

        FilteringContainer filtering = new FilteringContainer();
        filtering.setCategoryFiltering(NON_DEFAULT_CATEGORY_FILTERING);
        filtering.setLanguageFiltering(NON_DEFAULT_LANGUAGE_FILTERING);
        filtering.setCountryFiltering(NON_DEFAULT_COUNTRY_FILTERING);

        // set non default filtering
        mSourcesViewModel.setFiltering(filtering);

        mSourcesViewModel.loadSources()
                .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertValue(SOURCES_WITH_NON_DEFAULT_FILTERING);
    }


    @Test
    public void testSetFiltering_whenNonDefaultFilteringSet() {
        FilteringContainer filteringContainer = new FilteringContainer();
        filteringContainer.setCategoryFiltering(NON_DEFAULT_CATEGORY_FILTERING);
        filteringContainer.setLanguageFiltering(NON_DEFAULT_LANGUAGE_FILTERING);
        filteringContainer.setCountryFiltering(NON_DEFAULT_COUNTRY_FILTERING);

        mSourcesViewModel.setFiltering(filteringContainer);
        assertThat(mSourcesViewModel.getFiltering(), equalTo(filteringContainer));
    }


    @Test
    public void testGetFiltering_returnCorrectDefaultFiltering() {
        assertThat(mSourcesViewModel.getFiltering().getCategoryFiltering(),
                equalTo(DEFAULT_CATEGORY_FILTERING));
        assertThat(mSourcesViewModel.getFiltering().getLanguageFiltering(),
                equalTo(DEFAULT_LANGUAGE_FILTERING));
        assertThat(mSourcesViewModel.getFiltering().getCountryFiltering(),
                equalTo(DEFAULT_COUNTRY_FILTERING));
    }

}