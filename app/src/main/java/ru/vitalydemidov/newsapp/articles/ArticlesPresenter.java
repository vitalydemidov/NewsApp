package ru.vitalydemidov.newsapp.articles;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vitalydemidov.newsapp.data.source.NewsRepository;

class ArticlesPresenter implements ArticlesContract.Presenter {

    @NonNull
    private String mSourceId;


    @NonNull
    private final ArticlesContract.View mArticlesView;


    @NonNull
    private final NewsRepository mNewsRepository;


    @NonNull
    private final CompositeDisposable mCompositeDisposable;


    @Inject
    ArticlesPresenter(@NonNull String sourceId,
                      @NonNull ArticlesContract.View articlesView,
                      @NonNull NewsRepository newsRepository) {
        mSourceId = sourceId;
        mArticlesView = articlesView;
        mArticlesView.setPresenter(this);
        mNewsRepository = newsRepository;
        mCompositeDisposable = new CompositeDisposable();
    }


    @Override
    public void subscribe() {
        loadArticles(mSourceId);
    }


    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }


    public void loadArticles(@NonNull String sourceId) {
        mCompositeDisposable.add(
            mNewsRepository.getArticles(sourceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        mArticlesView::showArticles,
                        // onError
                        throwable -> mArticlesView.showLoadingError(),
                        // onComplete
                        () -> mArticlesView.showLoadingProgress(false)
                )
        );
    }

}
