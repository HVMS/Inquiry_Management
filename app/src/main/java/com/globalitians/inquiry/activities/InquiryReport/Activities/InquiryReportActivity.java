package com.globalitians.inquiry.activities.InquiryReport.Activities;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForCourses;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForStandard;
import com.globalitians.inquiry.activities.InquiryReport.Adapter.InquiryReportAdapter;
import com.globalitians.inquiry.activities.InquiryReport.Adapter.SampleCourseWiseAdapter;
import com.globalitians.inquiry.activities.InquiryReport.Adapter.SampleStandardFilterAdapter;
import com.globalitians.inquiry.activities.InquiryReport.Adapter.SampleTestTabsAdapter;
import com.globalitians.inquiry.activities.InquiryReport.Model.ModelClassForInquiryDetails;
import com.globalitians.inquiry.activities.MyTempWork.fragments.TempDateOneFragment;
import com.globalitians.inquiry.activities.MyTempWork.fragments.TempDateTwoFragment;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.Utility.LogUtil;
import com.globalitians.inquiry.activities.Utility.PermissionManager;
import com.globalitians.inquiry.activities.Utility.Permissions;
import com.globalitians.inquiry.activities.network.OkHttpInterface;
import com.globalitians.inquiry.activities.network.OkHttpRequest;
import com.globalitians.inquiry.activities.network.RequestParam;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import static com.globalitians.inquiry.activities.Utility.CommonUtil.playSoundForAttendance;
import static com.globalitians.inquiry.activities.Utility.CommonUtil.setFullScreenMode;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_INQUIRIES;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_INQUIRY_FILTER;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_SEARCH_INQUIRY;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_COURSES;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_STANDARD;

