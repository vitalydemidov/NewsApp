package ru.vitalydemidov.newsapp.articles;

import java.util.List;

import ru.vitalydemidov.newsapp.base.BasePresenter;
import ru.vitalydemidov.newsapp.base.BaseView;
import ru.vitalydemidov.newsapp.data.Article;

interface ArticlesContract {

    interface View extends BaseView {

        void showArticles(List<Article> articles);

    }


    interface Presenter extends BasePresenter<View> {

        void loadArticles();

    }

}
