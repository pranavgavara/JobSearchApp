package com.jobsearchrt.jobsearchapp;

import android.os.AsyncTask;
import android.util.Log;
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

/**
 * Created by Pranav on 8/2/2016.
 */
public class singleRow{
    String thumbnail;
    String VideoTitle;
    String videoID;
    singleRow(String img,String title,String id){
        this.thumbnail=img;
        this.VideoTitle=title;
        this.videoID=id;
    }
}
class YoutubeListDownloader extends AsyncTask<String,Void,ArrayList<singleRow>> {
    ArrayList<singleRow> resultVideos=new ArrayList<singleRow>();
    @Override
    protected ArrayList<singleRow> doInBackground(String... url) {
        String apiUrl="interview%20tips%20for%20"+url[0];
//        Log.d("YoutubeListDownloader1", "doInBackground1: "+apiUrl);
        String YoutubeAPIURL="https://www.googleapis.com/youtube/v3/search?part=snippet,id&q="+apiUrl+"&maxResults=50&type=video&key=AIzaSyBnwD7oP-j38RUdYTQuV0C3rcE4_MHXNac";
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
            for(int i=0;i<50;i++){
                JSONObject eachVideoobject=videoArray.getJSONObject(i);
                String id=eachVideoobject.getJSONObject("id").getString("videoId");
                String videoTitle=eachVideoobject.getJSONObject("snippet").getString("title");
                String thumbnails=eachVideoobject.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default").getString("url");
                resultVideos.add(new singleRow(thumbnails,videoTitle,id));
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
        return resultVideos;
    }
    @Override
    protected void onPostExecute(ArrayList<singleRow> singleRows) {
        super.onPostExecute(singleRows);
//        InterviewTipsActivity.resultsrow=singleRows;
//        Log.d("YoutubeListDownloader1", "doInBackground2: ");
//        Toast.makeText(InterviewTipsActivity.this,searchElement,Toast.LENGTH_LONG).show();
    }
}
