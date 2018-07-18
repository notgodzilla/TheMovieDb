package com.notgodzilla.themoviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchResultsActivity extends AppCompatActivity {

    private static final String API_KEY = "71ab1b19293efe581c569c1c79d0f004";
    private static final String BASE_QUERY = "https://api.themoviedb.org/3/search/multi";
    private static final String TAG = "SearchResultsActivity";

    private static final String LEFT = "left";
    private static final String RIGHT = "rigt";

    private TextView textView;
    private TextView searchTextView;
    private TextView pageNumberView;

    private ImageView leftArrow;
    private ImageView rightArrow;

    private String searchText;
    private int currentPage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        textView = findViewById(R.id.media_name_text_view);
        pageNumberView = findViewById(R.id.page_number);
        searchTextView = findViewById(R.id.search_results_text);

        searchText = getIntent().getStringExtra(MainActivity.SEARCH_QUERY_EXTRA);
        currentPage = getIntent().getIntExtra(MainActivity.PAGE_NUMBER_EXTRA, 1);

        leftArrow = findViewById(R.id.left_arrow);
        rightArrow = findViewById(R.id.right_arrow);
        setArrowListeners();

        getPage(searchText, currentPage);
    }

    public void setArrowListeners() {
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickArrow(LEFT, currentPage);
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickArrow(RIGHT, currentPage);
            }
        });
    }

    public void clickArrow(String direction, int page) {
        int newPage = direction.equals(RIGHT) ? page + 1 : page - 1;

        Intent searchResultsIntent = new Intent(getApplicationContext(), SearchResultsActivity.class);
        searchResultsIntent.putExtra(MainActivity.PAGE_NUMBER_EXTRA, newPage);
        searchResultsIntent.putExtra(MainActivity.SEARCH_QUERY_EXTRA, searchText);

        startActivity(searchResultsIntent);
        finish();
    }

    public void getPage(String searchText, int page) {
        getData(searchText, page);
    }

    public void getData(String searchText, int page) {
        RetrofitClient client = new RetrofitClient();
        TheMovieDbAPI movieDbAPI = client.getMovieDbAPIService();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("api_key", API_KEY);
        parameters.put("query", searchText);
        parameters.put("page", Integer.toString(page));

        movieDbAPI.baseApiCall(parameters)
                .enqueue(new Callback<SearchResultsHits>() {
                    @Override
                    public void onResponse(Call<SearchResultsHits> call, Response<SearchResultsHits> response) {
                        createRecyclerView(response.body());
                    }

                    @Override
                    public void onFailure(Call<SearchResultsHits> call, Throwable t) {

                    }
                });
    }

    private void createRecyclerView(SearchResultsHits hits) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_spinner);
        progressBar.setVisibility(View.GONE);

        String textToDisplay =
                hits.getTotalResults() > 0 ?
                        getString(R.string.showing_search_results_for, searchText) :
                        getString(R.string.sorry_no_results, searchText);
        searchTextView.setText(textToDisplay);
        searchTextView.setVisibility(View.VISIBLE);

        String pageText = getString(R.string.page_of, currentPage, hits.getTotalPages());
        pageNumberView.setText(pageText);
        pageNumberView.setVisibility(View.VISIBLE);

        if (hits.getTotalResults() > 0) {
            if (currentPage != 1) {
                leftArrow.setVisibility(View.VISIBLE);
            }

            if (currentPage != hits.getTotalPages()) {
                rightArrow.setVisibility(View.VISIBLE);
            }


            RecyclerView resultRecyclerView = (RecyclerView) findViewById(R.id.result_recycler_view);
            resultRecyclerView.setVisibility(View.VISIBLE);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
            resultRecyclerView.setLayoutManager(layoutManager);

            RecyclerView.Adapter adapter = new ResultGridAdapter(hits);
            resultRecyclerView.setAdapter(adapter);
        }

    }

}
