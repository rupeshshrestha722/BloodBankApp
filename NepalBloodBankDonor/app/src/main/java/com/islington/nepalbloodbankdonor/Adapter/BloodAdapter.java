package com.islington.nepalbloodbankdonor.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.islington.nepalbloodbankdonor.Pojo.Blood;
import com.islington.nepalbloodbankdonor.R;


import java.util.ArrayList;

/**
 * Created by rupesh on 12/1/16.
 */

public class BloodAdapter extends ArrayAdapter<Blood> {
    TextView mBloodGroup,mQuantity,mContactNo;
    Context context;
    public BloodAdapter(Context context, ArrayList<Blood> bloodList) {
        super(context, 0, bloodList);
        this.context =context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public Blood getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(Blood item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Blood blood= getItem(position);
        if(convertView ==null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_single_blood,parent,false);

        }
        mBloodGroup=(TextView)convertView.findViewById(R.id.adapter_single_blood_group);
         mQuantity =(TextView)convertView.findViewById(R.id.adapter_single_blood_quantity);
        mContactNo =(TextView) convertView.findViewById(R.id.adapter_single_blood_contact);

        mBloodGroup.setText(blood.getBlood_group());
        mQuantity.setText(blood.getQuantity());
        mContactNo.setText(blood.getContact());

        return convertView;
    }
}
