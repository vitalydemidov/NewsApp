package ru.vitalydemidov.newsapp.articles;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import ru.vitalydemidov.newsapp.base.BaseViewModel;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.source.NewsDataSource;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;

class ArticlesViewModel extends BaseViewModel {

    @NonNull
    private final String mSourceId;


    @NonNull
    private Sort mSort;


    @NonNull
    private final BehaviorSubject<Sort> mArticlesBehaviorSubject;


    ArticlesViewModel(@NonNull String sourceId,
                      @NonNull final NewsDataSource newsRepository,
                      @NonNull final BaseSchedulerProvider schedulerProvider) {
        super(newsRepository, schedulerProvider);
        mSourceId = sourceId;
        mSort = Sort.TOP;
        mArticlesBehaviorSubject = BehaviorSubject.createDefault(mSort);
    }


    Observable<List<Article>> loadArticles() {
        return mArticlesBehaviorSubject
                .observeOn(mSchedulerProvider.io())
                .flatMap(sort -> mNewsRepository.getArticles(mSourceId, mSort.getTitle()));
    }


    void setSort(@NonNull Sort sort) {
        mSort = sort;
        mArticlesBehaviorSubject.onNext(mSort);
    }


    @NonNull
    Sort getSort() {
        return mSort;
    }

}
