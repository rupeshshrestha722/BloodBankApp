package com.islington.nepalbloodbankdonor;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.islington.nepalbloodbankdonor.JsonData.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by rupesh on 11/12/16.
 */

public class RegisterActivity extends AppCompatActivity {
    EditText mName,mUsername,mEmail,mPassword,mConfirmPassword,mMobile;
    String sName,sUsername,sEmail,sPassword,sConfirmPassword,sMobile,sAddress;
    Spinner mDistrictSpinner;


    Button mRegisterButton;
    ProgressDialog progressDialog;
    JsonParser jsonParser=new JsonParser();
    int status=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName = (EditText) findViewById(R.id.register_activity_name_edit_text);
        mUsername = (EditText) findViewById(R.id.register_activity_username_edit_text);

        mMobile=(EditText) findViewById(R.id.register_activity_phone_edit_text);
        mEmail=(EditText)findViewById(R.id.register_activity_email_edit_text) ;
        mDistrictSpinner=(Spinner) findViewById(R.id.districtselect_sp_et);
        mPassword=(EditText) findViewById(R.id.register_activity_password_edit_text);
        mConfirmPassword=(EditText) findViewById(R.id.register_activity_confirm_password_edit_text);


        mRegisterButton=(Button)findViewById(R.id.register_activity_register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sName=mName.getText().toString();
                sUsername=mUsername.getText().toString();
                sEmail=mEmail.getText().toString();
                sAddress=mDistrictSpinner.getSelectedItem().toString();
                sMobile=mMobile.getText().toString();
                sPassword=mPassword.getText().toString();
                sConfirmPassword=mConfirmPassword.getText().toString();


                if (sName.length()<1 ||
                        sUsername.length()<1||
                        sEmail.length()<1 ||
                        sMobile.length()<1 ||
                        sPassword.length()<1 ||
                        sConfirmPassword.length()<1 ){
                    Toast.makeText(RegisterActivity.this, "Every field can be required", Toast.LENGTH_SHORT).show();

                } else if (!sPassword.equals(sConfirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();

                }else{
                    new RegisterAction().execute();
                }

            }
        });

    }
    class RegisterAction extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Registering...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String>hashMap=new HashMap<>();
            hashMap.put("name",sName);
            hashMap.put("username",sUsername);
            hashMap.put("email",sEmail);
            hashMap.put("address",sAddress);
            hashMap.put("mobile",sMobile);
            hashMap.put("password",sPassword);
            JSONObject jsonObject=jsonParser.performPostCI("http://wwwrupeshcom.000webhostapp.com/api/register.php",hashMap);
            if(jsonObject==null){
                status=1;

            }else{
                try{
                    if(jsonObject.get("status").equals("success")){
                        status =2;
                    }else{
                        status =3;
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(status==1){
                Toast.makeText(RegisterActivity.this, "No Internet Connection ", Toast.LENGTH_SHORT).show();
            }
            else if(status==2){
                Intent registerIntent=new Intent(RegisterActivity.this,LoginActivity.class);
               // registerIntent.putExtra("register_success","User Register Successfully.");
                startActivity(registerIntent);
                // Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
            }else if(status==3){
                Toast.makeText(RegisterActivity.this, "Input Only Valid Data.", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(RegisterActivity.this, "Sorry! Something goes wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }

}