package com.jpz.mynews.Models;

public class TopStories {

    public String title(APIClient apiClient) {
    return apiClient.getResultList().get(0).getTitle();
    }

    public String sectionSubsection(APIClient apiClient) {
        String sectionSubsection;
        String section;
        String subsection;

            section = apiClient.getResultList().get(0).getSection();
            subsection = apiClient.getResultList().get(0).getSubsection();
            // If subsection is empty, don't call it
            if (subsection.equals(""))
                sectionSubsection = section;
            else
                sectionSubsection = section + " > " + subsection;
            return sectionSubsection;
    }

    public String date(APIClient apiClient) {
        return apiClient.getResultList().get(0).getPublishedDate();
    }

    public String image(APIClient apiClient) {
        return apiClient.getResultList().get(0).getMultimedia().get(0).getUrl();

    }

    public String url(APIClient apiClient) {
        return apiClient.getResultList().get(0).getShortUrl();
    }

}
