package com.jpz.mynews;

import com.jpz.mynews.Models.ArticleSearchResponse;
import com.jpz.mynews.Models.Doc;
import com.jpz.mynews.Models.Headline;
import com.jpz.mynews.Models.Multimedium;
import com.jpz.mynews.Models.Response;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class DocListTest {

    private ArticleSearchResponse articleSearchResponse;

    @Before
    public void setArticleSearchResponse()
    {
        Response response = new Response();
        List<Doc> docList = new ArrayList<>();
        List<Multimedium> multimedia = new ArrayList<>();
        articleSearchResponse = new ArticleSearchResponse();

        // Set a test for headline
        Headline headlineTest = new Headline();
        headlineTest.setMain("Title");

        // Set tests for docList
        Doc docTest = new Doc();
        docTest.setSectionName("Section");
        docTest.setSubsectionName("Subsection");
        docTest.setPubDate("06052019");
        docTest.setWebUrl("https://api.nytimes.com/svc/");

        // Set a test for multimedia
        Multimedium multimediumTest = new Multimedium();
        multimediumTest.setUrl("https:-static01.nyt.com-images-2019-05-05-world-image-thumbStandard.jpg");
        multimedia.add(multimediumTest);

        // Set lists & object
        response.setDocs(docList);
        docTest.setHeadline(headlineTest);
        docTest.setMultimedia(multimedia);

        // Add fields twice in the list
        docList.add(docTest);
        docList.add(docTest);
        articleSearchResponse.setResponse(response);
    }

    @Test
    public void testArticleSearchResponse() throws Exception
    {
        for (Doc doc : articleSearchResponse.getResponse().getDocs())
        {
            assertThat("String is in the field", doc.getHeadline().getMain() != null);
            assertThat("String is in the field", doc.getSectionName() != null);
            assertThat("String is in the field", doc.getSubsectionName() != null);
            assertThat("String is in the field", doc.getPubDate() != null);
            assertThat("String is in the field",
                    doc.getMultimedia().get(0).getUrl() != null);
            assertThat("String is in the field", doc.getWebUrl() != null);
        }
    }

}
