package com.jobsearchrt.jobsearchapp;

/**
 * Created by suman on 8/1/2016.
 */
public class Results {
    String jobtitle;
    String company;
    String city;
    String state;
    String source;
    String snippet;
    String country;
//    String jobkey;
    String url;

    public Results( String jobtitle, String company, String city, String state, String source,String country, String snippet, String url) {
        this.jobtitle = jobtitle;
        this.company = company;
        this.city = city;
        this.state = state;
        this.source = source;
        this.snippet = snippet;
        this.country = country;
//        this.jobkey = jobkey;
        this.url = url;
    }
}
