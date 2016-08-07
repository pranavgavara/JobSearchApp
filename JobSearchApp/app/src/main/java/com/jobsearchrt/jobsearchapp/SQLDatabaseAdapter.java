package com.jobsearchrt.jobsearchapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Pranav on 8/5/2016.
 */
public class SQLDatabaseAdapter {
    SQLHelper helper;

    public SQLDatabaseAdapter(Context context){
        helper = new SQLHelper(context);
    }

    public long insertData(JobResults result) {

        if(getResultUrl(result.url)) return 0;
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLHelper.TITLE, result.jobtitle);
        contentValues.put(SQLHelper.CITY, result.city);
        contentValues.put(SQLHelper.COUNTRY, result.country);
        contentValues.put(SQLHelper.SNIPPET, result.snippet);
        contentValues.put(SQLHelper.SOURCE, result.source);
        contentValues.put(SQLHelper.STATE, result.state);
        contentValues.put(SQLHelper.URL, result.url);
        contentValues.put(SQLHelper.COMPANY,result.company);
        contentValues.put(SQLHelper.APPLIED,"false");

        long id = db.insert(SQLHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public Boolean getResultUrl(String Url){

        SQLiteDatabase db = helper.getReadableDatabase();

        String[] columns = {helper.URL};

        Cursor cursor = db.query(SQLHelper.TABLE_NAME, columns, SQLHelper.URL+" = '"+Url+"'", null, null, null, null);
        if(!cursor.moveToFirst() || cursor.getCount() == 0) return false;
        Log.d("RESULTS", "FOUND DUPLICATE");
        return true;
    }
    public void onDelete(JobResults deletedJob){
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete(SQLHelper.TABLE_NAME,helper.URL+" = ?",new String[]{deletedJob.url});

    }
    public boolean checkBoxStatus(JobResults job){
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns={SQLHelper.URL,SQLHelper.APPLIED};
        Cursor cursor = db.query(SQLHelper.TABLE_NAME, columns, SQLHelper.URL+" = '"+job.url+"'", null, null, null, null);
        while(cursor.moveToNext()){
            if(cursor.getString(cursor.getColumnIndex(SQLHelper.URL)).equals(job.url)){
                if(cursor.getString(cursor.getColumnIndex(SQLHelper.APPLIED)).equals("true")){
                    return true;
                }
            }
        }
        return false;

    }

    public void updateCheckbox(JobResults job){
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(SQLHelper.APPLIED, "true");
        db.update(SQLHelper.TABLE_NAME,newValues,SQLHelper.URL+" = ?",new String[]{job.url});
    }

//    public ArrayList<JobResults> fetchAllResults() {
//        SQLiteDatabase db = helper.getReadableDatabase();
//
//        String[] columns = {SQLHelper.TITLE,SQLHelper.COMPANY, SQLHelper.COUNTRY, SQLHelper.CITY, SQLHelper.SNIPPET, SQLHelper.SOURCE, SQLHelper.STATE,SQLHelper.URL};
//
//        Cursor cursor = db.query(SQLHelper.TABLE_NAME, columns, null, null, null, null, null);
////        Cursor  cursor = db.rawQuery("SELECT * FROM "+SQLHelper.TABLE_NAME,null);
//
//        ArrayList<JobResults> savedresults = new ArrayList<JobResults>();
////        cursor.moveToFirst();
//
//
//        while(cursor.moveToNext()) {
//
//            int indexTitle = cursor.getColumnIndex(SQLHelper.TITLE);
//            int indexCompany=cursor.getColumnIndex(SQLHelper.COMPANY);
//            int indexCity = cursor.getColumnIndex(SQLHelper.CITY);
//            int indexState = cursor.getColumnIndex(SQLHelper.STATE);
//            int indexCountry=cursor.getColumnIndex(SQLHelper.COUNTRY);
//            int indexSource=cursor.getColumnIndex(SQLHelper.SOURCE);
//            int indexSnippet=cursor.getColumnIndex(SQLHelper.SNIPPET);
//            int indexUrl=cursor.getColumnIndex(SQLHelper.URL);
//
//            String title = cursor.getString(indexTitle);
//            String company=cursor.getString(indexCompany);
//            String city = cursor.getString(indexCity);
//            String state = cursor.getString(indexState);
//            String country = cursor.getString(indexCountry);
//            String source = cursor.getString(indexSource);
//            String snippet = cursor.getString(indexSnippet);
//            String url = cursor.getString(indexUrl);
//            JobResults tempresults=new JobResults(title,company,city,state,source,country,snippet,url);
//
////            Log.d("Results", title +"-"+address);
//            savedresults.add(tempresults);
//        }
//        return  savedresults;
//
//    }
    public Cursor fetchAllResults() {
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] columns = {SQLHelper.TITLE, SQLHelper.COMPANY, SQLHelper.COUNTRY, SQLHelper.CITY, SQLHelper.SNIPPET, SQLHelper.SOURCE, SQLHelper.STATE, SQLHelper.URL};

        Cursor cursor = db.query(SQLHelper.TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }

    static class SQLHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "JobDatabase";
        private static final String TABLE_NAME = "SAVEDJOBS";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "sid";
        private static final String TITLE = "sJobTitle";
        private static final String COMPANY="sCompany";
        private static final String CITY = "sCity";
        private static final String STATE = "sState";
        private static final String SOURCE = "sSource";
        private static final String SNIPPET = "sSnippet";
        private static final String COUNTRY = "sCountry";
        private static final String URL = "sUrl";
        private static final String APPLIED="sApplied";

        private static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME +" ("+ UID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ TITLE +" VARCHAR(255), "+ COMPANY +" VARCHAR(255), "+ CITY +" VARCHAR(255), "+ STATE +" VARCHAR(255), "+ COUNTRY +" VARCHAR(255), "+ SOURCE +" VARCHAR(255), "+SNIPPET+" VARCHAR(255), "+URL+" VARCHAR, "+APPLIED+" VARCHAR)";
        //private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
        //private static final String ALTER_TABLE = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + PHONE + " int DEFAULT 0";


        Context context;

        public SQLHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context, "onCreate Called",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            db.execSQL(ALTER_TABLE);
            Toast.makeText(context, "onUpgrade Called", Toast.LENGTH_LONG).show();

        }


    }
}



