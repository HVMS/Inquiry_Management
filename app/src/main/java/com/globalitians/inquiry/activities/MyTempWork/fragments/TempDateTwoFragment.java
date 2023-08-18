package com.globalitians.inquiry.activities.MyTempWork.fragments;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.InquiryReport.Model.ModelClassForInquiryDetails;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.network.OkHttpInterface;
import com.globalitians.inquiry.activities.network.OkHttpRequest;
import com.globalitians.inquiry.activities.network.RequestParam;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.support.constraint.Constraints.TAG;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_INQUIRY_FILTER;

public class TempDateTwoFragment extends Fragment implements OkHttpInterface {

    private String fDate="";
    private String lDate="";
    private DateRangeCalendarView dateRangeCalendarView;

    // model class and response
    private ModelClassForInquiryDetails modelClassForInquiryDetails = null;
    private ArrayList<ModelClassForInquiryDetails.Inquiry> mALinquirydetails = null;

    public TempDateTwoFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_temp_date_two, container, false);
        findViews(rootview);
        init();
        return rootview;
    }

    private void init() {
        mALinquirydetails = new ArrayList<>();
    }

    private void findViews(View rootview) {
        dateRangeCalendarView = rootview.findViewById(R.id.calenderview_two);
        showCalendarStuff();
    }

    private void showCalendarStuff() {

        final Calendar startMonth = Calendar.getInstance();
        startMonth.add(Calendar.MONTH, -2);
        final Calendar endMonth = (Calendar) startMonth.clone();
        endMonth.add(Calendar.MONTH, 5);
        Log.d(TAG, "Start month: " + startMonth.getTime().toString() + " :: End month: " + endMonth.getTime().toString());
        dateRangeCalendarView.setVisibleMonthRange(startMonth, endMonth);

        final Calendar current = Calendar.getInstance();
        dateRangeCalendarView.setCurrentMonth(current);

        dateRangeCalendarView.setCalendarListener(new DateRangeCalendarView.CalendarListener() {

            @Override
            public void onFirstDateSelected(Calendar startDate) {
//                Toast.makeText(getActivity(), "Start Date: " + startDate.getTime().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {

                // convert calendar format to date format

                Date start_date = startDate.getTime();
                Date end_date = endDate.getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

                fDate = simpleDateFormat.format(start_date);
                lDate = simpleDateFormat.format(end_date);

                callApiToFilterDateBetweenData();

                /*Toast.makeText(getActivity(), "Start Date: " + startDate.getTime().toString() + " End date: " + endDate.getTime().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Start Date: " + fDate.trim() + " End date: " + lDate.trim(), Toast.LENGTH_SHORT).show();*/
            }
        });
    }

    private void callApiToFilterDateBetweenData() {
        if(!CommonUtil.isInternetAvailable(getActivity())){
            return;
        }

        new OkHttpRequest(getActivity(),
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_FILTER,
                RequestParam.dateBeetween(""+fDate.trim(),""+lDate.trim()),
                RequestParam.getNull(),
                CODE_INQUIRY_FILTER,false,this);
    }

    @Override
    public void onOkHttpStart(int requestId) {

    }

    @Override
    public void onOkHttpSuccess(int requestId, int statusCode, String response) {
        Log.e(">>",""+response);

        if(response==null){
            return;
        }

        switch (requestId){
            case CODE_INQUIRY_FILTER:
                Log.e("Date between",""+response);
                final Gson dateBetgson = new Gson();
                try{
                    mALinquirydetails = new ArrayList<>();
                    modelClassForInquiryDetails = dateBetgson.fromJson(response,ModelClassForInquiryDetails.class);
                    mALinquirydetails = modelClassForInquiryDetails.getInquiries();
                    DateBetweenListner dateBetweenListner = (DateBetweenListner) getActivity();
                    dateBetweenListner.returnDateBetResToInquiry(mALinquirydetails,21);
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

    public interface DateBetweenListner{
        void returnDateBetResToInquiry(ArrayList<ModelClassForInquiryDetails.Inquiry> marraylistDBfiltered,int flagValue);
    }

}
