package com.jobsearchrt.jobsearchapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Pranav on 8/9/2016.
 */
public class NavigationDrawer extends AppCompatActivity implements AdapterView.OnItemClickListener {
    DrawerLayout drawerLayout;
    ListView NaviView;
    String[] menuItems;
    ActionBarDrawerToggle actionBarDrawerToggle;
    protected FrameLayout frameLayout;
    protected static int position;
    private static boolean isLaunch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navi_drawer);
        menuItems=getResources().getStringArray(R.array.NavigationMenu);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerlayout);
        NaviView= (ListView) findViewById(R.id.navilistitems);
        NaviView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menuItems));
        NaviView.setOnItemClickListener(this);
        frameLayout= (FrameLayout) findViewById(R.id.flContent);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_opened,R.string.drawer_closed){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                Toast.makeText(NavigationDrawer.this,"Drawer opened",Toast.LENGTH_SHORT).show();
                setTheme(R.style.noActionBar);
                actionBarDrawerToggle.setDrawerIndicatorEnabled(false);


            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                Toast.makeText(NavigationDrawer.this,"Drawer closed",Toast.LENGTH_SHORT).show();
                setTheme(R.style.AppTheme);
                actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

            }

        };
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        if(isLaunch){
            isLaunch = false;
            openActivity(0);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        openActivity(i);
        }
    protected void openActivity(int position) {
        drawerLayout.closeDrawer(NaviView);
        NavigationDrawer.position = position;
        switch(position){
            case 0:
                Intent jobIntent=new Intent(this,JobSearchActivity.class);
                startActivity(jobIntent);
                break;
            case 1:
                Intent ContactIntent=new Intent(this,FullContactActivity.class);
                startActivity(ContactIntent);
                break;
            case 2:
                Intent SavedJobsIntent=new Intent(this,SavedJobsActivity.class);
                startActivity(SavedJobsIntent);
                break;
            case 3:
                Intent YoutubeIntent=new Intent(this,InterviewTipsActivity.class);
                startActivity(YoutubeIntent);
                break;

    }

    }
}


