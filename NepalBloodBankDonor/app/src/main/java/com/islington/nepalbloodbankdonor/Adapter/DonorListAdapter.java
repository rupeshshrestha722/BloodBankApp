package com.islington.nepalbloodbankdonor.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.islington.nepalbloodbankdonor.Pojo.DonorDetail;
import com.islington.nepalbloodbankdonor.R;

import java.util.ArrayList;


/**
 * Created by rupesh on 11/27/16.
 */

public class DonorListAdapter extends ArrayAdapter<DonorDetail>{
    TextView mName,mBloodGroup,mMobile;
    Context context;

    public DonorListAdapter(Context context, ArrayList<DonorDetail> donorList) {
        super(context, 0, donorList);
        this.context =context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public DonorDetail getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(DonorDetail item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      DonorDetail donorDetail = getItem(position);
        if(convertView ==null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_single_blood_donor_view,parent,false);

        }
        // Lookup view for data population

        mName = (TextView) convertView.findViewById(R.id.adapter_single_blood_donor_name);
        mBloodGroup = (TextView) convertView.findViewById(R.id.adapter_single_blood_donor_blood_group);
        mMobile = (TextView) convertView.findViewById(R.id.adapter_single_blood_donor_mobile_no);


        mName.setText(donorDetail.getName());
        mBloodGroup.setText(donorDetail.getBloodGroup());
        mMobile.setText(donorDetail.getPhone());

        return convertView;
    }
}
