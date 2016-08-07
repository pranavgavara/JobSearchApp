package com.jobsearchrt.jobsearchapp;

import android.os.AsyncTask;

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

import retrofit.http.HEAD;

/**
 * Created by suman on 8/1/2016.
 */
public class JobListDownloader extends AsyncTask<String,Integer,ArrayList> {
    JobsListActivity activity;
    public  JobListDownloader(JobsListActivity activity){
        this.activity=activity;
    }
    @Override
    protected ArrayList doInBackground(String... params) {
        params[0]=params[0].replaceAll("\\s+","%20");
        String jobURL="http://api.indeed.com/ads/apisearch?publisher=7663037959034577&q="+params[0]+"&v=2&format=json&l="+params[1]+"";

//        String jobURL="http://api.indeed.com/ads/apisearch?publisher=7663037959034577&q="+params[0]+"&v=2&format=json&l="+params[1]+"&limit=25";

       ArrayList <JobResults> resultsArrayList= new ArrayList<JobResults>();
        try {
            URL theUrl = new URL(jobURL);
            BufferedReader reader= new BufferedReader(new InputStreamReader(theUrl.openConnection().getInputStream(),"UTF-8"));
            String json=reader.readLine();
            JSONObject jsonObject=new JSONObject(json);
//            JSONObject jqueryObject = jsonObject.getJSONObject("query");
//            JSONObject jresultsObject = jsonObject.getJSONObject("results");
            JSONArray resultArray = jsonObject.getJSONArray("results");
            for(int i=0;i<resultArray.length();i++){
                JSONObject eachresult=resultArray.getJSONObject(i);
                String jobtitle = eachresult.getString("jobtitle");
                String company = eachresult.getString("company");
                String city = eachresult.getString("city");
                String state = eachresult.getString("state");
                String country = eachresult.getString("country");
                String source = eachresult.getString("source");
                String snippet = eachresult.getString("snippet");
                String url = eachresult.getString("url");

                JobResults results = new JobResults(jobtitle,company,city,state,source,country,snippet,url);
                resultsArrayList.add(results);

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

        return resultsArrayList;
    }
    protected void onPostExecute(ArrayList arrayList) {
        super.onPostExecute(arrayList);
        activity.drawListView(arrayList);
    }
}
