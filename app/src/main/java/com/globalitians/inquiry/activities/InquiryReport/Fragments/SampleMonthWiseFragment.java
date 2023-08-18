package com.globalitians.inquiry.activities.InquiryReport.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.globalitians.inquiry.R;

public class SampleMonthWiseFragment extends Fragment {

    public SampleMonthWiseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        String[] monthItems = {"1 Month","2 Month","3 Month","4 Month","5 Month","6 Month",
                               "7 Month","8 Month","9 Month","10 Month","11 Month","12 Month"};

        View view = inflater.inflate(R.layout.fragment_sample_month_wise, container, false);

        ListView listView= view.findViewById(R.id.sample_month_wise_listview);

        final ArrayAdapter<String> listAdpter = new ArrayAdapter<>(getActivity(),
                R.layout.sample_month_wise_filter_text,R.id.tv_sample_month_wise,monthItems);

        listView.setAdapter(listAdpter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),""+(i-1),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
