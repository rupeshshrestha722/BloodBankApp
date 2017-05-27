package com.islington.nepalbloodbankdonor.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.islington.nepalbloodbankdonor.Adapter.DonorListAdapter;
import com.islington.nepalbloodbankdonor.DonorDetailActivity;
import com.islington.nepalbloodbankdonor.Helper.GlobalState;
import com.islington.nepalbloodbankdonor.JsonData.JsonParser;
import com.islington.nepalbloodbankdonor.Pojo.DonorDetail;
import com.islington.nepalbloodbankdonor.ProfileActivity;
import com.islington.nepalbloodbankdonor.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;



public class FragmentSearchBlood extends Fragment {

    Spinner mSearchSpinner,mDistrictSpinner;
    Button mSearchBtn;
    JsonParser jsonParser=new JsonParser();
    ProgressDialog progressDialog;
    int status =0;
    String blood_group,address;
    DonorDetail donor;


    ListView listView;
    ArrayList<DonorDetail> arrayList=new ArrayList<>();
   // ArrayList<HashMap<String,String>> arrayList=new ArrayList<HashMap<String, String>>();
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_search_blood,container,false);
       mDistrictSpinner=(Spinner) view.findViewById(R.id.spinner_address_et);
        mSearchSpinner= (Spinner)view.findViewById(R.id.bloodgroupselect_sp_search);
        mSearchBtn=(Button)view.findViewById(R.id.btnsearch);
        listView =(ListView)view.findViewById(R.id.listView);


        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                blood_group=mSearchSpinner.getSelectedItem().toString();
                address=mDistrictSpinner.getSelectedItem().toString();
                if(mDistrictSpinner.getSelectedItem().toString().equals("")){
                    Toast.makeText(getContext(), "District should be insert", Toast.LENGTH_SHORT).show();
                }else{
                  //  arrayList.clear();
                /*    ListAdapter adapter=new SimpleAdapter(getContext(),arrayList,R.layout.list_items,
                            new String[]{"name","mobile","blood_group"},
                            new int[]{R.id.name_tv_listitem,
                                    R.id.mobile_tv_listitem,
                                    R.id.bloodgroup_tv_listitem});
                    listView.setAdapter(adapter); */
                    new Search().execute();

                }

            }
        });
      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getContext(),ProfileActivity.class);
                String ppp=((TextView) view.findViewById(R.id.name_tv_listitem)).getText().toString();
                i.putExtra("name",ppp);
                startActivity(i);
            }
        }); */

        return view;
    }
class Search extends AsyncTask<String,String,String>{
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Searching....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

    }

    @Override
    protected String doInBackground(String... strings) {
    /*    GlobalState globalState=GlobalState.singleton;
        String sName=globalState.getUserNameKey(); */


        HashMap<String,String>hashMap=new HashMap<>();
        hashMap.put("blood_group", blood_group);
        hashMap.put("address", address);
      //  hashMap.put("name", sName);

        JSONObject jsonObject=jsonParser.performPostCI("http://wwwrupeshcom.000webhostapp.com/api/search2.php",hashMap);
        try{
            if(jsonObject==null){
                status=1;
            }
            else if(jsonObject.getString("status").equals("success")){
                status=2;
                JSONArray jsonArray=jsonObject.getJSONArray("data");
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject donorObject = jsonArray.getJSONObject(i);
                   String id= donorObject.getString("id");
                    String name=donorObject.getString("name");
                    String email =donorObject.getString("email");
                    String phone =donorObject.getString("mobile");
                    String address =donorObject.getString("address");
                    String blood_group=donorObject.getString("blood_group");



                   /*   HashMap<String,String>hashMap1=new HashMap<>();

                  String name_get=json.getString("name");
                    String mobile_get=json.getString("mobile");

                    String blood_group_get=json.getString("blood_group"); */

                  /*  hashMap1.put("name",name_get);
                    hashMap1.put("mobile",mobile_get);
                    hashMap1.put("blood_group",blood_group_get); */
                    donor = new DonorDetail(id,name,phone,email, address,blood_group);
                    arrayList.add(donor);
                  //  arrayList.add(hashMap1);

                }

            }
            else{
                status=3;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        if(status==1){
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }else if(status==2){
          /*  ListAdapter adapter = new SimpleAdapter(getActivity(),arrayList,R.layout.list_items, new String[]{"name","mobile","blood_group"},
                    new int[]{R.id.name_tv_listitem,R.id.mobile_tv_listitem,R.id.bloodgroup_tv_listitem});

            listView.setAdapter(adapter); */
            DonorListAdapter donorListAdapter=new DonorListAdapter(getActivity(),arrayList);
            listView.setAdapter(donorListAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DonorDetail donorDetail= (DonorDetail) parent.getItemAtPosition(position);

                    Intent intent = new Intent(getContext(), DonorDetailActivity.class);
                    intent.putExtra("donor_detail", donorDetail);
                    startActivity(intent);
                }
            });
        }else{
            Toast.makeText(getContext(), "No Blood Found", Toast.LENGTH_SHORT).show();
        }

    }
}







}
