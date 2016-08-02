package com.jobsearchrt.jobsearchapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by suman on 8/1/2016.
 */
public class Results implements Parcelable {
    String jobtitle;
    String company;
    String city;
    String state;
    String source;
    String snippet;
    String country;
    String url;

    public Results( String jobtitle, String company, String city, String state, String source,String country, String snippet, String url) {
        this.jobtitle = jobtitle;
        this.company = company;
        this.city = city;
        this.state = state;
        this.source = source;
        this.snippet = snippet;
        this.country = country;
        this.url = url;
    }

    protected Results(Parcel in) {
        jobtitle = in.readString();
        company = in.readString();
        city = in.readString();
        state = in.readString();
        source = in.readString();
        snippet = in.readString();
        country = in.readString();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jobtitle);
        dest.writeString(company);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(source);
        dest.writeString(snippet);
        dest.writeString(country);
        dest.writeString(url);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Results> CREATOR = new Parcelable.Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel in) {
            return new Results(in);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };
}