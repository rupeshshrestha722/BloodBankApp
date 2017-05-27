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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.islington.nepalbloodbankdonor.JsonData.JsonParser;
import com.islington.nepalbloodbankdonor.MainActivity;
import com.islington.nepalbloodbankdonor.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by rupesh on 10/24/16.
 */

public class FragmentBloodRequest extends Fragment {
    String sName,sBloodGroup,sQuantity,sDistrict,sContact;
ProgressDialog progressDialog;
    JsonParser jsonParser=new JsonParser();
    int status =0;
    EditText mName,mContact;
    Spinner mSpinnerBloodgroup,mSpinnerDistrict,mSpinnerQuantity;
    Button btnRequest;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_blood_request,container,false);

        mName=(EditText)view.findViewById(R.id.fragment_blood_request_edit_text_name);
        mSpinnerBloodgroup=(Spinner)view.findViewById(R.id.fragment_blood_request_edit_text_bloodgroupselect_sp_et);
        mSpinnerDistrict=(Spinner)view.findViewById(R.id.fragment_blood_request_spinner_district);
        mSpinnerQuantity=(Spinner)view.findViewById(R.id.fragment_blood_request_spinner_quantity);
        mContact =(EditText)view.findViewById(R.id.fragment_blood_request_edit_text_contact_no);

        btnRequest=(Button) view.findViewById(R.id.fragment_blood_request_request_button);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sName=mName.getText().toString();
                sBloodGroup=mSpinnerBloodgroup.getSelectedItem().toString();
                sQuantity=mSpinnerQuantity.getSelectedItem().toString();
                sDistrict=mSpinnerDistrict.getSelectedItem().toString();
                sContact=mContact.getText().toString();

                if(sName.length()<1 ||
                        sBloodGroup.length()<1 ||
                        sQuantity.length()<1 ||
                        sDistrict.length()<1 ||
                        sContact.length()<1){
                    Toast.makeText(getContext(), "Field can not be vacant", Toast.LENGTH_SHORT).show();



                }else {
                    new bloodRequest().execute();
                }


            }
        });

        
        return  view;
    }
    class bloodRequest extends AsyncTask<String,String,String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getContext());
            progressDialog.setMessage("Submitting...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("name",sName);
            hashMap.put("blood_group",sBloodGroup);
            hashMap.put("quantity",sQuantity);
            hashMap.put("contact_no",sContact);
            hashMap.put("location",sDistrict);


            JSONObject jsonObject=jsonParser.performPostCI("http://wwwrupeshcom.000webhostapp.com/api/blood.php",hashMap);
            if(jsonObject==null){
                status =1;
            }else{
                try{
                    if(jsonObject.getString("status").equals("success")){
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
            if(status ==1){
                Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }else if(status ==2){
                Toast.makeText(getContext(), "Submit Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(),MainActivity.class));
            }else if(status ==3){
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
