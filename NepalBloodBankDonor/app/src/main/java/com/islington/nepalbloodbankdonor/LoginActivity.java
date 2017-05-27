package com.islington.nepalbloodbankdonor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.islington.nepalbloodbankdonor.Helper.GlobalState;
import com.islington.nepalbloodbankdonor.JsonData.JsonParser;
import com.islington.nepalbloodbankdonor.Pojo.DonorDetail;
import com.islington.nepalbloodbankdonor.Pojo.UserDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by rupesh on 11/12/16.
 */

public class LoginActivity extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginButton,mRegisterButton;
    String sEmail,sPassword;
    UserDetail userDetail;
    JSONArray user_detail=null;
    String id,name,username,email,password,phone,address,userInfoString;
    JsonParser jsonParser=new JsonParser();
    ProgressDialog progressDialog;

    int status =0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail=(EditText)findViewById(R.id.login_activity_email_edit_text);
        mPassword=(EditText)findViewById(R.id.login_activity_password_edit_text);

        mLoginButton=(Button)findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sEmail=mEmail.getText().toString();
                sPassword=mPassword.getText().toString();

                if(sEmail.length()<1 || sPassword.length()<1){
                    Toast.makeText(LoginActivity.this, "Please fill all the field", Toast.LENGTH_SHORT).show();
                }else if(!isValidEmail(sEmail)){
                    Toast.makeText(LoginActivity.this, "Please validate the email", Toast.LENGTH_SHORT).show();
                }else{
                    new LoginAccess().execute();
                }

            }
        });
        mRegisterButton=(Button)findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
    public final static boolean isValidEmail(CharSequence email){
        if (email==null){
            return false;
        }
        else{
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
   class LoginAccess extends AsyncTask<String,String,String>{
       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           progressDialog=new ProgressDialog(LoginActivity.this);
           progressDialog.setMessage("Logging....");
           progressDialog.setCancelable(false);
           progressDialog.show();

       }

       @Override
       protected String doInBackground(String... strings) {
           HashMap<String,String>hashMap=new HashMap<>();
           hashMap.put("email",sEmail);
           hashMap.put("password",sPassword);

           JSONObject jsonObject=jsonParser.performPostCI("http://wwwrupeshcom.000webhostapp.com/api/login.php",hashMap);
           if(jsonObject ==null){
               status=1;
           }else{
               try{
                  if(jsonObject.getString("status").equals("success")) {
                      status =3;
                      user_detail =jsonObject.getJSONArray("data");
                      for(int i=0;i<user_detail.length();i++){
                          JSONObject c =user_detail.getJSONObject(i);
                          id = c.getString("id");
                          name = c.getString("name");
                          username =c.getString("username");
                          password =c.getString("password");
                          email = c.getString("email");
                          phone = c.getString("mobile");
                          address = c.getString("address");



                           userDetail =new UserDetail(id,name,username,password,email,phone,address);
                      }



                  }else{
                      status =2;
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
               Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
           }else if(status==2){
               Toast.makeText(LoginActivity.this, "Please input correct data", Toast.LENGTH_SHORT).show();
           }else if(status ==3){


               GlobalState globalState=GlobalState.singleton;
               globalState.setPrefsCheckLogin("true",1);


               Gson gson =new Gson();
               userInfoString= gson.toJson(userDetail);
               globalState.setPrefsValidUserInfo(userInfoString,1);





               //Log.d("SendData",json);
               Intent intent=new Intent(getApplicationContext(),MainActivity.class);
             //  intent.putExtra("email",email);
               startActivity(intent);
               finish();
           }
       }
   }

}
