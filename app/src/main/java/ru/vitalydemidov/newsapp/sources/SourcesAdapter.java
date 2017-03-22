package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.vitalydemidov.newsapp.R;
import ru.vitalydemidov.newsapp.data.Source;

/**
 * Created by vitalydemidov on 25/01/2017.
 */

@UiThread
class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.SourceViewHolder> {


    private List<Source> mSources;


    public void setData(List<Source> sources) {
        mSources = sources;
        notifyDataSetChanged();
    }


    @Override
    public SourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new SourceViewHolder(inflater.inflate(R.layout.sources_item_list, parent, false));
    }


    @Override
    public void onBindViewHolder(SourceViewHolder holder, int position) {
        Source source = mSources.get(position);
        holder.name.setText(source.getName());
    }


    @Override
    public int getItemCount() {
        return mSources != null ? mSources.size() : 0;
    }


    static class SourceViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        SourceViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.source_item_list_name);
        }

    }

}
