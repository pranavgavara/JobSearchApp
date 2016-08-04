package com.jobsearchrt.jobsearchapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class JobdetailsActivity extends CustomMenuActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobdetails);
        webView = (WebView) findViewById(R.id.webView);
        JobResults result = getIntent().getParcelableExtra("result");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(result.url);
    }

    public void applyToJob(View view) {

    }
}
