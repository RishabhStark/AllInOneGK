package com.stark.quizzer.utility;

import com.stark.quizzer.ModelClasses.news_data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("top-headlines")
    Call<news_data> getNews(@Query("country") String country, @Query("apiKey") String key);
}
