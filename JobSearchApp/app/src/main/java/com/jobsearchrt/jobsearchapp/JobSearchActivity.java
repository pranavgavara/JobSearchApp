package com.jobsearchrt.jobsearchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class JobSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id){
            case R.id.FullContact:
                Intent ContactIntent=new Intent(this,FullContactActivity.class);
                startActivity(ContactIntent);
                break;
            case R.id.savedJobs:
                Intent SavedJobsIntent=new Intent(this,SavedJobsActivity.class);
                startActivity(SavedJobsIntent);
                break;
            case R.id.youtubeTips:
                Intent YoutubeIntent=new Intent(this,InterviewTipsActivity.class);
                startActivity(YoutubeIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
