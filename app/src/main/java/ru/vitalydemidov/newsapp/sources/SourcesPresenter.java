package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.data.source.NewsRepository;

class SourcesPresenter implements SourcesContract.Presenter {

    @NonNull
    private final SourcesContract.View mSourcesView;


    @NonNull
    private final NewsRepository mNewsRepository;


    @NonNull
    private final CompositeDisposable mCompositeDisposable;


    @NonNull
    private SourcesCategoryFiltering mCurrentCategoryFiltering = SourcesCategoryFiltering.CATEGORY_ALL;


    @NonNull
    private SourcesLanguageFiltering mCurrentLanguageFiltering = SourcesLanguageFiltering.LANGUAGE_ALL;


    @NonNull
    private SourcesCountryFiltering mCurrentCountryFiltering = SourcesCountryFiltering.COUNTRY_ALL;


    @Inject
    SourcesPresenter(@NonNull SourcesContract.View sourcesView,
                     @NonNull NewsRepository newsRepository) {
        mSourcesView = sourcesView;
        mSourcesView.setPresenter(this);
        mNewsRepository = newsRepository;
        mCompositeDisposable = new CompositeDisposable();
    }


    @Override
    public void subscribe() {
        loadSources();
    }


    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
