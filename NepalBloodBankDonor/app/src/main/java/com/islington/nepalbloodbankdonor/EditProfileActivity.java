package com.islington.nepalbloodbankdonor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.islington.nepalbloodbankdonor.Helper.GlobalState;
import com.islington.nepalbloodbankdonor.JsonData.JsonParser;
import com.islington.nepalbloodbankdonor.Pojo.UserDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by rupesh on 11/27/16.
 */

public class EditProfileActivity extends AppCompatActivity {
    EditText mName,mUsername,mPassword,mAddress,mMobile,mEmail;
    Button btnEdit;
    int status =0;
    JsonParser jsonParser=new JsonParser();
    ProgressDialog progressDialog;
    String id,name,username,password,email,address,mobile;
    GlobalState globalState=GlobalState.singleton;
    UserDetail userDetail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mName=(EditText)findViewById(R.id.edit_profile_name);
        mUsername=(EditText)findViewById(R.id.edit_profile_username);
        mPassword=(EditText)findViewById(R.id.edit_profile_password);
        mAddress=(EditText)findViewById(R.id.edit_profile_address);
        mMobile=(EditText)findViewById(R.id.edit_profile_number);
        mEmail=(EditText)findViewById(R.id.edit_profile_email);

        userDetail =(UserDetail)  getIntent().getSerializableExtra("userDetail");
        mName.setText(userDetail.getName());
        mUsername.setText(userDetail.getUsername());
        mPassword.setText(userDetail.getPassword());
        mAddress.setText(userDetail.getAddress());
        mMobile.setText(userDetail.getPhone());
        mEmail.setText(userDetail.getEmail());



        btnEdit=(Button)findViewById(R.id.edit_profile_button);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name =mName.getText().toString();
                username =mUsername.getText().toString();
                password=mPassword.getText().toString();
                address=mAddress.getText().toString();
                mobile=mMobile.getText().toString();
                email=mEmail.getText().toString();
                new  editAccess().execute();

            }
        });

    }
    class editAccess extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(EditProfileActivity.this);
            progressDialog.setMessage("Updating....");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("name",name);
            hashMap.put("username",username);
            hashMap.put("password",password);
            hashMap.put("address",address);
            hashMap.put("mobile",mobile);
            hashMap.put("email",email);


            JSONObject jsonObject=jsonParser.performPostCI("http://wwwrupeshcom.000webhostapp.com/api/editProfile.php",hashMap);
            if(jsonObject == null){

                status =1;
            }else{
               try{
                  if(jsonObject.getString("status").equals("success")){
                      status =2;



                  }else{
                      status =3;
                  }
               }catch(JSONException e) {
                   e.printStackTrace();
               }


            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (status == 1) {
                Toast.makeText(EditProfileActivity.this, "Update successfully", Toast.LENGTH_LONG).show();

                GlobalState state = GlobalState.singleton;
                /*state.setPrefsIsLoggedIn("true", 1);*/
                userDetail = new UserDetail(userDetail.getId(),name,username,password,address,mobile,email);
                Gson gson = new Gson();
                state.setPrefsValidUserInfo(gson.toJson(userDetail), 1);
                Intent i = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(i);

            } else if(status == 2)
            {
                Toast.makeText(EditProfileActivity.this,"Server issue or no internet connection",Toast.LENGTH_LONG).show();;
            } else {
                Toast.makeText(EditProfileActivity.this, "something is wrong, please try again", Toast.LENGTH_LONG).show();

              globalState.setPrefsCheckLogin("true",1);
                UserDetail userDetail = new UserDetail(id,name,username,password,address,mobile,email);
                Gson gson = new Gson();
                globalState.setPrefsValidUserInfo(gson.toJson(userDetail), 1);
                Intent i = new Intent(EditProfileActivity.this,MainActivity.class);
                startActivity(i);
                /*Intent i = new Intent(EditProfileActivity.this, LoginActivity.class);
                startActivity(i);*/
            }

        }
    }
}
