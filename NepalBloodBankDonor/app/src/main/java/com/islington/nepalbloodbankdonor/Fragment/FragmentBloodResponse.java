package com.islington.nepalbloodbankdonor.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.islington.nepalbloodbankdonor.Adapter.BloodAdapter;
import com.islington.nepalbloodbankdonor.Adapter.DonorListAdapter;
import com.islington.nepalbloodbankdonor.BloodDetailActivity;

import com.islington.nepalbloodbankdonor.JsonData.JsonParser;
import com.islington.nepalbloodbankdonor.Pojo.Blood;

import com.islington.nepalbloodbankdonor.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by rupesh on 10/24/16.
 */

public class FragmentBloodResponse extends Fragment {
    Blood blood;
    ListView listView;
    ProgressDialog progressDialog;
    int status=0;
    JsonParser jsonParser =new JsonParser();
    ArrayList<Blood> arrayList=new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_view, container, false);

        listView=(ListView)view.findViewById(R.id.fragment_other_view_list_item);
        new showBloodRequestList().execute();




        return view;
    }
    class showBloodRequestList extends AsyncTask<String,String,String>

    {

        @Override
        protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

        @Override
        protected String doInBackground(String... strings) {
        HashMap<String,String> hashMap=new HashMap<>();

        JSONObject jsonObject=jsonParser.getForJSONObject("http://wwwrupeshcom.000webhostapp.com/api/bloodLIST.php",hashMap);
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
                        String bloodGroup =donorObject.getString("blood_group");
                        String quantity =donorObject.getString("quantity");
                        String location =donorObject.getString("location");
                        String contactNo =donorObject.getString("contact_no");

                        blood=new Blood(id,name,bloodGroup,quantity,contactNo,location);
                        arrayList.add(blood);
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
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }else if(status==2){
            BloodAdapter bloodAdapter=new BloodAdapter(getContext(),arrayList);
            listView.setAdapter(bloodAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Blood blood=(Blood) adapterView.getItemAtPosition(i);
                    Intent intent=new Intent(getContext(),BloodDetailActivity.class);
                    intent.putExtra("blood_detail",blood);
                    startActivity(intent);
                }
            });


        }else if(status ==3){
            Toast.makeText(getContext(), "Loading Error", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getContext(), "Something goes wrong", Toast.LENGTH_SHORT).show();
        }
    }
    }

}
