package ru.vitalydemidov.newsapp.articles;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.vitalydemidov.newsapp.base.BasePresenterImpl;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.source.NewsDataSource;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;

class ArticlesPresenter extends BasePresenterImpl<ArticlesContract.View>
        implements ArticlesContract.Presenter {

    @NonNull
    private final String mSourceId;


    @NonNull
    private Sort mSort;


    @Nullable
    private ArticlesContract.View mArticlesView;


    ArticlesPresenter(@NonNull String sourceId,
                      @NonNull NewsDataSource newsRepository,
                      @NonNull BaseSchedulerProvider schedulerProvider) {
        super(newsRepository, schedulerProvider);
        mSourceId = sourceId;
        mSort = Sort.TOP;
    }


    @Override
    public void attachView(@NonNull ArticlesContract.View view) {
        mArticlesView = view;
    }


    @Override
    public void detachView() {
        mArticlesView = null;
        mCompositeDisposable.clear();
    }


    public void loadArticles() {
        if (mArticlesView == null) {
            return;
        }

        mArticlesView.showLoadingProgress();

        mCompositeDisposable.add(
            mNewsRepository.getArticles(mSourceId, mSort.getTitle())
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doOnTerminate(mArticlesView::hideLoadingProgress)
                .subscribe(
                        // onNext
                        mArticlesView::showArticles,
                        // onError
                        throwable -> mArticlesView.showLoadingError()
                )
        );
    }


    @Override
    public void openArticle(@NonNull Article article) {
        if (mArticlesView != null) {
            mArticlesView.showArticleDetails(article);
        }
    }


    @NonNull
    @Override
    public Sort getSort() {
        return mSort;
    }


    @Override
    public void setSort(@NonNull Sort sort) {
        mSort = sort;
    }

}
