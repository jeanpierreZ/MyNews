package com.jpz.mynews.Controller;

import android.support.annotation.Nullable;
import com.jpz.mynews.Model.NYTTopStories;
import java.lang.ref.WeakReference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewYorkTimesCalls {
    // Execute Calls with Retrofit, to New York Times' API, in background (Asyntask)
/*
    // Create a callback
    public interface Callbacks {
        void onResponse(@Nullable NYTTopStories topStories);
        void onFailure();
    }

    // Public method to start fetching the result for NYTTopStories
    public static void fetchTopStories(Callbacks callbacks, String sectionValue, String apiKey){

        // Create a weak reference to callback (avoid memory leaks)
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<>(callbacks);

        // Get a Retrofit instance and the related endpoints of the Interface
        NYTService newYorkTimesService = NYTService.retrofit.create(NYTService.class);

        // Create the call on NYTTopStories API
        Call<NYTTopStories> call = newYorkTimesService.getTopStories(sectionValue, apiKey);

        // Start the call
        call.enqueue(new Callback<NYTTopStories>() {
            @Override
            public void onResponse(Call<NYTTopStories> call, Response<NYTTopStories> response) {
                // Call the proper callback used in controller (MainActivity)
                if (callbacksWeakReference.get() != null)
                    callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<NYTTopStories> call, Throwable t) {
                // Call the proper callback used in controller (MainActivity)
                if (callbacksWeakReference.get() != null)
                    callbacksWeakReference.get().onFailure();
            }
        });
    }
    */
}
