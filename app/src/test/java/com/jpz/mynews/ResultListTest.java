package com.jpz.mynews;

import com.jpz.mynews.Models.MediaMetadatum;
import com.jpz.mynews.Models.Medium;
import com.jpz.mynews.Models.MostPopularResponse;
import com.jpz.mynews.Models.Multimedium;
import com.jpz.mynews.Models.Result;
import com.jpz.mynews.Models.TopStoriesResponse;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

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
    public void testTopStoriesResponse() throws Exception
    {
        for (Result result : topStoriesResponse.getResultList())
        {
            assertThat("String is in the field", result.getTitle() != null);
            assertThat("String is in the field", result.getSection() != null);
            assertThat("String is in the field", result.getSubsection() != null);
            assertThat("String is in the field", result.getPublishedDate() != null);
            assertThat("String is in the field",
                    result.getMultimedia().get(0).getUrl() != null);
            assertThat("String is in the field", result.getShortUrl() != null);
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
    public void testMostPopularResponse() throws Exception
    {
        for (Result result : mostPopularResponse.getResultList())
        {
            assertThat("String is in the field", result.getTitle() != null);
            assertThat("String is in the field", result.getSection() != null);
            assertThat("String is in the field", result.getSubsection() != null);
            assertThat("String is in the field", result.getPublishedDate() != null);
            assertThat("String is in the field",
                    result.getMedia().get(0).getMediaMetadata().get(0).getUrl() != null);
            assertThat("String is in the field", result.getUrl() != null);
        }
    }

}
