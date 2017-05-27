package com.islington.nepalbloodbankdonor;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.islington.nepalbloodbankdonor.Helper.GlobalState;


/**
 * Created by DemonPriEsT on 10/6/2016.
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView mLoader=(ImageView)findViewById(R.id.splash);
        Glide.with(SplashActivity.this).load(R.drawable.logo).into(mLoader);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GlobalState globalState= GlobalState.singleton;
                String checkLogin=globalState.getPrefsCheckLogin();
                if(checkLogin.equals("true")){
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));


                }else{

                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                }

                finish();
            }
        },3000);
    }
}
