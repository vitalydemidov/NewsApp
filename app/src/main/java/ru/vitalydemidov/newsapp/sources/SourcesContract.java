package ru.vitalydemidov.newsapp.sources;

import ru.vitalydemidov.newsapp.base.BasePresenter;
import ru.vitalydemidov.newsapp.base.BaseView;

/**
 * Created by vitalydemidov on 23/01/2017.
 */

public interface SourcesContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void loadSources();

    }

}
