package com.jpz.mynews.Models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GenericNews {

    public String title;
    public String section;
    public String date;
    public String image;
    public String url;


    public void configureTopStories(APIClient apiClient) {
        title = apiClient.getResultList().get(0).getTitle();
        section = apiClient.getResultList().get(0).getSection();
        date = apiClient.getResultList().get(0).getPublishedDate();
        image = apiClient.getResultList().get(0).getMultimedia().get(0).getUrl();
        url = apiClient.getResultList().get(0).getShortUrl();
    }


    /*

    public String title ="title";
    public String date = "date";
    public String sectionSubsection = "section";
    public String image = "image";
    public String url = "url";


    private MostPopular mostPopular = new MostPopular();
    private TopStories topStories = new TopStories();
    private ArticleSearch articleSearch = new ArticleSearch();

    private GenericNews genericNews = new GenericNews();


    public List getMostPopular(){
        title = mostPopular.title();
        sectionSubsection = mostPopular.sectionSubsection();
        date = mostPopular.date();
        image = mostPopular.image();
        url = mostPopular.url();
        return genericNewsList;
    }


    public List getArticleSearch(){
        title = articleSearch.title();
        sectionSubsection = apiClient.getResultList().get(0).getSection();
        date = apiClient.getResultList().get(0).getPublishedDate();
        image = "";
        url = "";
        return genericNewsList;
    }
*/

}
