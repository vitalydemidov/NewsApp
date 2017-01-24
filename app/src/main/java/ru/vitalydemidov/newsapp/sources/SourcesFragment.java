package ru.vitalydemidov.newsapp.sources;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.vitalydemidov.newsapp.R;

import static ru.vitalydemidov.newsapp.util.CommonUtils.checkNotNull;

/**
 * Created by vitalydemidov on 23/01/2017.
 */

public class SourcesFragment extends Fragment implements SourcesContract.View {

    @NonNull
    private SourcesContract.Presenter mSourcesPresenter;


    @NonNull
    private SourcesAdapter mSourcesAdapter;


    public static SourcesFragment newInstance() {
        return new SourcesFragment();
    }


    @Override
    public void setPresenter(@NonNull SourcesContract.Presenter presenter) {
        mSourcesPresenter = checkNotNull(presenter);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSourcesAdapter = new SourcesAdapter();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sources_fragment, container, false);
        initViews(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSourcesPresenter.start();
    }


    private void initViews(@NonNull View rootView) {
        initSourcesRecyclerView(rootView);
    }


    private void initSourcesRecyclerView(@NonNull View rootView) {
        RecyclerView sourcesRecyclerView = (RecyclerView) rootView.findViewById(R.id.sources_recycler_view);
        sourcesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sourcesRecyclerView.setAdapter(mSourcesAdapter);
    }

}
