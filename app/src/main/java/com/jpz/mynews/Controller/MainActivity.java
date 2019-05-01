package com.jpz.mynews.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.jpz.mynews.Model.NYTResult;
import com.jpz.mynews.Model.NYTTopStories;
import com.jpz.mynews.R;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class MainActivity extends AppCompatActivity {

    private TextView textView;

    //FOR DATA
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.activity_main_button);
        textView = findViewById(R.id.activity_main_text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeHttpRequestWithRxJava();
            }
        });
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
    private void executeHttpRequestWithRxJava(){
        // Update UI
        this.updateUIWhenStartingHTTPRequest();
        // Execute the stream subscribing to Observable defined inside NYTStream
        this.disposable = NYTStreams.fetchTopStories(NYTService.API_SECTION_TOPSTORIES, NYTService.API_KEY)
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

    // Dispose subscription
    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    /*
    // Execute HTTP request and update UI
    private void executeHttpRequestWithRetrofit(){
        updateUIWhenStartingHTTPRequest();
        NewYorkTimesCalls.fetchTopStories(this, NYTService.API_SECTION_TOPSTORIES, NYTService.API_KEY);
    }

    // Override Callbacks Interface methods
    @Override
    public void onResponse(@Nullable NYTTopStories topStories) {
        // When getting a response, we update UI
        if (topStories != null) updateUIWithTopStories(topStories);
    }

    @Override
    public void onFailure() {
        // When getting a fail, we update UI
        updateUIWhenStoppingHTTPRequest("An error happened !");
    }
*/
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
        updateUIWhenStoppingHTTPRequest(stringBuilder.toString());
    }

    private void updateUIWhenStartingHTTPRequest(){
        textView.setText("Downloading...");
    }

    private void updateUIWhenStoppingHTTPRequest(String response){
        textView.setText(response);
    }

}
