package com.jpz.mynews.Controllers.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jpz.mynews.Controllers.Utils.NYTService;
import com.jpz.mynews.Controllers.Utils.NYTStreams;
import com.jpz.mynews.Models.Doc;
import com.jpz.mynews.Models.NYTArticleSearch;
import com.jpz.mynews.Models.NYTMostPopular;
import com.jpz.mynews.Models.NYTResult;
import com.jpz.mynews.Models.NYTResultMP;
import com.jpz.mynews.Models.NYTTopStories;
import com.jpz.mynews.R;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private TextView textViewTopStories;
    private TextView textViewMostPopular;
    private TextView textViewArticleSearch;

    // For data
    private Disposable disposable;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        textViewTopStories = view.findViewById(R.id.fragment_main_text_topstories);
        textViewMostPopular = view.findViewById(R.id.fragment_main_text_mostpopular);
        textViewArticleSearch = view.findViewById(R.id.fragment_main_text_articlesearch);

        // Load articles of NY Times when launching the app
        executeTopStoriesRequest();
        executeMostPopularRequest();
        executeArticleSearchRequest();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Dispose subscription when activity is destroyed
        this.disposeWhenDestroy();
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    // Execute our Stream
    private void executeTopStoriesRequest(){
        // Update UI
        this.updateUIWhenStartingHTTPRequest(textViewTopStories);
        // Execute the stream subscribing to Observable defined inside NYTStream
        this.disposable = NYTStreams.fetchTopStories(NYTService.API_TOPSTORIES_SECTION, NYTService.API_KEY)
                .subscribeWith(new DisposableObserver<NYTTopStories>() {
                    @Override
                    public void onNext(NYTTopStories topStories) {
                        Log.e("TAG","On Next");
                        // Update UI with result of topStories
                        updateUIWithTopStories(topStories);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","On Error" + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG","On Complete");
                    }
                });
    }

    // Execute our Stream
    private void executeMostPopularRequest(){
        // Update UI
        this.updateUIWhenStartingHTTPRequest(textViewMostPopular);
        // Execute the stream subscribing to Observable defined inside NYTStream
        this.disposable = NYTStreams.fetchMostPopular(NYTService.API_PERIOD, NYTService.API_KEY)
                .subscribeWith(new DisposableObserver<NYTMostPopular>() {
                    @Override
                    public void onNext(NYTMostPopular mostPopular) {
                        Log.e("TAG","On Next");
                        // Update UI with result of topStories
                        updateUIWithMostPopular(mostPopular);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","On Error" + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG","On Complete");
                    }
                });
    }

    // Execute our Stream
    private void executeArticleSearchRequest(){
        // Update UI
        this.updateUIWhenStartingHTTPRequest(textViewArticleSearch);
        // Execute the stream subscribing to Observable defined inside NYTStream
        this.disposable = NYTStreams.fetchArticleSearch(NYTService.API_FILTER_QUERY_SOURCE,
                NYTService.API_FILTER_QUERY_NEWS_DESK, NYTService.API_FILTER_SORT_ORDER, NYTService.API_KEY)
                .subscribeWith(new DisposableObserver<NYTArticleSearch>() {
                    @Override
                    public void onNext(NYTArticleSearch articleSeach) {
                        Log.e("TAG","On Next");
                        // Update UI with result of topStories
                        updateUIWithArticleSearch(articleSeach);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","On Error" + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG","On Complete");
                    }
                });
    }

    // Dispose subscription
    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // ------------------
    //  UPDATE UI
    // ------------------

    // Update UI showing TopStories
    private void updateUIWithTopStories(NYTTopStories topStories){
        // Run through TopStories to recover results of section, subsection, updated date and title
        StringBuilder stringBuilder = new StringBuilder();
        for (NYTResult nytResult : topStories.getResults()) {
            stringBuilder.append(nytResult.getSection() + " > ");
            stringBuilder.append(nytResult.getSubsection() + "\n");
            stringBuilder.append(nytResult.getUpdatedDate() + "\n");
            stringBuilder.append(nytResult.getTitle() + "\n");
        }
        // Show them all with formatting above
        updateUIWhenStoppingHTTPRequest(textViewTopStories, stringBuilder.toString());
    }

    // Update UI showing MostPopular
    private void updateUIWithMostPopular(NYTMostPopular mostPopular){
        // Run through MostPopular to recover results of section, views, title and source
        StringBuilder stringBuilder = new StringBuilder();
        for (NYTResultMP nytResultMP : mostPopular.getResults()) {
            stringBuilder.append(nytResultMP.getSection() + " > ");
            stringBuilder.append(nytResultMP.getViews() + "\n");
            stringBuilder.append(nytResultMP.getTitle() + "\n");
            stringBuilder.append(nytResultMP.getSource() + "\n");
        }
        // Show them all with formatting above
        updateUIWhenStoppingHTTPRequest(textViewMostPopular, stringBuilder.toString());
    }

    // Update UI showing ArticleSearch
    private void updateUIWithArticleSearch(NYTArticleSearch articleSeach){
        // Run through MostPopular to recover results
        StringBuilder stringBuilder = new StringBuilder();
        for (Doc doc : articleSeach.getResponse().getDocs()) {
            stringBuilder.append(doc.getSource() + " > ");
            stringBuilder.append(doc.getHeadline().getMain() + "\n");
            stringBuilder.append(doc.getByline().getPerson() + "\n");
            stringBuilder.append(doc.getNewsDesk() + "\n");
        }
        // Show them all with formatting above
        updateUIWhenStoppingHTTPRequest(textViewArticleSearch, stringBuilder.toString());

/*
        updateUIWhenStoppingHTTPRequest(textViewArticleSearch,
                articleSeach.getResponse().getDocs().get(0).getSource());

        stringBuilder.append(doc.getByline().getPerson().get(0).getLastname() + "\n"); => Crash
*/
    }

    private void updateUIWhenStartingHTTPRequest(TextView textView){
        textView.setText("Downloading...");
    }

    private void updateUIWhenStoppingHTTPRequest(TextView textView, String response){
        textView.setText(response);
    }

}
