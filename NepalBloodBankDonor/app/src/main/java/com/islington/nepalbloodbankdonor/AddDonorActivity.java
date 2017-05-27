package com.islington.nepalbloodbankdonor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.islington.nepalbloodbankdonor.JsonData.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddDonorActivity extends AppCompatActivity {
    int clickCount=1;
    EditText mName, mEmail, mMobile;
    String sName, sEmail, sMobile, sAddress, sBloodGroup;
    Spinner bloodgroupselect_sp, mDistrictSpinner;


    Button mSubmitButton;
    ProgressDialog progressDialog;
    JsonParser jsonParser = new JsonParser();
    int status = 0;


    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);


        mName = (EditText) findViewById(R.id.donor_activity_name_edit_text);
        mMobile = (EditText) findViewById(R.id.donor_activity_phone_edit_text);
        mEmail = (EditText) findViewById(R.id.donor_activity_email_edit_text);
        mDistrictSpinner = (Spinner) findViewById(R.id.donor_activity_districtselect_sp_et);
        bloodgroupselect_sp = (Spinner) findViewById(R.id.bloodgroupselect_sp_et);

        mSubmitButton = (Button) findViewById(R.id.donor_activity_register_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sName = mName.getText().toString();
                sEmail = mEmail.getText().toString();
                sAddress = mDistrictSpinner.getSelectedItem().toString();
                sMobile = mMobile.getText().toString();
                sBloodGroup = bloodgroupselect_sp.getSelectedItem().toString();

                if (sName.length() < 1 ||
                        sEmail.length() < 1 ||
                        sMobile.length() < 1) {
                    Toast.makeText(AddDonorActivity.this, "Every field can be required", Toast.LENGTH_SHORT).show();

                } else {



                        new SubmitDonorAction().execute();
                    clickCount++;


                }

            }
        });


    }

    class SubmitDonorAction extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AddDonorActivity.this);
            progressDialog.setMessage("Submitting..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", sName);
            hashMap.put("email", sEmail);
            hashMap.put("address", sAddress);
            hashMap.put("mobile", sMobile);
            hashMap.put("blood_group", sBloodGroup);
            JSONObject jsonObject = jsonParser.performPostCI("http://wwwrupeshcom.000webhostapp.com/api/donorData.php", hashMap);
            if (jsonObject == null) {
                status = 1;

            } else {
                try {
                    if (jsonObject.get("status").equals("success")) {
                        status = 2;
                    } else {
                        status = 3;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (status == 1) {
                Toast.makeText(AddDonorActivity.this, "No Internet Connection ", Toast.LENGTH_SHORT).show();
            } else if (status == 2) {
                if(clickCount==1) {
                   startActivity(new Intent(AddDonorActivity.this,MainActivity.class));
                    Toast.makeText(AddDonorActivity.this, "Add Succesfully", Toast.LENGTH_SHORT).show();
                }else if(clickCount!=1){



                    mSubmitButton.setClickable(false);
                }




                // Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
            } else if (status == 3) {
                Toast.makeText(AddDonorActivity.this, "Input Only Valid Data.", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(AddDonorActivity.this, "Sorry! Something goes wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }

}