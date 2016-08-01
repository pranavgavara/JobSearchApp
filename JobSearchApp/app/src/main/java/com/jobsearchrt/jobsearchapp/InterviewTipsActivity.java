package com.jobsearchrt.jobsearchapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class InterviewTipsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView youTubeList;
    Spinner OptionSpinner;
    String[] spinnerElements={"java","android","ios","software developer"};
    String searchElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_tips);
        OptionSpinner= (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,spinnerElements);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        OptionSpinner.setAdapter(adapter);
        OptionSpinner.setOnItemSelectedListener(this);


    }

    public void searchYoutube(View view) {
        youTubeList= (ListView) findViewById(R.id.listView);
        youTubeList.setAdapter(new TheAdapter(this));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        searchElement=spinnerElements[i];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        searchElement="";

    }

    public class youtubelistDownloader extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }
    }

    class singleRow{
        int thumbnail;
        String VideoTitle;
        singleRow(int img,String title){
            this.thumbnail=img;
            this.VideoTitle=title;
        }
    }
    class TheAdapter extends BaseAdapter{
        ArrayList<singleRow> list;
        Context context;
        TheAdapter(Context c){
            this.context=c;


        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }
}
