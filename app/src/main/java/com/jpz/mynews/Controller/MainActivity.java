package com.jpz.mynews.Controller;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jpz.mynews.Model.NYTResult;
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

    // Execute HTTP request and update UI
    private void executeHttpRequestWithRetrofit(){
        updateUIWhenStartingHTTPRequest();
        NewYorkTimesCalls.fetchTopStories(this, NewYorkTimesService.API_SECTION_TOPSTORIES);
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
