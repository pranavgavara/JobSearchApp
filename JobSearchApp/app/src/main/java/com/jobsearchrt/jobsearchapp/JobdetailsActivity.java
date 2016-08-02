package com.jobsearchrt.jobsearchapp;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class JobdetailsActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobdetails);
        webView = (WebView) findViewById(R.id.webView);
        Results result = getIntent().getParcelableExtra("result");
        webView.loadUrl(result.url);
    }
}
