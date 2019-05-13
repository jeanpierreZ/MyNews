package com.jpz.mynews;

import android.support.test.runner.AndroidJUnit4;
import com.jpz.mynews.Controllers.Utils.NYTStreams;
import com.jpz.mynews.Controllers.Utils.NYTService;
import com.jpz.mynews.Models.TopStories;

import org.junit.Test;
import org.junit.runner.RunWith;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import static junit.framework.TestCase.assertEquals;


@RunWith(AndroidJUnit4.class)
public class ObservablesTest {

    @Test
    public void fetchTopStoriesTest() throws Exception {
        // Get the stream
        Observable<TopStories> topStoriesObservable = NYTStreams
                .fetchTopStories(NYTService.API_TOPSTORIES_SECTION);
        // Create a new TestObserver
        TestObserver<TopStories> topStoriesTestObserver = new TestObserver<>();
        // Launch observable
        topStoriesObservable.subscribeWith(topStoriesTestObserver)
                .assertNoErrors() // Check if no errors
                .assertNoTimeout() // Check if no Timeout
                .awaitTerminalEvent(); // Await the stream terminated before continue

        // Get result of topStories fetched
        TopStories topStoriesFetched = topStoriesTestObserver.values().get(0);

        /*
        Verify if the GET request connection is OK ( = 200) with getStatus.
        It indicates that the REST API successfully carried out whatever action the client requested,
        and that no more specific code in the 2xx series is appropriate.
        Unlike the 204 status code, a 200 response should include a response body.
        GET method : an entity corresponding to the requested resource is sent in the response.
         */
        assertEquals("OK", topStoriesFetched.getStatus());
    }
}
