package com.globalitians.inquiry.activities.SmsAndNotification.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForCourses;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForStandard;
import com.globalitians.inquiry.activities.InquiryReport.Activities.InquiryReportActivity;
import com.globalitians.inquiry.activities.InquiryReport.Adapter.SampleCourseWiseAdapter;
import com.globalitians.inquiry.activities.InquiryReport.Adapter.SampleStandardFilterAdapter;
import com.globalitians.inquiry.activities.InquiryReport.Adapter.SampleTestTabsAdapter;
import com.globalitians.inquiry.activities.InquiryReport.Fragments.SampleCourseWiseFragment;
import com.globalitians.inquiry.activities.InquiryReport.Fragments.SampleStandardWiseFragment;
import com.globalitians.inquiry.activities.InquiryReport.Model.ModelClassForInquiryDetails;
import com.globalitians.inquiry.activities.MyTempWork.fragments.TempDateOneFragment;
import com.globalitians.inquiry.activities.MyTempWork.fragments.TempDateTwoFragment;
import com.globalitians.inquiry.activities.SmsAndNotification.Adapters.CustomListAdapter;
import com.globalitians.inquiry.activities.SmsAndNotification.Adapters.SmsCategoryAdapter;
import com.globalitians.inquiry.activities.SmsAndNotification.Models.CategoryWiseResponse;
import com.globalitians.inquiry.activities.SmsAndNotification.Models.InquiryPersonModel;
import com.globalitians.inquiry.activities.SmsAndNotification.Models.Smscategoriesresponse;
import com.globalitians.inquiry.activities.UpcomingReport.Model.UpcomingDataModel;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.network.OkHttpInterface;
import com.globalitians.inquiry.activities.network.OkHttpRequest;
import com.globalitians.inquiry.activities.network.RequestParam;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.jar.Attributes;

import static com.globalitians.inquiry.activities.Utility.CommonUtil.playSoundForAttendance;
import static com.globalitians.inquiry.activities.Utility.CommonUtil.setFullScreenMode;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_INQUIRY_FILTER;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_SEARCH_INQUIRY;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_SMS_CATEGORIES;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_SMS_CATRGORIES_WISE_MSGS;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_COURSES;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_STANDARD;

