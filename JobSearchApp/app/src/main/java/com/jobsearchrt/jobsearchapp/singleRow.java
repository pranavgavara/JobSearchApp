package com.jobsearchrt.jobsearchapp;

import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
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
public class singleRow implements Parcelable {
    String thumbnail;
    String VideoTitle;
    String videoID;
    public singleRow(String img,String title,String id){
        this.thumbnail=img;
        this.VideoTitle=title;
        this.videoID=id;
    }

    protected singleRow(Parcel in) {
        thumbnail = in.readString();
        VideoTitle = in.readString();
        videoID = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumbnail);
        dest.writeString(VideoTitle);
        dest.writeString(videoID);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<singleRow> CREATOR = new Parcelable.Creator<singleRow>() {
        @Override
        public singleRow createFromParcel(Parcel in) {
            return new singleRow(in);
        }

        @Override
        public singleRow[] newArray(int size) {
            return new singleRow[size];
        }
    };
}
