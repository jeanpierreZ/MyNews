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
public class MostPopularFragment extends NewsFragment implements AdapterNews.Listener {

    public MostPopularFragment() {
        // Required empty public constructor
    }

    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
    }

    // HTTP (RxJAVA)
    @Override
    protected void fetchData() {
        // Execute the stream subscribing to Observable defined inside APIClient
        this.disposable = APIClient.getMostPopularNews()
                .subscribeWith(new DisposableObserver<List<GenericNews>>() {
                    @Override
                    public void onNext(List<GenericNews> genericNewsList) {
                        Log.i("TAG","On Next MostPopular");
                        // Update UI with the list of MostPopular
                        updateUI(genericNewsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","On Error MostPopular" + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.i("TAG","On Complete MostPopular");
                    }
                });
    }

}