package ru.vitalydemidov.newsapp.sources;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import ru.vitalydemidov.newsapp.data.Source;

/**
 * Created by vitalydemidov on 25/01/2017.
 */

public class SourcesAdapter extends RecyclerView.Adapter {


    private List<Source> mSources;


    public void setData(List<Source> sources) {
        mSources = sources;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return mSources.size();
    }

}
