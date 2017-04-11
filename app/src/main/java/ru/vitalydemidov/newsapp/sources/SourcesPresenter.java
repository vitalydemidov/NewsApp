package ru.vitalydemidov.newsapp.sources;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vitalydemidov.newsapp.data.source.NewsRepository;

class SourcesPresenter implements SourcesContract.Presenter {

    private static final String CATEGORY_FILTERING_STATE = SourcesFragment.class.getSimpleName() + ".CATEGORY_FILTERING_STATE";
    private static final String LANGUAGE_FILTERING_STATE = SourcesFragment.class.getSimpleName() + ".LANGUAGE_FILTERING_STATE";
    private static final String COUNTRY_FILTERING_STATE = SourcesFragment.class.getSimpleName() + ".COUNTRY_FILTERING_STATE";


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
        mCompositeDisposable.add(
                mNewsRepository.getSources(
                        mCurrentCategoryFiltering.getTitle(),
                        mCurrentLanguageFiltering.getTitle(),
                        mCurrentCountryFiltering.getTitle()
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        mSourcesView::showSources,
                        // onError
                        throwable -> mSourcesView.showLoadingError(),
                        // onComplete
                        () -> mSourcesView.showLoadingProgress(false)
                )
        );
    }


    @Override
    public void setCategoryFiltering(@NonNull SourcesCategoryFiltering category) {
        mCurrentCategoryFiltering = category;
    }


    @Override
    public void setLanguageFiltering(@NonNull SourcesLanguageFiltering language) {
        mCurrentLanguageFiltering = language;
    }


    @Override
    public void setCountryFiltering(@NonNull SourcesCountryFiltering country) {
        mCurrentCountryFiltering = country;
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


    @Override
    public void saveState(@Nullable Bundle outState) {
        if (outState != null) {
            outState.putSerializable(CATEGORY_FILTERING_STATE, mCurrentCategoryFiltering);
            outState.putSerializable(LANGUAGE_FILTERING_STATE, mCurrentLanguageFiltering);
            outState.putSerializable(COUNTRY_FILTERING_STATE, mCurrentCountryFiltering);
        }
    }


    @Override
    public void restoreState(@Nullable Bundle savedState) {
        if (savedState != null) {
            SourcesCategoryFiltering category = (SourcesCategoryFiltering) savedState.getSerializable(CATEGORY_FILTERING_STATE);
            mCurrentCategoryFiltering = category != null ? category : SourcesCategoryFiltering.CATEGORY_ALL;
            SourcesLanguageFiltering language = (SourcesLanguageFiltering) savedState.getSerializable(LANGUAGE_FILTERING_STATE);
            mCurrentLanguageFiltering = language != null ? language : SourcesLanguageFiltering.LANGUAGE_ALL;
            SourcesCountryFiltering country = (SourcesCountryFiltering) savedState.getSerializable(COUNTRY_FILTERING_STATE);
            mCurrentCountryFiltering = country != null ? country : SourcesCountryFiltering.COUNTRY_ALL;
        }
    }

}
