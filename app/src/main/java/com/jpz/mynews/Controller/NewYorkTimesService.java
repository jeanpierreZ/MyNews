package com.jpz.mynews.Controller;

import com.jpz.mynews.Model.NYTTopStories;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewYorkTimesService {
    // Interface for calls (endpoints) on the New York Times APIs

    String API_SECTION_TOPSTORIES = "home";
    String API_BASE_URL = "https://api.nytimes.com/";
    String API_KEY = "ZFLWOr4Llj4dNQEA4itSAoJJm2ggwLJx";

    // Create a Retrofit Object to do a request network
    Retrofit retrofit = new Retrofit.Builder()
            // Root URL
            .baseUrl(API_BASE_URL)
            // GSON converter
            .addConverterFactory(GsonConverterFactory.create())
            // RxJava Adapter
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
/*
    // GET type of REST request
    @GET("svc/topstories/v2/{sectionValue}.json")
        // sectionValue parameter return NYTTopStories Class
    Call<NYTTopStories> getTopStories(@Path("sectionValue") String sectionValue, @Query("api-key") String apiKey);
*/
    // GET type of REST request
    @GET("svc/topstories/v2/{sectionValue}.json")
    Observable<NYTTopStories> getTopStories(@Path("sectionValue") String sectionValue, @Query("api-key") String apiKey);

}
