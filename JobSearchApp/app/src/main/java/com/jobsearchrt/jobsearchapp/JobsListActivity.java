package com.jobsearchrt.jobsearchapp;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.ContextMenu;
import android.view.MenuItem;
=======
import android.support.v7.app.AppCompatActivity;
>>>>>>> ecacba348274a3909cbc8abf9c44060b04cd13b0
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class JobsListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    ArrayList<JobResults> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_list);
        listView = (ListView) findViewById(R.id.listView);
        String searchTerm = getIntent().getStringExtra("searchTerm");
        String zipcode = getIntent().getStringExtra("zipcode");
        JobListDownloader downloader = new JobListDownloader(this);
        downloader.execute(searchTerm,zipcode);

         }

    public  void drawListView(ArrayList<JobResults> resultsArray){
        results = new ArrayList<JobResults>();
        results = resultsArray;
        JoblistAdapter adapter = new JoblistAdapter(this, resultsArray);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);


    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        JobResults result = results.get(position);
        Intent intent= new Intent(this, JobdetailsActivity.class);
        intent.putExtra("result", result);
        startActivity(intent);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.job_saveshare_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        Results saved_result=results.get(info.position);
        if(item.getItemId()==R.id.Save){
            Intent intent=new Intent(this,SavedJobsActivity.class);
            intent.putExtra("saved_result", saved_result);
            startActivity(intent);

        }else if(item.getItemId()==R.id.Share){
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String job_title_str=saved_result.jobtitle;
            String job_company_str=saved_result.company;
            intent.putExtra(Intent.EXTRA_TEXT,job_title_str);
            intent.putExtra(Intent.EXTRA_TEXT,job_company_str);
            Intent chooser=Intent.createChooser(intent,"Share Job");
            startActivity(chooser);
        }
        return super.onContextItemSelected(item);

    }
}
