package ru.vitalydemidov.newsapp.base;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;

@UiThread
public abstract class BaseActivity extends AppCompatActivity {

    @NonNull
    protected Disposable mDisposable;


    @Inject
    @NonNull
    protected BaseSchedulerProvider mSchedulerProvider;


    @NonNull
    protected SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onResume() {
        super.onResume();
        bind();
    }


    @Override
    protected void onPause() {
        unBind();
        super.onPause();
    }


    protected void showLoadingProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }


    protected void hideLoadingProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }


    protected abstract void showLoadingError();


    protected abstract void bind();


    protected abstract void unBind();

}
