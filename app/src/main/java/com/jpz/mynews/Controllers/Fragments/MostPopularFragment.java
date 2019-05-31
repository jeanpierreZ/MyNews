package com.jpz.mynews.Controllers.Fragments;


import android.support.v4.app.Fragment;
import android.util.Log;

import com.jpz.mynews.Controllers.Utils.APIClient;
import com.jpz.mynews.Models.GenericNews;
import com.jpz.mynews.Views.AdapterAPI;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostPopularFragment extends NewsFragment implements AdapterAPI.Listener {

    public MostPopularFragment() {
        // Required empty public constructor
    }

    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
    }

    @Override
    protected void executeRequest(int page) {
        // Execute the stream subscribing to Observable defined inside Stream
        this.disposable = APIClient.fetchPopularToGeneric()
                .subscribeWith(new DisposableObserver<List<GenericNews>>() {
                    @Override
                    public void onNext(List<GenericNews> genericNewsList) {
                        Log.i("TAG","On Next MostPopular");
                        // Update UI with lis of MostPopular
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

    @Override
    protected void updateUI(List<GenericNews> newsList) {
        genericNewsList.addAll(newsList);
        adapterAPI.notifyDataSetChanged();
    }

    @Override
    protected void fetchData() {

    }
}
