package com.jpz.mynews.controllers.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.jpz.mynews.R;

import static com.jpz.mynews.controllers.activities.MainActivity.KEY_URL;

public class WebViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This is the FrameLayout area within the activity_base.xml
        FrameLayout contentFrameLayout = findViewById(R.id.activity_base_frame_layout);
        // Inflate the activity to load
        getLayoutInflater().inflate(R.layout.activity_web_view, contentFrameLayout);

        // Display settings of Toolbar & NavigationView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setElevation(8);

        navigationView.getMenu().getItem(0).setVisible(true);
        navigationView.getMenu().getItem(1).setVisible(false);

        // Get WebView
        WebView webView = findViewById(R.id.webview);

        // Get the transferred data from the source activity
        Intent intent = getIntent();
        String url = intent.getStringExtra(KEY_URL);

        // Open the webView in the app instead of a browser
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}