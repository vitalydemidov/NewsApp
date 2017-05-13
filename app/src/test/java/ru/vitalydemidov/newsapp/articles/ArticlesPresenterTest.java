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
import ru.vitalydemidov.newsapp.data.source.NewsDataSource;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;
import ru.vitalydemidov.newsapp.util.schedulers.TrampolineSchedulerProvider;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.same;
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
    private static final Sort DEFAULT_SORT = Sort.TOP;
    private static  final Sort NOT_DEFAULT_SORT = Sort.POPULAR;


    @Mock
    private ArticlesContract.View mArticlesViewMock;


    @Mock
    private NewsDataSource mNewsRepositoryMock;


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
    public void clickOnArticle_showArticleDetails() {
        // Stubbed article
        Article selectedArticle = ARTICLES.get(0);

        // When a article was selected
        mArticlesPresenter.openArticle(selectedArticle);

        // Then selected article's details is shown
        verify(mArticlesViewMock).showArticleDetails(same(selectedArticle));
    }


    @Test
    public void setSortAndCheck() {
        mArticlesPresenter.setSort(NOT_DEFAULT_SORT);
        assertThat(mArticlesPresenter.getSort(), equalTo(NOT_DEFAULT_SORT));
    }


    @Test
    public void checkDefaultSortValue() {
        // default sort value must equal to Sort.TOP
        assertThat(mArticlesPresenter.getSort(), equalTo(DEFAULT_SORT));
    }


    @Test
    public void loadArticlesFromRepositoryAndLoadIntoView() {
        when(mNewsRepositoryMock.getArticles(anyString(), anyString()))
                .thenReturn(Observable.just(ARTICLES));

        mArticlesPresenter.setSort(NOT_DEFAULT_SORT);
        mArticlesPresenter.loadArticles();

        verify(mArticlesViewMock).showLoadingProgress();
        verify(mNewsRepositoryMock).getArticles(SOURCE_ID, NOT_DEFAULT_SORT.getTitle());
        verify(mArticlesViewMock).hideLoadingProgress();
        verify(mArticlesViewMock).showArticles(ARTICLES);
    }


    @Test
    public void loadArticlesFromRepositoryWithError() {
        when(mNewsRepositoryMock.getArticles(anyString(), anyString()))
                .thenReturn(Observable.error(new RuntimeException()));

        mArticlesPresenter.setSort(NOT_DEFAULT_SORT);
        mArticlesPresenter.loadArticles();

        verify(mArticlesViewMock).showLoadingProgress();
        verify(mNewsRepositoryMock).getArticles(SOURCE_ID, NOT_DEFAULT_SORT.getTitle());
        verify(mArticlesViewMock).hideLoadingProgress();
        verify(mArticlesViewMock).showLoadingError();
    }


    @Test
    public void loadArticlesFromRepositoryWithErrorBecauseViewIsNull() {
        when(mNewsRepositoryMock.getArticles(anyString(), anyString()))
                .thenReturn(Observable.just(ARTICLES));

        mArticlesPresenter.detachView();
        mArticlesPresenter.setSort(NOT_DEFAULT_SORT);
        mArticlesPresenter.loadArticles();

        verify(mArticlesViewMock, times(0)).showLoadingProgress();
        verify(mNewsRepositoryMock, times(0)).getArticles(SOURCE_ID, NOT_DEFAULT_SORT.getTitle());
        verify(mArticlesViewMock, times(0)).hideLoadingProgress();
        verify(mArticlesViewMock, times(0)).showArticles(ARTICLES);
    }

}