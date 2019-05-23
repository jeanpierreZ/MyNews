package com.jpz.mynews.Models;

public class ArticleSearch {


    public String title(APIClient apiClient) {
        return apiClient.getResponse().getDocs().get(0).getHeadline().getMain();
    }

    public String sectionSubsection(APIClient apiClient) {
        return apiClient.getResponse().getDocs().get(0).getSectionName();
    }

    public String date(APIClient apiClient) {
        return apiClient.getResponse().getDocs().get(0).getPubDate();
    }

    public String image(APIClient apiClient) {
        return "https://www.nytimes.com/"
                + apiClient.getResponse().getDocs().get(0).getMultimedia().get(0).getUrl();

    }

    public String url(APIClient apiClient) {
        return apiClient.getResponse().getDocs().get(0).getWebUrl();
    }


}
