package ru.vitalydemidov.newsapp.articles;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import ru.vitalydemidov.newsapp.R;
import ru.vitalydemidov.newsapp.data.Article;

@UiThread
class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {

    private List<Article> mArticles;


    @Nullable
    private ArticleItemListener mListener;


    public void setArticles(List<Article> articles) {
        mArticles = articles;
        notifyDataSetChanged();
    }


    void setArticleItemListener(@NonNull ArticleItemListener listener) {
        mListener = listener;
    }


    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_item_list, parent, false);
        return new ArticleViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        Article article = mArticles.get(position);
        holder.image.setImageURI(article.getUrlToImage());
        holder.title.setText(article.getTitle());

        holder.itemView.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onArticleSelected(article);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mArticles != null ? mArticles.size() : 0;
    }


    static class ArticleViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView image;
        TextView title;

        ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (SimpleDraweeView) itemView.findViewById(R.id.article_item_list_image);
            title = (TextView) itemView.findViewById(R.id.article_item_list_title);
        }

    }


    interface ArticleItemListener {
        void onArticleSelected(@NonNull Article article);
    }

}
