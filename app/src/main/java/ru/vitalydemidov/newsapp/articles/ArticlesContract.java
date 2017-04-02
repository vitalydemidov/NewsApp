package ru.vitalydemidov.newsapp.articles;

import android.support.annotation.NonNull;

import java.util.List;

import ru.vitalydemidov.newsapp.base.BasePresenter;
import ru.vitalydemidov.newsapp.base.BaseView;
import ru.vitalydemidov.newsapp.data.Article;

/**
 * Created by vitalydemidov on 02/04/2017.
 */

public interface ArticlesContract {

    interface View extends BaseView<Presenter> {

        void showArticles(List<Article> articles);

    }


    interface Presenter extends BasePresenter {

        void loadArticles(@NonNull String sourceId);

    }

}
