package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.vitalydemidov.newsapp.base.BasePresenterImpl;
import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.data.source.NewsRepository;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;

class SourcesPresenter extends BasePresenterImpl implements SourcesContract.Presenter {

    @NonNull
    private final SourcesContract.View mSourcesView;


    @NonNull
    private SourcesCategoryFiltering mCurrentCategoryFiltering = SourcesCategoryFiltering.CATEGORY_ALL;


    @NonNull
    private SourcesLanguageFiltering mCurrentLanguageFiltering = SourcesLanguageFiltering.LANGUAGE_ALL;


    @NonNull
    private SourcesCountryFiltering mCurrentCountryFiltering = SourcesCountryFiltering.COUNTRY_ALL;


    @Inject
    SourcesPresenter(@NonNull SourcesContract.View sourcesView,
                     @NonNull NewsRepository newsRepository,
                     @NonNull BaseSchedulerProvider schedulerProvider) {
        super(newsRepository, schedulerProvider);
        mSourcesView = sourcesView;
        mSourcesView.setPresenter(this);
    }


    @Override
    public void subscribe() {
        loadSources();
    }


    @Override
    public void loadSources() {
        mSourcesView.showLoadingProgress(true);

        mCompositeDisposable.add(
                mNewsRepository.getSources(
                        mCurrentCategoryFiltering.getTitle(),
                        mCurrentLanguageFiltering.getTitle(),
                        mCurrentCountryFiltering.getTitle()
                )
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doOnTerminate(() -> mSourcesView.showLoadingProgress(false))
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
        mSourcesView.showArticlesForSourceUi(selectedSource);
    }

    @Override
    public void setCategoryFiltering(SourcesCategoryFiltering category) {
        mCurrentCategoryFiltering = category != null ? category : SourcesCategoryFiltering.CATEGORY_ALL;
    }


    @Override
    public void setLanguageFiltering(SourcesLanguageFiltering language) {
        mCurrentLanguageFiltering = language != null ? language : SourcesLanguageFiltering.LANGUAGE_ALL;;
    }


    @Override
    public void setCountryFiltering(SourcesCountryFiltering country) {
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
