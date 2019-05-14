package com.jpz.mynews.Controllers.Utils;

import com.jpz.mynews.Models.ModelAPI;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Streams {
    // Class for streams the New York Times APIs with Observables of RxJava

    // Public method to start fetching the result for Top Stories
    public static Observable<ModelAPI> fetchTopStories(String section){
        // Get a Retrofit instance and the related Observable of the Interface
        Service service = Service.retrofit.create(Service.class);
        // Create the call on Top Stories API
        return service.getTopStories(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Public method to start fetching the result for Most Popular
    public static Observable<ModelAPI> fetchMostPopular(int period) {
        // Get a Retrofit instance and the related Observable of the Interface
        Service service = Service.retrofit.create(Service.class);
        // Create the call on Most Popular API
        return service.getMostPopular(period)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Public method to start fetching the result for Article Search
    public static Observable<ModelAPI>
    fetchArticleSearch(String facetFields, String newsDesk, String sortOrder, int page) {
        // Get a Retrofit instance and the related Observable of the Interface
        Service service = Service.retrofit.create(Service.class);
        // Create the call on Article Search API
        return service.getArticleSearch(facetFields, newsDesk, sortOrder, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
