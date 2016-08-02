package com.jobsearchrt.jobsearchapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Pranav on 8/1/2016.
 */
public class ThumbnailDownloader extends AsyncTask<String,Void,Bitmap> {
    Bitmap result;
    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            result= BitmapFactory.decodeStream(new URL(strings[0]).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
//        InterviewTipsActivity.myThumbnail.setImageBitmap(bitmap);
        InterviewTipsActivity.row_thumbnails=bitmap;
    }
}
