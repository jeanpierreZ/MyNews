package com.jpz.mynews.Controllers.Utils;

import com.jpz.mynews.Models.ArticleSearchResponse;
import com.jpz.mynews.Models.Doc;
import com.jpz.mynews.Models.GenericNews;
import com.jpz.mynews.Models.MostPopularResponse;

import com.jpz.mynews.Models.Result;
import com.jpz.mynews.Models.TopStoriesResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class APIClient {
    // Class for streams the New York Times APIs with Observables of RxJava

    // Public method to start fetching the result for Top Stories
    public static Observable<TopStoriesResponse> fetchTopStories(String section){
        // Get a Retrofit instance and the related Observable of the Interface
        Service service = Service.retrofit.create(Service.class);
        // Create the call on Top Stories API
        return service.getTopStories(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Public method to start fetching the result for Top Stories
    public static Observable<List<GenericNews>> getTopStoriesNews(){
        return fetchTopStories(Service.API_TOPSTORIES_SECTION)
                .map(new Function<TopStoriesResponse, List<Result>>() {
                    @Override
                    public List<Result> apply(TopStoriesResponse response) throws Exception {

                        return response.getResultList();
                    }
                }).map(new Function<List<Result>, List<GenericNews>>() {
                    @Override
                    public List<GenericNews> apply(List<Result> resultList) throws Exception {

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

    // Public method to start fetching the result for Most Popular
    public static Observable<MostPopularResponse> fetchMostPopular(int period) {
        // Get a Retrofit instance and the related Observable of the Interface
        Service service = Service.retrofit.create(Service.class);
        // Create the call on Most Popular API
        return service.getMostPopular(period)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Public method to start fetching the result for Top Stories
    public static Observable<List<GenericNews>> getMostPopularNews(){
        return fetchMostPopular(Service.API_PERIOD)
                .map(new Function<MostPopularResponse, List<Result>>() {
                    @Override
                    public List<Result> apply(MostPopularResponse response) throws Exception {

                        return response.getResultList();
                    }
                }).map(new Function<List<Result>, List<GenericNews>>() {
                    @Override
                    public List<GenericNews> apply(List<Result> resultList) throws Exception {

                        List<GenericNews> genericNewsList = new ArrayList<>();

                        for(Result result : resultList){

                            GenericNews genericNews = new GenericNews();

                            genericNews.title = result.getTitle();
                            genericNews.date = result.getPublishedDate();
                            genericNews.section = result.getSection();
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
    public static Observable<ArticleSearchResponse>
    fetchArticleSearch(String facetFields, String newsDesk, String sortOrder, int page) {
        // Get a Retrofit instance and the related Observable of the Interface
        Service service = Service.retrofit.create(Service.class);
        // Create the call on Article Search API
        return service.getArticleSearch(facetFields, newsDesk, sortOrder, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Public method to start fetching the result for Top Stories
    public static Observable<List<GenericNews>> getArticleSearchNews(String desk, int page){
        return fetchArticleSearch(Service.API_FACET_FIELDS, desk,
                Service.API_FILTER_SORT_ORDER, page)
                .map(new Function<ArticleSearchResponse, List<Doc>>() {
                    @Override
                    public List<Doc> apply(ArticleSearchResponse response) throws Exception {

                        return response.getResponse().getDocs();
                    }
                }).map(new Function<List<Doc>, List<GenericNews>>() {
                    @Override
                    public List<GenericNews> apply(List<Doc> docList) throws Exception {

                        List<GenericNews> genericNewsList = new ArrayList<>();

                        for(Doc doc : docList){

                            GenericNews genericNews = new GenericNews();

                            genericNews.title = doc.getHeadline().getMain();
                            genericNews.date = doc.getPubDate();
                            genericNews.section = doc.getSectionName();
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