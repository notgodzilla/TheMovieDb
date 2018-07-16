package com.notgodzilla.themoviedb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchResultsActivity extends AppCompatActivity {

    private static final String API_KEY = "71ab1b19293efe581c569c1c79d0f004";
    private static final String BASE_QUERY ="https://api.themoviedb.org/3/search/multi";
    private static final String TAG = "SearchResultsActivity";

    private TextView textView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        textView = findViewById(R.id.search_result_text_view);
        String searchText = getIntent().getStringExtra(MainActivity.SEARCH_QUERY_EXTRA);
        getData(searchText);

    }

    public void getData(String searchText) {
        RetrofitClient client = new RetrofitClient();
        TheMovieDbAPI movieDbAPI = client.getMovieDbAPIService();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("api_key", API_KEY);
        parameters.put("query", searchText);
        parameters.put("page", "1");

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
        RecyclerView resultRecyclerView = (RecyclerView) findViewById(R.id.result_recycler_view);
        resultRecyclerView.setVisibility(View.VISIBLE);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_spinner);
        progressBar.setVisibility(View.GONE);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        resultRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new ResultGridAdapter(hits);
        resultRecyclerView.setAdapter(adapter);

    }

}
