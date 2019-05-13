package com.jpz.mynews.Controllers.Utils;

import com.jpz.mynews.Models.ArticleSearch;
import com.jpz.mynews.Models.ModelAPI;
import com.jpz.mynews.Models.MostPopular;
import com.jpz.mynews.Models.TopStories;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NYTStreams {
    // Class for streams the New York Times APIs with Observables of RxJava

    // Public method to start fetching the result for TopStories
    public static Observable<ModelAPI> fetchTopStories(String section){
        // Get a Retrofit instance and the related Observable of the Interface
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        // Create the call on TopStories API
        return nytService.getTopStories(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Public method to start fetching the result for MostPopular
    public static Observable<ModelAPI> fetchMostPopular(int period) {
        // Get a Retrofit instance and the related Observable of the Interface
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        // Create the call on MostPopular API
        return nytService.getMostPopular(period)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Public method to start fetching the result for ArticleSearch
    public static Observable<ArticleSearch>
    fetchArticleSearch(String facetFields, String newsDesk, String sortOrder, int page) {
        // Get a Retrofit instance and the related Observable of the Interface
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        // Create the call on ArticleSearch API
        return nytService.getArticleSearch(facetFields, newsDesk, sortOrder, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
