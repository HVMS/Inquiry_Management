package com.globalitians.inquiry.activities.SmsAndNotification.Activities;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.activities.AddinquiryActvity;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForCourses;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForStandard;
import com.globalitians.inquiry.activities.InquiryReport.Activities.InquiryReportActivity;
import com.globalitians.inquiry.activities.InquiryReport.Model.ModelClassForInquiryDetails;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.network.OkHttpInterface;
import com.globalitians.inquiry.activities.network.OkHttpRequest;
import com.globalitians.inquiry.activities.network.RequestParam;
import com.globalitians.inquiry.activities.others.CourseFilterSelectionAdapter;
import com.globalitians.inquiry.activities.others.StandardFilterSelectionAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import static com.globalitians.inquiry.activities.Utility.CommonUtil.getSharedPrefrencesInstance;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_INQUIRY_FILTER;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_COURSES;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_STANDARD;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_EMPLOYEE_BRANCH_ID;

public class AddBottomSheetTestDialog extends BottomSheetDialogFragment
        implements View.OnClickListener,
        OkHttpInterface, CourseFilterSelectionAdapter.CourseClickListener,
        StandardFilterSelectionAdapter.StandardClickListner {

    private TextView mStartDate;
    private TextView mEndDate;

    private RecyclerView mRvYear;
    private RecyclerView mRvMonths;

    private ArrayList<ModelClassForCourses.Course> mALcourses;
    private ArrayList<ModelClassForStandard.Standard> mALstandard;
    private ArrayList<ModelClassForInquiryDetails.Inquiry> mAlfilters;

    private ModelClassForCourses modelClassForCoursesList;
    private ModelClassForStandard modelClassForStandardList;
    private ModelClassForInquiryDetails filterclassModel;

    private DatePickerDialog mDatePickerDiaoginquirydate = null;
    private DatePickerDialog mDatePickerDialogJoinedDate = null;

    private Button btnApplyfilter;
    private String strSelectedstartdate="";
    private String strrangerfirstdate = "";
    private String strrangelastdate = "";
    private String strCoursesids = "";
    private String strStandardids = "";

    public static AddBottomSheetTestDialog newInstances(){
        return new AddBottomSheetTestDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_common_filter,container,false);
        findViews(view);
        initializersfordate();
        initializeArraylists();
        callApitToLoadCourseData();
        callApiToLoadStandardData();
        return view;
    }

    private void initializeArraylists() {
        mAlfilters = new ArrayList<>();
        mALcourses = new ArrayList<>();
        mALstandard = new ArrayList<>();
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

    private void initializersfordate() {
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
                getActivity(), R.style.DialogTheme,inquirydatelistner, year, month, day);
        mDatePickerDiaoginquirydate.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);

        //initializing date filter date picker dialog
        mDatePickerDialogJoinedDate = new DatePickerDialog(
                getActivity(),R.style.DialogTheme, joinedDateListener, year, month, day);
        mDatePickerDialogJoinedDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    private void findViews(View view) {
        mStartDate = view.findViewById(R.id.start_date);
        mEndDate = view.findViewById(R.id.end_date);
        mRvMonths = view.findViewById(R.id.rv_months);
        mRvYear = view.findViewById(R.id.rv_year);
        btnApplyfilter = view.findViewById(R.id.apply_btn);
        setListners();
    }

    private void setListners() {
        mStartDate.setOnClickListener(this);
        mEndDate.setOnClickListener(this);
        btnApplyfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strSelectedstartdate = mStartDate.getText().toString();
                callApitoapplyfilter();
                startActivity(new Intent(getActivity(), InquiryReportActivity.class));
                getActivity().finish();
            }
        });
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

    private void callApitoapplyfilter() {
        if (!CommonUtil.isInternetAvailable(getActivity())){
            return;
        }

        new OkHttpRequest(getActivity(),
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_FILTER,
                RequestParam.filterbottomsheet(""+strSelectedstartdate),
                RequestParam.getNull(),
                CODE_INQUIRY_FILTER,
                false, this);
    }

    /*RequestParam.applyFilters(""+strSelectedstartdate,
            ""+strrangerfirstdate,
            ""+strrangelastdate,
            ""+strCoursesids,
            ""+strStandardids,
            ""+getSharedPrefrencesInstance(getActivity()).getString(KEY_EMPLOYEE_BRANCH_ID,"")),*/

    @Override
    public void onOkHttpStart(int requestId) {

    }

    @Override
    public void onOkHttpSuccess(int requestId, int statusCode, String response) {

        /*{"status":"success","message":"Inquiry List Found","inquiries":
        [{"id":96,"slug":"vf6g4lVU","fname":"Aashutosh","lname":"Soni",
        "contact":"9712195787","inquiry_date":"16-01-2020","status":
        "Inquiry","courses":[{"id":17,"name":"Advance Java",
        "image":"http:\/\/globalitians.com\/demoapp\/pic\/9XJhCOGM.png"}],"branch_name":"Vastral"},{"id":95,"slug":"cWsFgS9a","fname":"Viral","lname":"Siddhapura","contact":"9537995047","inquiry_date":"16-01-2020","status":"Inquiry","courses":
        [{"id":31,"name":"Android App. Development","image":"http:\/\/globalitians.com\/demoapp\/pic\/KMDmpHLm.png"}],"branch_name":"Vastral"},
        {"id":94,"slug":"98BYzPJU","fname":"Bhargav","lname":"Patel","contact":
        "6353064190","inquiry_date":"16-01-2020","status":
        "Inquiry","courses":[{"id":23,"name":"Angular JS","image":"http:\/\/globalitians.com\/demoapp\/pic\/PSsL8RZB.png"}],"branch_name":"Vastral"}]}
        */
        Log.e("temp data only>>>", "" + response);

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
            case CODE_INQUIRY_FILTER:
                Log.e("Filters >>>",""+response);
                final Gson gson = new Gson();
                try{
                    filterclassModel = gson.fromJson(response,ModelClassForInquiryDetails.class);
                    if(filterclassModel.getStatus().equals(Constants.SUCCESS_CODE)){
                       mAlfilters = filterclassModel.getInquiries();
                       if(mAlfilters!=null && mAlfilters.size()>0){
                           MyBottomsheetclicklistner myBottomsheetclicklistner = (MyBottomsheetclicklistner) getActivity();
                           myBottomsheetclicklistner.onReturnValue(mAlfilters);
                       }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setCourseStandardListAdapter(ArrayList<ModelClassForStandard.Standard> standards) {
        StandardFilterSelectionAdapter standardFilterSelectionAdapter =
                new StandardFilterSelectionAdapter(getActivity(),standards,this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        StaggeredGridLayoutManager gridLayoutManager1 = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
        mRvYear.setLayoutManager(gridLayoutManager1);
        mRvYear.setAdapter(standardFilterSelectionAdapter);
        mRvYear.setHasFixedSize(true);
    }

    private void setCourseCourseListAdapter(ArrayList<ModelClassForCourses.Course> courses) {
        CourseFilterSelectionAdapter courseFilterSelectionAdapter =
                new CourseFilterSelectionAdapter(getActivity(),courses,this);
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

    public interface MyBottomsheetclicklistner{
        void onReturnValue(ArrayList<ModelClassForInquiryDetails.Inquiry> marraylistfiltered);
    }
}
