package com.jpz.mynews.controllers.fragments;


import android.support.v4.app.Fragment;
import android.util.Log;

import com.jpz.mynews.controllers.utils.APIClient;
import com.jpz.mynews.models.GenericNews;
import com.jpz.mynews.controllers.adapters.AdapterNews;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopStoriesFragment extends NewsFragment implements AdapterNews.Listener {

    public TopStoriesFragment() {
        // Required empty public constructor
    }

    public static TopStoriesFragment newInstance() {
        return (new TopStoriesFragment());
    }

    // HTTP (RxJAVA)
    @Override
    protected void fetchData() {
        // Execute the stream subscribing to Observable defined inside APIClient
        this.disposable = APIClient.getTopStoriesNews()
                .subscribeWith(new DisposableObserver<List<GenericNews>>() {
                    @Override
                    public void onNext(List<GenericNews> genericNewsList) {
                        Log.i("TAG","On Next TopStories");
                        // Update UI with the list of TopStories
                        updateUI(genericNewsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","On Error TopStories" + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.i("TAG","On Complete TopStories");
                    }
                });
    }

}