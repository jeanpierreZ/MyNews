package com.jpz.mynews.Controller;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jpz.mynews.Model.NYTimesTopStories;
import com.jpz.mynews.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NewYorkTimesCalls.Callbacks {

    private TextView textView = findViewById(R.id.activity_main_text);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.activity_main_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    // 4 - Execute HTTP request and update UI
    private void executeHttpRequestWithRetrofit(){
        this.updateUIWhenStartingHTTPRequest();
        NewYorkTimesCalls.fetchSectionValue(this, "upshot");
    }

    // Override callback methods

    @Override
    public void onResponse(@Nullable List<NYTimesTopStories> topStories) {
        // When getting response, we update UI
        if (topStories != null) this.updateUIWithListOfTopStories(topStories);
    }

    @Override
    public void onFailure() {
        // When getting error, we update UI
        this.updateUIWhenStoppingHTTPRequest("An error happened !");
    }

    // ------------------
    //  UPDATE UI
    // ------------------

    // Update UI showing only NYT top stories
    private void updateUIWithListOfTopStories(List<NYTimesTopStories> topStories){
        StringBuilder stringBuilder = new StringBuilder();
        for (NYTimesTopStories nyTimesTopStories : topStories){
            stringBuilder.append("-"+nyTimesTopStories.getSection()+"\n");
        }
        updateUIWhenStoppingHTTPRequest(stringBuilder.toString());
    }

    private void updateUIWhenStartingHTTPRequest(){
        this.textView.setText("Downloading...");
    }

    private void updateUIWhenStoppingHTTPRequest(String response){
        this.textView.setText(response);
    }

}
