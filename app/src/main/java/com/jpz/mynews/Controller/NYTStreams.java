package com.jpz.mynews.Controller;

import com.jpz.mynews.Model.NYTArticleSearch;
import com.jpz.mynews.Model.NYTMostPopular;
import com.jpz.mynews.Model.NYTTopStories;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NYTStreams {
    // Class for streams the New York Times APIs with Observables of RxJava

    // Public method to start fetching the result for NYTTopStories
    public static Observable<NYTTopStories> fetchTopStories(String section, String apiKey){
        // Get a Retrofit instance and the related Observable of the Interface
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        // Create the call on NYTTopStories API
        return nytService.getTopStories(section, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Public method to start fetching the result for NYTMostPopular
    public static Observable<NYTMostPopular> fetchMostPopular(int period, String apiKey) {
        // Get a Retrofit instance and the related Observable of the Interface
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        // Create the call on NYTTopStories API
        return nytService.getMostPopular(period, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Public method to start fetching the result for NYTMostPopular
    public static Observable<NYTArticleSearch>
    fetchArticleSearch(String fqSource, String fqNewsDesk, String sortOrder, String apiKey) {
        // Get a Retrofit instance and the related Observable of the Interface
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        // Create the call on NYTTopStories API
        return nytService.getArticleSearch(fqSource, fqNewsDesk, sortOrder, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
