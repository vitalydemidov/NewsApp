package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import static ru.vitalydemidov.newsapp.util.CommonUtils.checkNotNull;

/**
 * Created by vitalydemidov on 23/01/2017.
 */

public class SourcesFragment extends Fragment implements SourcesContract.View {

    private SourcesContract.Presenter mSourcesPresenter;

    public static SourcesFragment newInstance() {
        return new SourcesFragment();
    }

    @Override
    public void setPresenter(@NonNull SourcesContract.Presenter presenter) {
        mSourcesPresenter = checkNotNull(presenter);
    }
}
