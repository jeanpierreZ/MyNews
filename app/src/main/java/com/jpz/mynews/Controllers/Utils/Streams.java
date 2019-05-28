package com.jpz.mynews.Controllers.Utils;

import android.util.Log;

import com.jpz.mynews.Models.APIClient;
import com.jpz.mynews.Models.ArticleSearch;
import com.jpz.mynews.Models.ArticleSearchResponse;
import com.jpz.mynews.Models.Doc;
import com.jpz.mynews.Models.GenericNews;
import com.jpz.mynews.Models.MostPopular;
import com.jpz.mynews.Models.MostPopularResponse;

import com.jpz.mynews.Models.TopStories;
import com.jpz.mynews.Models.TopStoriesResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class Streams {
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
    public static Observable<List<GenericNews>> fetchStoriesToGeneric(){
        return fetchTopStories(Service.API_TOPSTORIES_SECTION)
                .map(new Function<TopStoriesResponse, List<TopStories>>() {
                    @Override
                    public List<TopStories> apply(TopStoriesResponse response) throws Exception {

                        return response.getTopStoriesList();
                    }
                }).map(new Function<List<TopStories>, List<GenericNews>>() {
                    @Override
                    public List<GenericNews> apply(List<TopStories> topStoriesList) throws Exception {

                        List<GenericNews> genericNewsList = new ArrayList<>();

                        for(TopStories topStories : topStoriesList){

                            GenericNews genericNews = new GenericNews();

                            genericNews.title = topStories.getTitle();
                            genericNews.date = topStories.getPublishedDate();
                            genericNews.section = topStories.getSection();
                            genericNews.subSection = topStories.getSubsection();
                            genericNews.image = topStories.getTopStoriesMultimediaList().get(0).getUrl();
                            genericNews.url = topStories.getShortUrl();

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
    public static Observable<List<GenericNews>> fetchPopularToGeneric(){
        return fetchMostPopular(Service.API_PERIOD)
                .map(new Function<MostPopularResponse, List<MostPopular>>() {
                    @Override
                    public List<MostPopular> apply(MostPopularResponse response) throws Exception {

                        return response.getMostPopularList();
                    }
                }).map(new Function<List<MostPopular>, List<GenericNews>>() {
                    @Override
                    public List<GenericNews> apply(List<MostPopular> mostPopularList) throws Exception {

                        List<GenericNews> genericNewsList = new ArrayList<>();

                        for(MostPopular mostPopular : mostPopularList){

                            GenericNews genericNews = new GenericNews();

                            genericNews.title = mostPopular.getTitle();
                            genericNews.date = mostPopular.getPublishedDate();
                            genericNews.section = mostPopular.getSection();
                            genericNews.image = mostPopular.getMostPopularMediaList().get(0)
                                    .getMostPopularMediaMetadaList().get(0).getUrl();
                            genericNews.url = mostPopular.getUrl();

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
    public static Observable<List<GenericNews>> fetchSearchToGeneric(){
        return fetchArticleSearch(Service.API_FACET_FIELDS, Service.API_DESK_FOREIGN,
                Service.API_FILTER_SORT_ORDER, 0)
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
                            // If Multimedia is empty don't display the photo
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
