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
    private SourcesCategoryFiltering mCurrentCategoryFiltering = SourcesCategoryFiltering.CATEGORY_ALL;


    @NonNull
    private SourcesLanguageFiltering mCurrentLanguageFiltering = SourcesLanguageFiltering.LANGUAGE_ALL;


    @NonNull
    private SourcesCountryFiltering mCurrentCountryFiltering = SourcesCountryFiltering.COUNTRY_ALL;


    SourcesPresenter(@NonNull NewsDataSource newsRepository,
                     @NonNull BaseSchedulerProvider schedulerProvider) {
        super(newsRepository, schedulerProvider);
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
                        mCurrentCategoryFiltering.getTitle(),
                        mCurrentLanguageFiltering.getTitle(),
                        mCurrentCountryFiltering.getTitle()
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
    public void setCategoryFiltering(@Nullable SourcesCategoryFiltering category) {
        mCurrentCategoryFiltering = category != null ? category : SourcesCategoryFiltering.CATEGORY_ALL;
    }


    @Override
    public void setLanguageFiltering(@Nullable SourcesLanguageFiltering language) {
        mCurrentLanguageFiltering = language != null ? language : SourcesLanguageFiltering.LANGUAGE_ALL;
    }


    @Override
    public void setCountryFiltering(@Nullable SourcesCountryFiltering country) {
        mCurrentCountryFiltering = country != null ? country : SourcesCountryFiltering.COUNTRY_ALL;
    }


    @NonNull
    @Override
    public SourcesCategoryFiltering getCategoryFiltering() {
        return mCurrentCategoryFiltering;
    }


    @NonNull
    @Override
    public SourcesLanguageFiltering getLanguageFiltering() {
        return mCurrentLanguageFiltering;
    }


    @NonNull
    @Override
    public SourcesCountryFiltering getCountryFiltering() {
        return mCurrentCountryFiltering;
    }

}
