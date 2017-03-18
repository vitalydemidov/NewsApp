package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vitalydemidov.newsapp.data.source.SourcesRepository;

import static ru.vitalydemidov.newsapp.util.CommonUtils.checkNotNull;

/**
 * Created by vitalydemidov on 23/01/2017.
 */

public class SourcesPresenter implements SourcesContract.Presenter {

    @NonNull
    private final SourcesContract.View mSourcesView;

    @NonNull
    private final SourcesRepository mSourcesRepository;

    @NonNull
    private final CompositeDisposable mCompositeDisposable;

    public SourcesPresenter(@NonNull SourcesRepository sourcesRepository,
                            @NonNull SourcesContract.View sourcesView) {
        mSourcesRepository = checkNotNull(sourcesRepository);
        mSourcesView = checkNotNull(sourcesView);
        mSourcesView.setPresenter(this);
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
                mSourcesRepository.getSources()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mSourcesView::showSources,
                        throwable -> mSourcesView.showLoadingError(),
                        () -> mSourcesView.showLoadingProgress(false)
                )
        );
    }
}
