package com.jpz.mynews.Controllers.Fragments;


import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.jpz.mynews.Controllers.Utils.APIClient;
import com.jpz.mynews.Models.GenericNews;
import com.jpz.mynews.Views.AdapterAPI;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleSearchFragment extends NewsFragment implements AdapterAPI.Listener {

    public ArticleSearchFragment() {
        // Required empty public constructor
    }

    public static ArticleSearchFragment newInstance() {
        return (new ArticleSearchFragment());
    }

    @Override
    protected void executeRequest(int page) {
        // Execute the stream subscribing to Observable defined inside Stream
        this.disposable = APIClient.fetchSearchToGeneric(page)
                .subscribeWith(new DisposableObserver<List<GenericNews>>() {
                    @Override
                    public void onNext(List<GenericNews> genericNewsList) {
                        Log.i("TAG","On Next ArticleSearch");
                        // Update UI with a list of ArticleSearch
                        updateUI(genericNewsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","On Error ArticleSearch" + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.i("TAG","On Complete ArticleSearch");
                    }
                });
    }

    @Override
    protected void fetchData() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                executeRequest(++page);
                adapterAPI.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        }, 2000);
    }
}
