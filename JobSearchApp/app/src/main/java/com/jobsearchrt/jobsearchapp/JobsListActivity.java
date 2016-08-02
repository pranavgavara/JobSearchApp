package com.jobsearchrt.jobsearchapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class JobsListActivity extends AppCompatActivity {
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_list);
        listView = (ListView) findViewById(R.id.listView);
        String searchTerm = getIntent().getStringExtra("searchTerm");
        String zipcode = getIntent().getStringExtra("zipcode");
        Downloader downloader = new Downloader(this);
        downloader.execute(searchTerm,zipcode);


         }

    public  void drawListView(ArrayList<Results> resultsArray){
        JoblistAdapter adapter = new JoblistAdapter(this, resultsArray);


    }
}
