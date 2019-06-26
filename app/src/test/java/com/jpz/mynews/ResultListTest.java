package com.jpz.mynews;

import com.jpz.mynews.models.MediaMetadatum;
import com.jpz.mynews.models.Medium;
import com.jpz.mynews.models.MostPopularResponse;
import com.jpz.mynews.models.Multimedium;
import com.jpz.mynews.models.Result;
import com.jpz.mynews.models.TopStoriesResponse;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ResultListTest {

    private TopStoriesResponse topStoriesResponse;
    private MostPopularResponse mostPopularResponse;

    @Before
    public void setTopStoriesResponse()
    {
        List<Result> resultList = new ArrayList<>();
        List<Multimedium> multimedia = new ArrayList<>();
        topStoriesResponse = new TopStoriesResponse();

        // Set tests for resultList
        Result resultTest = new Result();
        resultTest.setTitle("Title");
        resultTest.setSection("Section");
        resultTest.setSubsection("Subsection");
        resultTest.setPublishedDate("06052019");
        resultTest.setShortUrl("https://api.nytimes.com/svc/");

        // Set a test for multimedia
        Multimedium multimediumTest = new Multimedium();
        multimediumTest.setUrl("https:-static01.nyt.com-images-2019-05-05-world-image-thumbStandard.jpg");
        multimedia.add(multimediumTest);

        // Set list
        resultTest.setMultimedia(multimedia);

        // Add fields twice in the list
        resultList.add(resultTest);
        resultList.add(resultTest);
        topStoriesResponse.setResultList(resultList);
    }

    @Test
    public void testTopStoriesResponse()
    {
        for (Result result : topStoriesResponse.getResultList())
        {
            assertEquals("Title", result.getTitle());
            assertEquals("Section", result.getSection());
            assertEquals("Subsection", result.getSubsection());
            assertEquals("06052019", result.getPublishedDate());
            assertEquals("https:-static01.nyt.com-images-2019-05-05-world-image-thumbStandard.jpg",
                    result.getMultimedia().get(0).getUrl());
            assertEquals("https://api.nytimes.com/svc/", result.getShortUrl());
        }
    }

    @Before
    public void setMostPopularResponse()
    {
        List<Result> resultList = new ArrayList<>();
        List<Medium> media = new ArrayList<>();
        List<MediaMetadatum> mediaMetadata = new ArrayList<>();
        mostPopularResponse = new MostPopularResponse();

        // Set tests for resultList
        Result resultTest = new Result();
        resultTest.setTitle("Title");
        resultTest.setSection("Section");
        resultTest.setSubsection("Subsection");
        resultTest.setPublishedDate("06052019");
        resultTest.setUrl("https://api.nytimes.com/svc/");

        // Set a test for media
        Medium mediumTest = new Medium();
        media.add(mediumTest);

        // Set a test for mediaMetadata
        MediaMetadatum mediaMetadatumTest = new MediaMetadatum();
        mediaMetadatumTest.setUrl("https:-static01.nyt.com-images-2019-05-05-world-image-thumbStandard.jpg");
        mediaMetadata.add(mediaMetadatumTest);

        // Set lists
        resultTest.setMedia(media);
        mediumTest.setMediaMetadata(mediaMetadata);

        // Add fields twice in the list
        resultList.add(resultTest);
        resultList.add(resultTest);
        mostPopularResponse.setResultList(resultList);
    }

    @Test
    public void testMostPopularResponse()
    {
        for (Result result : mostPopularResponse.getResultList())
        {
            assertEquals("Title", result.getTitle());
            assertEquals("Section", result.getSection());
            assertEquals("Subsection", result.getSubsection());
            assertEquals("06052019", result.getPublishedDate());
            assertEquals("https:-static01.nyt.com-images-2019-05-05-world-image-thumbStandard.jpg",
                    result.getMedia().get(0).getMediaMetadata().get(0).getUrl());
            assertEquals("https://api.nytimes.com/svc/", result.getUrl());
        }
    }

}
