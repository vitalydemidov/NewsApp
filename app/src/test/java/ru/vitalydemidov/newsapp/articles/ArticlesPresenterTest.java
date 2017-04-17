package ru.vitalydemidov.newsapp.articles;

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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the implementation of {@link ArticlesPresenter}.
 */
public class ArticlesPresenterTest {

    private static final List<Article> ARTICLES = Arrays.asList(new Article(), new Article(), new Article());
    private static final String SOURCE_ID = "test_source_id";


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
        mArticlesPresenter = new ArticlesPresenter(SOURCE_ID, mArticlesViewMock,
                mNewsRepositoryMock, mSchedulerProvider);
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

}