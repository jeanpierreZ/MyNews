package com.jpz.mynews.Controllers.Fragments;


import android.support.v4.app.Fragment;
import android.util.Log;

import com.jpz.mynews.Controllers.Utils.Service;
import com.jpz.mynews.Controllers.Utils.Streams;
import com.jpz.mynews.Models.APIClient;
import com.jpz.mynews.Models.GenericNews;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopStoriesFragment extends NewsFragment {

    // For data
    private Disposable disposable;

    @Override
    protected NewsFragment newInstance() {
        return new TopStoriesFragment();
    }

    @Override
    protected void configureDesign() {

    }

    @Override
    protected void updateRecyclerView() {

    }

    @Override
    protected void destroyView() {

    }

    @Override
    protected void updateOnClickItem() {

    }

    @Override
    public List<NewsFragment> fetchNews(int page) {
        return null;
    }

    @Override
    protected void updateNewsUI() {

    }

    public TopStoriesFragment() {
        // Required empty public constructor
    }


    // Execute TopStories stream
    private void executeTopStoriesRequest(){
        // Execute the stream subscribing to Observable defined inside Stream
        this.disposable = Streams.fetchTopStories(Service.API_TOPSTORIES_SECTION)
                .subscribeWith(new DisposableObserver<APIClient>() {
                    @Override
                    public void onNext(APIClient apiClient) {
                        Log.i("TAG","On Next TopStories");
                        // Update UI with result of Top Stories
                        List<GenericNews> genericNewsList = new ArrayList<>();

                        GenericNews genericNews = new GenericNews();

                        genericNews.configureTopStories(apiClient);

                        genericNewsList.add(genericNews);

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

/*
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_stories, container, false);
    }
*/
}
