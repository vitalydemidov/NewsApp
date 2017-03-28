package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vitalydemidov.newsapp.data.source.SourcesRepository;

import static ru.vitalydemidov.newsapp.util.CommonUtils.checkNotNull;

/**
 * Created by vitalydemidov on 23/01/2017.
 */

class SourcesPresenter implements SourcesContract.Presenter {

    @NonNull
    private final SourcesContract.View mSourcesView;


    @NonNull
    private final SourcesRepository mSourcesRepository;


    @NonNull
    private final CompositeDisposable mCompositeDisposable;


    @NonNull
    private SourcesFilterCategory mCurrentFilterCategory = SourcesFilterCategory.CATEGORY_ALL;


    @NonNull
    private SourcesFilterLanguage mCurrentFilterLanguage = SourcesFilterLanguage.LANGUAGE_ALL;


    @NonNull
    private SourcesFilterCountry mCurrentFilterCountry = SourcesFilterCountry.COUNTRY_ALL;


    public SourcesPresenter(@NonNull SourcesRepository sourcesRepository,
                            @NonNull SourcesContract.View sourcesView) {
        mSourcesRepository = checkNotNull(sourcesRepository);
        mSourcesView = checkNotNull(sourcesView);
        mSourcesView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }


    @Override
    public void subscribe() {
        loadSources(mCurrentFilterCategory.getTitle(),
                    mCurrentFilterLanguage.getTitle(),
                    mCurrentFilterCountry.getTitle());
    }


    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }


    @Override
    public void loadSources(@Nullable String category,
                            @Nullable String language,
                            @Nullable String country) {
        mCompositeDisposable.add(
                mSourcesRepository.getSources(category, language, country)
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

}
