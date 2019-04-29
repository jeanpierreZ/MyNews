package com.jpz.mynews.Controller;

import android.support.annotation.Nullable;
import com.jpz.mynews.Model.NYTimesTopStories;
import java.lang.ref.WeakReference;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewYorkTimesCalls {
    // Execute Calls to New York Times API in background (Asyntask)

    // Creating a callback
    public interface Callbacks {
        void onResponse(@Nullable List<NYTimesTopStories> users);
        void onFailure();
    }

    // Public method to start fetching result by NYT TopStories
    public static void fetchSectionValue(Callbacks callbacks, String sectionValue){

        // Create a weak reference to callback (avoid memory leaks)
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<>(callbacks);

        // Get a Retrofit instance and the related endpoints in the Interface
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofit.create(NewYorkTimesService.class);

        // Create the call on NYTimesTopStories API
        Call<List<NYTimesTopStories>> call = newYorkTimesService.getResult(sectionValue);

        // Start the call
        call.enqueue(new Callback<List<NYTimesTopStories>>() {
            @Override
            public void onResponse(Call<List<NYTimesTopStories>> call, Response<List<NYTimesTopStories>> response) {
                // Call the proper callback used in controller (MainActivity)
                if (callbacksWeakReference.get() != null)
                    callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<NYTimesTopStories>> call, Throwable t) {
                // Call the proper callback used in controller (MainActivity)
                if (callbacksWeakReference.get() != null)
                    callbacksWeakReference.get().onFailure();
            }
        });
    }
}
