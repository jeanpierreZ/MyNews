package com.jpz.mynews.Controller;

import com.jpz.mynews.Model.NYTimesTopStories;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewYorkTimesService {

    // Interface for calls (endpoints) on the New York Times API

    // Create Retrofit Object to do a request network
    public static final Retrofit retrofit = new Retrofit.Builder()
            // Root URL
            .baseUrl("https://api.nytimes.com/")
            // GSON converter
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // GET type of REST request
    @GET("svc/topstories/v2/{sectionValue}.json?api-key=ZFLWOr4Llj4dNQEA4itSAoJJm2ggwLJx")
    // sectionValue parameter return NYTimesTopStories Class
    Call<List<NYTimesTopStories>> getResult(@Path("sectionValue") String sectionValue);
}
