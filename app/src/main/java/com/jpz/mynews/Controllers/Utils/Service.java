package com.jpz.mynews.Controllers.Utils;

import com.jpz.mynews.Models.APIClient;
import com.jpz.mynews.Models.ArticleSearchResponse;
import com.jpz.mynews.Models.MostPopularResponse;
import com.jpz.mynews.Models.TopStoriesResponse;

import io.reactivex.Observable;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    // Interface for requests the New York Times APIs

    // Fields to complete requests

    String API_BASE_URL = "https://api.nytimes.com/svc/";
    String API_KEY = "ZFLWOr4Llj4dNQEA4itSAoJJm2ggwLJx";

    String API_TOPSTORIES_SECTION = "home";

    // Time period : 1, 7, or 30 days ; the following values are allowed: 1, 7, 30
    int API_PERIOD = 7;

    String API_DESK_ENVIRONMENT = "Environment";
    String API_DESK_FINANCIAL = "Financial";
    String API_DESK_FOREIGN = "Foreign";
    String API_DESK_SCIENCE = "Science";
    String API_DESK_SPORTS = "Sports";
    String API_DESK_TECHNOLOGY = "Technology";

    String API_FACET_FIELDS = "news_desk";

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

    // GET type of REST request for Top Stories
    @GET("topstories/v2/{section}.json?api-key=" + API_KEY)
    Observable<TopStoriesResponse>
    getTopStories(@Path("section") String sectionValue);

    // GET type of REST request for Most Popular
    @GET("mostpopular/v2/viewed/{period}.json?api-key=" + API_KEY)
    Observable<MostPopularResponse>
    getMostPopular(@Path("period") int period);

    // GET type of REST request for Article Search
    @GET("search/v2/articlesearch.json?api-key=" + API_KEY)
    Observable<ArticleSearchResponse>
    getArticleSearch(@Query("facet_fields") String facetFields, @Query("fq") String newsDesk,
                     @Query("sort") String sortOrder, @Query("page") int page);

}