package com.jpz.mynews.Controllers.Utils;

import android.util.Log;

import com.jpz.mynews.Models.Doc;
import com.jpz.mynews.Models.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class GetData {

    private Result result = new Result();
    private Doc doc = new Doc();


    public List<GetData> getDataList = new ArrayList<>();


    public String title() {
        // Display title of an article
        String title = result.getTitle();
        String titleArticleSearch = doc.getHeadline().getMain();

        if (!title.equals(""))
            return title;
        else
            return titleArticleSearch;
    }

    public String sectionSubsection() {
        // Build string for section and subsection and display it
        String sectionSubsection;
        String section = result.getSection();
        String subSection = result.getSubsection();
        String sectionName = doc.getSectionName();

        // If sectionName of Article Search is available
        if (!sectionName.equals(""))
            return sectionName;

        // If subsection is empty, don't call it
        else if (subSection.equals(""))
            sectionSubsection = section;
        else
            sectionSubsection = section + " > " + subSection;
        return sectionSubsection;
    }


    public String date() {
        // Display date of an article
        String date = result.getPublishedDate();
        String dateArticleSearch = doc.getPubDate();

        if (!date.equals(""))
            return date;
        else
            return dateArticleSearch;
    }

    public String convertDate(String topStoriesDate) {
        // Build date in dd/MM/yyyy for PubDate
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());

        Date date;
        String newDate = "";

        try {
            date = inputFormat.parse(topStoriesDate);
            newDate = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "ParseException - dateFormat");
        }
        return newDate;
    }

    public String image() {
        // Display image of an article
        String image = "";
        String imageMostPop = result.getMedia().get(0).getMediaMetadata().get(0).getUrl();
        String imageTopStories = result.getMultimedia().get(0).getUrl();
        String imageArticleSearch = "https://www.nytimes.com/" + doc.getMultimedia().get(0).getUrl();

        // If MediaMetadatum is empty don't display the photo
        if (result.getMedia().get(0).getMediaMetadata().size() != 0)
            image = imageMostPop;

        // If Multimedium is empty don't display the photo and imageSearch has not https address
        else if (result.getMultimedia().size() != 0 && !imageArticleSearch.substring(0,4).equals("https"))
            image = imageTopStories;

        // If Multimedium is empty don't display the photo
        else if (result.getMultimedia().size() != 0)
            image = imageArticleSearch;

        return image;
    }

    public String shorturl() {

        return result.getShortUrl();
    }

}




