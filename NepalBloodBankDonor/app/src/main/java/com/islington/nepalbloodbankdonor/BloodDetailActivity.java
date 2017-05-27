package com.islington.nepalbloodbankdonor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.islington.nepalbloodbankdonor.Pojo.Blood;
import com.islington.nepalbloodbankdonor.Pojo.DonorDetail;

public class BloodDetailActivity extends AppCompatActivity {

    TextView mName,mBloodGroup,mQuantity,mLocation,mContact;

    Button btnCall,btnMessage;
    Blood blood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood);


        mName=(TextView)findViewById(R.id.blood_name);
        mBloodGroup=(TextView)findViewById(R.id.blood_blood_group);
        mQuantity=(TextView)findViewById(R.id.blood_quantity);
        mLocation=(TextView)findViewById(R.id.blood_location);
        mContact=(TextView)findViewById(R.id.blood_contact);


        btnCall=(Button)findViewById(R.id.blood_btn_call);
        btnMessage=(Button)findViewById(R.id.blood_btn_message);

        blood =(Blood) getIntent().getSerializableExtra("blood_detail");
        mName.setText(blood.getName());
        mBloodGroup.setText(blood.getBlood_group());
        mQuantity.setText(blood.getQuantity());
        mContact.setText(blood.getContact());
        mLocation.setText(blood.getLocation());

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber ="tel: " +blood.getContact();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(phoneNumber));

                if (ActivityCompat.checkSelfPermission(BloodDetailActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);



            }
        });


        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String singleNumbers=blood.getContact();

                SmsManager sms= SmsManager.getDefault();
                sms.sendTextMessage(singleNumbers,null,"I want to donate blood. Please contact me",null,null);

            }
        });





    }

}
