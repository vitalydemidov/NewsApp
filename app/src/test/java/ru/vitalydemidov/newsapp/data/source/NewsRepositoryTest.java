package ru.vitalydemidov.newsapp.data.source;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import ru.vitalydemidov.newsapp.articles.Sort;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.sources.SourcesCategoryFiltering;
import ru.vitalydemidov.newsapp.sources.SourcesCountryFiltering;
import ru.vitalydemidov.newsapp.sources.SourcesLanguageFiltering;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the implementation of {@link NewsRepository}
 */
public class NewsRepositoryTest {

    private static final SourcesCategoryFiltering CATEGORY_FILTERING
            = SourcesCategoryFiltering.CATEGORY_ALL;


    private static final SourcesLanguageFiltering LANGUAGE_FILTERING
            = SourcesLanguageFiltering.LANGUAGE_ALL;


    private static final SourcesCountryFiltering COUNTRY_FILTERING
            = SourcesCountryFiltering.COUNTRY_ALL;


    private static final String SOURCE_ID = "test_source_id";


    private static final Sort SORT = Sort.TOP;


    private static final List<Source> SOURCES = Arrays.asList(
            new Source("mock1"),
            new Source("mock2"),
            new Source("mock3")
    );


    private static final List<Article> ARTICLES = Arrays.asList(
            new Article("mock1"),
            new Article("mock2"),
            new Article("mock3")
    );


    private NewsRepository mNewsRepository;


    @Mock
    private NewsDataSource mNewsRemoteDataSource;


    @Mock
    private NewsDataSource mNewsLocalDataSource;


    @Before
    public void setUp() {
        // Inject the mock dependencies
        MockitoAnnotations.initMocks(this);

        mNewsRepository = new NewsRepository(mNewsRemoteDataSource, mNewsLocalDataSource);
    }


    @Test
    public void getSources_onlyFromRemoteDataSource() {
        TestObserver<List<Source>> testObserver = new TestObserver<>();

        when(mNewsRemoteDataSource.getSources(any(), any(), any()))
                .thenReturn(Observable.just(SOURCES));

        mNewsRepository.getSources(
                CATEGORY_FILTERING.getTitle(),
                LANGUAGE_FILTERING.getTitle(),
                COUNTRY_FILTERING.getTitle()
        )
        .subscribe(testObserver);

        // Verify that remote data source was called and one time only
        verify(mNewsRemoteDataSource, times(1)).getSources(
                CATEGORY_FILTERING.getTitle(),
                LANGUAGE_FILTERING.getTitle(),
                COUNTRY_FILTERING.getTitle());

        // Verify that local data source was not called
        verify(mNewsLocalDataSource, times(0)).getSources(
                CATEGORY_FILTERING.getTitle(),
                LANGUAGE_FILTERING.getTitle(),
                COUNTRY_FILTERING.getTitle());

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(SOURCES);
    }


    @Test
    public void getArticles_onlyFromRemoteDataSource() {
        TestObserver<List<Article>> testObserver = new TestObserver<>();

        when(mNewsRemoteDataSource.getArticles(anyString(), anyString()))
                .thenReturn(Observable.just(ARTICLES));

        mNewsRepository.getArticles(SOURCE_ID, SORT.getTitle())
                .subscribe(testObserver);

        // Verify that remote data source was called and one time only
        verify(mNewsRemoteDataSource, times(1)).getArticles(SOURCE_ID, SORT.getTitle());

        // Verify that local data source was not called
        verify(mNewsLocalDataSource, times(0)).getArticles(SOURCE_ID, SORT.getTitle());

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(ARTICLES);
    }

}