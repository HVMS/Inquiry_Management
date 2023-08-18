package com.globalitians.inquiry.activities.others;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.activities.AddinquiryActvity;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForCourses;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForStandard;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.Utility.LogUtil;
import com.globalitians.inquiry.activities.network.OkHttpInterface;
import com.globalitians.inquiry.activities.network.OkHttpRequest;
import com.globalitians.inquiry.activities.network.RequestParam;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_COURSES;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_STANDARD;

public class TestActivity extends AppCompatActivity
        implements CourseFilterSelectionAdapter.CourseClickListener,
        StandardFilterSelectionAdapter.StandardClickListner,
        OkHttpInterface, View.OnClickListener {
    private TextView mTitleMonth;
    private RecyclerView mRvYear;
    private RecyclerView mRvMonths;
    private TextView mTitleYear;
    private TextView mTitleStartDate;
    private TextView mStartDate;
    private TextView mEndDate;
    private TextView mTitleEndDate;

    private ArrayList<ModelClassForCourses.Course> mALcourses = new ArrayList<>();
    private ArrayList<ModelClassForStandard.Standard> mALstandard = new ArrayList<>();

    private ModelClassForCourses modelClassForCoursesList;
    private ModelClassForStandard modelClassForStandardList;

    private DatePickerDialog mDatePickerDiaoginquirydate = null;
    private DatePickerDialog mDatePickerDialogJoinedDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottomsheet_common_filter);
        findViews();
        initializedate();
        callApitToLoadCourseData();
        callApiToLoadStandardData();
    }

    private void initializedate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener inquirydatelistner;
        inquirydatelistner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if ((month + 1) < 10 && dayOfMonth < 10) {
                    mStartDate.setText("0" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if ((month + 1) < 10) {
                    mStartDate.setText("" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if (dayOfMonth < 10) {
                    mStartDate.setText("0" + dayOfMonth + "" + (month + 1) + "-" + year);
                } else {
                    mStartDate.setText("" + dayOfMonth + "" + (month + 1) + "-" + year);
                }
            }
        };

        DatePickerDialog.OnDateSetListener joinedDateListener;
        joinedDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if ((month + 1) < 10 && dayOfMonth < 10) {
                    mEndDate.setText("0" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if ((month + 1) < 10) {
                    mEndDate.setText("" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if (dayOfMonth < 10) {
                    mEndDate.setText("0" + dayOfMonth + "" + (month + 1) + "-" + year);
                } else {
                    mEndDate.setText("" + dayOfMonth + "" + (month + 1) + "-" + year);
                }
            }
        };

        //initializing date filter date picker dialog
        mDatePickerDiaoginquirydate = new DatePickerDialog(
                TestActivity.this, R.style.DialogTheme,inquirydatelistner, year, month, day);
        mDatePickerDiaoginquirydate.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);

        //initializing date filter date picker dialog
        mDatePickerDialogJoinedDate = new DatePickerDialog(
                TestActivity.this,R.style.DialogTheme, joinedDateListener, year, month, day);
        mDatePickerDialogJoinedDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    private void callApiToLoadStandardData() {
        if (!CommonUtil.isInternetAvailable(TestActivity.this)) {
            return;
        }

        new OkHttpRequest(TestActivity.this,
                OkHttpRequest.Method.GET,
                Constants.WS_COURSE_STANDARD,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_STUDENT_STANDARD,
                false, this);
    }

    private void callApitToLoadCourseData() {
        if (!CommonUtil.isInternetAvailable(TestActivity.this)) {
            return;
        }

        new OkHttpRequest(TestActivity.this,
                OkHttpRequest.Method.GET,
                Constants.WS_COURSE_LIST,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_STUDENT_COURSES,
                false, this);
    }

    private void setCourseStandardListAdapter(ArrayList<ModelClassForStandard.Standard> standards) {
        StandardFilterSelectionAdapter standardFilterSelectionAdapter =
                new StandardFilterSelectionAdapter(TestActivity.this,standards,this);
        LinearLayoutManager manager1 = new LinearLayoutManager(TestActivity.this);
        StaggeredGridLayoutManager gridLayoutManager1 = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
        mRvYear.setLayoutManager(gridLayoutManager1);
        mRvYear.setAdapter(standardFilterSelectionAdapter);
    }

    private void setCourseCourseListAdapter(ArrayList<ModelClassForCourses.Course> courses) {
        CourseFilterSelectionAdapter courseFilterSelectionAdapter =
                new CourseFilterSelectionAdapter(TestActivity.this,courses,this);
        LinearLayoutManager manager = new LinearLayoutManager(TestActivity.this);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(6,StaggeredGridLayoutManager.HORIZONTAL);
        mRvMonths.setLayoutManager(gridLayoutManager);
        mRvMonths.setAdapter(courseFilterSelectionAdapter);
    }

    private void findViews() {
        mTitleMonth = (TextView) findViewById(R.id.title_month);
        mRvMonths = (RecyclerView) findViewById(R.id.rv_months);
        mRvYear = (RecyclerView) findViewById(R.id.rv_year);
        mTitleYear = (TextView) findViewById(R.id.title_year);
        mTitleStartDate = (TextView) findViewById(R.id.title_start_date);
        mStartDate = (TextView) findViewById(R.id.start_date);
        mTitleEndDate = (TextView) findViewById(R.id.title_end_date);
        mEndDate = (TextView) findViewById(R.id.end_date);

        setListeners();
    }

    private void setListeners() {
        mStartDate.setOnClickListener(this);
        mEndDate.setOnClickListener(this);
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
            case CODE_STUDENT_COURSES:
                Log.e("Courses >>", "" + response);
                final Gson studentCoursesList = new Gson();
                try {
                    modelClassForCoursesList = studentCoursesList.fromJson(response, ModelClassForCourses.class);
                    if (modelClassForCoursesList.getCourses() != null &&
                            modelClassForCoursesList.getCourses().size() > 0) {
                        mALcourses = modelClassForCoursesList.getCourses();
                        setCourseCourseListAdapter(mALcourses);
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
                        setCourseStandardListAdapter(mALstandard);
                    }
                } catch (Exception e) {
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

    @Override
    public void onCourseClick(int position, boolean isChecked) {

    }

    @Override
    public void onStandardClick(int position, boolean isChecked) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_date:
                mDatePickerDiaoginquirydate.show();
                break;
            case R.id.end_date:
                mDatePickerDialogJoinedDate.show();
                break;
        }
    }
}