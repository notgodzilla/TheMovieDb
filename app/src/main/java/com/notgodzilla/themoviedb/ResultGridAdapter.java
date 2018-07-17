package com.notgodzilla.themoviedb;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.stream.Collectors;

public class ResultGridAdapter extends RecyclerView.Adapter {

    private SearchResultsHits hits;

    private final static String BASE_IMG_URL = "https://image.tmdb.org/t/p/original/";

    private final static String  MEDIA_TYPE_KEY = "media_type";
    private final static String MOVIE = "movie";
    private final static String PERSON = "person";
    private final static String TV = "tv";



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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final Result result = hits.getResults().get(position);
        createViewBasedOnMediaType(result, (ResultViewHolder) holder);
    }

    private static void createViewBasedOnMediaType(Result result, ResultViewHolder resultViewHolder) {
        String mediaType = result.getMediaType();
        resultViewHolder.mediaTypeView.setText(mediaType);

        String imageDisplayPath = "";
        String textToDisplay = "";
        Uri imageUri;

        if(mediaType.equals(MOVIE)|| mediaType.equals(TV)) {
            imageDisplayPath = BASE_IMG_URL.concat(result.getPosterPath());
            textToDisplay = mediaType.equals(MOVIE) ? result.getTitle() : result.getOriginalName();

        } else if(mediaType.equals(PERSON)) {
            imageDisplayPath = BASE_IMG_URL.concat(result.getProfilePath());
            textToDisplay = result.getName();
        }

        imageUri = Uri.parse(imageDisplayPath);
        resultViewHolder.draweeView.setImageURI(imageUri);
        resultViewHolder.textView.setText(textToDisplay);

    }


    @Override
    public int getItemCount() {
        return hits.getResults().size()-1;
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView draweeView;
        public TextView textView;
        public TextView mediaTypeView;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.fresco_view);
            textView = (TextView) itemView.findViewById(R.id.media_name_text_view);
            mediaTypeView = (TextView) itemView.findViewById(R.id.media_type);

        }


    }

}
