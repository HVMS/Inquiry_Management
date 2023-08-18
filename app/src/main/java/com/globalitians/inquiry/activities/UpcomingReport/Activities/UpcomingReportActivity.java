package com.globalitians.inquiry.activities.UpcomingReport.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForCourses;
import com.globalitians.inquiry.activities.InquiryReport.Activities.InquiryReportActivity;
import com.globalitians.inquiry.activities.InquiryReport.Adapter.InquiryReportAdapter;
import com.globalitians.inquiry.activities.InquiryReport.Adapter.SampleCourseWiseAdapter;
import com.globalitians.inquiry.activities.InquiryReport.Adapter.SampleTestTabsAdapter;
import com.globalitians.inquiry.activities.InquiryReport.Fragments.SampleCourseWiseFragment;
import com.globalitians.inquiry.activities.InquiryReport.Fragments.SampleStandardWiseFragment;
import com.globalitians.inquiry.activities.InquiryReport.Model.ModelClassForInquiryDetails;
import com.globalitians.inquiry.activities.UpcomingReport.bottomsheet.Feedbackbottomsheetdialog;
import com.globalitians.inquiry.activities.UpcomingReport.Adapter.UpcomingActivityAdapter;
import com.globalitians.inquiry.activities.UpcomingReport.Model.UpcomingDataModel;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.Utility.LogUtil;
import com.globalitians.inquiry.activities.network.OkHttpInterface;
import com.globalitians.inquiry.activities.network.OkHttpRequest;
import com.globalitians.inquiry.activities.network.RequestParam;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.globalitians.inquiry.activities.Utility.CommonUtil.playSoundForAttendance;
import static com.globalitians.inquiry.activities.Utility.CommonUtil.setFullScreenMode;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_INQUIRY_FILTER;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_COURSES;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_UPCOMING_INQUIRIES;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_UPCOMING_SEARCH;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_UPCOMING_TODAY_OR_TOMORROW_FILTER;

