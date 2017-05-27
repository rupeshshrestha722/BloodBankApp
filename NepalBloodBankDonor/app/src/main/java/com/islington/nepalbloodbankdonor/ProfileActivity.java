package com.islington.nepalbloodbankdonor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.islington.nepalbloodbankdonor.Helper.GlobalState;
import com.islington.nepalbloodbankdonor.Pojo.UserDetail;

/**
 * Created by rupesh on 11/19/16.
 */

public class ProfileActivity extends AppCompatActivity {
    UserDetail userDetail;
    Button mEditBtn;
    Gson gson =new Gson();
    GlobalState globalState=GlobalState.singleton;
    TextView mName,mUsername,mPassword, mEmail, mAddress, mMobile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userDetail =gson.fromJson(globalState.getPREFS_VALID_USER_INFO(),UserDetail.class);
        mName=(TextView)findViewById(R.id.profile_name);
        mUsername=(TextView)findViewById(R.id.profile_username);
        mPassword=(TextView) findViewById(R.id.profile_password);
        mAddress=(TextView)findViewById(R.id.profile_address);
        mMobile=(TextView)findViewById(R.id.profile_number);
        mEmail=(TextView)findViewById(R.id.profile_email);

        mName.setText(userDetail.getName());
        mUsername.setText(userDetail.getUsername());
        mPassword.setText(userDetail.getPassword());
        mAddress.setText(userDetail.getAddress());
        mMobile.setText(userDetail.getPhone());
        mEmail.setText(userDetail.getEmail());



        mEditBtn = (Button)findViewById(R.id.edit_button);
        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this,EditProfileActivity.class);
                i.putExtra("userDetail",userDetail);
                startActivity(i);
            }
        });

    }
}
