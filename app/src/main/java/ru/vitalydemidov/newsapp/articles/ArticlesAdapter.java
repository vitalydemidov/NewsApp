package ru.vitalydemidov.newsapp.articles;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.vitalydemidov.newsapp.R;
import ru.vitalydemidov.newsapp.data.Article;

/**
 * Created by vitalydemidov on 02/04/2017.
 */

@UiThread
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {

    private List<Article> mArticles;


    public void setArticles(List<Article> articles) {
        mArticles = articles;
        notifyDataSetChanged();
    }


    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_item_list, parent, false);
        return new ArticleViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        Article article = mArticles.get(position);
        holder.title.setText(article.getTitle());
    }


    @Override
    public int getItemCount() {
        return mArticles != null ? mArticles.size() : 0;
    }


    static class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.article_item_list_title);
        }

    }

}
