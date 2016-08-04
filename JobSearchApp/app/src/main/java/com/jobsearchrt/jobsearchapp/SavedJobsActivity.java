package com.jobsearchrt.jobsearchapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SavedJobsActivity extends CustomMenuActivity implements AdapterView.OnItemClickListener {
    ArrayList<JobResults> savedjoblist;
    ListView savedjobsListView;
    JobResults savedResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_jobs);
        savedjobsListView= (ListView) findViewById(R.id.savedjobslistview);
        savedjoblist=new ArrayList<JobResults>();
        if(checkDataBase()){
            getfromDB();
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
    public void getfromDB(){

        SQLiteDatabase database=openOrCreateDatabase("JobDB",MODE_PRIVATE,null);
        Cursor cursor=database.rawQuery("Select * from SavedJobs",null);
        cursor.moveToFirst();
        do{
            savedResults=new JobResults(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
            savedjoblist.add(savedResults);
        }while(cursor.moveToNext());
        cursor.close();
        database.close();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        JobResults result = savedjoblist.get(i);
        Intent intent= new Intent(this, JobdetailsActivity.class);
        intent.putExtra("result", result);
        startActivity(intent);
    }
    public void DeleteFromDB(JobResults deleteJob){
        SQLiteDatabase Existing_database=openOrCreateDatabase("JobDB",MODE_PRIVATE,null);
        Existing_database.delete("SavedJobs","JobTitle"+"=?",new String[] {deleteJob.jobtitle});
        Existing_database.close();

    }
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase("JobDB", null,SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }
}

