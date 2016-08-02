package com.jobsearchrt.jobsearchapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class InterviewTipsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView youTubeList;
    Spinner OptionSpinner;
    String[] spinnerElements={"","java","android","ios","software developer"};
    String searchElement;
    ArrayList<singleRow> resultsrow;
    Bitmap row_thumbnails;
//    TextView myVideoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_tips);
        OptionSpinner= (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinnerElements);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        OptionSpinner.setAdapter(adapter);
        OptionSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        searchElement=spinnerElements[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        searchElement="";
    }
    public void searchYoutube(View view) {
        youTubeList= (ListView) findViewById(R.id.listView);
        resultsrow=new ArrayList<singleRow>();
        YoutubeListDownloader youtubeListDownloader=new YoutubeListDownloader();
        try {
            resultsrow=youtubeListDownloader.execute(searchElement).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        youTubeList.setAdapter(new MyAdapter(this,resultsrow));
        youTubeList.setOnItemClickListener(onClickListener);
    }

    class MyViewHolder{
        ImageView myImage;
        TextView myTitle;
        MyViewHolder(View v){
            myImage= (ImageView) v.findViewById(R.id.thumbview);
            myTitle= (TextView) v.findViewById(R.id.TitletextView);
            }
        }
    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<singleRow> list;

        public MyAdapter(Context c,ArrayList<singleRow> resultList){
            super(c,R.layout.single_row_youtube_videos);
            this.context=c;
            this.list=resultList;
        }
        @Override
        public int getCount() {

            return list.size();
        }

        @Override
        public long getItemId(int arg0) {

            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row=convertView;
            MyViewHolder holder=null;
            if(row==null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.single_row_youtube_videos, parent, false);
                holder=new MyViewHolder(row);
                row.setTag(holder);
            }else{
                holder= (MyViewHolder) row.getTag();
            }
            singleRow temp=resultsrow.get(position);
            ThumbnailDownloader thumbnailDownloader=new ThumbnailDownloader();
            try {
                holder.myImage.setImageBitmap(thumbnailDownloader.execute(temp.thumbnail).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();            }
            holder.myTitle.setText(temp.VideoTitle);
            return row;
        }
    }
    private ListView.OnItemClickListener onClickListener= new ListView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent=new Intent(InterviewTipsActivity.this,YouTubePlayerActivity.class);

            startActivity(intent);

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}
