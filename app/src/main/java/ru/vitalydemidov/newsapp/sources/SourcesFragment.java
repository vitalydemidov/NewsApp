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
import android.widget.Toast;

import java.util.List;

import ru.vitalydemidov.newsapp.R;
import ru.vitalydemidov.newsapp.data.Source;

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
        mSourcesPresenter.subscribe();
    }


    @Override
    public void onPause() {
        super.onPause();
        mSourcesPresenter.unsubscribe();
    }


    private void initViews(@NonNull View rootView) {
        initSourcesRecyclerView(rootView);
    }


    private void initSourcesRecyclerView(@NonNull View rootView) {
        RecyclerView sourcesRecyclerView = (RecyclerView) rootView.findViewById(R.id.sources_recycler_view);
        sourcesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sourcesRecyclerView.setAdapter(mSourcesAdapter);
    }


    @Override
    public void showSources(List<Source> sources) {
        mSourcesAdapter.setData(sources);
    }


    @Override
    public void showLoadingError() {
        Toast.makeText(getContext(), "Error when sources loading", Toast.LENGTH_LONG).show();
    }


    @Override
    public void showLoadingProgress(boolean showProgress) {

    }

}
