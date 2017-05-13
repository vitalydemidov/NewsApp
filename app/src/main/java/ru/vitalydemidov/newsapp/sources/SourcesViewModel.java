package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import ru.vitalydemidov.newsapp.base.BaseViewModelImpl;
import ru.vitalydemidov.newsapp.data.Source;
import ru.vitalydemidov.newsapp.data.source.NewsDataSource;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;

class SourcesViewModel extends BaseViewModelImpl {

    @NonNull
    private FilteringContainer mFilteringContainer;


    @NonNull
    private final BehaviorSubject<FilteringContainer> mSourcesBehaviorSubject;


    SourcesViewModel(@NonNull final NewsDataSource newsRepository,
                     @NonNull final BaseSchedulerProvider schedulerProvider) {
        super(newsRepository, schedulerProvider);
        mFilteringContainer = new FilteringContainer();
        mSourcesBehaviorSubject = BehaviorSubject.createDefault(mFilteringContainer);
    }


    Observable<List<Source>> loadSources() {
        return mSourcesBehaviorSubject
                .observeOn(mSchedulerProvider.io())
                .flatMap(filteringContainer -> mNewsRepository.getSources(
                            filteringContainer.getCategoryFiltering().getTitle(),
                            filteringContainer.getLanguageFiltering().getTitle(),
                            filteringContainer.getCountryFiltering().getTitle()
                        )
                );
    }


    void setFiltering(@NonNull FilteringContainer filteringContainer) {
        mFilteringContainer = filteringContainer;
        mSourcesBehaviorSubject.onNext(filteringContainer);
    }

    @NonNull
    FilteringContainer getFiltering() {
        return mFilteringContainer;
    }

}
