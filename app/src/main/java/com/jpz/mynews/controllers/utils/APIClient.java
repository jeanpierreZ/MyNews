package com.jpz.mynews.controllers.utils;

import com.jpz.mynews.models.ArticleSearchResponse;
import com.jpz.mynews.models.Doc;
import com.jpz.mynews.models.GenericNews;
import com.jpz.mynews.models.MostPopularResponse;

import com.jpz.mynews.models.Result;
import com.jpz.mynews.models.TopStoriesResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class APIClient {
    // Class for streams of the New York Times APIs with Observables of RxJava

    @SuppressWarnings("SameParameterValue")
    // Public method to start fetching the result for Top Stories
    private static Observable<TopStoriesResponse> fetchTopStories(String section){
        // Get a Retrofit instance and the related Observable of the Interface
        Service service = Service.retrofit.create(Service.class);
        // Create the call on Top Stories API
        return service.getTopStories(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Public method to generify the result list of Top Stories
    public static Observable<List<GenericNews>> getTopStoriesNews(){
        return fetchTopStories(Service.API_TOPSTORIES_SECTION)
                .map(new Function<TopStoriesResponse, List<Result>>() {
                    @Override
                    public List<Result> apply(TopStoriesResponse response) {
                        return response.getResultList();
                    }
                }).map(new Function<List<Result>, List<GenericNews>>() {
                    @Override
                    public List<GenericNews> apply(List<Result> resultList) {

                        List<GenericNews> genericNewsList = new ArrayList<>();

                        for(Result result : resultList){

                            GenericNews genericNews = new GenericNews();

                            genericNews.title = result.getTitle();
                            genericNews.date = result.getPublishedDate();
                            genericNews.section = result.getSection();
                            genericNews.subSection = result.getSubsection();
                            // If Multimedium is empty don't display the photo
                            if ( result.getMultimedia().size() != 0)
                            genericNews.image = result.getMultimedia().get(0).getUrl();
                            genericNews.url = result.getShortUrl();

                            genericNewsList.add(genericNews);
                        }
                        return genericNewsList;
                    }
                });
    }

    @SuppressWarnings("SameParameterValue")
    // Public method to start fetching the result for Most Popular
    private static Observable<MostPopularResponse> fetchMostPopular(int period) {
        // Get a Retrofit instance and the related Observable of the Interface
        Service service = Service.retrofit.create(Service.class);
        // Create the call on Most Popular API
        return service.getMostPopular(period)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Public method to generify the result list of Most Popular
    public static Observable<List<GenericNews>> getMostPopularNews(){
        return fetchMostPopular(Service.API_PERIOD)
                .map(new Function<MostPopularResponse, List<Result>>() {
                    @Override
                    public List<Result> apply(MostPopularResponse response) {
                        return response.getResultList();
                    }
                }).map(new Function<List<Result>, List<GenericNews>>() {
                    @Override
                    public List<GenericNews> apply(List<Result> resultList) {

                        List<GenericNews> genericNewsList = new ArrayList<>();

                        for(Result result : resultList){

                            GenericNews genericNews = new GenericNews();

                            genericNews.title = result.getTitle();
                            genericNews.date = result.getPublishedDate();
                            genericNews.section = result.getSection();
                            genericNews.subSection = result.getSubsection();
                            // If MediaMetadatum is empty don't display the photo
                            if ( result.getMedia().get(0).getMediaMetadata().size() != 0)
                            genericNews.image = result.getMedia().get(0)
                                    .getMediaMetadata().get(0).getUrl();
                            genericNews.url = result.getUrl();

                            genericNewsList.add(genericNews);
                        }
                        return genericNewsList;
                    }
                });
    }

    // Public method to start fetching the result for Article Search
    @SuppressWarnings("WeakerAccess")
    public static Observable<ArticleSearchResponse>
    fetchArticleSearch(String newsDesk, String sortOrder, int page, String query,
                       String beginDate, String endDate) {
        // Get a Retrofit instance and the related Observable of the Interface
        Service service = Service.retrofit.create(Service.class);
        // Create the call on Article Search API
        return service.getArticleSearch(newsDesk, sortOrder, page, query, beginDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Public method to generify the result list of Article Search
    public static Observable<List<GenericNews>>
    getArticleSearchNews(String desk, int page, String query, String beginDate, String endDate){
        return fetchArticleSearch(desk, Service.API_FILTER_SORT_ORDER, page, query, beginDate, endDate)
                .map(new Function<ArticleSearchResponse, List<Doc>>() {
                    @Override
                    public List<Doc> apply(ArticleSearchResponse response) {
                        return response.getResponse().getDocs();
                    }
                }).map(new Function<List<Doc>, List<GenericNews>>() {
                    @Override
                    public List<GenericNews> apply(List<Doc> docList) {

                        List<GenericNews> genericNewsList = new ArrayList<>();

                        for(Doc doc : docList){

                            GenericNews genericNews = new GenericNews();

                            genericNews.title = doc.getHeadline().getMain();
                            genericNews.date = doc.getPubDate();
                            genericNews.section = doc.getSectionName();
                            genericNews.subSection = doc.getSubsectionName();
                            // If Multimedium is empty don't display the photo
                            if ( doc.getMultimedia().size() != 0)
                                genericNews.image = "https://www.nytimes.com/"
                                        + doc.getMultimedia().get(0).getUrl();
                            genericNews.url = doc.getWebUrl();

                            genericNewsList.add(genericNews);
                        }
                        return genericNewsList;
                    }
                });
    }
}