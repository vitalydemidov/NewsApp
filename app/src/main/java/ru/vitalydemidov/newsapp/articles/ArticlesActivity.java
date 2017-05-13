package ru.vitalydemidov.newsapp.articles;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.annotation.VisibleForTesting;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import ru.vitalydemidov.newsapp.NewsApp;
import ru.vitalydemidov.newsapp.R;
import ru.vitalydemidov.newsapp.base.BaseActivity;
import ru.vitalydemidov.newsapp.data.Article;
import ru.vitalydemidov.newsapp.data.Source;

@UiThread
public class ArticlesActivity extends BaseActivity {

    private static final String SORT_STATE = "ru.vitalydemidov.newsapp.sort_state";


    @VisibleForTesting
    public static final String EXTRA_SOURCE = "ru.vitalydemidov.newsapp.extra_source";


    @Inject
    ArticlesViewModel mArticlesViewModel;


    private ArticlesAdapter mArticlesAdapter;


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
        onRestoreState(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.action_sort));
                Source source = getIntent().getParcelableExtra(EXTRA_SOURCE);
                for (String sortBy : source.getSortBysAvailable()) {
                    popupMenu.getMenu().add(sortBy);
                }
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    mArticlesViewModel.setSort(Sort.fromValue(String.valueOf(menuItem.getTitle())));
                    return true;
                });
                popupMenu.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SORT_STATE, mArticlesViewModel.getSort());
    }


    private void onRestoreState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Sort savedSort = (Sort) savedInstanceState.getSerializable(SORT_STATE);
            mArticlesViewModel.setSort(savedSort != null ? savedSort : Sort.TOP);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void setupToolbar(@NonNull String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(title);
        }
    }


    @Inject
    void setArticlesAdapter(@NonNull ArticlesAdapter adapter) {
        mArticlesAdapter = adapter;
        mArticlesAdapter.setArticleItemListener(this::openArticleInBrowser);
    }


    private void initViews() {
        initArticlesSwipeRefreshLayout();
        initArticlesRecyclerView();
    }


    private void initArticlesSwipeRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.articles_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(() -> mArticlesViewModel.setSort(mArticlesViewModel.getSort()));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }


    private void initArticlesRecyclerView() {
        RecyclerView articlesRecyclerView =
                (RecyclerView) findViewById(R.id.articles_recycler_view);
        articlesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        articlesRecyclerView.setAdapter(mArticlesAdapter);
    }


    public void showArticles(@NonNull List<Article> articles) {
        mArticlesAdapter.setArticles(articles);
    }


    private void openArticleInBrowser(@NonNull Article article) {
        Uri articleUri = Uri.parse(article.getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, articleUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    @Override
    protected void showLoadingError() {
        Toast.makeText(this, R.string.articles_loading_error, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void bind() {
        showLoadingProgress();
        mDisposable = mArticlesViewModel.loadArticles()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        // onNext
                        sources -> {
                            hideLoadingProgress();
                            showArticles(sources);
                        },
                        // onError
                        throwable -> {
                            hideLoadingProgress();
                            showLoadingError();
                        }
                );
    }


    @Override
    protected void unBind() {
        mDisposable.dispose();
    }

}
