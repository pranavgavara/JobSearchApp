package com.jobsearchrt.jobsearchapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class JobsListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    ArrayList<Results> results;

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
        results = new ArrayList<Results>();
        results = resultsArray;
        JoblistAdapter adapter = new JoblistAdapter(this, resultsArray);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Results result = results.get(position);
        Intent intent= new Intent(this, JobdetailsActivity.class);
        intent.putExtra("result", result);
        startActivity(intent);

    }
}
