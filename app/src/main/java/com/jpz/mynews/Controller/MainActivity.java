package com.jpz.mynews.Controller;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jpz.mynews.Model.NYTTopStories;
import com.jpz.mynews.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NewYorkTimesCalls.Callbacks {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.activity_main_button);

        textView = findViewById(R.id.activity_main_text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    // 4 - Execute HTTP request and update UI
    private void executeHttpRequestWithRetrofit(){
        updateUIWhenStartingHTTPRequest();
        NewYorkTimesCalls.fetchSectionValue(this, "upshot");
    }

    // Override callback methods

    @Override
    public void onResponse(@Nullable List<NYTTopStories> topStories) {
        // When getting response, we update UI
        if (topStories != null) updateUIWithListOfTopStories(topStories);
    }

    @Override
    public void onFailure() {
        // When getting error, we update UI
        updateUIWhenStoppingHTTPRequest("An error happened !");
    }

    // ------------------
    //  UPDATE UI
    // ------------------

    // Update UI showing only NYT top stories
    private void updateUIWithListOfTopStories(List<NYTTopStories> topStories){
        StringBuilder stringBuilder = new StringBuilder();
        for (NYTTopStories nytTopStories : topStories){
            stringBuilder.append("-"+nytTopStories.getSection()+"\n");
        }
        updateUIWhenStoppingHTTPRequest(stringBuilder.toString());
    }

    private void updateUIWhenStartingHTTPRequest(){
        textView.setText("Downloading...");
    }

    private void updateUIWhenStoppingHTTPRequest(String response){
        textView.setText(response);
    }

}
