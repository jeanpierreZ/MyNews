package com.jpz.mynews.Controller;

import android.support.annotation.Nullable;
import com.jpz.mynews.Model.NYTTopStories;
import java.lang.ref.WeakReference;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewYorkTimesCalls {
    // Execute Calls to New York Times API in background (Asyntask)

    // Creating a callback
    public interface Callbacks {
        void onResponse(@Nullable List<NYTTopStories> topStories);
        void onFailure();
    }

    // Public method to start fetching result by NYT TopStories
    public static void fetchSectionValue(Callbacks callbacks, String sectionValue, String key){

        // Create a weak reference to callback (avoid memory leaks)
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<>(callbacks);

        // Get a Retrofit instance and the related endpoints in the Interface
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofit.create(NewYorkTimesService.class);

        // Create the call on NYTTopStories API
        Call<List<NYTTopStories>> call = newYorkTimesService.getTopStories(sectionValue, key);

        // Start the call
        call.enqueue(new Callback<List<NYTTopStories>>() {
            @Override
            public void onResponse(Call<List<NYTTopStories>> call, Response<List<NYTTopStories>> response) {
                // Call the proper callback used in controller (MainActivity)
                if (callbacksWeakReference.get() != null)
                    callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<NYTTopStories>> call, Throwable t) {
                // Call the proper callback used in controller (MainActivity)
                if (callbacksWeakReference.get() != null)
                    callbacksWeakReference.get().onFailure();
            }
        });
    }
}
