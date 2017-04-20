package ru.vitalydemidov.newsapp.articles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import ru.vitalydemidov.newsapp.NewsApp;
import ru.vitalydemidov.newsapp.R;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.Source;

@UiThread
public class ArticlesActivity extends AppCompatActivity implements ArticlesContract.View {

    private static final String EXTRA_SOURCE = "ru.vitalydemidov.newsapp.extra_source";


    @Inject
    @NonNull
    ArticlesContract.Presenter mArticlesPresenter;


    @NonNull
    private ArticlesAdapter mArticlesAdapter;


    @NonNull
    private SwipeRefreshLayout mArticlesSwipeRefreshLayout;


    @NonNull
    public static Intent newIntent(@NonNull Context context, @NonNull Source source) {
        Intent intent = new Intent(context, ArticlesActivity.class);
        intent.putExtra(EXTRA_SOURCE, source);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articles_activity);

        Source source = getIntent().getParcelableExtra(EXTRA_SOURCE);
        setupToolbar(source.getName());

        DaggerArticlesComponent.builder()
                .sourceId(source.getId())
                .appComponent(NewsApp.getAppComponent())
                .build()
                .inject(this);

        initViews();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mArticlesPresenter.attachView(this);
        mArticlesPresenter.loadArticles();
    }


    @Override
    protected void onPause() {
        mArticlesPresenter.detachView();
        super.onPause();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void setupToolbar(@NonNull String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
    }


    @Inject
    void setArticlesAdapter(@NonNull ArticlesAdapter adapter) {
        mArticlesAdapter = adapter;
        // TODO: 18/04/2017 set listener
    }

    private void initViews() {
        initArticlesSwipeRefreshLayout();
        initArticlesRecyclerView();
    }


    private void initArticlesSwipeRefreshLayout() {
        mArticlesSwipeRefreshLayout =
                (SwipeRefreshLayout) findViewById(R.id.articles_swipe_refresh_layout);
        mArticlesSwipeRefreshLayout.setOnRefreshListener(() -> mArticlesPresenter.loadArticles());
        mArticlesSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }


    private void initArticlesRecyclerView() {
        RecyclerView articlesRecyclerView =
                (RecyclerView) findViewById(R.id.articles_recycler_view);
        articlesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        articlesRecyclerView.setAdapter(mArticlesAdapter);
    }


    //region Contract
    @Override
    public void showArticles(List<Article> articles) {
        mArticlesAdapter.setArticles(articles);
    }


    @Override
    public void showLoadingError() {
        Toast.makeText(this, R.string.articles_loading_error, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showLoadingProgress() {
        mArticlesSwipeRefreshLayout.setRefreshing(true);
    }


    @Override
    public void hideLoadingProgress() {
        mArticlesSwipeRefreshLayout.setRefreshing(false);
    }
    //endregion Contract

}
