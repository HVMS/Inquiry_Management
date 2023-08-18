package com.globalitians.inquiry.activities.InquiryReport.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForStandard;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForStraem;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForSubjects;
import com.globalitians.inquiry.activities.InquiryReport.Model.ModelClassForInquiryDetails;
import com.globalitians.inquiry.activities.UpcomingReport.Adapter.SubjectsFilterSelectionAdapter;
import com.globalitians.inquiry.activities.UpcomingReport.Model.UpcomingDataModel;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.network.OkHttpInterface;
import com.globalitians.inquiry.activities.network.OkHttpRequest;
import com.globalitians.inquiry.activities.network.RequestParam;
import com.globalitians.inquiry.activities.others.StandardFilterSelectionAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.globalitians.inquiry.activities.Utility.Constants.CODE_INQUIRY_FILTER;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_STANDARD;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_SUBJCETS;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_UPCOMING_TODAY_OR_TOMORROW_FILTER;

public class SampleStandardWiseFragment extends Fragment implements OkHttpInterface,
        StandardFilterSelectionAdapter.StandardClickListner,
        SubjectsFilterSelectionAdapter.SubjectsListner{

    // staggered gridlayout stufff
    private ModelClassForStandard modelClassForStandardList=null;
    private ArrayList<ModelClassForStandard.Standard> mALstandard;
    private RecyclerView mRvYear;

    private ModelClassForInquiryDetails modelClassForInquiryDetails;
    private ArrayList<ModelClassForInquiryDetails.Inquiry> mALinquirydetails;

    private ModelClassForStraem modelClassForStraem = null;
    private ArrayList<ModelClassForStraem.Stream> mAlstreams;
    private RecyclerView mRvstream;

    private ModelClassForSubjects modelClassForSubjects=null;
    private ArrayList<ModelClassForSubjects.Subject> mAlsubjects;
    private RecyclerView mRvsubjects;

    private ArrayList<UpcomingDataModel.Inquiry> mArrlistfilteredupcoming;
    private UpcomingDataModel upcomingDataModel;

    private SubjectsFilterSelectionAdapter subjectsFilterSelectionAdapter;
    private StandardFilterSelectionAdapter standardFilterSelectionAdapter;

    // another textviews
    private TextView mTxtforrvsubjects;
    private String strSelectedStandardIds = "";

    public SampleStandardWiseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sample_standard_wise, container, false);

        mRvYear= view.findViewById(R.id.rv_year);
        mRvstream = view.findViewById(R.id.rv_stream);
        mRvsubjects = view.findViewById(R.id.rv_subjects);
        mTxtforrvsubjects = view.findViewById(R.id.tv_subject);

        init();

        callApiForLoadStandardData();

        return view;
    }

    private void init() {
        mALstandard = new ArrayList<>();
        mALinquirydetails = new ArrayList<>();
        mAlsubjects = new ArrayList<>();
        mArrlistfilteredupcoming = new ArrayList<>();
    }

    private void callApiForLoadStandardData() {
        if (!CommonUtil.isInternetAvailable(getContext())) {
            return;
        }

        new OkHttpRequest(getActivity(),
                OkHttpRequest.Method.GET,
                Constants.WS_COURSE_STANDARD,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_STUDENT_STANDARD,
                false, this);
    }

    @Override
    public void onOkHttpStart(int requestId) {

    }

    @Override
    public void onOkHttpSuccess(int requestId, int statusCode, String response) {
        Log.e(">>>", "" + response);
        if (response == null) {
            return;
        }

        switch (requestId){
            case CODE_STUDENT_STANDARD:
                Log.e("Standards >>", "" + response);
                final Gson studentStandardList = new Gson();
                try {
                    modelClassForStandardList = studentStandardList.fromJson(response, ModelClassForStandard.class);
                    if (modelClassForStandardList.getStandards() != null
                            && modelClassForStandardList.getStandards().size() > 0) {
                        mALstandard = modelClassForStandardList.getStandards();
                        setCourseStandardListAdapter();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CODE_INQUIRY_FILTER:
                Log.e("standard wise filter",""+response);
                final Gson standardwisefilter = new Gson();
                try{
                    mALinquirydetails = new ArrayList<>();
                    modelClassForInquiryDetails = standardwisefilter.fromJson(response,ModelClassForInquiryDetails.class);
                    mALinquirydetails = modelClassForInquiryDetails.getInquiries();
                    MyBottomsheetclicklistner myBottomsheetclicklistner = (MyBottomsheetclicklistner) getActivity();
                    myBottomsheetclicklistner.onReturnValue(mALinquirydetails,1);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case CODE_UPCOMING_TODAY_OR_TOMORROW_FILTER:
                Log.e("std upcoming",""+response);
                final Gson stdWiseUpcoming = new Gson();
                try{
                    mArrlistfilteredupcoming = new ArrayList<>();
                    upcomingDataModel = stdWiseUpcoming.fromJson(response,UpcomingDataModel.class);
                    mArrlistfilteredupcoming = upcomingDataModel.getInquiries();
                    MyBottomsheetclicklistner myBottomsheetclicklistner = (MyBottomsheetclicklistner) getActivity();
                    myBottomsheetclicklistner.onReturnValuetoUpcomingActivity(mArrlistfilteredupcoming,12);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case CODE_STUDENT_SUBJCETS:
                Log.e("subjects>>>",""+response);
                final Gson subjectGson = new Gson();
                try{
                    mAlsubjects = new ArrayList<>();
                    modelClassForSubjects = subjectGson.fromJson(response,ModelClassForSubjects.class);
                    mAlsubjects = modelClassForSubjects.getSubjects();
                    setSubjectsToAdapter(mAlsubjects);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setSubjectsToAdapter(ArrayList<ModelClassForSubjects.Subject> mAlsubjects) {
        if(mAlsubjects!=null && mAlsubjects.size()>0){
            subjectsFilterSelectionAdapter = new SubjectsFilterSelectionAdapter(getActivity(),mAlsubjects,this);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            StaggeredGridLayoutManager gridLayoutManager2 = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
            mRvsubjects.setLayoutManager(gridLayoutManager2);
            mTxtforrvsubjects.setVisibility(View.VISIBLE);
            mRvsubjects.setAdapter(subjectsFilterSelectionAdapter);
            subjectsFilterSelectionAdapter.notifyDataSetChanged();
            mRvsubjects.setHasFixedSize(true);
        }else if(mAlsubjects==null){
            mAlsubjects.clear();
            mRvsubjects.setAdapter(subjectsFilterSelectionAdapter);
            mRvsubjects.setVisibility(View.GONE);
            mTxtforrvsubjects.setVisibility(View.GONE);
        }
    }

    private void setCourseStandardListAdapter() {
        standardFilterSelectionAdapter = new StandardFilterSelectionAdapter(getActivity(),mALstandard,this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        StaggeredGridLayoutManager gridLayoutManager1 = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
        mRvYear.setLayoutManager(gridLayoutManager1);
        mRvYear.setAdapter(standardFilterSelectionAdapter);
        mRvYear.setHasFixedSize(true);
    }

    @Override
    public void onOkHttpFailure(int requestId, int statusCode, String response, Throwable error) {

    }

    @Override
    public void onOkHttpFinish(int requestId) {

    }

    @Override
    public void onStandardClick(int position, boolean isChecked) {
        mALstandard.get(position).isSelected = isChecked;

        if(mALstandard.get(position).isSelected()==true){
            strSelectedStandardIds = ""+mAlsubjects.get(position).getId();
            callApiToLoadSubjectsAccStd();
        }else if(mALstandard.get(position).isSelected()==true){
            strSelectedStandardIds="";
            callApiToLoadSubjectsAccStd();
        }

        callApiForFilteredData();
        callApiForUpcomingFilterData();
    }

    private void callApiToLoadSubjectsAccStd() {
        if(!CommonUtil.isInternetAvailable(getActivity())){
            return;
        }

        new OkHttpRequest(getActivity(),
                OkHttpRequest.Method.POST,
                Constants.WS_SUBJECT_LIST,
                RequestParam.studentSubjetcs(""+strSelectedStandardIds),
                RequestParam.getNull(),
                CODE_STUDENT_SUBJCETS,
                false,this);
    }

    private void callApiForUpcomingFilterData() {
        if(!CommonUtil.isInternetAvailable(getActivity())){
            return;
        }

        new OkHttpRequest(getActivity(),
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_TODAY_FILTER,
                RequestParam.OnlystandardWiseFilter(""+strSelectedStandardIds),
                RequestParam.getNull(),
                CODE_UPCOMING_TODAY_OR_TOMORROW_FILTER,
                false,this);
    }

    private void callApiForFilteredData() {

        if(!CommonUtil.isInternetAvailable(getActivity())){
            return;
        }

        new OkHttpRequest(getActivity(),
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_FILTER,
                RequestParam.OnlystandardWiseFilter(""+strSelectedStandardIds),
                RequestParam.getNull(),
                CODE_INQUIRY_FILTER,
                false,this);

    }

    @Override
    public void onSubjectsClick(int position, boolean isChecked) {

    }

    public interface MyBottomsheetclicklistner{
        void onReturnValue(ArrayList<ModelClassForInquiryDetails.Inquiry> marraylistfiltered, int flagValue);
        void onReturnValuetoUpcomingActivity(ArrayList<UpcomingDataModel.Inquiry> marraylistfiltered, int flagValue);
    }
}
