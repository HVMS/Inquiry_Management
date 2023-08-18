package com.globalitians.inquiry.activities.MyTempWork.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.InquiryReport.Model.ModelClassForInquiryDetails;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.network.OkHttpInterface;
import com.globalitians.inquiry.activities.network.OkHttpRequest;
import com.globalitians.inquiry.activities.network.RequestParam;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.globalitians.inquiry.activities.Utility.Constants.CODE_INQUIRY_FILTER;


public class TempDateOneFragment extends Fragment implements OkHttpInterface {

    private CalendarView calendarView;
    private String strDate;

    private ModelClassForInquiryDetails modelClassForInquiryDetails;
    private ArrayList<ModelClassForInquiryDetails.Inquiry> mALinquirydetails;

    public TempDateOneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_temp_date_one, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        calendarView = view.findViewById(R.id.calenderview_one);

        calendarView.setDate(System.currentTimeMillis()-1000);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                if ((month + 1) < 10 && dayOfMonth < 10) {
                    strDate = "" + "0" + dayOfMonth + "-0" + (month + 1) + "-" + year;
                } else if ((month + 1) < 10) {
                    strDate= "" + dayOfMonth + "-0" + (month + 1) + "-" + year;
                } else if (dayOfMonth < 10) {
                    strDate = "" + "0" + dayOfMonth + "" + (month + 1) + "-" + year;
                } else {
                    strDate = "" + "" + dayOfMonth + "-" + (month + 1) + "-" + year;
                }
//                Toast.makeText(getContext(), "Selected Date is : " +strDate, Toast.LENGTH_SHORT).show();
                callApiToGetFilteredData();
            }
        });
    }

    private void callApiToGetFilteredData() {
        if(!CommonUtil.isInternetAvailable(getActivity())){
            return;
        }
        new OkHttpRequest(getActivity(),
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_FILTER,
                RequestParam.filterbottomsheet(""+strDate),
                RequestParam.getNull(),
                CODE_INQUIRY_FILTER,
                false,this);
    }

    @Override
    public void onOkHttpStart(int requestId) {

    }

    @Override
    public void onOkHttpSuccess(int requestId, int statusCode, String response) {
        Log.e(">>>",""+response);

        if(response==null){
            return;
        }

        switch (requestId){
            case CODE_INQUIRY_FILTER:
                Log.e("single date",""+response);
                final Gson gson = new Gson();
                try{
                    mALinquirydetails = new ArrayList<>();
                    modelClassForInquiryDetails = gson.fromJson(response,ModelClassForInquiryDetails.class);
                    mALinquirydetails = modelClassForInquiryDetails.getInquiries();
                    MyDialogListner myDialogListner = (MyDialogListner) getActivity();
                    myDialogListner.onReturnSingleDate(mALinquirydetails,11);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }

    }

    @Override
    public void onOkHttpFailure(int requestId, int statusCode, String response, Throwable error) {

    }

    @Override
    public void onOkHttpFinish(int requestId) {

    }

    public interface MyDialogListner{
        void onReturnSingleDate(ArrayList<ModelClassForInquiryDetails.Inquiry> mALfilteredlistdatewise,int flagValue);
    }
}
