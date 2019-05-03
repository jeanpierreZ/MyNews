package com.jpz.mynews.Controllers.Utils;

import com.jpz.mynews.Models.NYTArticleSearch;
import com.jpz.mynews.Models.NYTMostPopular;
import com.jpz.mynews.Models.NYTTopStories;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NYTStreams {
    // Class for streams the New York Times APIs with Observables of RxJava

    // Public method to start fetching the result for NYTTopStories
    public static Observable<NYTTopStories> fetchTopStories(String section){
        // Get a Retrofit instance and the related Observable of the Interface
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        // Create the call on NYTTopStories API
        return nytService.getTopStories(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Public method to start fetching the result for NYTMostPopular
    public static Observable<NYTMostPopular> fetchMostPopular(int period) {
        // Get a Retrofit instance and the related Observable of the Interface
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        // Create the call on NYTMostPopular API
        return nytService.getMostPopular(period)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Public method to start fetching the result for NYTArticleSearch
    public static Observable<NYTArticleSearch>
    fetchArticleSearch(String fqSource, String fqNewsDesk, String sortOrder) {
        // Get a Retrofit instance and the related Observable of the Interface
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        // Create the call on NYTArticleSearch API
        return nytService.getArticleSearch(fqSource, fqNewsDesk, sortOrder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
