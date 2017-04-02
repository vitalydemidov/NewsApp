package ru.vitalydemidov.newsapp.articles;

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
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.util.CommonUtils;

/**
 * Created by vitalydemidov on 02/04/2017.
 */

@UiThread
public class ArticlesFragment extends Fragment implements ArticlesContract.View {

    private static final String SOURCE_ID_EXTRA = ArticlesFragment.class.getCanonicalName() + ".SOURCE_ID_EXTRA";


    @NonNull
    private ArticlesContract.Presenter mArticlesPresenter;


    @NonNull
    private ArticlesAdapter mArticlesAdapter;


    public static ArticlesFragment newInstance(@NonNull String sourceId) {
        Bundle args = new Bundle();
        args.putString(SOURCE_ID_EXTRA, sourceId);
        ArticlesFragment fragment = new ArticlesFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArticlesAdapter = new ArticlesAdapter();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.articles_fragment, container, false);
        initViews(rootView);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        mArticlesPresenter.subscribe();
    }


    @Override
    public void onPause() {
        super.onPause();
        mArticlesPresenter.unsubscribe();
    }

    private void initViews(@NonNull View rootView) {
        initArticlesRecyclerView(rootView);
    }


    private void initArticlesRecyclerView(@NonNull View rootView) {
        RecyclerView articlesRecyclerView = (RecyclerView) rootView.findViewById(R.id.articles_recycler_view);
        articlesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        articlesRecyclerView.setAdapter(mArticlesAdapter);
    }


    @Override
    public void setPresenter(@NonNull ArticlesContract.Presenter presenter) {
        mArticlesPresenter = CommonUtils.checkNotNull(presenter);
    }


    @Override
    public void showArticles(List<Article> articles) {
        mArticlesAdapter.setArticles(articles);
    }


    @Override
    public void showLoadingError() {
        Toast.makeText(getContext(), R.string.articles_loading_error, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showLoadingProgress(boolean showProgress) {

    }
}
