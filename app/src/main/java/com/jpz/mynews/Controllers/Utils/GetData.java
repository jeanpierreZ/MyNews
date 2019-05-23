package com.jpz.mynews.Controllers.Utils;

import android.util.Log;

import com.jpz.mynews.Models.API;
import com.jpz.mynews.Models.APIClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class GetData {

    private APIClient apiClient = new APIClient();

    public String title(API api) {
        // Display title of an article
        String title = "";
        switch (api) {
            case TopStories:
                title = apiClient.getResultList().get(0).getTitle();
                break;
            case MostPopular:
                title = apiClient.getResultList().get(0).getTitle();
                break;
            case Foreign:
                title = apiClient.getResponse().getDocs().get(0).getHeadline().getMain();
                break;
        }
        return title;
    }

    public String sectionSubsection(API api) {
        // Display section & subsection of an article
        String sectionSubsection = "";
        String section;
        String subsection;
        switch (api) {
            case TopStories:
                section = apiClient.getResultList().get(0).getSection();
                subsection = apiClient.getResultList().get(0).getSubsection();
                // If subsection is empty, don't call it
                if (subsection.equals(""))
                    sectionSubsection = section;
                else
                    sectionSubsection = section + " > " + subsection;
                break;
            case MostPopular:
                sectionSubsection = apiClient.getResultList().get(0).getSection();
                break;
            case Foreign:
                sectionSubsection = apiClient.getResponse().getDocs().get(0).getSectionName();
                break;
        }
        return sectionSubsection;
    }

    public String date(API api) {
        // Display date of an article
        String date = "";
        switch (api) {
            case TopStories:
                date = apiClient.getResultList().get(0).getPublishedDate();
                break;
            case MostPopular:
                date = apiClient.getResultList().get(0).getPublishedDate();
                break;
            case Foreign:
                date = apiClient.getResponse().getDocs().get(0).getPubDate();
                break;
        }
        return date;
    }

    public String convertDate(String DateAPI) {
        // Build date in dd/MM/yyyy for PubDate
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());

        Date date;
        String newDate = "";

        try {
            date = inputFormat.parse(DateAPI);
            newDate = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "ParseException - dateFormat");
        }
        return newDate;
    }

    public String image(API api) {
        // Display date of an article
        String image = "";
        switch (api) {
            case TopStories:
                image = apiClient.getResultList().get(0).getMultimedia().get(0).getUrl();
                break;
            case MostPopular:
                image = apiClient.getResultList().get(0).getMedia().get(0).getMediaMetadata().get(0).getUrl();
                break;
            case Foreign:
                image = "https://www.nytimes.com/"
                        + apiClient.getResponse().getDocs().get(0).getMultimedia().get(0).getUrl();
                break;
        }
        return image;
    }

    public String url(API api) {
        // Display an article in a WebView
        String url ="";
        switch (api) {
            case TopStories:
                url = apiClient.getResultList().get(0).getShortUrl();
            break;
            case MostPopular:
                url = apiClient.getResultList().get(0).getUrl();
            break;
            case Foreign:
                url = apiClient.getResponse().getDocs().get(0).getWebUrl();
                break;
        }
        return url;
    }

}

