package ru.vitalydemidov.newsapp.articles;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.source.NewsDataSource;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;
import ru.vitalydemidov.newsapp.util.schedulers.TrampolineSchedulerProvider;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the implementation of {@link ArticlesViewModel}
 */
public class ArticlesViewModelTest {

    private static final List<Article> ARTICLES_WITH_DEFAULT_SORT = Arrays.asList(
            new Article("mock1 with default sort"),
            new Article("mock2 with default sort"),
            new Article("mock3 with default sort")
    );


    private static final List<Article> ARTICLES_WITH_NON_DEFAULT_SORT = Arrays.asList(
            new Article("mock1 with non default sort"),
            new Article("mock2 with non default sort"),
            new Article("mock3 with non default sort")
    );


    private static final String SOURCE_ID = "mockId";


    private static final Sort DEFAULT_SORT = Sort.TOP;


    private static  final Sort NON_DEFAULT_SORT = Sort.POPULAR;


    @Mock
    private NewsDataSource mNewsRepositoryMock;


    private BaseSchedulerProvider mSchedulerProvider;


    private ArticlesViewModel mArticlesViewModel;


    @Before
    public void setUp() {
        // Inject the mock dependencies
        MockitoAnnotations.initMocks(this);

        // Make the sure that all schedulers are immediate.
        mSchedulerProvider = new TrampolineSchedulerProvider();

        // Get a reference to the class under test
        mArticlesViewModel = new ArticlesViewModel(SOURCE_ID, mNewsRepositoryMock, mSchedulerProvider);
    }


    @Test
    public void loadArticles_doesNotEmit_whenNoSubscribers() {
        TestObserver<List<Article>> testObserver = new TestObserver<>();

        // Set sort but did not subscribe before
        mArticlesViewModel.setSort(NON_DEFAULT_SORT);

        testObserver.assertNoErrors();
        testObserver.assertNoValues();
    }


    @Test
    public void loadArticles_emitsCorrectArticles_whenDefaultSortSet() {
        when(mNewsRepositoryMock.getArticles(SOURCE_ID, DEFAULT_SORT.getTitle()))
                .thenReturn(Observable.just(ARTICLES_WITH_DEFAULT_SORT));

        TestObserver<List<Article>> testObserver = new TestObserver<>();

        mArticlesViewModel.loadArticles()
                .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertValue(ARTICLES_WITH_DEFAULT_SORT);
    }


    @Test
    public void loadArticles_emitsCorrectArticles_whenNonDefaultSortSet() {
        when(mNewsRepositoryMock.getArticles(SOURCE_ID, NON_DEFAULT_SORT.getTitle()))
                .thenReturn(Observable.just(ARTICLES_WITH_NON_DEFAULT_SORT));

        TestObserver<List<Article>> testObserver = new TestObserver<>();

        // Set non default filtering
        mArticlesViewModel.setSort(NON_DEFAULT_SORT);

        mArticlesViewModel.loadArticles()
                .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertValue(ARTICLES_WITH_NON_DEFAULT_SORT);
    }


    @Test
    public void loadArticles_emitsError_whenErrorOccurs() {
        RuntimeException exception = new RuntimeException();
        when(mNewsRepositoryMock.getArticles(any(), any()))
                .thenReturn(Observable.error(exception));

        TestObserver<List<Article>> testObserver = new TestObserver<>();

        mArticlesViewModel.loadArticles()
                .subscribe(testObserver);

        testObserver.assertError(exception);
    }


    @Test
    public void setSort_whenNonDefaultSortSet() {
        mArticlesViewModel.setSort(NON_DEFAULT_SORT);
        assertThat(mArticlesViewModel.getSort(), equalTo(NON_DEFAULT_SORT));
    }


    @Test
    public void getSort_returnsCorrectDefaultSort() {
        // Default sort value must equal to Sort.TOP
        assertThat(mArticlesViewModel.getSort(), equalTo(DEFAULT_SORT));
    }

}