package com.jpz.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIClient {

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    @SerializedName("results")
    @Expose
    private List<Result> resultList = null;

    public List<Result> getResultList() {
        return resultList;
    }

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }


    /*
    private List<TopStories> topStoriesList;

    public List<TopStories> getTopStoriesList() {
        return topStoriesList;
    }

    private List<MostPopular> mostPopularList;

    public List<MostPopular> getMostPopularList() {
        return mostPopularList;

    } private List<ArticleSearch> articleSearchList;

    public List<ArticleSearch> getArticleSearchList() {
        return articleSearchList;
    }
*/

}
