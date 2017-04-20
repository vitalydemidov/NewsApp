package ru.vitalydemidov.newsapp.articles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.source.NewsRepository;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;
import ru.vitalydemidov.newsapp.util.schedulers.TrampolineSchedulerProvider;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the implementation of {@link ArticlesPresenter}.
 */
public class ArticlesPresenterTest {

    private static final List<Article> ARTICLES = Arrays.asList(
            new Article("mock1"),
            new Article("mock2"),
            new Article("mock3")
    );
    private static final String SOURCE_ID = "mockId";


    @Mock
    private ArticlesContract.View mArticlesViewMock;


    @Mock
    private NewsRepository mNewsRepositoryMock;


    private BaseSchedulerProvider mSchedulerProvider;


    private ArticlesPresenter mArticlesPresenter;


    @Before
    public void setupSourcesPresenter() {
        // Inject the mock dependencies
        MockitoAnnotations.initMocks(this);

        // Make the sure that all schedulers are immediate.
        mSchedulerProvider = new TrampolineSchedulerProvider();

        // Get a reference to the class under test
        mArticlesPresenter = new ArticlesPresenter(SOURCE_ID, mNewsRepositoryMock, mSchedulerProvider);
        mArticlesPresenter.attachView(mArticlesViewMock);
    }


    @After
    public void release() {
        mArticlesPresenter.detachView();
    }


    @Test
    public void loadArticlesFromRepositoryAndLoadIntoView() {
        when(mNewsRepositoryMock.getArticles(anyString()))
                .thenReturn(Observable.just(ARTICLES));

        mArticlesPresenter.loadArticles();

        verify(mArticlesViewMock).showLoadingProgress();
        verify(mNewsRepositoryMock).getArticles(SOURCE_ID);
        verify(mArticlesViewMock).hideLoadingProgress();
        verify(mArticlesViewMock).showArticles(ARTICLES);
    }


    @Test
    public void loadArticlesFromRepositoryWithError() {
        when(mNewsRepositoryMock.getArticles(anyString()))
                .thenReturn(Observable.error(new RuntimeException()));

        mArticlesPresenter.loadArticles();

        verify(mArticlesViewMock).showLoadingProgress();
        verify(mNewsRepositoryMock).getArticles(SOURCE_ID);
        verify(mArticlesViewMock).hideLoadingProgress();
        verify(mArticlesViewMock).showLoadingError();
    }


    @Test
    public void loadArticlesFromRepositoryWithErrorBecauseViewIsNull() {
        when(mNewsRepositoryMock.getArticles(anyString()))
                .thenReturn(Observable.just(ARTICLES));

        mArticlesPresenter.detachView();
        mArticlesPresenter.loadArticles();

        verify(mArticlesViewMock, times(0)).showLoadingProgress();
        verify(mNewsRepositoryMock, times(0)).getArticles(SOURCE_ID);
        verify(mArticlesViewMock, times(0)).hideLoadingProgress();
        verify(mArticlesViewMock, times(0)).showArticles(ARTICLES);
    }

}