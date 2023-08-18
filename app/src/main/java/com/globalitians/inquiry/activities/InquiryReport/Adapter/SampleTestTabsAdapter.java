package com.globalitians.inquiry.activities.InquiryReport.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SampleTestTabsAdapter extends FragmentPagerAdapter {

    int noOftabs;

    List<Fragment> mFragmentCollection = new ArrayList<>();
    List<String> mTitleCollection = new ArrayList<>();

    public SampleTestTabsAdapter(FragmentManager fm,int noOftabs) {
        super(fm);
        this.noOftabs = noOftabs;
    }

    public void addFragment(String title, Fragment fragment){
        mTitleCollection.add(title);
        mFragmentCollection.add(fragment);
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentCollection.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentCollection.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleCollection.get(position);
    }
}
