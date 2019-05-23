package com.jpz.mynews.Models;

public class MostPopular {

    public String title(APIClient apiClient) {
        return apiClient.getResultList().get(0).getTitle();
    }

    public String sectionSubsection(APIClient apiClient) {

        return apiClient.getResultList().get(0).getSection();
    }

    public String date(APIClient apiClient) {
        return apiClient.getResultList().get(0).getPublishedDate();
    }

    public String image(APIClient apiClient) {
        return apiClient.getResultList().get(0).getMedia().get(0).getMediaMetadata().get(0).getUrl();

    }

    public String url(APIClient apiClient) {
        return apiClient.getResultList().get(0).getUrl();
    }

}
