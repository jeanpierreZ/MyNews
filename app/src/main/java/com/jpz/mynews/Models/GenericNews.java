package com.jpz.mynews.Models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GenericNews {

    public String title;
    public String section;
    public String subSection;
    public String date;
    public String image;
    public String url;

       /*

    public void configureTopStories(APIClient apiClient) {
        title = apiClient.getResultList().get(0).getTitle();
        section = apiClient.getResultList().get(0).getSection();
        date = apiClient.getResultList().get(0).getPublishedDate();
        image = apiClient.getResultList().get(0).getMultimedia().get(0).getUrl();
        url = apiClient.getResultList().get(0).getShortUrl();
    }

    public String title ="title";
    public String date = "date";
    public String sectionSubsection = "section";
    public String image = "image";
    public String url = "url";


    private MostPopular mostPopular = new MostPopular();
    private TopStories topStories = new TopStories();
    private ArticleSearch articleSearch = new ArticleSearch();

    private List<GenericNews> genericNewsList = new ArrayList<>();


    public List getMostPopular(APIClient apiClient){
        title = mostPopular.title(apiClient);
        section = mostPopular.sectionSubsection(apiClient);
        date = mostPopular.date(apiClient);
        image = mostPopular.image(apiClient);
        url = mostPopular.url(apiClient);
        return genericNewsList;
    }


    public List getArticleSearch(){
        title = articleSearch.title();
        section = apiClient.getResultList().get(0).getSection();
        date = apiClient.getResultList().get(0).getPublishedDate();
        image = "";
        url = "";
        return genericNewsList;
    }
*/

}
