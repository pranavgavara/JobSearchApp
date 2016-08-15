package com.jobsearchrt.jobsearchapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;

public class JobdetailsActivity extends AppCompatActivity {
    WebView webView;
    CheckBox checkBox;
    JobResults result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobdetails);
//        getLayoutInflater().inflate(R.layout.activity_jobdetails, frameLayout);
//        NaviView.setItemChecked(position, true);
//        setTitle(menuItems[position]);
        webView = (WebView) findViewById(R.id.webView);
        result = getIntent().getParcelableExtra("result");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(result.url);
        checkBox= (CheckBox) findViewById(R.id.checkBox);
        SQLDatabaseAdapter adapter=new SQLDatabaseAdapter(this);
        if(adapter.getResultUrl(result.snippet)){
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
            if (!sqlDatabaseAdapter.getResultUrl(result.snippet)){
                sqlDatabaseAdapter.insertData(result);
            }
            sqlDatabaseAdapter.updateCheckbox(result,"true");
        }else{
            if (sqlDatabaseAdapter.getResultUrl(result.snippet)){
                sqlDatabaseAdapter.updateCheckbox(result,"false");
            }
        }

    }
}
