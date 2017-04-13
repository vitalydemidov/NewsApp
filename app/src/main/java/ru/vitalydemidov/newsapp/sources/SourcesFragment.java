package ru.vitalydemidov.newsapp.sources;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

@UiThread
public class SourcesFragment extends Fragment implements SourcesContract.View {

    private static final String EXTRA_SOURCE_ID = "ru.vitalydemidov.newsapp.extra_source_id";
    private static final String EXTRA_SOURCE_TITLE = "ru.vitalydemidov.newsapp.extra_source_title";
    private static final String CATEGORY_FILTERING_STATE = "ru.vitalydemidov.newsapp.category_filtering_state";
    private static final String LANGUAGE_FILTERING_STATE = "ru.vitalydemidov.newsapp.language_filtering_stat";
    private static final String COUNTRY_FILTERING_STATE = "ru.vitalydemidov.newsapp.country_filtering_stat";


    @NonNull
    private SourcesContract.Presenter mSourcesPresenter;


    @NonNull
    private SourcesAdapter mSourcesAdapter;


    @NonNull
    private SwipeRefreshLayout mSourcesSwipeRefreshLayout;


    @NonNull
    private SourcesAdapter.SourceItemListener mItemListener =
            (source -> mSourcesPresenter.openArticlesForSource(source));


    public static SourcesFragment newInstance() {
        return new SourcesFragment();
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
        if (savedInstanceState != null) {
            mSourcesPresenter.setCategoryFiltering((SourcesCategoryFiltering) savedInstanceState.getSerializable(CATEGORY_FILTERING_STATE));
            mSourcesPresenter.setLanguageFiltering((SourcesLanguageFiltering) savedInstanceState.getSerializable(LANGUAGE_FILTERING_STATE));
            mSourcesPresenter.setCountryFiltering((SourcesCountryFiltering) savedInstanceState.getSerializable(COUNTRY_FILTERING_STATE));
        }
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
        outState.putSerializable(CATEGORY_FILTERING_STATE, mSourcesPresenter.getCategoryFiltering());
        outState.putSerializable(LANGUAGE_FILTERING_STATE, mSourcesPresenter.getLanguageFiltering());
        outState.putSerializable(COUNTRY_FILTERING_STATE, mSourcesPresenter.getCountryFiltering());
    }


    private void initViews(@NonNull View rootView) {
        initArticlesSwipeRefreshLayout(rootView);
        initSourcesRecyclerView(rootView);
    }


    private void initArticlesSwipeRefreshLayout(@NonNull View rootView) {
        mSourcesSwipeRefreshLayout =
                (SwipeRefreshLayout) rootView.findViewById(R.id.sources_swipe_refresh_layout);
        mSourcesSwipeRefreshLayout.setOnRefreshListener(() -> mSourcesPresenter.loadSources());
        mSourcesSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }


    private void initSourcesRecyclerView(@NonNull View rootView) {
        RecyclerView sourcesRecyclerView = (RecyclerView) rootView.findViewById(R.id.sources_recycler_view);
        sourcesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sourcesRecyclerView.setAdapter(mSourcesAdapter);
    }


    private void initSourcesAdapter() {
        mSourcesAdapter = new SourcesAdapter();
        mSourcesAdapter.setSourceItemListener(mItemListener);
    }


    //region Contract
    @Override
    public void setPresenter(@NonNull SourcesContract.Presenter presenter) {
        mSourcesPresenter = presenter;
    }


    @Override
    public void showLoadingError() {
        Toast.makeText(getContext(), R.string.sources_loading_error, Toast.LENGTH_LONG).show();
    }


    @Override
    public void showLoadingProgress(boolean showProgress) {
        mSourcesSwipeRefreshLayout.setRefreshing(showProgress);
    }


    @Override
    public void showSources(List<Source> sources) {
        mSourcesAdapter.setSources(sources);
    }


    @Override
    public void showArticlesForSourceUi(@NonNull Source selectedSource) {
        Intent intent = new Intent(getContext(), ArticlesActivity.class);
        intent.putExtra(EXTRA_SOURCE_ID, selectedSource.getId());
        intent.putExtra(EXTRA_SOURCE_TITLE, selectedSource.getName());
        startActivity(intent);
    }
    //endregion Contract

}
