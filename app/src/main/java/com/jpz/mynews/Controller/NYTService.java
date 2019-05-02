package com.jpz.mynews.Controller;

import com.jpz.mynews.Model.NYTArticleSearch;
import com.jpz.mynews.Model.NYTMostPopular;
import com.jpz.mynews.Model.NYTTopStories;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NYTService {
    // Interface for calls (endpoints) on the New York Times APIs

    String API_BASE_URL = "https://api.nytimes.com/svc/";
    String API_KEY = "ZFLWOr4Llj4dNQEA4itSAoJJm2ggwLJx";
    String API_TOPSTORIES_SECTION = "home";
    // Time period : 1, 7, or 30 days ; the following values are allowed: 1, 7, 30
    int API_PERIOD = 1;
    String API_FILTER_QUERY_NEWS_DESK = "Technology";
    String API_FILTER_QUERY_SOURCE = "The New York Times";
    // Sort order ; the following values are allowed: newest, oldest, relevance
    String API_FILTER_SORT_ORDER = "newest";

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
    // GET type of REST request for Top Stories
    @GET("topstories/v2/{section}.json")
    Observable<NYTTopStories>
    getTopStories(@Path("section") String sectionValue, @Query("api-key") String apiKey);

    // GET type of REST request for Most Popular
    @GET("mostpopular/v2/viewed/{period}.json")
    Observable<NYTMostPopular>
    getMostPopular(@Path("period") int period, @Query("api-key") String apiKey);

    // GET type of REST request for Article Search
    @GET("search/v2/articlesearch.json")
    Observable<NYTArticleSearch>
    getArticleSearch(@Query("fq=source") String fqSource, @Query("fq=news_desk") String fqNewsDesk,
                     @Query("sort") String sortOrder, @Query("api-key") String apiKey);
}
