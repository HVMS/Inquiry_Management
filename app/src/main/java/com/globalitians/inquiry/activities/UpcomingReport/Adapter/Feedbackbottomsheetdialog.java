package com.globalitians.inquiry.activities.UpcomingReport.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForCourses;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForStandard;
import com.globalitians.inquiry.activities.UpcomingReport.Activities.UpcomingReportActivity;
import com.globalitians.inquiry.activities.UpcomingReport.Model.UpcomingDataModel;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.network.OkHttpInterface;
import com.globalitians.inquiry.activities.network.OkHttpRequest;
import com.globalitians.inquiry.activities.network.RequestParam;
import com.globalitians.inquiry.activities.others.CourseFilterSelectionAdapter;
import com.globalitians.inquiry.activities.others.StandardFilterSelectionAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_COURSES;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_STANDARD;


public class Feedbackbottomsheetdialog extends BottomSheetDialogFragment
    implements OkHttpInterface,CourseFilterSelectionAdapter.CourseClickListener,
        StandardFilterSelectionAdapter.StandardClickListner {

    private RecyclerView mRvYear;
    private RecyclerView mRvMonths;

    private ArrayList<ModelClassForCourses.Course> mALcourses;
    private ArrayList<ModelClassForStandard.Standard> mALstandard;

    private ModelClassForCourses modelClassForCoursesList;
    private ModelClassForStandard modelClassForStandardList;

    private Button btnApplyfilter;
    private LinearLayout linearLayout;

    private ArrayList<UpcomingDataModel.Inquiry> mAlfilteredlist;

    public static Feedbackbottomsheetdialog newInstances(){
        return new Feedbackbottomsheetdialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_common_filter,container,false);
        findViews(view);
        initializeArraylists();
        callApitToLoadCourseData();
        callApiToLoadStandardData();
        return view;
    }

    private void callApiToLoadStandardData() {
        if (!CommonUtil.isInternetAvailable(getActivity())) {
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

    private void callApitToLoadCourseData() {
        if (!CommonUtil.isInternetAvailable(getActivity())){
            return;
        }

        new OkHttpRequest(getActivity(),
                OkHttpRequest.Method.GET,
                Constants.WS_COURSE_LIST,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_STUDENT_COURSES,
                false, this);
    }

    private void initializeArraylists() {
        mALcourses = new ArrayList<>();
        mALstandard = new ArrayList<>();
        mAlfilteredlist = new ArrayList<>();
    }

    private void findViews(View view) {
        mRvMonths = view.findViewById(R.id.rv_months);
        mRvYear = view.findViewById(R.id.rv_year);
        linearLayout = view.findViewById(R.id.lin_start_end_date);
        linearLayout.setVisibility(View.GONE);
        btnApplyfilter = view.findViewById(R.id.apply_btn);
        setListners();
    }

    private void setListners() {
        btnApplyfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                callApiToApplyFilterByCourseOrStandard();
                startActivity(new Intent(getActivity(), UpcomingReportActivity.class));
            }
        });
    }

    /*private void callApiToApplyFilterByCourseOrStandard() {
        if(!CommonUtil.isInternetAvailable(getActivity())){
            return;
        }

        new OkHttpRequest(getActivity(),
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_TODAY_FILTER,
                RequestParam.todayFilter("",
                        "",
                        "",
                        "",
                        ""),
                RequestParam.getNull(),
                CODE_UPCOMING_TODAY_OR_TOMORROW_FILTER,
                false,this);
    }*/

    @Override
    public void onOkHttpStart(int requestId) {

    }

    @Override
    public void onOkHttpSuccess(int requestId, int statusCode, String response) {
        Log.e("upcoming data>>>", "" + response);

        if (response == null) {
            return;
        }

        switch (requestId) {
            case CODE_STUDENT_COURSES:
                Log.e("Courses >>", "" + response);
                final Gson studentCoursesList = new Gson();
                try {
                    modelClassForCoursesList = studentCoursesList.fromJson(response, ModelClassForCourses.class);
                    if (modelClassForCoursesList.getCourses() != null &&
                            modelClassForCoursesList.getCourses().size() > 0) {
                        mALcourses = modelClassForCoursesList.getCourses();
                        setCourseCourseListAdapter();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
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
        }
    }

    private void setCourseStandardListAdapter() {
        StandardFilterSelectionAdapter standardFilterSelectionAdapter =
                new StandardFilterSelectionAdapter(getActivity(),mALstandard,this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        StaggeredGridLayoutManager gridLayoutManager1 = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
        mRvYear.setLayoutManager(gridLayoutManager1);
        mRvYear.setAdapter(standardFilterSelectionAdapter);
        mRvYear.setHasFixedSize(true);
    }

    private void setCourseCourseListAdapter() {
        CourseFilterSelectionAdapter courseFilterSelectionAdapter =
                new CourseFilterSelectionAdapter(getActivity(),mALcourses,this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.HORIZONTAL);
        mRvMonths.setLayoutManager(gridLayoutManager);
        mRvMonths.setAdapter(courseFilterSelectionAdapter);
        mRvMonths.setHasFixedSize(true);
    }

    @Override
    public void onOkHttpFailure(int requestId, int statusCode, String response, Throwable error) {

    }

    @Override
    public void onOkHttpFinish(int requestId) {

    }

    @Override
    public void onCourseClick(int position, boolean isChecked) {

    }

    @Override
    public void onStandardClick(int position, boolean isChecked) {

    }
}

