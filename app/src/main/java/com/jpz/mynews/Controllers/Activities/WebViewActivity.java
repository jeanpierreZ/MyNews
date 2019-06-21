package com.jpz.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jpz.mynews.R;

import static com.jpz.mynews.Controllers.Activities.MainActivity.KEY_URL;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        // Get WebView
        WebView webView = findViewById(R.id.webview);

        // Display settings toolbar
        configureToolbar();

        // Get the transferred data from the source activity
        Intent intent = getIntent();
        String url = intent.getStringExtra(KEY_URL);

        // Open the webView in the app instead of a browser
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    private void configureToolbar(){
        //Get the toolbar (Serialise)
        Toolbar toolbar = findViewById(R.id.toolbar);
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        if (ab != null)
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
