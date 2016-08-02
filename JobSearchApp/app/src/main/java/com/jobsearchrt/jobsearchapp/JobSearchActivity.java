package com.jobsearchrt.jobsearchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class JobSearchActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    EditText searchTerm, zipcode;
    GoogleApiClient mGoogleApi;
    LocationRequest mlocationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search);
        zipcode = (EditText) findViewById(R.id.zipcode);
        searchTerm = (EditText) findViewById(R.id.searchTerm);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id) {
            case R.id.FullContact:
                Intent ContactIntent = new Intent(this, FullContactActivity.class);
                startActivity(ContactIntent);
                break;
            case R.id.savedJobs:
                Intent SavedJobsIntent = new Intent(this, SavedJobsActivity.class);
                startActivity(SavedJobsIntent);
                break;
            case R.id.youtubeTips:
                Intent YoutubeIntent = new Intent(this, InterviewTipsActivity.class);
                startActivity(YoutubeIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public void search(View view) {
        Intent intent =new Intent(this, JobsListActivity.class);
        intent.putExtra("searchTerm",searchTerm.getText().toString());
        intent.putExtra("zipcode",zipcode.getText().toString());
        startActivity(intent);

    }

    public void getCurrentLocation(View view) {
        mGoogleApi = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApi.connect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mlocationRequest = LocationRequest.create();
        mlocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mlocationRequest.setInterval(1000);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApi, mlocationRequest, this);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            Toast.makeText(this, "Unable to find current Location", Toast.LENGTH_LONG).show();
        } else {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                android.location.Address address = addresses.get(0);
                zipcode.setText(address.getPostalCode());
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApi, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
<<<<<<< HEAD
          }
=======
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id){
            case R.id.FullContact:
                Intent ContactIntent=new Intent(this,FullContactActivity.class);
                startActivity(ContactIntent);
                break;
            case R.id.savedJobs:
                Intent SavedJobsIntent=new Intent(this,SavedJobsActivity.class);
                startActivity(SavedJobsIntent);
                break;
            case R.id.youtubeTips:
                Intent YoutubeIntent=new Intent(this,InterviewTipsActivity.class);
                startActivity(YoutubeIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
>>>>>>> c05e294f00edc7b925a96ea655e604c01b0a58ba
}