public class InquiryReportActivity extends AppCompatActivity
        implements View.OnClickListener,OkHttpInterface,
        TempDateOneFragment.MyDialogListner,
        TempDateTwoFragment.DateBetweenListner,
        SampleCourseWiseAdapter.CourseWiseClickListner,
        SampleStandardFilterAdapter.OnStandardItemClickListner{

    private FloatingActionButton floatingActionButton;
    private FloatingActionButton fabcalendarbtn;
    private FloatingActionButton fabStandardbtn;
    private ImageView mIvMoreOptions;
    private TextView mTxtfilternames;
    private SearchView searchView;
    private TextView mTvextra;
    private ImageView mIvclose;
    private SwipeRefreshLayout swipeRefreshLayout;

    // course bottom sheet filter stuff
    private BottomSheetDialog mBottomSheetCourseFilter;
    private BottomSheetDialog mBottomSheetStandardFilter;
    private ListView mLvcourselist;
    private ListView mLvstandardlist;
    private Button btnStandardApply;
    private Button btncourseApply;
    private TextView mTxtcourseby;
    private TextView mTxtstandardby;
    private ModelClassForCourses modelClassForCoursesList=null;
    private SampleCourseWiseAdapter mAdapterCourseList = null;
    private ModelClassForStandard modelClassForStandard = null;
    private SampleStandardFilterAdapter sampleStandardFilterAdapter = null;
    private ArrayList<ModelClassForCourses.Course> mAlcourselist;
    private ArrayList<ModelClassForStandard.Standard> mAlstandardlist;
    private ArrayList<String> mAlstrings;
    private ArrayList<String> mAlitems;
    private ArrayList<String> mAlstandardstring;
    private ArrayList<String> mAlstandarditems;
    private String mStringids = "";
    private String mStrnames="";
    private String mStrStandardNames = "";
    private String mStrStandardIds = "";
    private String mStringFilterSearchValue="";
    private Button btnClearFilter;
    private Button btnStandardClearFilter;

    // api data only
    private ModelClassForInquiryDetails modelClassForInquiryDetails = null;
    private ArrayList<ModelClassForInquiryDetails.Inquiry> mALinquirydetails = null;
    private ArrayList<ModelClassForInquiryDetails.Inquiry> mArrListInquirySearch = null;

    private InquiryReportAdapter inquiryReportAdapter = null;
    private RecyclerView recyclerView;

    // date wise variables and stuff
    public static TempDialogBox tempDialogBox;
    public static TextView mTxtcancel,mTxtokay;

    public static SampleTestTabsAdapter adapter;
    private String searchQuery="";

    // filter search view
    private SearchView mSearchFromFilter;

    // excel code stuff for permission listner
    /*private final PermissionManager.PermissionListener permissionListener = new PermissionManager.PermissionListener() {
        @Override
        public void onPermissionsGranted(List<String> perms) {
            if (perms.size() == Permissions.STORAGE_PERMISSIONS.length) {
                exportStudentDataToExcel();
            } else {
                LogUtil.i("Add Course Activity >>", "User denied some of required permissions! "
                        + "Even though we have following permissions now, "
                        + "task will still be aborted.\n" + CommonUtil.getStringFromList(perms));
            }
        }

        @Override
        public void onPermissionsDenied(List<String> perms) {
            Toast.makeText(InquiryReportActivity.this, "Please grant storage permissions.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionRequestRejected() {
        }

        @Override
        public void onPermissionNeverAsked(List<String> perms) {
        }

    };
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode(InquiryReportActivity.this);
        setContentView(R.layout.activity_inquiry_report);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Inquiry Report");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.bv_primaryColor)));

        findViews();
        initialize();
        callApiDataForInquiryReport(true);
    }

    private void callApiDataForInquiryReport(boolean b) {

        if (!CommonUtil.isInternetAvailable(InquiryReportActivity.this)) {
            return;
        }

        new OkHttpRequest((InquiryReportActivity.this),
                OkHttpRequest.Method.GET,
                Constants.WS_INQUIRIES,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_INQUIRIES,
                false, this);
    }

    private void initialize() {
        mALinquirydetails = new ArrayList<>();
        mArrListInquirySearch = new ArrayList<>();
        mAlstrings = new ArrayList<>();
        mAlitems = new ArrayList<>();

        mAlstandardlist = new ArrayList<>();
        mAlstandarditems = new ArrayList<>();
        mAlstandardstring = new ArrayList<>();
    }

    private void findViews() {
        mIvMoreOptions = findViewById(R.id.iv_more_options);
        floatingActionButton = findViewById(R.id.fabFilterInquiry);
        recyclerView = findViewById(R.id.recviewinqrepo);
        mTvextra = findViewById(R.id.blank_content);
        mTxtfilternames = findViewById(R.id.tv_filter_names);
        mIvclose = findViewById(R.id.iv_close);
        swipeRefreshLayout = findViewById(R.id.swipeToRefreshStudentList);
        mSearchFromFilter = findViewById(R.id.filter_search);

        fabcalendarbtn = findViewById(R.id.calendar_fab);
        fabcalendarbtn.setOnClickListener(this);

        fabStandardbtn = findViewById(R.id.standard_fab);
        fabStandardbtn.setOnClickListener(this);

        setAnimationToFloatingActionButton();
        setAnimationOnFab();
        setAnimationToStandardActionButton();

        floatingActionButton.setOnClickListener(this);
        
        mIvclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!CommonUtil.isNullString("" + mStrnames)){
                    for (int i = 0; i < mAlcourselist.size(); i++) {
                        mAlcourselist.get(i).isSelected = false;
                    }
                }

                if(!CommonUtil.isNullString(""+mStrStandardNames)){
                    for(int i = 0 ; i < mAlstandardlist.size() ; i++){
                        mAlstandardlist.get(i).isSelected = false;
                    }
                }

                // all data should be cleared here
                mStringids = "";
                mStrStandardIds = "";
                mAlstrings.clear();
                mAlstandardstring.clear();
                mAlitems.clear();
                mAlstandarditems.clear();

                /*refreshUpcomingInquiries();*/
                callApiDataForInquiryReport(false);
                mIvclose.setVisibility(View.GONE);
                mTxtfilternames.setVisibility(View.GONE);
                mTvextra.setVisibility(View.GONE);
                Toast.makeText(InquiryReportActivity.this, "Filters Are Cleared", Toast.LENGTH_SHORT).show();
            }
        });
        
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshUpcomingInquiries();
                mIvclose.setVisibility(View.GONE);
                mTxtfilternames.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void setAnimationToStandardActionButton() {
        Animation animationfadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_animation);
        animationfadeIn.setStartOffset(370);
        fabStandardbtn.startAnimation(animationfadeIn);
    }

    public void refreshUpcomingInquiries() {

        if(mArrListInquirySearch!=null && mArrListInquirySearch.size()>0){
            mArrListInquirySearch.clear();
        }

        if(inquiryReportAdapter!=null){
            inquiryReportAdapter.notifyDataSetChanged();
        }

        mArrListInquirySearch = new ArrayList<>();

        if (CommonUtil.isNullString("")) {
            //default service call for inquiry list
            callApiDataForInquiryReport(false);
        } else {
            //search value call..
            callApiToSearchInquiry(searchQuery);
        }
    }

    private void setAnimationOnFab() {
        Animation animationfadeIn1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_animation);
        animationfadeIn1.setStartOffset(370);
        fabcalendarbtn.startAnimation(animationfadeIn1);
    }

    private void setAnimationToFloatingActionButton() {
        Animation animationfadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_animation);
        animationfadeIn.setStartOffset(370);
        floatingActionButton.startAnimation(animationfadeIn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.only_search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to Search");

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));

        SearchView.OnQueryTextListener queryTextListener = null;
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);

            if(mStringids.length()>0){
                queryTextListener = new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        searchQuery = s.toLowerCase();
                        if (searchQuery.length() > 0) {
                            OkHttpRequest.cancelOkHttpRequest(Constants.WS_INQUIRY_FILTER);
                            callApiToSearchInquiryFromFilteredItems(searchQuery);
                        } else {
                            searchQuery = "";
                            /*refreshUpcomingInquiries();*/
                            callApiToLoadInquiryDataAccToCourse(""+mStringids);
                        }
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        searchQuery = s.toLowerCase();
                        if (searchQuery.length() > 0) {
                            OkHttpRequest.cancelOkHttpRequest(Constants.WS_INQUIRY_FILTER);
                            callApiToSearchInquiryFromFilteredItems(searchQuery);
                        } else {
                            searchQuery = "";
                            /*refreshUpcomingInquiries();*/
                            callApiToLoadInquiryDataAccToCourse(""+mStringids);
                        }
                        return true;
                    }
                };
            }else{
                queryTextListener = new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        searchQuery = s.toLowerCase();
                        if (searchQuery.length() > 0) {
                            OkHttpRequest.cancelOkHttpRequest(Constants.WS_INQUIRY_SEARCH);
                            callApiToSearchInquiry(searchQuery);
                        } else {
                            searchQuery = "";
                            refreshUpcomingInquiries();
                        }
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        searchQuery = s.toLowerCase();
                        if (searchQuery.length() > 0) {
                            OkHttpRequest.cancelOkHttpRequest(Constants.WS_INQUIRY_SEARCH);
                            callApiToSearchInquiry(searchQuery);
                        } else {
                            searchQuery = "";
                            refreshUpcomingInquiries();
                        }
                        return true;
                    }
                };
            }
        }

        searchView.setOnQueryTextListener(queryTextListener);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setQueryHint("");
                return true;
            }
        });

        return true;
    }

    private void callApiToSearchInquiry(String strSearch) {
        if(!CommonUtil.isInternetAvailable(InquiryReportActivity.this)){
            return;
        }

        new OkHttpRequest(InquiryReportActivity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_SEARCH,
                RequestParam.searchInquiry(""+strSearch),
                RequestParam.getNull(),
                CODE_SEARCH_INQUIRY,
                false,this);
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

        if(id==R.id.action_excel){
            Toast.makeText(this, "feature is in progress", Toast.LENGTH_SHORT).show();
            /*if (PermissionManager.hasPermissions(InquiryReportActivity.this, Permissions.STORAGE_PERMISSIONS)) {
                exportStudentDataToExcel();
            } else {
                requestPermission();
                PermissionManager.requestPermissions(InquiryReportActivity.this, Constants.CODE_RUNTIME_STORAGE_PERMISSION,
                        permissionListener, "", Permissions.STORAGE_PERMISSIONS);
            }*/
            return true;
        }

        if(id==R.id.action_pdf){
            Toast.makeText(this, "feature is in progress", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void exportStudentDataToExcel() {

        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "GlobalIT_Student_Report.xls";

        File directory = new File(sd.getAbsolutePath());
        //create directory if not exist

        if (directory.isDirectory()) {
            directory.mkdirs();
        }

        try {
            //file path
            File file = new File(directory, csvFile);

            Log.e("Path >>", "" + file.getAbsolutePath());

            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);

            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("userList", 0);
            sheet.setName("Student Report");

            // column and row
            sheet.addCell(new Label(0, 0, "ID"));
            sheet.addCell(new Label(1, 0, "Branch"));
            sheet.addCell(new Label(2, 0, "FirstName"));
            sheet.addCell(new Label(3, 0, "LastName"));
            sheet.addCell(new Label(4, 0, "Contact"));
            sheet.addCell(new Label(5, 0, "Status"));
            sheet.addCell(new Label(6, 0, "Courses"));
            sheet.addCell(new Label(7, 0, "JoinedOn"));

            if(mALinquirydetails != null && mALinquirydetails.size()>0){
                for(int i = 0 ; i < mALinquirydetails.size() ; i++){

                    sheet.addCell(new Label(0, i + 1 , mALinquirydetails.get(i).getId().toString()));
                    sheet.addCell(new Label(1, i + 1 , mALinquirydetails.get(i).getBranchName()));
                    sheet.addCell(new Label(2, i + 1 , mALinquirydetails.get(i).getFname()));

                    sheet.addCell(new Label(3, i + 1 , mALinquirydetails.get(i).getLname()));
                    sheet.addCell(new Label(4, i + 1 , mALinquirydetails.get(i).getContact()));
                    sheet.addCell(new Label(5, i + 1 , mALinquirydetails.get(i).getStatus()));

                    sheet.addCell(new Label(6, i + 1 , mALinquirydetails.get(i).getCourses().get(0).getName()));
                    sheet.addCell(new Label(7, i + 1 , mALinquirydetails.get(i).getInquiryDate()));
                }
            }
            workbook.write();
            workbook.close();
            openExcelFile(file);
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            Toast.makeText(this, "Hello World Program", Toast.LENGTH_SHORT).show();
        }
    }

    private void openExcelFile(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File("" + file.toString())), "application/vnd.ms-excel");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(InquiryReportActivity.this, "No Application Available to View Excel", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabFilterInquiry:
                callBottomSheetDialog();
                break;
            case R.id.calendar_fab:
                callCalendarViewDialog();
                break;
            case R.id.standard_fab:
                callStandardBottomSheetDialog();
                break;
            default:
                Toast.makeText(getApplicationContext(), "neither", Toast.LENGTH_SHORT).show();
        }
    }

    private void callStandardBottomSheetDialog() {

        final View mViewStandardFilter = getLayoutInflater().inflate(R.layout.standard_bottom_sheet_filter,null);
        mBottomSheetStandardFilter = new BottomSheetDialog(InquiryReportActivity.this);
        mBottomSheetStandardFilter.setContentView(mViewStandardFilter);
        mBottomSheetStandardFilter.show();

        setFullHightForBottomSheetDialog(mBottomSheetStandardFilter);

        mTxtstandardby = mViewStandardFilter.findViewById(R.id.tv_standard_name);
        btnStandardApply = mViewStandardFilter.findViewById(R.id.btn_standard_filter);
        btnStandardClearFilter = mViewStandardFilter.findViewById(R.id.btn_clear_filter);
        mLvstandardlist = mViewStandardFilter.findViewById(R.id.sample_standard_wise_filter_listview);

        callApiForLoadStandardData();

        btnStandardClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0 ; i < mAlstandardlist.size() ; i++){
                    mAlstandardlist.get(i).isSelected = false;
                }

                // all data should be cleared here
                mStrStandardIds = "";
                mAlstandarditems.clear();
                mAlstandardstring.clear();
                mTxtfilternames.setVisibility(View.GONE);
                mIvclose.setVisibility(View.GONE);
                callApiDataForInquiryReport(false);
                playSoundForAttendance("Standard Filters Are Cleared",InquiryReportActivity.this);
                Toast.makeText(InquiryReportActivity.this, "Standard Filters Are Cleared", Toast.LENGTH_SHORT).show();

                if(mBottomSheetStandardFilter.isShowing()==true){
                    mBottomSheetStandardFilter.dismiss();
                }

            }
        });

        btnStandardApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0 ; i < mAlstandardlist.size() ; i++){
                    if(mAlstandardlist.get(i).isSelected() == true){
                        mAlstandardstring.add(""+mAlstandardlist.get(i).getId());
                        mAlstandarditems.add(""+mAlstandardlist.get(i).getName());
                    }else{
                        mAlstandardstring.remove(""+mAlstandardlist.get(i).getId());
                        mAlstandarditems.remove(""+mAlstandardlist.get(i).getName());
                    }
                }

                StringBuilder stringBuilder = new StringBuilder("");
                for(int i = 0 ; i < mAlstandardstring.size() ; i++){
                    stringBuilder.append(mAlstandardstring.get(i)).append(",");
                }

                mStrStandardIds = stringBuilder.toString();
                if(mStrStandardIds.endsWith(",")){
                    mStrStandardIds = mStrStandardIds.substring(0,mStrStandardIds.length()-1);
                }

                StringBuilder stringBuilder1 = new StringBuilder("");
                for(int i = 0 ; i < mAlstandarditems.size() ; i++){
                    stringBuilder1.append(mAlstandarditems.get(i)).append(",");
                }

                mStrStandardNames = stringBuilder1.toString();
                if(mStrStandardNames.endsWith(",")){
                    mStrStandardNames = mStrStandardNames.substring(0,mStrStandardNames.length()-1);
                }

                if(!CommonUtil.isNullString(""+mStrStandardIds)){
                    callApiToLoadInquiryDataAccToStandard(mStrStandardIds);

                    if(mBottomSheetStandardFilter.isShowing()==true){
                        mBottomSheetStandardFilter.dismiss();
                    }

                }else{
                    playSoundForAttendance("Please Select Standard Before Applying Filter",InquiryReportActivity.this);
                    Toast.makeText(InquiryReportActivity.this, "Please Select Standard Before Applying Filter", Toast.LENGTH_SHORT).show();
                }

                // set visibility for SearchView and Filtered Items name as well
                // then remove from above main search view so that do not affect main items list
                mIvclose.setVisibility(View.VISIBLE);
                mTxtfilternames.setVisibility(View.VISIBLE);
                mTxtfilternames.setText(""+mStrStandardNames);

            }
        });
    }

    private void callApiToLoadInquiryDataAccToStandard(String mStrStandardIds) {
        if(!CommonUtil.isInternetAvailable(InquiryReportActivity.this)){
            return;
        }

        new OkHttpRequest(InquiryReportActivity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_FILTER,
                RequestParam.standardWiseData(""+mStrStandardIds),
                RequestParam.getNull(),
                CODE_INQUIRY_FILTER,
                false,this);
    }

    private void callApiForLoadStandardData() {
        if(!CommonUtil.isInternetAvailable(InquiryReportActivity.this)){
            return;
        }

        new OkHttpRequest(InquiryReportActivity.this,
                OkHttpRequest.Method.GET,
                Constants.WS_COURSE_STANDARD,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_STUDENT_STANDARD,
                false,this);
    }

    private void callCalendarViewDialog() {
        tempDialogBox = new TempDialogBox();
        tempDialogBox.show(getSupportFragmentManager(),"add tag");
    }

    @Override
    public void onReturnSingleDate(ArrayList<ModelClassForInquiryDetails.Inquiry> mALfilteredlistdatewise, int flagValue) {
        if(flagValue==11){
            if(mALfilteredlistdatewise!=null && mALfilteredlistdatewise.size()>0){
                mALinquirydetails = mALfilteredlistdatewise;
                mTvextra.setVisibility(View.GONE);
                mTxtokay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(tempDialogBox!=null){
                            setInquiryReportDetails(mALinquirydetails);
                            inquiryReportAdapter.notifyDataSetChanged();
                            tempDialogBox.dismiss();
                            Toast.makeText(InquiryReportActivity.this, "Data has been found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else if(mALfilteredlistdatewise==null){
                mTxtokay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(tempDialogBox!=null){
                            mALinquirydetails.clear();
                            recyclerView.getRecycledViewPool().clear();
                            inquiryReportAdapter.notifyDataSetChanged();
                            mTvextra.setVisibility(View.VISIBLE);
                            tempDialogBox.dismiss();
                            Toast.makeText(InquiryReportActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void returnDateBetResToInquiry(ArrayList<ModelClassForInquiryDetails.Inquiry> marraylistDBfiltered, int flagValue) {
        if(flagValue==21){
            if(marraylistDBfiltered!=null && marraylistDBfiltered.size()>0){
                mALinquirydetails = marraylistDBfiltered;
                mTvextra.setVisibility(View.GONE);
                mTxtokay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setInquiryReportDetails(mALinquirydetails);
                        inquiryReportAdapter.notifyDataSetChanged();
                        if(tempDialogBox!=null){
                            tempDialogBox.dismiss();
                        }
                        Toast.makeText(InquiryReportActivity.this, "Data has come", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                mTxtokay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mALinquirydetails.clear();
                        recyclerView.getRecycledViewPool().clear();
                        inquiryReportAdapter.notifyDataSetChanged();
                        tempDialogBox.dismiss();
                    }
                });
                mTvextra.setVisibility(View.VISIBLE);
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCourseListChecking(int position, boolean value) {
        mAlcourselist.get(position).isSelected = value;
        mAdapterCourseList.notifyDataSetChanged();
    }

    @Override
    public void onStandardItemClick(int position, boolean value) {
        mAlstandardlist.get(position).isSelected = value;
        sampleStandardFilterAdapter.notifyDataSetChanged();
    }

    @SuppressLint("ValidFragment")
    public static class TempDialogBox extends DialogFragment {

        private ViewPager viewPager;
        private TabLayout tabLayout;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.temp_test_dialog,container,false);

            tabLayout = rootView.findViewById(R.id.temp_test_layout);
            viewPager = rootView.findViewById(R.id.temp_test_viewpager);
            mTxtcancel = rootView.findViewById(R.id.cancel_txt);
            mTxtokay = rootView.findViewById(R.id.tv_okay);

            mTxtcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });

            mTxtokay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });

            SampleTestTabsAdapter adapter = new SampleTestTabsAdapter(getChildFragmentManager(),2);
            adapter.addFragment("BY\n Date",new TempDateOneFragment());
            adapter.addFragment("By\n Two Date",new TempDateTwoFragment());

            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            return rootView;
        }
    }

    private void callBottomSheetDialog() {

        final View mViewcourseFilter = getLayoutInflater().inflate(R.layout.course_bottom_sheet_filter,null);
        mBottomSheetCourseFilter = new BottomSheetDialog(InquiryReportActivity.this);
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
                mTxtfilternames.setVisibility(View.GONE);
                mIvclose.setVisibility(View.GONE);
                callApiDataForInquiryReport(false);
                playSoundForAttendance("Courses Filters Are Cleared",InquiryReportActivity.this);
                Toast.makeText(InquiryReportActivity.this, "Courses Filters Are Cleared", Toast.LENGTH_SHORT).show();

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

                if(!CommonUtil.isNullString(""+mStringids)){
                    callApiToLoadInquiryDataAccToCourse(mStringids);

                    if(mBottomSheetCourseFilter.isShowing()==true){
                        mBottomSheetCourseFilter.dismiss();
                    }

                }else{
                    playSoundForAttendance("Please Select Courses Before Applying Filter",InquiryReportActivity.this);
                    Toast.makeText(InquiryReportActivity.this, "Please Select Courses Before Applying Filter", Toast.LENGTH_SHORT).show();
                }

                // set visibility for SearchView and Filtered Items name as well
                // then remove from above main search view so that do not affect main items list

                mIvclose.setVisibility(View.VISIBLE);
                mTxtfilternames.setVisibility(View.VISIBLE);
                mTxtfilternames.setText(""+mStrnames);

            }
        });
    }

    private void callApiToSearchInquiryFromFilteredItems(String searchQuery) {

        if(!CommonUtil.isInternetAvailable(InquiryReportActivity.this)){
            return;
        }

        new OkHttpRequest(InquiryReportActivity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_FILTER,
                RequestParam.getSearchFromFilteredItems(""+mStringids,
                                                        ""+searchQuery),
                RequestParam.getNull(),
                CODE_INQUIRY_FILTER,
                false,this);
    }

    private void callApiToLoadInquiryDataAccToCourse(String mStringids) {
        if(!CommonUtil.isInternetAvailable(InquiryReportActivity.this)){
            return;
        }

        new OkHttpRequest(InquiryReportActivity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_FILTER,
                RequestParam.courseWiseData(""+mStringids),
                RequestParam.getNull(),
                CODE_INQUIRY_FILTER,
                false,this);
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
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels-100;
    }

    private void callApiForLoadCourseData() {
        if (!CommonUtil.isInternetAvailable(InquiryReportActivity.this)) {
            return;
        }

        new OkHttpRequest(InquiryReportActivity.this,
                OkHttpRequest.Method.GET,
                Constants.WS_COURSE_LIST,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_STUDENT_COURSES,
                false, this);
    }

    @Override
    public void onOkHttpStart(int requestId) {

    }

    @Override
    public void onOkHttpSuccess(int requestId, int statusCode, String response) {
        if (response == null) {
            return;
        }

        switch (requestId) {
            case CODE_INQUIRIES:
                Log.e("Inquiries >>", "" + response);
                final Gson inquiryReport = new Gson();
                try {
                    modelClassForInquiryDetails = inquiryReport.fromJson(response, ModelClassForInquiryDetails.class);
                    if (modelClassForInquiryDetails.getInquiries() != null &&
                            modelClassForInquiryDetails.getInquiries().size() > 0) {
                        mALinquirydetails = modelClassForInquiryDetails.getInquiries();
                        setInquiryReportDetails(mALinquirydetails);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CODE_SEARCH_INQUIRY:
                Log.e("search inquiry",""+response);
                final Gson searchData = new Gson();
                try{
                    modelClassForInquiryDetails = searchData.fromJson(response,ModelClassForInquiryDetails.class);
                    if(modelClassForInquiryDetails.getInquiries()!=null && modelClassForInquiryDetails.getInquiries().size()>0){
                        mALinquirydetails = modelClassForInquiryDetails.getInquiries();
                        setInquiryReportDetails(mALinquirydetails);
                    }else{
                        mALinquirydetails.clear();
                        inquiryReportAdapter = new InquiryReportAdapter(this,mALinquirydetails);
                        recyclerView.setAdapter(inquiryReportAdapter);
                        inquiryReportAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
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
            case CODE_STUDENT_STANDARD:
                Log.e("Standards >>",""+response);
                final Gson gson = new Gson();
                try{
                    mAlstandardlist = new ArrayList<>();
                    modelClassForStandard = gson.fromJson(response,ModelClassForStandard.class);
                    if(modelClassForStandard.getStandards()!=null &&
                            modelClassForStandard.getStandards().size()>0){
                        mAlstandardlist = modelClassForStandard.getStandards();
                        setStandardlistAdapter(mAlstandardlist);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case CODE_INQUIRY_FILTER:
                Log.e("Inquiry>>>",""+response);
                final Gson courseFilter = new Gson();
                try{
                    mALinquirydetails = new ArrayList<>();
                    modelClassForInquiryDetails = courseFilter.fromJson(response,ModelClassForInquiryDetails.class);
                    mALinquirydetails = modelClassForInquiryDetails.getInquiries();
                    // call function for Applying data
                    if(!CommonUtil.isNullString(""+mStringids)){
                        setCourseWiseFilteredDataToRecyclerView(mALinquirydetails);
                    }else if(!CommonUtil.isNullString(""+mStrStandardIds)){
                        setStandardWiseFilteredDataToRecyclerView(mALinquirydetails);
                    }else{
                        // do nothing here
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setStandardWiseFilteredDataToRecyclerView(ArrayList<ModelClassForInquiryDetails.Inquiry> mALinquirydetails) {
        if(mALinquirydetails.size()>0 && mALinquirydetails!=null){
            inquiryReportAdapter = new InquiryReportAdapter(InquiryReportActivity.this, mALinquirydetails);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(inquiryReportAdapter);
            inquiryReportAdapter.notifyDataSetChanged();
            mTvextra.setVisibility(View.GONE);
            Toast.makeText(InquiryReportActivity.this, "Yes Data Found", Toast.LENGTH_SHORT).show();
        }else{
            mTvextra.setVisibility(View.VISIBLE);
            mALinquirydetails.clear();
            inquiryReportAdapter = new InquiryReportAdapter(InquiryReportActivity.this,mALinquirydetails);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(inquiryReportAdapter);
            inquiryReportAdapter.notifyDataSetChanged();
            Toast.makeText(InquiryReportActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void setStandardlistAdapter(ArrayList<ModelClassForStandard.Standard> mAlstandardlist) {
        sampleStandardFilterAdapter = new SampleStandardFilterAdapter(InquiryReportActivity.this,mAlstandardlist,this);
        mLvstandardlist.setNestedScrollingEnabled(true);
        mLvstandardlist.setAdapter(sampleStandardFilterAdapter);

        if(mAlstandardstring.size()>0){
            for(int i = 0 ; i < mAlstandardstring.size() ; i++){
                for(int j = 0 ; j < mAlstandardlist.size() ; j++){
                    if(mAlstandardlist.get(j).getId().toString().equals(mAlstandardstring.get(i))){
                        mAlstandardlist.get(j).isSelected = true;
                    }
                }
            }
        }
        mAlstandardstring.clear();
        mAlstandarditems.clear();
    }

    private void setCourseCourseListAdapter(ArrayList<ModelClassForCourses.Course> mAlcourselist) {
        mAdapterCourseList = new SampleCourseWiseAdapter(InquiryReportActivity.this,mAlcourselist,this);
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

    private void setCourseWiseFilteredDataToRecyclerView(ArrayList<ModelClassForInquiryDetails.Inquiry> mALinquirydetails) {
        if(mALinquirydetails.size()>0 && mALinquirydetails!=null){
            inquiryReportAdapter = new InquiryReportAdapter(InquiryReportActivity.this, mALinquirydetails);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(inquiryReportAdapter);
            inquiryReportAdapter.notifyDataSetChanged();
            mTvextra.setVisibility(View.GONE);
            Toast.makeText(InquiryReportActivity.this, "Yes Data Found", Toast.LENGTH_SHORT).show();
        }else{
            mTvextra.setVisibility(View.VISIBLE);
            mALinquirydetails.clear();
            inquiryReportAdapter = new InquiryReportAdapter(InquiryReportActivity.this,mALinquirydetails);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(inquiryReportAdapter);
            inquiryReportAdapter.notifyDataSetChanged();
            Toast.makeText(InquiryReportActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void setInquiryReportDetails(ArrayList<ModelClassForInquiryDetails.Inquiry> mALinquirydetails) {
        inquiryReportAdapter = new InquiryReportAdapter(InquiryReportActivity.this, mALinquirydetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(inquiryReportAdapter);
        inquiryReportAdapter.notifyDataSetChanged();
    }

    @Override
    public void onOkHttpFailure(int requestId, int statusCode, String response, Throwable error) {

    }

    @Override
    public void onOkHttpFinish(int requestId) {

    }

}