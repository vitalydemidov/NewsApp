package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.vitalydemidov.newsapp.base.BasePresenterImpl;
import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.data.source.NewsDataSource;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;

class SourcesPresenter extends BasePresenterImpl<SourcesContract.View>
        implements SourcesContract.Presenter {

    @Nullable
    private SourcesContract.View mSourcesView;


    @NonNull
    private FilteringContainer mFilteringContainer;


    SourcesPresenter(@NonNull NewsDataSource newsRepository,
                     @NonNull BaseSchedulerProvider schedulerProvider) {
        super(newsRepository, schedulerProvider);
        mFilteringContainer = new FilteringContainer();
    }


    @Override
    public void attachView(@NonNull SourcesContract.View sourcesView) {
        mSourcesView = sourcesView;
    }


    @Override
    public void detachView() {
        mSourcesView = null;
        mCompositeDisposable.clear();
    }


    @Override
    public void loadSources() {
        if (mSourcesView == null) {
            return;
        }

        mSourcesView.showLoadingProgress();

        mCompositeDisposable.add(
                mNewsRepository.getSources(
                        mFilteringContainer.getCategoryFiltering().getTitle(),
                        mFilteringContainer.getLanguageFiltering().getTitle(),
                        mFilteringContainer.getCountryFiltering().getTitle()
                )
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doOnTerminate(mSourcesView::hideLoadingProgress)
                .subscribe(
                        // onNext
                        mSourcesView::showSources,
                        // onError
                        throwable -> mSourcesView.showLoadingError()
                )
        );
    }


    @Override
    public void openArticlesForSource(@NonNull Source selectedSource) {
        if (mSourcesView != null) {
            mSourcesView.showArticlesForSourceUi(selectedSource);
        }
    }


    @Override
    public void setFiltering(@NonNull FilteringContainer filtering) {
        mFilteringContainer = filtering;
    }


    @NonNull
    @Override
    public FilteringContainer getFiltering() {
        return mFilteringContainer;
    }

}