public class UpcomingReportActivity extends AppCompatActivity
        implements UpcomingActivityAdapter.OnUpcomingListListener,
        View.OnClickListener,
        OkHttpInterface,
        SampleCourseWiseAdapter.CourseWiseClickListner{

    private UpcomingActivityAdapter upcomingActivityAdapter;

    private SwipeRefreshLayout mSwipeToRefresh;

    private LinearLayout ll_filter_today;
    private LinearLayout ll_filter_tommorow;
    private LinearLayout ll_filter_withinsevendays;

    private TextView tv_filter_by_today;
    private TextView tv_filter_by_tommorow;
    private TextView tv_filter_by_wihtin_seven_days;

    private RecyclerView recyclerView;
    private FloatingActionButton mFabInquiryOptions;

    private String strTodayFilterdata = "";
    private String strTomorrowFilterdata = "";
    private String strWithinsevendaysFilterdata = "";

    public static Button btnApply;

    // api views

    private TextView mTvextra;
    private UpcomingDataModel upcomingDataModel;
    private ArrayList<UpcomingDataModel.Inquiry> mAlupcomingOrFilterlist;
    private ArrayList<UpcomingDataModel.Inquiry> mAlsearchupcominlist;

    // final api variable stuff

    private BottomSheetDialog mBottomSheetCourseFilter;
    private ListView mLvcourselist;
    private Button btncourseApply;
    private TextView mTxtcourseby;
    private ModelClassForCourses modelClassForCoursesList=null;
    private SampleCourseWiseAdapter mAdapterCourseList = null;
    private ArrayList<ModelClassForCourses.Course> mAlcourselist;
    private ArrayList<String> mAlstrings;
    private String mStringids = "";
    private String mStrnames="";
    private Button btnClearFilter;
    private ArrayList<String> mAlitems;
    private TextView mTxtselectedcourses;

    private TextView mTxtfilterview;

    // ttws = today, tomorrow, within seven days filter view
    private LinearLayout mLinearttws;
    private RelativeLayout mRelativettwsfilter;
    private ImageView mIvclose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode(UpcomingReportActivity.this);
        setContentView(R.layout.activity_upcoming_report);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Upcoming Report");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.bv_primaryColor)));

        findViews();
        initialize();
        callApitoloadupcominginquiries();
    }

    private void initialize() {
        mAlsearchupcominlist = new ArrayList<>();
        mAlstrings = new ArrayList<>();
        mAlitems = new ArrayList<>();
        mAlupcomingOrFilterlist = new ArrayList<>();
        mAlcourselist = new ArrayList<>();
    }

    private void findViews() {

        mTvextra = findViewById(R.id.tvextra);
        recyclerView = findViewById(R.id.upcomingrecycler);
        mTxtselectedcourses = findViewById(R.id.tv_total_selected_courses);
        mTxtfilterview = findViewById(R.id.txtfiltername);
        mRelativettwsfilter = findViewById(R.id.rel_filter);
        mIvclose = findViewById(R.id.iv_close);
        mLinearttws = findViewById(R.id.lin_temp);

        ll_filter_today = findViewById(R.id.ll_filter_today);
        ll_filter_tommorow = findViewById(R.id.ll_filter_tommorow);
        ll_filter_withinsevendays = findViewById(R.id.ll_filter_within_seven_days);

        tv_filter_by_today = ll_filter_today.findViewById(R.id.tv_filter_by_today);
        tv_filter_by_tommorow = ll_filter_tommorow.findViewById(R.id.tv_filter_by_tommorow);
        tv_filter_by_wihtin_seven_days = ll_filter_withinsevendays.findViewById(R.id.tv_filter_by_within_seven_days);

        ll_filter_today.setOnClickListener(this);
        ll_filter_tommorow.setOnClickListener(this);
        ll_filter_withinsevendays.setOnClickListener(this);

        mFabInquiryOptions = findViewById(R.id.fabFilterInquiry);

        setListners();

        mSwipeToRefresh = findViewById(R.id.swipeToRefreshStudentList);
        mSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshUpcomingInquiries();
                mSwipeToRefresh.setRefreshing(false);
            }
        });

        // Image view close stuff
        mIvclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playSoundForAttendance("Course Filter's closed now !!!",UpcomingReportActivity.this);
                Toast.makeText(UpcomingReportActivity.this, "Course Filter's closed now !!!", Toast.LENGTH_SHORT).show();
                refreshUpcomingInquiries();

            }
        });
    }

    private void refreshUpcomingInquiries() {

        if(mAlupcomingOrFilterlist!=null && mAlupcomingOrFilterlist.size()>0){
            mAlupcomingOrFilterlist.clear();
        }
        if(mAlsearchupcominlist!=null && mAlsearchupcominlist.size()>0){
            mAlsearchupcominlist.clear();
        }

        if(upcomingActivityAdapter!=null){
            upcomingActivityAdapter.notifyDataSetChanged();
        }

        mAlupcomingOrFilterlist = new ArrayList<>();
        mAlsearchupcominlist = new ArrayList<>();

        mTvextra.setVisibility(View.GONE);
        mRelativettwsfilter.setVisibility(View.GONE);
        mLinearttws.setVisibility(View.VISIBLE);

        for(int i = 0 ; i < mAlcourselist.size() ; i++){
            mAlcourselist.get(i).isSelected = false;
        }

        // all data should be cleared here
        mStringids = "";
        mAlstrings.clear();
        mAlitems.clear();

        changeTodayFilterbk();
        changeTomorrowFilterbk();
        changeWithinsevendays();

        // call default to load upcoming inquiries
        callApitoloadupcominginquiries();
    }

    private void setListners() {
        mFabInquiryOptions.setOnClickListener(this);
    }

    @Override
    public void onMoreOptionsClick(int adapterPosition, ImageView ivMoreOptions) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to Search");

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));

        SearchView.OnQueryTextListener queryTextListener = null;
        if(searchView!=null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    String searchQuery = s.toLowerCase();
                    if (searchQuery.length() > 0) {
                        OkHttpRequest.cancelOkHttpRequest(Constants.WS_INQUIRY_TODAY_FILTER);
                        callApiToSearchUpcomingInquiries(searchQuery);
                    } else {
                        searchQuery = "";
                        refreshUpcomingInquiries();
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    String searchQuery = s.toLowerCase();
                    if (searchQuery.length() > 0) {
                        OkHttpRequest.cancelOkHttpRequest(Constants.WS_INQUIRY_TODAY_FILTER);
                        callApiToSearchUpcomingInquiries(searchQuery);
                    } else {
                        searchQuery = "";
                        refreshUpcomingInquiries();
                    }
                    return true;
                }
            };
        }

        searchView.setOnQueryTextListener(queryTextListener);

        final SearchView finalSearchView = searchView;
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finalSearchView.setQueryHint("");
                return true;
            }
        });

        return true;
    }

    private void callApiToSearchUpcomingInquiries(String searchQuery) {
        if(!CommonUtil.isInternetAvailable(UpcomingReportActivity.this)){
            return;
        }

        new OkHttpRequest(UpcomingReportActivity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_UPCOMING_SEARCH,
                RequestParam.searchUpcomingInquiries(""+searchQuery),
                RequestParam.getNull(),
                CODE_UPCOMING_SEARCH,
                true,this);
    }

    private void setSearchupcomingdatatoadapter(ArrayList<UpcomingDataModel.Inquiry> mAlsearchupcominlist) {
        upcomingActivityAdapter = new UpcomingActivityAdapter(UpcomingReportActivity.this, this.mAlsearchupcominlist,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(upcomingActivityAdapter);
        upcomingActivityAdapter.notifyDataSetChanged();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_filter_today:
                callBackgroundllfiltertoday();
                changeTomorrowFilterbk();
                changeWithinsevendays();
                strTodayFilterdata = "1";
                strTomorrowFilterdata = "";
                strWithinsevendaysFilterdata = "";
                callApitoloadtodayortommorowfilterdata(strTodayFilterdata,strTomorrowFilterdata,strWithinsevendaysFilterdata);
                break;
            case R.id.ll_filter_tommorow:
                callBackgroundllfiltertommorow();
                changeTodayFilterbk();
                changeWithinsevendays();
                strTodayFilterdata = "";
                strTomorrowFilterdata = "1";
                strWithinsevendaysFilterdata = "";
                callApitoloadtodayortommorowfilterdata(strTodayFilterdata,strTomorrowFilterdata,strWithinsevendaysFilterdata);
                break;
            case R.id.ll_filter_within_seven_days:
                callbackgroundllfilterwithinsevendays();
                changeTodayFilterbk();
                changeTomorrowFilterbk();
                strTodayFilterdata = "";
                strTomorrowFilterdata = "";
                strWithinsevendaysFilterdata = "1";
                callApitoloadtodayortommorowfilterdata(strTodayFilterdata,strTomorrowFilterdata,strWithinsevendaysFilterdata);
                break;
            case R.id.fabFilterInquiry:
                callbottomsheetdialog();
                break;
        }
    }

    @SuppressLint("ResourceAsColor")
    private void changeWithinsevendays() {
        ll_filter_withinsevendays.setBackgroundResource(R.drawable.rounded_filter_white);
        tv_filter_by_wihtin_seven_days.setTextColor(R.color.colorBlackAlpha);
    }

    @SuppressLint("ResourceAsColor")
    private void changeTomorrowFilterbk() {
        ll_filter_tommorow.setBackgroundResource(R.drawable.rounded_filter_white);
        tv_filter_by_tommorow.setTextColor(R.color.colorBlackAlpha);
    }

    @SuppressLint("ResourceAsColor")
    private void changeTodayFilterbk() {
        ll_filter_today.setBackgroundResource(R.drawable.rounded_filter_white);
        tv_filter_by_today.setTextColor(R.color.colorBlackAlpha);
    }

    private void callApitoloadtodayortommorowfilterdata(String strTodayFilterdata,String strTomorrowFilterdata,String strWithinsevendaysFilterdata) {
        if(!CommonUtil.isInternetAvailable(UpcomingReportActivity.this)){
            return;
        }

        new OkHttpRequest(UpcomingReportActivity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_TODAY_FILTER,
                RequestParam.todayFilter(""+strTodayFilterdata,
                        ""+strTomorrowFilterdata,
                        ""+strWithinsevendaysFilterdata),
                RequestParam.getNull(),
                CODE_UPCOMING_TODAY_OR_TOMORROW_FILTER,
                true,this);
    }

    private void callApitoloadupcominginquiries() {
        if(!CommonUtil.isInternetAvailable(UpcomingReportActivity.this)){
            return;
        }

        new OkHttpRequest((UpcomingReportActivity.this),
                OkHttpRequest.Method.GET,
                Constants.WS_UPCOMING_INQUIRIES,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_UPCOMING_INQUIRIES,
                false, this);
    }

    private void callbackgroundllfilterwithinsevendays() {
        ll_filter_withinsevendays.setBackgroundResource(R.drawable.new_ll_filter_today);
        tv_filter_by_wihtin_seven_days.setTextColor(Color.WHITE);
    }

    private void callBackgroundllfiltertommorow() {
        ll_filter_tommorow.setBackgroundResource(R.drawable.new_ll_filter_today);
        tv_filter_by_tommorow.setTextColor(Color.WHITE);
    }

    private void callBackgroundllfiltertoday() {
        ll_filter_today.setBackgroundResource(R.drawable.new_ll_filter_today);
        tv_filter_by_today.setTextColor(Color.WHITE);
    }

    private void callbottomsheetdialog() {
        /*addBottomSheetDialog = AddBottomSheetDialog.newInstances();
        addBottomSheetDialog.show(getSupportFragmentManager(),"add tag");*/
        final View mViewcourseFilter = getLayoutInflater().inflate(R.layout.course_bottom_sheet_filter,null);
        mBottomSheetCourseFilter = new BottomSheetDialog(UpcomingReportActivity.this);
        mBottomSheetCourseFilter.setContentView(mViewcourseFilter);
        mBottomSheetCourseFilter.show();

        setFullHightForBottomSheetDialog(mBottomSheetCourseFilter);

        mTxtcourseby = mViewcourseFilter.findViewById(R.id.tv_course_name);
        btncourseApply = mViewcourseFilter.findViewById(R.id.btn_course_filter);
        btnClearFilter = mViewcourseFilter.findViewById(R.id.btn_clear_filter);
        mLvcourselist = mViewcourseFilter.findViewById(R.id.sample_course_wise_listview);

        callApiForLoadCourseData();

        btnClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0 ; i < mAlcourselist.size() ; i++){
                    mAlcourselist.get(i).isSelected = false;
                }

                // all data should be cleared here
                mStringids = "";
                mAlstrings.clear();
                mAlitems.clear();
                mRelativettwsfilter.setVisibility(View.GONE);
                mLinearttws.setVisibility(View.VISIBLE);
                mTvextra.setVisibility(View.GONE);
                callApitoloadupcominginquiries();
                playSoundForAttendance("Courses Filter's cleared now !!!",UpcomingReportActivity.this);
                Toast.makeText(UpcomingReportActivity.this, "Courses Filter's cleared now !!!", Toast.LENGTH_SHORT).show();

                if(mBottomSheetCourseFilter.isShowing()==true){
                    mBottomSheetCourseFilter.dismiss();
                }
            }
        });

        btncourseApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0 ; i < mAlcourselist.size() ; i++){
                    if(mAlcourselist.get(i).isSelected()==true){
                        mAlstrings.add(""+mAlcourselist.get(i).getId());
                        mAlitems.add(""+mAlcourselist.get(i).getName());
                    }else{
                        mAlstrings.remove(""+mAlcourselist.get(i).getId());
                        mAlitems.remove(""+mAlcourselist.get(i).getName());
                    }
                }

                StringBuilder stringBuilder = new StringBuilder("");
                for(int i = 0 ; i < mAlstrings.size() ; i++){
                    stringBuilder.append(mAlstrings.get(i)).append(",");
                }

                mStringids = stringBuilder.toString();
                if(mStringids.endsWith(",")){
                    mStringids = mStringids.substring(0,mStringids.length()-1);
                }

                StringBuilder stringBuilder1 = new StringBuilder("");
                for(int i = 0 ; i < mAlitems.size() ; i++){
                    stringBuilder1.append(mAlitems.get(i)).append(",");
                }

                mStrnames = stringBuilder1.toString();
                if(mStrnames.endsWith(",")){
                    mStrnames = mStrnames.substring(0,mStrnames.length()-1);
                }

                if(mBottomSheetCourseFilter.isShowing()==true){
                    mBottomSheetCourseFilter.dismiss();
                }

                mTxtfilterview.setText(""+mStrnames);
                mRelativettwsfilter.setVisibility(View.VISIBLE);
                mLinearttws.setVisibility(View.GONE);

                callApiToLoadInquiryDataAccToCourse(mStringids);
            }
        });
    }

    private void callApiToLoadInquiryDataAccToCourse(String mStringids) {
        if(!CommonUtil.isInternetAvailable(UpcomingReportActivity.this)){
            return;
        }

        new OkHttpRequest(UpcomingReportActivity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_TODAY_FILTER,
                RequestParam.courseWiseData(""+mStringids),
                RequestParam.getNull(),
                CODE_UPCOMING_TODAY_OR_TOMORROW_FILTER,
                false,this);
    }

    private void callApiForLoadCourseData() {

        if (!CommonUtil.isInternetAvailable(UpcomingReportActivity.this)) {
            return;
        }

        new OkHttpRequest(UpcomingReportActivity.this,
                OkHttpRequest.Method.GET,
                Constants.WS_COURSE_LIST,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_STUDENT_COURSES,
                false, this);
    }

    private void setFullHightForBottomSheetDialog(BottomSheetDialog mBottomSheetCourseFilter) {
        FrameLayout bottomSheet = (FrameLayout) mBottomSheetCourseFilter.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }

        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getWindowHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels-100;
    }

    @Override
    public void onOkHttpStart(int requestId) {

    }

    @SuppressLint("LongLogTag")
    @Override
    public void onOkHttpSuccess(int requestId, int statusCode, String response) {
        Log.e("upcoming data >>>",""+response);

        if(response==null){
            return;
        }

        switch (requestId) {
            case CODE_UPCOMING_INQUIRIES:
                Log.e("my data>>>", "" + response);
                final Gson data = new Gson();
                mAlupcomingOrFilterlist = new ArrayList<>();
                try {
                    upcomingDataModel = data.fromJson(response, UpcomingDataModel.class);
                    if (upcomingDataModel.getInquiries() != null &&
                            upcomingDataModel.getInquiries().size() > 0) {
                        mAlupcomingOrFilterlist = upcomingDataModel.getInquiries();
                        setUpcomingAdapter(mAlupcomingOrFilterlist);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CODE_UPCOMING_TODAY_OR_TOMORROW_FILTER:
                Log.e("today or tomorrow filter>>>", "" + response);
                final Gson filtertodayOrtomoOrsevendays = new Gson();

                try {
                    upcomingDataModel = filtertodayOrtomoOrsevendays.fromJson(response,UpcomingDataModel.class);

                    if(upcomingDataModel.getInquiries()!=null &&
                            upcomingDataModel.getInquiries().size()>0){
                        mAlupcomingOrFilterlist = new ArrayList<>();
                        mAlupcomingOrFilterlist = upcomingDataModel.getInquiries();
                        setUpcomingAdapter(mAlupcomingOrFilterlist);
                    }else{
                        if(mAlupcomingOrFilterlist!=null){
                            mTvextra.setVisibility(View.VISIBLE);
                            mAlupcomingOrFilterlist.clear();
                        }
                        if(upcomingActivityAdapter!=null){
                            upcomingActivityAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
                    LogUtil.e("tag",e.getMessage());
                    e.printStackTrace();
                }
                break;
            case CODE_STUDENT_COURSES:
                Log.e("Courses >>", "" + response);
                final Gson studentCoursesList = new Gson();
                try {
                    mAlcourselist = new ArrayList<>();
                    modelClassForCoursesList = studentCoursesList.fromJson(response, ModelClassForCourses.class);
                    if (modelClassForCoursesList.getCourses() != null &&
                            modelClassForCoursesList.getCourses().size() > 0) {
                        mAlcourselist = modelClassForCoursesList.getCourses();
                        setCourseCourseListAdapter(mAlcourselist);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CODE_UPCOMING_SEARCH:
                Log.e(">>> search",""+response);
                final Gson upcomingSearchGson = new Gson();
                try{
                    upcomingDataModel = upcomingSearchGson.fromJson(response,UpcomingDataModel.class);
                    if(upcomingDataModel.getInquiries()!=null && upcomingDataModel.getInquiries().size()>0){
                        mAlupcomingOrFilterlist = upcomingDataModel.getInquiries();
                        setUpcomingAdapter(mAlupcomingOrFilterlist);
                    }else{
                        mAlupcomingOrFilterlist.clear();
                        upcomingActivityAdapter = new UpcomingActivityAdapter(this,mAlupcomingOrFilterlist,this);
                        recyclerView.setAdapter(upcomingActivityAdapter);
                        upcomingActivityAdapter.notifyDataSetChanged();
                        playSoundForAttendance("No Data Found",UpcomingReportActivity.this);
                        Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setCourseWiseFilteredDataToRecyclerView(ArrayList<UpcomingDataModel.Inquiry> mAlupcomingOrFilterlist) {
        if(mAlupcomingOrFilterlist.size()>0 && mAlupcomingOrFilterlist!=null){
            upcomingActivityAdapter = new UpcomingActivityAdapter(UpcomingReportActivity.this, mAlupcomingOrFilterlist,this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(upcomingActivityAdapter);
            upcomingActivityAdapter.notifyDataSetChanged();
            mTvextra.setVisibility(View.GONE);
            mTxtselectedcourses.setVisibility(View.GONE);
            mTxtselectedcourses.setText(""+mStrnames);
        }else{
            mTvextra.setVisibility(View.VISIBLE);
            mAlupcomingOrFilterlist.clear();
            upcomingActivityAdapter = new UpcomingActivityAdapter(UpcomingReportActivity.this,mAlupcomingOrFilterlist,this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(upcomingActivityAdapter);
            upcomingActivityAdapter.notifyDataSetChanged();
            mTxtselectedcourses.setVisibility(View.GONE);
            mTxtselectedcourses.setText(""+mStrnames);
        }
    }

    private void setCourseCourseListAdapter(ArrayList<ModelClassForCourses.Course> mAlcourselist) {
        mAdapterCourseList = new SampleCourseWiseAdapter(UpcomingReportActivity.this,mAlcourselist,this);
        mLvcourselist.setNestedScrollingEnabled(true);
        mLvcourselist.setAdapter(mAdapterCourseList);

        if(mAlstrings.size()>0){
            for(int i = 0 ; i < mAlstrings.size() ; i++){
                for(int j = 0 ; j < mAlcourselist.size() ; j++){
                    if(mAlcourselist.get(j).getId().toString().equals(mAlstrings.get(i))){
                        mAlcourselist.get(j).isSelected = true;
                    }
                }
            }
        }

        mAlstrings.clear();
        mAlitems.clear();
    }

    private void setUpcomingAdapter(ArrayList<UpcomingDataModel.Inquiry> mAlupcominglist) {
        upcomingActivityAdapter = new UpcomingActivityAdapter(UpcomingReportActivity.this,
                mAlupcominglist, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(upcomingActivityAdapter);
        mTvextra.setVisibility(View.GONE);
    }

    @Override
    public void onOkHttpFailure(int requestId, int statusCode, String response, Throwable error) {

    }

    @Override
    public void onOkHttpFinish(int requestId) {

    }

    @Override
    public void onCourseListChecking(int position, boolean value) {
        mAlcourselist.get(position).isSelected = value;
        mAdapterCourseList.notifyDataSetChanged();
    }

}
