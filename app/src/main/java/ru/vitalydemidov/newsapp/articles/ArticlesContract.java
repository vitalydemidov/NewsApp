package ru.vitalydemidov.newsapp.articles;

import android.support.annotation.NonNull;

import java.util.List;

import ru.vitalydemidov.newsapp.base.BasePresenter;
import ru.vitalydemidov.newsapp.base.BaseView;
import ru.vitalydemidov.newsapp.data.Article;

interface ArticlesContract {

    interface View extends BaseView {

        void showArticles(@NonNull List<Article> articles);

        void showArticleDetails(@NonNull Article article);

    }


    interface Presenter extends BasePresenter<View> {

        void loadArticles();

        void openArticleDetails(@NonNull Article article);

        void setSort(@NonNull Sort sort);

        @NonNull
        Sort getSort();

    }

}
