package com.globalitians.inquiry.activities.MyTempWork.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.InquiryReport.Adapter.SampleTestTabsAdapter;
import com.globalitians.inquiry.activities.MyTempWork.fragments.TempDateOneFragment;

/*
public class TempDialogBox extends DialogFragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.temp_test_dialog,container,false);

        tabLayout = rootView.findViewById(R.id.temp_test_layout);
        viewPager = rootView.findViewById(R.id.temp_test_viewpager);

        SampleTestTabsAdapter adapter = new SampleTestTabsAdapter(getChildFragmentManager(),2);

        */
/*adapter.addFragment("By\n Course", new SampleCourseWiseFragment());
        adapter.addFragment("By\n Month",new SampleMonthWiseFragment());
        adapter.addFragment("By\nStd",new SampleStandardWiseFragment());
        adapter.addFragment("By\n Date",new SampleTestFragment());*//*


        adapter.addFragment("BY\n Date",new TempDateOneFragment());
        adapter.addFragment("By\n Two Date",new TempDateTwoFragment());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }
}
*/
