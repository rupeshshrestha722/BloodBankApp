package com.islington.nepalbloodbankdonor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.islington.nepalbloodbankdonor.Adapter.DonorListAdapter;
import com.islington.nepalbloodbankdonor.JsonData.JsonParser;
import com.islington.nepalbloodbankdonor.Pojo.DonorDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rupesh on 11/28/16.
 */

public class DonorListActivity extends AppCompatActivity {
    ListView listView;
    ProgressDialog progressDialog;
    int status=0;
    JsonParser jsonParser =new JsonParser();
    ArrayList<DonorDetail>arrayList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);

        listView=(ListView)findViewById(R.id.donor_list_activity_list_view);
        new showDonorList().execute();




    }
    class showDonorList extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(DonorListActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String>hashMap=new HashMap<>();

            JSONObject jsonObject=jsonParser.getForJSONObject("http://wwwrupeshcom.000webhostapp.com/api/donorlist.php",hashMap);
            if(jsonObject==null){
                status =1;
            }else{

                try{
                    if(jsonObject.getString("status").equals("success")){
                        status =2;

                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject donorObject =jsonArray.getJSONObject(i);
                            String id =donorObject.getString("id");
                            String name =donorObject.getString("name");
                            String email =donorObject.getString("email");
                            String address =donorObject.getString("address");
                            String blood_group =donorObject.getString("blood_group");
                            String mobile =donorObject.getString("mobile");

                            DonorDetail donorDetail=new DonorDetail(id,name,mobile,email,address,blood_group);
                            arrayList.add(donorDetail);
                        }
                    }else {
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
            if(status ==1){
                Toast.makeText(DonorListActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }else if(status==2){
                DonorListAdapter donorListAdapter=new DonorListAdapter(DonorListActivity.this,arrayList);
                listView.setAdapter(donorListAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        DonorDetail donorDetail=(DonorDetail) adapterView.getItemAtPosition(i);
                        Intent intent=new Intent(DonorListActivity.this,DonorDetailActivity.class);
                        intent.putExtra("donor_detail",donorDetail);
                        startActivity(intent);
                    }
                });


            }else if(status ==3){
                Toast.makeText(DonorListActivity.this, "Loading Error", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(DonorListActivity.this, "Something goes wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
