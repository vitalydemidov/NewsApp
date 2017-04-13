package ru.vitalydemidov.newsapp.sources;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.vitalydemidov.newsapp.R;
import ru.vitalydemidov.newsapp.data.Source;

@UiThread
class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.SourceViewHolder> {


    private List<Source> mSources;


    @Nullable
    private SourceItemListener mListener;


    public void setSources(List<Source> sources) {
        mSources = sources;
        notifyDataSetChanged();
    }


    void setSourceItemListener(@NonNull SourceItemListener listener) {
        mListener = listener;
    }


    @Override
    public SourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sources_item_list, parent, false);
        return new SourceViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(SourceViewHolder holder, int position) {
        Source source = mSources.get(position);
        holder.name.setText(source.getName());

        holder.itemView.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onSourceSelected(source);
            }
        });
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


    interface SourceItemListener {
        void onSourceSelected(@NonNull Source source);
    }

}
