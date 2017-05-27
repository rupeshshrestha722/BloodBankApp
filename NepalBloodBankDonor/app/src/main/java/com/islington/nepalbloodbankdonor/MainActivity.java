package com.islington.nepalbloodbankdonor;

import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.islington.nepalbloodbankdonor.Adapter.ViewPageAdapter;
import com.islington.nepalbloodbankdonor.Helper.GlobalState;




public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TabLayout mtabLayout;
    ViewPager mviewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //declaring TabLayout and ViewPage
        mtabLayout=(TabLayout)  findViewById(R.id.tab_layout);
        mviewPager=(ViewPager)  findViewById(R.id.pager);

        //setting TabLayout
        mtabLayout.addTab(mtabLayout.newTab().setText("Search Blood"));
        mtabLayout.addTab(mtabLayout.newTab().setText("Blood Request"));
        mtabLayout.addTab(mtabLayout.newTab().setText("Blood Response"));
        mtabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mviewPager.setOffscreenPageLimit(2);

        final ViewPageAdapter adapter=new ViewPageAdapter(getSupportFragmentManager(),mtabLayout.getTabCount());
        mviewPager.setAdapter(adapter);

        mviewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mtabLayout));
        mtabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mviewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
        } else if (id == R.id.AddDonor) {

            Intent intent =new Intent(MainActivity.this,AddDonorActivity.class);
            startActivity(intent);




        } else if(id==R.id.donorList) {
            startActivity(new Intent(MainActivity.this, DonorListActivity.class));

        }
        else if (id == R.id.profile) {
            startActivity(new Intent(MainActivity.this,ProfileActivity.class));
        } else if(id ==R.id.appinfo){
            startActivity(new Intent(MainActivity.this,AppActivity.class));
        }else if(id==R.id.health_tips){
            startActivity(new Intent(MainActivity.this,TipsActivity.class));
        }else if (id == R.id.logout) {
            GlobalState globalState=GlobalState.singleton;
            globalState.setPrefsCheckLogin("true",2);
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
