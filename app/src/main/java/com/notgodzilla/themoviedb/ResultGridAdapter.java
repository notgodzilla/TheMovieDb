package com.notgodzilla.themoviedb;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

public class ResultGridAdapter extends RecyclerView.Adapter {

    private SearchResultsHits hits;

    private final static String BASE_IMG_URL = "https://image.tmdb.org/t/p/original/";

    public ResultGridAdapter(SearchResultsHits hits) {
        this.hits = hits;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_result_layout, parent, false);
        ResultViewHolder resultViewHolder = new ResultViewHolder(view);
        return resultViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Result result = hits.getResults().get(position);

        String posterPath = result.getPosterPath();
        String imgUrl = BASE_IMG_URL.concat(posterPath);
        Uri uri = Uri.parse(imgUrl);
        String resultText = result.getOriginalTitle()!=null ? result.getOriginalTitle() : "";

        ((ResultViewHolder) holder).draweeView.setImageURI(uri);
        ((ResultViewHolder) holder).textView.setText(resultText);

    }

    @Override
    public int getItemCount() {
        return hits.getResults().size();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView draweeView;
        public TextView textView;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.fresco_view);
            textView = (TextView) itemView.findViewById(R.id.search_result_text_view);

        }
    }

}
