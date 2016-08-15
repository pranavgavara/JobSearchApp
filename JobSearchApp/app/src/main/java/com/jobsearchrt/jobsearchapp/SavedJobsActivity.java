package com.jobsearchrt.jobsearchapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SavedJobsActivity extends NavigationDrawer implements AdapterView.OnItemClickListener {
    ArrayList<JobResults> savedjoblist;
    ListView savedjobsListView;
    JobResults savedResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_saved_jobs);
        getLayoutInflater().inflate(R.layout.activity_saved_jobs, frameLayout);
        NaviView.setItemChecked(position, true);
        setTitle(menuItems[position]);
        savedjobsListView= (ListView) findViewById(R.id.savedjobslistview);
        savedjoblist=new ArrayList<JobResults>();
        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, null, null, null, null);


        while(cursor.moveToNext()) {

            int indexTitle = cursor.getColumnIndex("sJobTitle");
            int indexCompany = cursor.getColumnIndex("sCompany");
            int indexCity = cursor.getColumnIndex("sCity");
            int indexState = cursor.getColumnIndex("sState");
            int indexCountry = cursor.getColumnIndex("sCountry");
            int indexSource = cursor.getColumnIndex("sSource");
            int indexSnippet = cursor.getColumnIndex("sSnippet");
            int indexUrl = cursor.getColumnIndex("sUrl");

            String title = cursor.getString(indexTitle);
            String company = cursor.getString(indexCompany);
            String city = cursor.getString(indexCity);
            String state = cursor.getString(indexState);
            String country = cursor.getString(indexCountry);
            String source = cursor.getString(indexSource);
            String snippet = cursor.getString(indexSnippet);
            String url = cursor.getString(indexUrl);
            savedResults=new JobResults(title,company,city,state,source,country,snippet,url);
            savedjoblist.add(savedResults);
        }

        JoblistAdapter savedjobs_adapter = new JoblistAdapter(this, savedjoblist);
        savedjobsListView.setAdapter(savedjobs_adapter);
        savedjobsListView.setOnItemClickListener(this);
        registerForContextMenu(savedjobsListView);

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.savedjobs_delete,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        JobResults saved_result=savedjoblist.get(info.position);
        if(item.getItemId()==R.id.Delete) {
            DeleteFromDB(saved_result);
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        JobResults result = savedjoblist.get(i);
        Intent intent= new Intent(this, JobdetailsActivity.class);
        intent.putExtra("result", result);
        startActivity(intent);
    }
    public void DeleteFromDB(JobResults deleteJob){
        SQLDatabaseAdapter databaseAdapter=new SQLDatabaseAdapter(this);
        databaseAdapter.onDelete(deleteJob);
        Intent refresh_intent=getIntent();
        finish();
        startActivity(refresh_intent);


    }

}

