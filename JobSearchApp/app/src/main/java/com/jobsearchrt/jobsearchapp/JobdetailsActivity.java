package com.jobsearchrt.jobsearchapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;

public class JobdetailsActivity extends CustomMenuActivity {
    WebView webView;
    CheckBox checkBox;
    JobResults result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobdetails);
        webView = (WebView) findViewById(R.id.webView);
        result = getIntent().getParcelableExtra("result");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(result.url);
        checkBox= (CheckBox) findViewById(R.id.checkBox);
        SQLDatabaseAdapter adapter=new SQLDatabaseAdapter(this);
        if(adapter.getResultUrl(result.url)){
            if(adapter.checkBoxStatus(result)){
                checkBox.setChecked(true);
            }
            else{
                checkBox.setChecked(false);
            }
        }
    }

    public void jobApplied(View view) {
        boolean applied=checkBox.isChecked();
        SQLDatabaseAdapter sqlDatabaseAdapter=new SQLDatabaseAdapter(this);
        if (applied==true){

            sqlDatabaseAdapter.insertData(result);
            sqlDatabaseAdapter.updateCheckbox(result);
        }
    }
}