public class SmsandnotificationActivity extends AppCompatActivity implements View.OnClickListener, OkHttpInterface,
        TempDateOneFragment.MyDialogListner,
        TempDateTwoFragment.DateBetweenListner,
        SampleCourseWiseAdapter.CourseWiseClickListner,
        SampleStandardFilterAdapter.OnStandardItemClickListner{

    private CheckBox checkBoxAll;
    private Context context=this;
    private Button btn_predefined_msg;
    private Button btn_send;
    private Button btn_select_student;
    private EditText mEdtsms;
    private TextView mTvextra;

    private ArrayList<ModelClassForInquiryDetails.Inquiry> mALInquiryPersons;
    private ModelClassForInquiryDetails modelClassForInquiryDetails;
    private FloatingActionButton fabcalendarbtn;
    private FloatingActionButton fabStandardbtn;
    private CustomListAdapter adapterInquiryPersons=null;
    private ListView smsList;
    private RelativeLayout relCheckalllayout;

    public static TempDialogBox tempDialogBox;
    public static TextView mTxtcancel,mTxtokay;

    // course bottom sheet filter stuff
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
    private RelativeLayout mRvFilteredItemslist;
    private TextView mTxtfiltereditems;
    private ArrayList<String> mAlitems;

    // pre defined stuff
    private ListView listview;
    private TextView txt;
    private Dialog dialog;
    private Spinner mSprcategories;

    private ArrayList<String> mAlsmscategorylist;
    private Smscategoriesresponse smscategoriesresponse;

    private CategoryWiseResponse categoryWiseResponse;
    private ArrayList<CategoryWiseResponse.Message> mAlcategorywisemsgs;
    private String mStrSelectedSmsTypeId="";


    private BottomSheetDialog mBottomSheetStandardFilter;
    private TextView mTxtstandardby;
    private ListView mLvstandardlist;
    private Button btnStandardApply;
    private Button btnStandardClearFilter;
    private ModelClassForStandard modelClassForStandard;
    private ArrayList<ModelClassForStandard.Standard> mAlstandardlist;
    private SampleStandardFilterAdapter sampleStandardFilterAdapter;

    private ArrayList<String> mAlstandardstring;
    private ArrayList<String> mAlstandarditems;

    private String mStrStandardNames = "";
    private String mStrStandardIds = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode(SmsandnotificationActivity.this);
        setContentView(R.layout.activity_smsandnotification);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("SMS");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.bv_primaryColor)));

        findViews();
        init();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Message sending is in progress", Toast.LENGTH_SHORT).show();
            }
        });

        btn_predefined_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDialogSMSType();
            }
        });
    }

    private void callDialogSMSType() {
        dialog = new Dialog(SmsandnotificationActivity.this);
        dialog.setContentView(R.layout.custom_sms_type_layout);
        txt = dialog.findViewById(R.id.vis);

        calApiToLoadSmsTYpeData();

        mSprcategories = dialog.findViewById(R.id.spinneer_predefined_sms);
        listview = dialog.findViewById(R.id.lv_predefined_sms_list);

        mSprcategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    mStrSelectedSmsTypeId = ""+smscategoriesresponse.getCategories().get(position-1).getId();
                    txt.setVisibility(View.GONE);
                    callApiToLoadSmsMessagesCategoryWise();
                }else{
                    txt.setVisibility(View.VISIBLE);
                    listview.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialog.show();
    }

    private void callApiToLoadSmsMessagesCategoryWise() {
        if(!CommonUtil.isInternetAvailable(SmsandnotificationActivity.this)){
            return;
        }

        new OkHttpRequest(SmsandnotificationActivity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_SMS_CATEGORIES_WISE_MESSAGES,
                RequestParam.categoryWiseFilter(""+mStrSelectedSmsTypeId),
                RequestParam.getNull(),
                CODE_SMS_CATRGORIES_WISE_MSGS,
                false,this);
    }

    private void calApiToLoadSmsTYpeData() {
        if(!CommonUtil.isInternetAvailable(SmsandnotificationActivity.this)){
            return;
        }

        new OkHttpRequest(SmsandnotificationActivity.this,
                OkHttpRequest.Method.GET,
                Constants.WS_SMS_CATEGORIES,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_SMS_CATEGORIES,
                false,this);
    }

    private void findViews() {
        checkBoxAll = findViewById(R.id.chkCourse);
        btn_send=findViewById(R.id.btn_send);
        btn_predefined_msg = findViewById(R.id.btn_predefined_msg);
        btn_select_student = findViewById(R.id.btn_select_student);
        mEdtsms = findViewById(R.id.edt_sms_area);
        fabcalendarbtn = findViewById(R.id.calendar_fab);
        fabStandardbtn = findViewById(R.id.standard_fab);
        smsList = findViewById(R.id.lv);
        mTvextra = findViewById(R.id.tvextra);
        relCheckalllayout = findViewById(R.id.rel_sms_name_and_check);
        mRvFilteredItemslist = findViewById(R.id.filtered_items);
        mTxtfiltereditems = findViewById(R.id.mTxtfiltereditems);

        fabcalendarbtn.setOnClickListener(this);
        fabStandardbtn.setOnClickListener(this);
        btn_select_student.setOnClickListener(this);
        btn_send.setOnClickListener(this);

        setAnimationToDatwFabButton();
        setAnimationToStandardActionButton();
    }

    private void setAnimationToDatwFabButton() {
        Animation animationfadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_animation);
        animationfadeIn.setStartOffset(370);
        fabcalendarbtn.startAnimation(animationfadeIn);
    }

    private void setAnimationToStandardActionButton() {
        Animation animationfadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_animation);
        animationfadeIn.setStartOffset(370);
        fabStandardbtn.startAnimation(animationfadeIn);
    }

    private void init() {
        mALInquiryPersons=new ArrayList<>();
        mAlstrings = new ArrayList<>();
        mAlitems = new ArrayList<>();
        mAlstandardlist = new ArrayList<>();

        mAlstandardstring = new ArrayList<>();
        mAlstandarditems = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sms_menu, menu);

        MenuItem searchFilter = menu.findItem(R.id.search);

        SearchView searchView = (SearchView)searchFilter.getActionView();
        searchView.setQueryHint("Type here to Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String query = s.toLowerCase();
                callApiToSearchInquiry(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    private void callApiToSearchInquiry(String query) {
        if(!CommonUtil.isInternetAvailable(SmsandnotificationActivity.this)){
            return;
        }

        new OkHttpRequest(SmsandnotificationActivity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_SEARCH,
                RequestParam.searchInquiry(query),
                RequestParam.getNull(),
                CODE_SEARCH_INQUIRY,
                true,this);
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_select_student:
                callSMSBottomSheetTestDialog();
                break;
            case R.id.btn_send:
                Toast.makeText(context, "Sending in Progress", Toast.LENGTH_SHORT).show();
                break;
            case R.id.calendar_fab:
                showCalenderViewDialog();
                break;
            case R.id.standard_fab:
                callStandardBottomSheetDialog();
                break;
        }
    }

    private void callStandardBottomSheetDialog() {
        final View mViewStandardFilter = getLayoutInflater().inflate(R.layout.standard_bottom_sheet_filter,null);
        mBottomSheetStandardFilter = new BottomSheetDialog(SmsandnotificationActivity.this);
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
                mALInquiryPersons.clear();
                adapterInquiryPersons.notifyDataSetChanged();
                mTxtfiltereditems.setVisibility(View.GONE);
                mRvFilteredItemslist.setVisibility(View.GONE);
                relCheckalllayout.setVisibility(View.GONE);
                smsList.setVisibility(View.GONE);
                playSoundForAttendance("Standard Filters Are Cleared",SmsandnotificationActivity.this);
                Toast.makeText(SmsandnotificationActivity.this, "Standard Filters Are Cleared", Toast.LENGTH_SHORT).show();

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
                    playSoundForAttendance("Please Select Standard Before Applying Filter",SmsandnotificationActivity.this);
                    Toast.makeText(SmsandnotificationActivity.this, "Please Select Standard Before Applying Filter", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void callApiToLoadInquiryDataAccToStandard(String mStrStandardIds) {
        if(!CommonUtil.isInternetAvailable(SmsandnotificationActivity.this)){
            return;
        }

        new OkHttpRequest(SmsandnotificationActivity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_FILTER,
                RequestParam.standardWiseData(""+mStrStandardIds),
                RequestParam.getNull(),
                CODE_INQUIRY_FILTER,
                false,this);
    }

    private void callApiForLoadStandardData() {
        if(!CommonUtil.isInternetAvailable(SmsandnotificationActivity.this)){
            return;
        }

        new OkHttpRequest(SmsandnotificationActivity.this,
                OkHttpRequest.Method.GET,
                Constants.WS_COURSE_STANDARD,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_STUDENT_STANDARD,
                false,this);
    }

    private void showCalenderViewDialog() {
        tempDialogBox = new TempDialogBox();
        tempDialogBox.show(getSupportFragmentManager(),"add tag");
    }

    @Override
    public void onReturnSingleDate(ArrayList<ModelClassForInquiryDetails.Inquiry> mALfilteredlistdatewise, int flagValue) {
        if(flagValue==11){
            if(mALfilteredlistdatewise!=null && mALfilteredlistdatewise.size()>0){
                mALInquiryPersons = mALfilteredlistdatewise;
                mTxtokay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(tempDialogBox!=null){
                            relCheckalllayout.setVisibility(View.VISIBLE);
                            adapterInquiryPersons = new CustomListAdapter(SmsandnotificationActivity.this,mALInquiryPersons);
                            smsList.setVisibility(View.VISIBLE);
                            mTvextra.setVisibility(View.GONE);
                            smsList.setAdapter(adapterInquiryPersons);
                            adapterInquiryPersons.notifyDataSetChanged();
                            tempDialogBox.dismiss();
                        }
                    }
                });

                checkBoxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (mALInquiryPersons != null && mALInquiryPersons.size() > 0) {
                            if(b) {
                                for (int i = 0; i < mALInquiryPersons.size(); i++) {
                                    mALInquiryPersons.get(i).setSelected(true);
                                }
                            }else{
                                for (int i = 0; i < mALInquiryPersons.size(); i++) {
                                    mALInquiryPersons.get(i).setSelected(false);
                                }
                            }
                            adapterInquiryPersons.notifyDataSetChanged();
                        }
                    }
                });
            }else if(mALfilteredlistdatewise==null){
                mTxtokay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(tempDialogBox!=null){
                            mALInquiryPersons.clear();
                            adapterInquiryPersons = new CustomListAdapter(SmsandnotificationActivity.this,mALInquiryPersons);
                            smsList.setVisibility(View.VISIBLE);
                            smsList.setAdapter(adapterInquiryPersons);
                            adapterInquiryPersons.notifyDataSetChanged();
                            relCheckalllayout.setVisibility(View.GONE);
                            mTvextra.setVisibility(View.VISIBLE);
                            tempDialogBox.dismiss();
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
                mALInquiryPersons = marraylistDBfiltered;
                mTxtokay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(tempDialogBox!=null){
                            relCheckalllayout.setVisibility(View.VISIBLE);
                            adapterInquiryPersons = new CustomListAdapter(SmsandnotificationActivity.this,mALInquiryPersons);
                            smsList.setVisibility(View.VISIBLE);
                            mTvextra.setVisibility(View.GONE);
                            smsList.setAdapter(adapterInquiryPersons);
                            adapterInquiryPersons.notifyDataSetChanged();
                            tempDialogBox.dismiss();
                        }
                    }
                });
                checkBoxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (mALInquiryPersons != null && mALInquiryPersons.size() > 0) {
                            if(b) {
                                for (int i = 0; i < mALInquiryPersons.size(); i++) {
                                    mALInquiryPersons.get(i).setSelected(true);
                                }
                            }else{
                                for (int i = 0; i < mALInquiryPersons.size(); i++) {
                                    mALInquiryPersons.get(i).setSelected(false);
                                }
                            }
                            adapterInquiryPersons.notifyDataSetChanged();
                        }
                    }
                });
            }else if(marraylistDBfiltered==null){
                mTxtokay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(tempDialogBox!=null){
                            mALInquiryPersons.clear();
                            adapterInquiryPersons = new CustomListAdapter(SmsandnotificationActivity.this,mALInquiryPersons);
                            smsList.setAdapter(adapterInquiryPersons);
                            adapterInquiryPersons.notifyDataSetChanged();
                            relCheckalllayout.setVisibility(View.GONE);
                            mTvextra.setVisibility(View.VISIBLE);
                            tempDialogBox.dismiss();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onStandardItemClick(int position, boolean value) {
        mAlstandardlist.get(position).isSelected = value;
        sampleStandardFilterAdapter.notifyDataSetChanged();
    }

    // temp dialog box for sms activity

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

    private void callSMSBottomSheetTestDialog() {
        final View mViewcourseFilter = getLayoutInflater().inflate(R.layout.course_bottom_sheet_filter,null);
        mBottomSheetCourseFilter = new BottomSheetDialog(SmsandnotificationActivity.this);
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

                // all data should be cleared here otherwise something will be wrong
                mStringids = "";
                mEdtsms.setText("");
                mAlstrings.clear();
                mAlitems.clear();
                mALInquiryPersons.clear();
                adapterInquiryPersons.notifyDataSetChanged();
                mRvFilteredItemslist.setVisibility(View.GONE);
                relCheckalllayout.setVisibility(View.GONE);
                smsList.setVisibility(View.GONE);
                playSoundForAttendance("Courses Filters Are Cleared",SmsandnotificationActivity.this);
                Toast.makeText(context, "Courses Filters Are Cleared", Toast.LENGTH_SHORT).show();

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
                    playSoundForAttendance("Please Select Courses Before Applying Filter",SmsandnotificationActivity.this);
                    Toast.makeText(context, "Please Select Courses Before Applying Filter", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void callApiToLoadInquiryDataAccToCourse(String mStringids) {
        if(!CommonUtil.isInternetAvailable(SmsandnotificationActivity.this)){
            return;
        }

        new OkHttpRequest(SmsandnotificationActivity.this,
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
        if (!CommonUtil.isInternetAvailable(SmsandnotificationActivity.this)) {
            return;
        }

        new OkHttpRequest(SmsandnotificationActivity.this,
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
        Log.e("temp log>>>", "" + response);
        if (response == null) {
            return;
        }

        switch (requestId){
            case CODE_SMS_CATEGORIES:
                Log.e("Sms categories>>>",""+response);
                final Gson gson1 = new Gson();
                try{
                    smscategoriesresponse = gson1.fromJson(response, Smscategoriesresponse.class);
                    if(smscategoriesresponse.getCategories()!=null && smscategoriesresponse.getCategories().size()>0){
                        setDatatospinnercategory(smscategoriesresponse.getCategories());
                    }
                }catch (Exception e){
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
            case CODE_SMS_CATRGORIES_WISE_MSGS:
                Log.e("msgs >>>",""+response);
                final Gson msgs = new Gson();
                try{
                    categoryWiseResponse = msgs.fromJson(response, CategoryWiseResponse.class);
                    if(categoryWiseResponse.getStatus().equals(Constants.SUCCESS_CODE)){
                        mAlcategorywisemsgs = categoryWiseResponse.getMessages();
                        setMessagesToListview();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case CODE_SEARCH_INQUIRY:
                Log.e("search sms",""+response);
                final Gson searchSms = new Gson();
                try{
                    modelClassForInquiryDetails = searchSms.fromJson(response,ModelClassForInquiryDetails.class);
                    if(modelClassForInquiryDetails.getInquiries()!=null &&
                        modelClassForInquiryDetails.getInquiries().size()>0){
                        mALInquiryPersons = modelClassForInquiryDetails.getInquiries();

                        // setting to the list_view

                        relCheckalllayout.setVisibility(View.VISIBLE);
                        adapterInquiryPersons = new CustomListAdapter(SmsandnotificationActivity.this,mALInquiryPersons);
                        smsList.setVisibility(View.VISIBLE);
                        mTvextra.setVisibility(View.GONE);
                        smsList.setAdapter(adapterInquiryPersons);
                        adapterInquiryPersons.notifyDataSetChanged();
                        Toast.makeText(context, "Yes Data Found", Toast.LENGTH_SHORT).show();
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
            case CODE_INQUIRY_FILTER:
                Log.e("Inquiry>>>",""+response);
                final Gson courseFilter = new Gson();
                try{
                    mALInquiryPersons = new ArrayList<>();
                    modelClassForInquiryDetails = courseFilter.fromJson(response,ModelClassForInquiryDetails.class);
                    mALInquiryPersons = modelClassForInquiryDetails.getInquiries();
                    // call function for Applying data
                    if(!CommonUtil.isNullString(""+mStringids)){
                        setCourseWiseFilteredDataToListView(mALInquiryPersons);
                    }else if(!CommonUtil.isNullString(""+mStrStandardIds)){
                        setStandardWiseFilteredDataToListView(mALInquiryPersons);
                    }else{
                        // do nothing here
                        mTvextra.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "No Data Found Here", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setStandardWiseFilteredDataToListView(final ArrayList<ModelClassForInquiryDetails.Inquiry> mALInquiryPersons) {
        if(mALInquiryPersons.size()>0 && mALInquiryPersons!=null){
            relCheckalllayout.setVisibility(View.VISIBLE);
            adapterInquiryPersons = new CustomListAdapter(SmsandnotificationActivity.this,mALInquiryPersons);
            smsList.setVisibility(View.VISIBLE);
            mTvextra.setVisibility(View.GONE);
            smsList.setAdapter(adapterInquiryPersons);
            adapterInquiryPersons.notifyDataSetChanged();
            mRvFilteredItemslist.setVisibility(View.VISIBLE);
            mTxtfiltereditems.setText(""+mStrStandardNames.trim());
            checkBoxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (mALInquiryPersons != null && mALInquiryPersons.size() > 0) {
                        if(b) {
                            for (int i = 0; i < mALInquiryPersons.size(); i++) {
                                mALInquiryPersons.get(i).setSelected(true);
                            }
                        }else{
                            for (int i = 0; i < mALInquiryPersons.size(); i++) {
                                mALInquiryPersons.get(i).setSelected(false);
                            }
                        }
                        adapterInquiryPersons.notifyDataSetChanged();
                    }
                }
            });
        }else{
            mALInquiryPersons.clear();
            adapterInquiryPersons = new CustomListAdapter(SmsandnotificationActivity.this,mALInquiryPersons);
            smsList.setAdapter(adapterInquiryPersons);
            adapterInquiryPersons.notifyDataSetChanged();
            relCheckalllayout.setVisibility(View.GONE);
            mTvextra.setVisibility(View.VISIBLE);
        }
    }

    private void setStandardlistAdapter(ArrayList<ModelClassForStandard.Standard> mAlstandardlist) {
        sampleStandardFilterAdapter = new SampleStandardFilterAdapter(SmsandnotificationActivity.this,mAlstandardlist,this);
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

    private void setCourseWiseFilteredDataToListView(final ArrayList<ModelClassForInquiryDetails.Inquiry> mALInquiryPersons) {
        if(mALInquiryPersons.size()>0 && mALInquiryPersons!=null){
            relCheckalllayout.setVisibility(View.VISIBLE);
            adapterInquiryPersons = new CustomListAdapter(SmsandnotificationActivity.this,mALInquiryPersons);
            smsList.setVisibility(View.VISIBLE);
            mTvextra.setVisibility(View.GONE);
            smsList.setAdapter(adapterInquiryPersons);
            adapterInquiryPersons.notifyDataSetChanged();
            mRvFilteredItemslist.setVisibility(View.VISIBLE);
            mTxtfiltereditems.setText(""+mStrnames.trim());
            checkBoxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (mALInquiryPersons != null && mALInquiryPersons.size() > 0) {
                        if(b) {
                            for (int i = 0; i < mALInquiryPersons.size(); i++) {
                                mALInquiryPersons.get(i).setSelected(true);
                            }
                        }else{
                            for (int i = 0; i < mALInquiryPersons.size(); i++) {
                                mALInquiryPersons.get(i).setSelected(false);
                            }
                        }
                        adapterInquiryPersons.notifyDataSetChanged();
                    }
                }
            });
        }else{
            mALInquiryPersons.clear();
            adapterInquiryPersons = new CustomListAdapter(SmsandnotificationActivity.this,mALInquiryPersons);
            smsList.setAdapter(adapterInquiryPersons);
            adapterInquiryPersons.notifyDataSetChanged();
            relCheckalllayout.setVisibility(View.GONE);
            mTvextra.setVisibility(View.VISIBLE);
        }
    }

    private void setCourseCourseListAdapter(ArrayList<ModelClassForCourses.Course> mAlcourselist) {
        mAdapterCourseList = new SampleCourseWiseAdapter(SmsandnotificationActivity.this,mAlcourselist,this);
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

    private void setMessagesToListview() {
        SmsCategoryAdapter smsCategoryAdapter = new SmsCategoryAdapter(SmsandnotificationActivity.this,mAlcategorywisemsgs);
        listview.setVisibility(View.VISIBLE);
        listview.setAdapter(smsCategoryAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String id = mAlcategorywisemsgs.get(position).getMessage();
                if(dialog!=null && dialog.isShowing()){
                    mEdtsms.setText(""+id);
                    dialog.dismiss();
                }
            }
        });
    }

    private void setDatatospinnercategory(ArrayList<Smscategoriesresponse.Category> categories) {
        mAlsmscategorylist = new ArrayList<>();

        if(categories.size()>0 && categories!=null){
            for(int i = 0 ; i < categories.size() ; i++){
                mAlsmscategorylist.add(""+categories.get(i).getName());
            }
        }

        mAlsmscategorylist.add(0,"Select Category From Here");
        ArrayAdapter<String> adapterStandard = new ArrayAdapter<>(SmsandnotificationActivity.this,
                R.layout.my_spinner_item,R.id.tvCustomSpinner, mAlsmscategorylist);
        mSprcategories.setAdapter(adapterStandard);
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