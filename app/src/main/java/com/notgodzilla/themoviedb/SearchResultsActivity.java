package com.notgodzilla.themoviedb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
                .enqueue(new Callback<SearchResultsHit>() {
                    @Override
                    public void onResponse(Call<SearchResultsHit> call, Response<SearchResultsHit> response) {
                        String firstResultText = response.body().getResults().get(0).toString();
                        textView.setText(firstResultText);
                        Log.i(TAG, firstResultText);
                    }

                    @Override
                    public void onFailure(Call<SearchResultsHit> call, Throwable t) {

                    }
                });



    }

}
