package com.islington.nepalbloodbankdonor.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.islington.nepalbloodbankdonor.Fragment.FragmentBloodRequest;
import com.islington.nepalbloodbankdonor.Fragment.FragmentBloodResponse;
import com.islington.nepalbloodbankdonor.Fragment.FragmentSearchBlood;

/**
 * Created by rupesh on 10/24/16.
 */

public class ViewPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public ViewPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                FragmentSearchBlood tab1=new FragmentSearchBlood();
                return tab1;

            case 1:
                FragmentBloodRequest tab2=new FragmentBloodRequest();
                return tab2;

            case 2:
                FragmentBloodResponse tab3=new FragmentBloodResponse();
                return tab3;

            default:
                return null;




        }

    }

    @Override
    public int getCount() {

        return mNumOfTabs;
    }
}
