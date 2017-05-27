package com.islington.nepalbloodbankdonor;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.islington.nepalbloodbankdonor.Pojo.DonorDetail;

/**
 * Created by rupesh on 11/27/16.
 */

public class DonorDetailActivity extends AppCompatActivity {
    TextView txtName,txtEmail,txtAddress,txtBloodGroup,txtMobile;
    Button btnCall,btnMessage;
    DonorDetail donorDetail;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list_items);

        txtName=(TextView)findViewById(R.id.donorList_name);
        txtEmail=(TextView)findViewById(R.id.donorList_email);
        txtAddress=(TextView)findViewById(R.id.donorList_address);
        txtBloodGroup=(TextView)findViewById(R.id.donorList_blood_group);
        txtMobile=(TextView)findViewById(R.id.donorList_mobile);

        btnCall=(Button)findViewById(R.id.donorList_btn_call);
        btnMessage=(Button)findViewById(R.id.donorList_btn_message);

        donorDetail =(DonorDetail) getIntent().getSerializableExtra("donor_detail");
        txtName.setText(donorDetail.getName());
        txtEmail.setText(donorDetail.getEmail());
        txtAddress.setText(donorDetail.getAddress());
        txtBloodGroup.setText(donorDetail.getBloodGroup());
        txtMobile.setText(donorDetail.getPhone());


        // click on call button
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber ="tel: " +donorDetail.getPhone();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(phoneNumber));

                if (ActivityCompat.checkSelfPermission(DonorDetailActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);



            }
        });
        //click on message button

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String singleNumbers=donorDetail.getPhone();

                SmsManager sms= SmsManager.getDefault();
                sms.sendTextMessage(singleNumbers,null,"I need Blood. Please help me",null,null);

            }
        });





    }
}
