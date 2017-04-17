package ru.vitalydemidov.newsapp.articles;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.vitalydemidov.newsapp.base.BasePresenterImpl;
import ru.vitalydemidov.newsapp.data.source.NewsRepository;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;

class ArticlesPresenter extends BasePresenterImpl implements ArticlesContract.Presenter {

    @NonNull
    private final String mSourceId;


    @NonNull
    private final ArticlesContract.View mArticlesView;


    @Inject
    ArticlesPresenter(@NonNull String sourceId,
                      @NonNull ArticlesContract.View articlesView,
                      @NonNull NewsRepository newsRepository,
                      @NonNull BaseSchedulerProvider schedulerProvider) {
        super(newsRepository, schedulerProvider);
        mSourceId = sourceId;
        mArticlesView = articlesView;
        mArticlesView.setPresenter(this);
    }


    @Override
    public void subscribe() {
        loadArticles();
    }


    public void loadArticles() {
        mArticlesView.showLoadingProgress();

        mCompositeDisposable.add(
            mNewsRepository.getArticles(mSourceId)
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

}
