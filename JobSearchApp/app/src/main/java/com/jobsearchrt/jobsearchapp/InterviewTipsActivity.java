package com.jobsearchrt.jobsearchapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class InterviewTipsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView youTubeList;
    Spinner OptionSpinner;
    String[] spinnerElements={"","java","android","ios","software developer"};
    String searchElement;
    ArrayList<singleRow> resultsrow;
    public static Bitmap row_thumbnails;
//    TextView myVideoTitle;

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
        resultsrow=new ArrayList<singleRow>();
        YoutubeListDownloader youtubeListDownloader=new YoutubeListDownloader();
        youtubeListDownloader.execute(searchElement);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        searchElement=spinnerElements[i];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        searchElement="";

    }


    public class YoutubeListDownloader extends AsyncTask<String,Void,ArrayList<singleRow>>{

        @Override
        protected ArrayList<singleRow> doInBackground(String... url) {
            String apiUrl=url[0];
            String YoutubeAPIURL="https://www.googleapis.com/youtube/v3/search?part=snippet,id&q=interview tips for "+ apiUrl+"&maxResults=5&type=video&key=AIzaSyBnwD7oP-j38RUdYTQuV0C3rcE4_MHXNac";
            try {
                URL youtubeapiURL=new URL(YoutubeAPIURL);
                BufferedReader br=new BufferedReader(new InputStreamReader(youtubeapiURL.openConnection().getInputStream(),"UTF-8"));
                StringBuilder stringBuilder=new StringBuilder();
                String videolines;
                while ((videolines=br.readLine())!=null){
                    stringBuilder.append(videolines);
                }
                JSONObject youtubeObject=new JSONObject(stringBuilder.toString());
                JSONArray videoArray=youtubeObject.getJSONArray("items");
                for(int i=0;i<5;i++){
                    JSONObject eachVideoobject=videoArray.getJSONObject(i);
                    String id=eachVideoobject.getJSONObject("id").getString("videoId");
                    String videoTitle=eachVideoobject.getJSONObject("snippet").getString("title");
                    String thumbnails=eachVideoobject.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default").getString("url");
                    resultsrow.add(new singleRow(thumbnails,videoTitle,id));
                    System.out.println("videoTitle"+videoTitle);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return resultsrow;
        }

        @Override
        protected void onPostExecute(ArrayList<singleRow> singleRows) {
            super.onPostExecute(singleRows);
            youTubeList.setAdapter(new TheAdapter(InterviewTipsActivity.this));
            for(int h=0;h<singleRows.size();h++){
                System.out.println("ghello"+singleRows.get(h).VideoTitle);
            }
            Toast.makeText(InterviewTipsActivity.this,searchElement,Toast.LENGTH_LONG).show();
        }
    }
    class singleRow{
        String thumbnail;
        String VideoTitle;
        String videoID;
        singleRow(String img,String title,String id){
            this.thumbnail=img;
            this.VideoTitle=title;
            this.videoID=id;
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
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.single_row_youtube_videos,viewGroup,false);
            ImageView myThumbnail= (ImageView) row.findViewById(R.id.thumbview);
            TextView myVideoTitle= (TextView) row.findViewById(R.id.TitletextView);
            singleRow temp=resultsrow.get(i);
            ThumbnailDownloader thumbnailDownloader=new ThumbnailDownloader();
            thumbnailDownloader.execute(temp.thumbnail);
            myThumbnail.setImageBitmap(row_thumbnails);
            myVideoTitle.setText(temp.VideoTitle);
            return row;
        }
    }
//       public class MyViewHolder{
//            ImageView myImage;
//            TextView myTitle;
//            MyViewHolder(View v){
//                myImage= (ImageView) v.findViewById(R.id.thumbview);
//                myTitle= (TextView) v.findViewById(R.id.TitletextView);
//            }
//        }
//    class MyAdapter extends ArrayAdapter<String> {
//        Context context;
//
//        public MyAdapter(Context c){
//            super(c,R.layout.single_row_youtube_videos);
//            this.context=c;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            View row=convertView;
//            MyViewHolder holder=null;
//            if(row==null) {
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                row = inflater.inflate(R.layout.single_row_youtube_videos, parent, false);
//                holder=new MyViewHolder(row);
//                row.setTag(holder);
//            }else{
//                holder= (MyViewHolder) row.getTag();
//            }
//
//            singleRow temp=resultsrow.get(position);
//            ThumbnailDownloader thumbnailDownloader=new ThumbnailDownloader();
//            thumbnailDownloader.execute(temp.thumbnail);
//            holder.myImage.setImageBitmap(row_thumbnails);
//            holder.myTitle.setText(temp.VideoTitle);
//
//            return row;
//        }
//    }

}
