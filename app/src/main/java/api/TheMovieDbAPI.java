package com.notgodzilla.themoviedb;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface TheMovieDbAPI {
    @GET("search/multi")
    Call<SearchResultsHits> baseApiCall(@QueryMap Map<String, String> parameters);
}
