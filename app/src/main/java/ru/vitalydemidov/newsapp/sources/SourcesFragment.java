package ru.vitalydemidov.newsapp.sources;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ru.vitalydemidov.newsapp.R;
import ru.vitalydemidov.newsapp.articles.ArticlesActivity;
import ru.vitalydemidov.newsapp.data.Source;

import static ru.vitalydemidov.newsapp.util.CommonUtils.checkNotNull;

/**
 * Created by vitalydemidov on 23/01/2017.
 */

@UiThread
public class SourcesFragment extends Fragment implements SourcesContract.View, SourcesAdapter.OnSourceSelectedListener {

    private static final String EXTRA_SOURCE_ID = "ru.vitalydemidov.newsapp.extra_source_id";
    private static final String EXTRA_SOURCE_TITLE = "ru.vitalydemidov.newsapp.extra_source_title";


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
        initSourcesAdapter();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sources_fragment, container, false);
        initViews(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSourcesPresenter.restoreState(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSourcesPresenter.subscribe();
    }


    @Override
    public void onPause() {
        mSourcesPresenter.unsubscribe();
        super.onPause();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSourcesPresenter.saveState(outState);
    }


    private void initViews(@NonNull View rootView) {
        initSourcesRecyclerView(rootView);
    }


    private void initSourcesRecyclerView(@NonNull View rootView) {
        RecyclerView sourcesRecyclerView = (RecyclerView) rootView.findViewById(R.id.sources_recycler_view);
        sourcesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sourcesRecyclerView.setAdapter(mSourcesAdapter);
    }


    private void initSourcesAdapter() {
        mSourcesAdapter = new SourcesAdapter();
        mSourcesAdapter.setSourceSelectedListener(this);
    }


    @Override
    public void showSources(List<Source> sources) {
        mSourcesAdapter.setSources(sources);
    }


    @Override
    public void showLoadingError() {
        Toast.makeText(getContext(), R.string.sources_loading_error, Toast.LENGTH_LONG).show();
    }


    @Override
    public void showLoadingProgress(boolean showProgress) {

    }

    //region SourcesAdapter.OnSourceSelectedListener interface implementation
    @Override
    public void onSourceSelected(@NonNull Source source) {
        Intent intent = new Intent(getContext(), ArticlesActivity.class);
        intent.putExtra(EXTRA_SOURCE_ID, source.getId());
        intent.putExtra(EXTRA_SOURCE_TITLE, source.getName());
        startActivity(intent);
    }
    //endregion SourcesAdapter.OnSourceSelectedListener interface implementation

}
