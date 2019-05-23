package com.jpz.mynews.Controllers.Utils;

import android.util.Log;

import com.jpz.mynews.Models.APIClient;
import com.jpz.mynews.Models.GenericNews;
import com.jpz.mynews.Models.TopStories;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class Streams {
    // Class for streams the New York Times APIs with Observables of RxJava

    private static String section;
    private static int period;

    private static String facetFields;
    private static String newsDesk;
    private static String sortOrder;
    private static int page;


    //private static List<GenericNews> genericNewsList;



    // Public method to start fetching the result for Top Stories
    public static Observable<APIClient> fetchTopStories(String section){
        // Get a Retrofit instance and the related Observable of the Interface
        Service service = Service.retrofit.create(Service.class);
        // Create the call on Top Stories API
        return service.getTopStories(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Public method to start fetching the result for Most Popular
    public static Observable<APIClient> fetchMostPopular(int period) {
        // Get a Retrofit instance and the related Observable of the Interface
        Service service = Service.retrofit.create(Service.class);
        // Create the call on Most Popular API
        return service.getMostPopular(period)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Public method to start fetching the result for Article Search
    public static Observable<APIClient>
    fetchArticleSearch(String facetFields, String newsDesk, String sortOrder, int page) {
        // Get a Retrofit instance and the related Observable of the Interface
        Service service = Service.retrofit.create(Service.class);
        // Create the call on Article Search API
        return service.getArticleSearch(facetFields, newsDesk, sortOrder, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // --------------------------------------------------------------------------------------


    public static Observable<List<String>> topStoriesToString() {
        return fetchTopStories(Service.API_TOPSTORIES_SECTION)
                .map(new Function<APIClient, List<String>>() {
                    @Override
                    public List<String> apply(APIClient apiClient) throws Exception {
                        String title;
                        String date;
                        String sectionSubsection;
                        String image;
                        String url;

                        title = apiClient.getResultList().get(0).getTitle();
                        date = apiClient.getResultList().get(0).getPublishedDate();
                        sectionSubsection = apiClient.getResultList().get(0).getSection();
                        image = apiClient.getResultList().get(0).getMultimedia().get(0).getUrl();
                        url = apiClient.getResultList().get(0).getShortUrl();

                        List<String> list = new ArrayList<>();
                        list.add(title);
                        list.add(date);
                        list.add(sectionSubsection);
                        list.add(image);
                        list.add(url);

                        Log.i("TAG", "je contiens" + list );

                        return list;
                    }
                });
    }

/*

    public static Observable<List<GenericNews>> generifyTopStories() {
        return fetchTopStories(Service.API_TOPSTORIES_SECTION)
                .map(new Function<APIClient, List<GenericNews>>() {
                    @Override
                    public List<GenericNews> apply(APIClient apiClient) throws Exception {

                        List<GenericNews> genericNewsList = new ArrayList<>();

                        GenericNews genericNews = new GenericNews();

                        genericNews.title = apiClient.getResultList().get(0).getTitle();
                        genericNews.date = apiClient.getResultList().get(0).getPublishedDate();
                        genericNews.sectionSubsection = apiClient.getResultList().get(0).getSection();


                        genericNews.news.add(genericNews.title);
                        genericNews.news.add(genericNews.date);
                        genericNews.news.add(genericNews.sectionSubsection);


                        Log.i("TAG", "je contiens" +  genericNewsList);

                        return genericNewsList;
                    }
                });
    }



    public static Observable<List<GenericNews>> convertTopStoriesToGeneric() {
        return fetchTopStories(Service.API_TOPSTORIES_SECTION)
                .map(new Function<APIClient, List<String>>() {
                    @Override
                    public List<String> apply(APIClient apiClient) throws Exception {
                        String title;
                        String date;
                        String sectionSubsection;
                        String image;
                        String url;

                        title = apiClient.getResultList().get(0).getTitle();
                        date = apiClient.getResultList().get(0).getPublishedDate();
                        sectionSubsection = apiClient.getResultList().get(0).getSection();
                        image = apiClient.getResultList().get(0).getMultimedia().get(0).getUrl();
                        url = apiClient.getResultList().get(0).getShortUrl();

                        List<String> list = new ArrayList<>();
                        list.add(title);
                        list.add(date);
                        list.add(sectionSubsection);
                        list.add(image);
                        list.add(url);

                        Log.i("TAG", "je contiens" + list );

                        return list;
                    }
                })
                .flatMap(list ->
                        Observable.fromIterable(list)
                                .map(item -> new GenericNews())
                                .toList()
                                .toObservable() // Required for RxJava 2.x
                );
    }




    public static Observable<List<TopStories>> testTopStories() {
        return fetchTopStories(Service.API_TOPSTORIES_SECTION)
                .map(new Function<APIClient, List<TopStories>>() {
                    @Override
                    public List<TopStories> apply(APIClient apiClient) throws Exception {

                        TopStories topStories = new TopStories();

                        topStories.getTopStoriesList();

                        return topStories.getTopStoriesList();
                    }
                });
    }


    public static Observable<List<GenericNews>> generifyTopStories() {
        return fetchTopStories(Service.API_TOPSTORIES_SECTION)
                .map(new Function<APIClient, List<TopStories>>() {
                    @Override
                    public List<TopStories> apply(APIClient apiClient) throws Exception {
                        return apiClient.getTopStoriesList();
                    }
                })
                .flatMap(list ->
                Observable.fromIterable(list)
                        .map(item -> new GenericNews())
                        .toList()
                        .toObservable() // Required for RxJava 2.x
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }



    public static Observable<List<GenericNews>> generifyMostPopular() {
        return fetchMostPopular(Service.API_PERIOD)
                .map(new Function<APIClient, List<MostPopular>>() {
                    @Override
                    public List<MostPopular> apply(APIClient apiClient) throws Exception {
                        return apiClient.getMostPopularList();
                    }
                })
                .flatMap(list ->
                        Observable.fromIterable(list)
                                .map(item -> new GenericNews())
                                .toList()
                                .toObservable() // Required for RxJava 2.x
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<List<GenericNews>> generifyArticleSearch() {
        return fetchArticleSearch(facetFields, newsDesk, sortOrder, page)
                .map(new Function<APIClient, List<ArticleSearch>>() {
                    @Override
                    public List<ArticleSearch> apply(APIClient apiClient) throws Exception {
                        return apiClient.getArticleSearchList();
                    }
                })
                .flatMap(list ->
                        Observable.fromIterable(list)
                                .map(item -> new GenericNews())
                                .toList()
                                .toObservable() // Required for RxJava 2.x
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
    */

}
