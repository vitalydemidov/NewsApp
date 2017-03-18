package ru.vitalydemidov.newsapp.sources;

import java.util.List;

import ru.vitalydemidov.newsapp.base.BasePresenter;
import ru.vitalydemidov.newsapp.base.BaseView;
import ru.vitalydemidov.newsapp.data.Source;

/**
 * Created by vitalydemidov on 23/01/2017.
 */

public interface SourcesContract {

    interface View extends BaseView<Presenter> {

        void showSources(List<Source> sources);

        void showLoadingError();

        void showLoadingProgress(boolean showProgress);

    }

    interface Presenter extends BasePresenter {

        void loadSources();

    }

}
