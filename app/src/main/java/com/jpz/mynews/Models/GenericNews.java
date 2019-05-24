package com.jpz.mynews.Models;

public class GenericNews {

    private String title;
    private String section;
    private String date;
    private String image;
    private String url;


    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public void configureTopStories(APIClient apiClient) {
        title = apiClient.getResultList().get(0).getTitle();
        section = apiClient.getResultList().get(0).getSection();
        date = apiClient.getResultList().get(0).getPublishedDate();
        image = apiClient.getResultList().get(0).getMultimedia().get(0).getUrl();
        url = apiClient.getResultList().get(0).getShortUrl();
    }
}
