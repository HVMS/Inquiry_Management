package com.globalitians.inquiry.activities.AddInquiry.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.adapter.CoursesAdapter;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForCheckSmsDays;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForCourses;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForPartners;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForStandard;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForStraem;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForSubjects;
import com.globalitians.inquiry.activities.Dashboard.Activities.DashboardActivity;
import com.globalitians.inquiry.activities.Inquirydetails.model.InquiryDetailsModel;
import com.globalitians.inquiry.activities.SmsAndNotification.Adapters.SmsCategoryAdapter;
import com.globalitians.inquiry.activities.SmsAndNotification.Models.CategoryWiseResponse;
import com.globalitians.inquiry.activities.SmsAndNotification.Models.Smscategoriesresponse;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.network.OkHttpInterface;
import com.globalitians.inquiry.activities.network.OkHttpRequest;
import com.globalitians.inquiry.activities.network.RequestParam;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static com.globalitians.inquiry.activities.Utility.CommonUtil.getSharedPrefrencesInstance;
import static com.globalitians.inquiry.activities.Utility.CommonUtil.playSoundForAttendance;
import static com.globalitians.inquiry.activities.Utility.CommonUtil.setFullScreenMode;
import static com.globalitians.inquiry.activities.Utility.Constants.ATTACHMENT_IMAGE;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_INQUIRY_DETAILS;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_MESSAGE_DAYS;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_PARTNER_IDS;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_POST_INQUIRIES;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_SMS_CATEGORIES;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_SMS_CATRGORIES_WISE_MSGS;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_COURSES;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_STANDARD;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_STREAM;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_SUBJCETS;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_EMPLOYEE_BRANCH_ID;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_SMS_DAYS;
import static com.globalitians.inquiry.activities.Utility.Constants.LOGIN_PREFRENCES;
import static com.globalitians.inquiry.activities.Utility.Constants.SUCCESS_CODE;

public class AddinquiryActvity extends AppCompatActivity
        implements OkHttpInterface,
        View.OnClickListener,
        CoursesAdapter.OnCourseCheckListner{

    // cardview and other stuff
    private TextView mTxtpersonaldetails;
    private TextView mTxtinquirydetails;
    private CardView mCardpersonaldetails;
    private CardView mCardinquirydetails;
    private TextView mTxtcircleone;
    private TextView mTxtcircletwo;
    private TextView mTxtpartnerselect;
    private View mViewlineone;
    private View mViewlinetwo;
    private Button btnSubmit;
    private CircleImageView mCirivprofilephoto;

    // personal and inquiry view stuff
    private EditText mEdtfirstname;
    private EditText mEdtlastname;
    private EditText mEdtmobileno;
    private EditText mEdtfeedback;
    private EditText mEdtinquirydate;
    private EditText mEdtupcomingdate;
    private EditText mEdtsmstypeselect;

    private DatePickerDialog mDatePickerDiaoginquirydate = null;
    private DatePickerDialog mDatePickerDialogJoinedDate = null;

    private Spinner mSprreference;
    private Spinner mSprpartner;
    private Spinner mSprcourse;
    private Spinner mSprstandards;
    private Spinner mSprstreams;
    private Spinner mSprsubjects;
    private Spinner mSprcategories;

    private String mStrfname="";
    private String mStrlname="";
    private String mStrmobileno="";
    private String mStrfeedback="";
    private String mStrdateofinquiry="";
    private String mStrdateofupcoming="";
    private String mStrreference="";
    private String mStrpartner="";
    private String mStrstandard="";
    private String mStrstream="";
    private String mStrsubject="";

    // API STUFF
    private ModelClassForStandard modelClassForStandardlist;
    private ArrayList<String> mAlStandard;

    private ModelClassForCourses modelClassForCourses;
    private ArrayList<ModelClassForCourses.Course> mAlCourses;
    private CoursesAdapter coursesAdapter;

    private ModelClassForStraem modelClassForStraem;
    private ArrayList<String> mAlStreams;

    private ModelClassForSubjects modelClassForSubjects;
    private ArrayList<String> mAlSubjects;

    private ModelClassForPartners modelClassForPartners;
    private ArrayList<String> mALpartner;

    private ModelClassForCheckSmsDays modelClassForCheckSmsDays;
    private InquiryDetailsModel inquiryDetailsModel;

    private ArrayList<String> mAlsmscategorylist;
    private Smscategoriesresponse smscategoriesresponse;

    private CategoryWiseResponse categoryWiseResponse;
    private ArrayList<CategoryWiseResponse.Message> mAlcategorywisemsgs;

    private String mStrSelectedStandardId = "";
    private String mStrSelectedCourseId = "";
    private String mStrSelectedPartnerId = "";
    private String mStrSelectedStreamId = "";
    private String mStrSelectedSubjectId = "";
    private String mStrSelectedMessageNotification = "";
    private String mStrSlug = "";
    private String inquiry_id = "";
    private String mStrSelectedSmsTypeId="";
    private ListView listview;
    private TextView txt;
    private Dialog dialog;
    private Dialog courseDialog=null;
    private EditText mEdtSelectCourses;
    private TextView mTxtCourseTitle;
    private RecyclerView mRvCourses;
    private Button mBtnAssgin;
    private Button mBtncancel;
    private ArrayList<ModelClassForCourses.Course> mArraylistCoursesSelected = null;


    private static final String PHOTOS_KEY = "easy_image_photos_list";
    private static final int CHOOSER_PERMISSIONS_REQUEST_CODE = 7459;
    private static final int CAMERA_REQUEST_CODE = 7500;
    private static final int GALLERY_REQUEST_CODE = 7502;

    /* Easy image picker variable stuff */

    private static String ATTACHMENT_TYPE = ""; // IMAGE

    private EasyImage easyImage;
    private ArrayList<File> photos = new ArrayList<File>();
    private File mFileimage=null;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode(AddinquiryActvity.this);
        setContentView(R.layout.activity_addinquiry);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Inquiry Form");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.bv_primaryColor)));

        findViews();
        initializeDatePickers();
        init();

        if (savedInstanceState != null) {
            photos = (ArrayList<File>) savedInstanceState.getSerializable(PHOTOS_KEY);
        }

        callApiToLoadCourseData();

        // get inquiry_detail object from inquiryDetails Activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getInquiryIdFromIntet();
            }
        },700);

        // call Api through functions
        callApiToLoadStandardData();
        callApiToLoadPartnerIds();
    }

    private void getInquiryIdFromIntet() {
        Intent intent = getIntent();
        if(intent!=null){
            getSupportActionBar().setTitle("Change Details");
            btnSubmit.setText("Save");
            inquiry_id = ""+intent.getStringExtra("sample_inquiry_id");
            if(!CommonUtil.isNullString(""+inquiry_id)){
                callApiToFetchInquiryDetails(""+inquiry_id);
            }
        }
    }

    private void callApiToFetchInquiryDetails(String inquiryId) {
        if(!CommonUtil.isInternetAvailable(AddinquiryActvity.this)){
            return;
        }

        new OkHttpRequest(AddinquiryActvity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_DETAILS,
                RequestParam.inquirydetails(""+inquiryId),
                RequestParam.getNull(),
                CODE_INQUIRY_DETAILS,
                true,this);
    }

    private void init() {
        mArraylistCoursesSelected = new ArrayList<>();
    }

    private void calApiToLoadSmsTYpeData() {
        if(!CommonUtil.isInternetAvailable(AddinquiryActvity.this)){
            return;
        }

        new OkHttpRequest(AddinquiryActvity.this,
                OkHttpRequest.Method.GET,
                Constants.WS_SMS_CATEGORIES,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_SMS_CATEGORIES,
                false,this);
    }

    private void callApiToLoadPartnerIds() {
        if(!CommonUtil.isInternetAvailable(AddinquiryActvity.this)){
            return;
        }

        new OkHttpRequest(AddinquiryActvity.this,
                OkHttpRequest.Method.GET,
                Constants.WS_PARTNERS_LIST,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_PARTNER_IDS,
                false,this);
    }

    private void callApiToLoadStandardData() {
        if (!CommonUtil.isInternetAvailable(AddinquiryActvity.this)) {
            return;
        }

        new OkHttpRequest(AddinquiryActvity.this,
                OkHttpRequest.Method.GET,
                Constants.WS_COURSE_STANDARD,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_STUDENT_STANDARD,
                false, this);
    }

    private void callApiToLoadCourseData() {
        if (!CommonUtil.isInternetAvailable(AddinquiryActvity.this)) {
            return;
        }

        new OkHttpRequest(AddinquiryActvity.this,
                OkHttpRequest.Method.GET,
                Constants.WS_COURSE_LIST,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_STUDENT_COURSES,
                false, this);
    }

    private void callApiToLoadSubjectData() {
        if(!CommonUtil.isInternetAvailable(AddinquiryActvity.this)){
            return;
        }
        new OkHttpRequest(AddinquiryActvity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_SUBJECT_LIST,
                RequestParam.studentSubjetcs(""+mStrSelectedStandardId),
                RequestParam.getNull(),
                CODE_STUDENT_SUBJCETS,
                false,this);
    }

    private void callApiToLoadStreamData() {
        if (!CommonUtil.isInternetAvailable(AddinquiryActvity.this)) {
            return;
        }

        new OkHttpRequest(AddinquiryActvity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_COURSE_STREAM,
                RequestParam.studentStream(""+mStrSelectedStandardId),
                RequestParam.getNull(),
                CODE_STUDENT_STREAM,
                false, this);
    }

    private void initializeDatePickers() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener inquirydatelistner;
        inquirydatelistner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if ((month + 1) < 10 && dayOfMonth < 10) {
                    mEdtinquirydate.setText("0" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if ((month + 1) < 10) {
                    mEdtinquirydate.setText("" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if (dayOfMonth < 10) {
                    mEdtinquirydate.setText("0" + dayOfMonth + "-" + (month + 1) + "-" + year);
                } else {
                    mEdtinquirydate.setText("" + dayOfMonth + "-" + (month + 1) + "-" + year);
                }
            }
        };

        DatePickerDialog.OnDateSetListener joinedDateListener;
        joinedDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if ((month + 1) < 10 && dayOfMonth < 10) {
                    mEdtupcomingdate.setText("0" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if ((month + 1) < 10) {
                    mEdtupcomingdate.setText("" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if (dayOfMonth < 10) {
                    mEdtupcomingdate.setText("0" + dayOfMonth + "-" + (month + 1) + "-" + year);
                } else {
                    mEdtupcomingdate.setText("" + dayOfMonth + "-" + (month + 1) + "-" + year);
                }
                validateInquirydetails();
                //validateFinction();
            }
        };

        //initializing date filter date picker dialog
        mDatePickerDiaoginquirydate = new DatePickerDialog(
                AddinquiryActvity.this, R.style.DialogTheme,inquirydatelistner, year, month, day);
        mDatePickerDiaoginquirydate.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);

        //initializing date filter date picker dialog
        mDatePickerDialogJoinedDate = new DatePickerDialog(
                AddinquiryActvity.this,R.style.DialogTheme, joinedDateListener, year, month, day);
        mDatePickerDialogJoinedDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    @SuppressLint("ResourceAsColor")
    private void validateInquirydetails(){
        if(!CommonUtil.isNullString(""+mEdtSelectCourses.getText().toString()) && !CommonUtil.isNullString(mEdtinquirydate.getText().toString().trim()) && !CommonUtil.isNullString(mEdtupcomingdate.getText().toString().trim())){
            mTxtcircletwo.setBackgroundColor(R.color.colorGreenIn);
            mTxtcircletwo.setText("");
            mTxtcircletwo.setBackgroundResource(R.drawable.ic_check_final);
        }
    }

    private void findViews() {
        mTxtpersonaldetails = findViewById(R.id.tv_personal_deetails);
        mTxtinquirydetails = findViewById(R.id.tv_inquiry_details);
        mCardpersonaldetails = findViewById(R.id.card_view);
        mCardinquirydetails = findViewById(R.id.card_view_another);
        mViewlineone = findViewById(R.id.lineone);
        mViewlinetwo = findViewById(R.id.linetwo);
        btnSubmit = findViewById(R.id.submit_btn);
        mTxtcircleone = findViewById(R.id.textview_one);
        mTxtcircletwo = findViewById(R.id.textview_two);
        mTxtpartnerselect = findViewById(R.id.tv_partner_select);
        mCirivprofilephoto = findViewById(R.id.sample_course_wise_image);

        mEdtfirstname = findViewById(R.id.edt_firstname);
        mEdtlastname = findViewById(R.id.edt_lastname);
        mEdtmobileno = findViewById(R.id.edt_mobile_number);
        mEdtfeedback = findViewById(R.id.edt_feedback);
        mEdtinquirydate = findViewById(R.id.edt_inquiry_date);
        mEdtupcomingdate = findViewById(R.id.edt_upcoming_date);
        mEdtsmstypeselect = findViewById(R.id.edt_my_sms_list);
        mEdtSelectCourses = findViewById(R.id.edt_select_courses);
        mSprreference = findViewById(R.id.spinner_referenceBy);
        mSprcourse = findViewById(R.id.spinner_course_select);
        mSprstandards = findViewById(R.id.spinner_standard_select);
        mSprstreams = findViewById(R.id.spinner_stream_select);
        mSprsubjects = findViewById(R.id.spinner_subject_select);
        mSprpartner = findViewById(R.id.spinner_partner_select);

        listners();
    }

    private void listners() {

        mTxtpersonaldetails.setOnClickListener(this);
        mTxtinquirydetails.setOnClickListener(this);
        mEdtinquirydate.setOnClickListener(this);
        mEdtupcomingdate.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        mTxtcircleone.setOnClickListener(this);
        mTxtcircletwo.setOnClickListener(this);
        mEdtsmstypeselect.setOnClickListener(this);
        mCirivprofilephoto.setOnClickListener(this);
        mEdtSelectCourses.setOnClickListener(this);

        // spinner stuff

        mSprstandards.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    mStrSelectedStandardId = "" + modelClassForStandardlist.getStandards().get(i - 1).getId();
                    mStrstandard = "" + modelClassForStandardlist.getStandards().get(i-1).getName();
                    callApiToLoadStreamData();
                    callApiToLoadSubjectData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSprstreams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    mStrstream = mSprstreams.getItemAtPosition(i).toString();
                    mStrSelectedStreamId = "" + modelClassForStraem.getStreams().get(i-1).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSprsubjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    mStrsubject = mSprsubjects.getItemAtPosition(position).toString().trim();
                    mStrSelectedSubjectId = ""+modelClassForSubjects.getSubjects().get(position-1).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSprreference.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    mStrreference = mSprreference.getItemAtPosition(i).toString().trim();

                    if(mStrreference.equals("Global IT Partner")){

                        mTxtpartnerselect.setVisibility(View.VISIBLE);
                        mSprpartner.setVisibility(View.VISIBLE);

                        mSprpartner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if(i>0){
                                    mStrSelectedPartnerId = "" + modelClassForPartners.getCourses().get(i-1).getId();
                                    mStrpartner = ""+modelClassForPartners.getCourses().get(i-1).getPartnerUserid();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }

                        });

                    }else{
                        mTxtpartnerselect.setVisibility(View.GONE);
                        mSprpartner.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void callApiToLoadSmsMessagesCategoryWise() {
        if(!CommonUtil.isInternetAvailable(AddinquiryActvity.this)){
            return;
        }

        new OkHttpRequest(AddinquiryActvity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_SMS_CATEGORIES_WISE_MESSAGES,
                RequestParam.categoryWiseFilter(""+mStrSelectedSmsTypeId),
                RequestParam.getNull(),
                CODE_SMS_CATRGORIES_WISE_MSGS,
                false,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_personal_deetails:
                mCardpersonaldetails.setVisibility(View.VISIBLE);
                mCardinquirydetails.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                mViewlinetwo.setVisibility(View.GONE);
                mViewlineone.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_inquiry_details:
                callApiToValidateData();
                break;
            case R.id.edt_inquiry_date:
                mDatePickerDiaoginquirydate.show();
                break;
            case R.id.edt_upcoming_date:
                mDatePickerDialogJoinedDate.show();
                break;
            case R.id.edt_my_sms_list:
                callDialogSMSType();
                break;
            case R.id.submit_btn:
                validatedataanduploadtoserver();
                break;
            case R.id.sample_course_wise_image:
                ATTACHMENT_TYPE = ATTACHMENT_IMAGE;

                String[] necessaryPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
                requestPermissionsCompat(necessaryPermissions, CHOOSER_PERMISSIONS_REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CHOOSER_PERMISSIONS_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage.openChooserWithGallery(AddinquiryActvity.this,"Choose Photo from",0);
        } else if (requestCode == CAMERA_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage.openCamera(AddinquiryActvity.this,0);
        }else if (requestCode == GALLERY_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage.openGallery(AddinquiryActvity.this,0);
        }

    }

    private void onPhotoReturned(List<File> imageFiles) {
        photos.addAll(imageFiles);
        mFileimage = photos.get(photos.size()-1);
        mCirivprofilephoto.setImageURI(Uri.fromFile(mFileimage));
    }

    private void requestPermissionsCompat(String[] necessaryPermissions, int chooserPermissionsRequestCode) {
        ActivityCompat.requestPermissions(AddinquiryActvity.this, necessaryPermissions, chooserPermissionsRequestCode);
    }

    private void findCourseDialogViews(Dialog courseDialog) {
        mTxtCourseTitle = courseDialog.findViewById(R.id.tv_course_title);
        mRvCourses = courseDialog.findViewById(R.id.rv_course_list);
        mBtnAssgin = courseDialog.findViewById(R.id.btn_assign);
        mBtncancel = courseDialog.findViewById(R.id.cancel_button);
    }

    private void callDialogSMSType() {
        dialog = new Dialog(AddinquiryActvity.this);
        dialog.setContentView(R.layout.custom_sms_type_layout);
        txt = dialog.findViewById(R.id.vis);

        calApiToLoadSmsTYpeData();
        mSprcategories = dialog.findViewById(R.id.spinneer_predefined_sms);
        listview = dialog.findViewById(R.id.lv_predefined_sms_list);

        mSprcategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    Toast.makeText(AddinquiryActvity.this, ""+smscategoriesresponse.getCategories().get(position-1).getName(), Toast.LENGTH_SHORT).show();
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

    @SuppressLint("ResourceAsColor")
    private void callApiToValidateData() {
        mCardinquirydetails.setVisibility(View.VISIBLE);
        mTxtcircleone.setBackgroundResource(R.drawable.ic_check_final);
        mViewlinetwo.setBackgroundColor(R.color.colorGreen_A400);
        mViewlinetwo.setVisibility(View.VISIBLE);
        mTxtcircleone.setText("");
        btnSubmit.setVisibility(View.VISIBLE);
        mViewlineone.setVisibility(View.GONE);
        mCardpersonaldetails.setVisibility(View.GONE);
    }

    private void validatedataanduploadtoserver() {

        if(!CommonUtil.isInternetAvailable(AddinquiryActvity.this)){
            playSoundForAttendance("No Internet Connection. Please Check your internet connection.", AddinquiryActvity.this);
            Toast.makeText(this, "No Internet Connection. Please Check your internet connection.", Toast.LENGTH_SHORT).show();
            return;
        }

        mStrfname = ""+mEdtfirstname.getText().toString().trim();
        mStrlname = ""+mEdtlastname.getText().toString().trim();
        mStrmobileno = ""+mEdtmobileno.getText().toString().trim();
        mStrfeedback = ""+mEdtfeedback.getText().toString().trim();
        mStrdateofinquiry = ""+mEdtinquirydate.getText().toString().trim();
        mStrdateofupcoming = ""+mEdtupcomingdate.getText().toString().trim();

        if(CommonUtil.isNullString(mStrfname)){
            playSoundForAttendance("" + getResources().getString(R.string.toast_invalid_firstname), AddinquiryActvity.this);
            Toast.makeText(this, ""+getResources().getString(R.string.toast_invalid_firstname), Toast.LENGTH_SHORT).show();
        }else if(mStrfname.length() < 2){
            playSoundForAttendance("" + getResources().getString(R.string.toast_invalid_length_firstname), AddinquiryActvity.this);
            Toast.makeText(this, "" + getResources().getString(R.string.toast_invalid_length_firstname), Toast.LENGTH_SHORT).show();
            return;
        }else if(CommonUtil.isNullString(mStrlname)){
            playSoundForAttendance("" + getResources().getString(R.string.toast_invalid_lastname), AddinquiryActvity.this);
            Toast.makeText(this, ""+getResources().getString(R.string.toast_invalid_lastname), Toast.LENGTH_SHORT).show();
        }else if(mStrlname.length() < 2){
            playSoundForAttendance("" + getResources().getString(R.string.toast_invalid_length_lastname), AddinquiryActvity.this);
            Toast.makeText(this, "" + getResources().getString(R.string.toast_invalid_length_lastname), Toast.LENGTH_SHORT).show();
            return;
        }else if (CommonUtil.isNullString(mStrmobileno)) {
            playSoundForAttendance("" + getResources().getString(R.string.toast_invalid_mobile), AddinquiryActvity.this);
            Toast.makeText(this, "" + getResources().getString(R.string.toast_invalid_mobile), Toast.LENGTH_SHORT).show();
            return;
        } else if (mStrmobileno.length() < 10) {
            playSoundForAttendance("" + getResources().getString(R.string.toast_invalid_phone_number), AddinquiryActvity.this);
            Toast.makeText(this, "" + getResources().getString(R.string.toast_invalid_phone_number), Toast.LENGTH_SHORT).show();
            return;
        }else if(CommonUtil.isNullString(mStrreference)) {
            playSoundForAttendance("" + getResources().getString(R.string.toast_invalid_reference), AddinquiryActvity.this);
            Toast.makeText(this, "" + getResources().getString(R.string.toast_invalid_reference), Toast.LENGTH_SHORT).show();
            return;
        } else if(CommonUtil.isNullString(mStrfeedback)){
            playSoundForAttendance("" + getResources().getString(R.string.toast_invalid_feedback), AddinquiryActvity.this);
            Toast.makeText(this, "" + getResources().getString(R.string.toast_invalid_feedback), Toast.LENGTH_SHORT).show();
            return;
        } else if(CommonUtil.isNullString(mStrdateofinquiry)){
            playSoundForAttendance(""+getResources().getString(R.string.toast_invalid_inquiry),AddinquiryActvity.this);
            Toast.makeText(this, ""+getResources().getString(R.string.toast_invalid_inquiry), Toast.LENGTH_SHORT).show();
            return;
        }else if(CommonUtil.isNullString(mStrdateofupcoming)) {
            playSoundForAttendance("" + getResources().getString(R.string.toast_invalid_upcoming), AddinquiryActvity.this);
            Toast.makeText(this, "" + getResources().getString(R.string.toast_invalid_upcoming), Toast.LENGTH_SHORT).show();
            return;
        }else {

            callApiToFetchSmsDays();

            Log.e("<<<<",""+inquiry_id);
            Log.e("slug <<<<",""+mStrSlug);

            for(int i = 0 ; i < mArraylistCoursesSelected.size(); i++){
                if(CommonUtil.isNullString(mStrSelectedCourseId)){
                    mStrSelectedCourseId = ""+mArraylistCoursesSelected.get(i).getId();
                }else{
                    mStrSelectedCourseId = mStrSelectedCourseId + ","+mArraylistCoursesSelected.get(i).getId();
                }
            }

            Toast.makeText(this, ""+mStrSelectedCourseId, Toast.LENGTH_SHORT).show();

            if(CommonUtil.isNullString(mStrSelectedCourseId)){
                playSoundForAttendance("Please Select Courses",AddinquiryActvity.this);
                Toast.makeText(this, "Please Select Courses", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!CommonUtil.isInternetAvailable(AddinquiryActvity.this)){
                return;
            }

            new OkHttpRequest(AddinquiryActvity.this,
                    OkHttpRequest.Method.POST,
                    Constants.WS_POST_INQUIRIES,
                    RequestParam.submitPostInquiry(""+mStrfname,
                            ""+mStrlname,
                            ""+getSharedPrefrencesInstance(AddinquiryActvity.this).getString(KEY_EMPLOYEE_BRANCH_ID,""),
                            ""+mStrfeedback,
                            ""+mStrreference,
                            ""+mStrmobileno,
                            ""+mStrSelectedPartnerId,
                            ""+mStrSelectedCourseId,
                            ""+mStrSelectedStandardId,
                            ""+mStrSelectedStreamId,
                            ""+mStrSelectedSubjectId,
                            ""+mStrdateofinquiry.replace('/','-'),
                            ""+mStrSlug,
                            ""+mStrdateofupcoming.replace('/','-'),
                            ""+mStrSelectedMessageNotification,
                            ""+mEdtsmstypeselect.getText().toString().trim()),
                    RequestParam.getNull(),
                    CODE_POST_INQUIRIES,
                    false,this);
        }
    }

    private void callApiToFetchSmsDays() {
        if(!CommonUtil.isInternetAvailable(AddinquiryActvity.this)){
            return;
        }

        new OkHttpRequest(AddinquiryActvity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_CHECK_MESSAGE_DAYS,
                RequestParam.checkMessageDays(""+mStrdateofinquiry.replace('/','-').trim(),
                        ""+mStrdateofupcoming.replace('/','-').trim()),
                RequestParam.getNull(),
                CODE_MESSAGE_DAYS,
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
        return super.onOptionsItemSelected(item);
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
        switch (requestId) {
            case CODE_STUDENT_STANDARD:
                Log.e("Standards >>", "" + response);
                final Gson studentStandardList = new Gson();
                try {
                    modelClassForStandardlist = studentStandardList.fromJson(response, ModelClassForStandard.class);
                    if (modelClassForStandardlist.getStandards() != null
                            && modelClassForStandardlist.getStandards().size() > 0) {
                        setCourseStandardListAdapter(modelClassForStandardlist.getStandards());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CODE_STUDENT_COURSES:
                Log.e("Courses >>", "" + response);
                final Gson studentCoursesList = new Gson();
                try {
                    modelClassForCourses = studentCoursesList.fromJson(response, ModelClassForCourses.class);
                    if (modelClassForCourses.getCourses() != null &&
                            modelClassForCourses.getCourses().size() > 0) {
                        mAlCourses = modelClassForCourses.getCourses();
                        setcoursedataadapter(mAlCourses);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CODE_STUDENT_SUBJCETS:
                Log.e("Subjects >>", "" + response);
                final Gson studentsSubjectsList = new Gson();
                try {
                    modelClassForSubjects = studentsSubjectsList.fromJson(response, ModelClassForSubjects.class);
                    if (modelClassForSubjects.getStatus().equals(Constants.SUCCESS_CODE)) {
                        setSubjectsListAdapter(modelClassForSubjects.getSubjects());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CODE_STUDENT_STREAM:
                Log.e("Streams >>", "" + response);
                final Gson studentStreamsList = new Gson();
                try {
                    modelClassForStraem = studentStreamsList.fromJson(response, ModelClassForStraem.class);
                    if (modelClassForStraem.getStatus().equals(Constants.SUCCESS_CODE)) {
                        setStudentStreamData(modelClassForStraem.getStreams());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CODE_PARTNER_IDS:
                Log.e("Partners ids >>> ",""+response);
                final Gson partner_ids = new Gson();
                try{
                    modelClassForPartners = partner_ids.fromJson(response,ModelClassForPartners.class);
                    if(modelClassForPartners.getCourses() != null && modelClassForPartners.getCourses().size() > 0){
                        setPartnersIdsToSpinner(modelClassForPartners.getCourses());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case CODE_MESSAGE_DAYS:
                Log.e("Check msg >>>",""+response);
                final Gson gson = new Gson();
                try{
                    modelClassForCheckSmsDays = gson.fromJson(response,ModelClassForCheckSmsDays.class);
                    if(modelClassForCheckSmsDays.getStatus().equals(Constants.SUCCESS_CODE)){
                        if(modelClassForCheckSmsDays.getMessage()!=null){
                            mStrSelectedMessageNotification = ""+modelClassForCheckSmsDays.getDays().get(0);
                            Toast.makeText(this, ""+mStrSelectedMessageNotification, Toast.LENGTH_SHORT).show();
                            saveLoginPrefrences(modelClassForCheckSmsDays);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
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
            case CODE_INQUIRY_DETAILS:
                Log.e("inquiry_details",""+response);
                final Gson detailsGson = new Gson();
                try{
                    inquiryDetailsModel = detailsGson.fromJson(response,InquiryDetailsModel.class);
                    if(inquiryDetailsModel.getStatus().equals(SUCCESS_CODE)){
                        if(inquiryDetailsModel.getInquiryDetail()!=null){
                            setInquiryDetails(inquiryDetailsModel);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case CODE_POST_INQUIRIES:
                Log.e("post inquiries>>>>",""+response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject!=null && jsonObject.has("status")){
                        if(jsonObject.getString("status").equalsIgnoreCase("success")){
                            Toast.makeText(this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setInquiryDetails(InquiryDetailsModel inquiryDetailsModel) {

        mEdtSelectCourses.setText("");
        mStrSelectedCourseId = "";

        Toast.makeText(this, ""+inquiryDetailsModel.getInquiryDetail().getCourses().get(0).getName(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+mAlCourses.get(0).getName(), Toast.LENGTH_SHORT).show();

        for(int i = 0 ; i < inquiryDetailsModel.getInquiryDetail().getCourses().size(); i++){
            for(int j = 0 ; j < mAlCourses.size() ; j++){
                if(inquiryDetailsModel.getInquiryDetail().getCourses().get(i).getId()
                        .equals(mAlCourses.get(j).getId())){
                    mAlCourses.get(j).setSelected(true);
                    if(mEdtSelectCourses.getText().toString().length()>0){
                        mEdtSelectCourses.append(mAlCourses.get(j).getName()+", ");
                        mStrSelectedCourseId = mStrSelectedPartnerId+","+mAlCourses.get(j).getId();
                    }else{
                        mEdtSelectCourses.setText(""+mAlCourses.get(j).getName());
                        mStrSelectedCourseId = ""+mAlCourses.get(j).getId();
                    }
                }
            }
        }

        mEdtfirstname.setText(""+inquiryDetailsModel.getInquiryDetail().getFname());
        mEdtlastname.setText(""+inquiryDetailsModel.getInquiryDetail().getLname());
        mEdtmobileno.setText(""+inquiryDetailsModel.getInquiryDetail().getMobileno());
        mEdtfeedback.setText(""+inquiryDetailsModel.getInquiryDetail().getFeedback());
        mEdtinquirydate.setText(""+inquiryDetailsModel.getInquiryDetail().getInquiyDate());
        mEdtupcomingdate.setText(""+inquiryDetailsModel.getInquiryDetail().getUpcomingConfirmDate());
        mStrSlug = ""+inquiryDetailsModel.getInquiryDetail().getSlug();

        String[] arrReferenceBy = getResources().getStringArray(R.array.array_referenceBY);
        for (int i = 0; i < arrReferenceBy.length; i++) {
            if (arrReferenceBy[i].equalsIgnoreCase("" + inquiryDetailsModel.getInquiryDetail().getReference())) {
                mSprreference.setSelection(i);
                return;
            }
        }
    }

    private void setcoursedataadapter(final ArrayList<ModelClassForCourses.Course> mAlCourses) {

        courseDialog = new Dialog(AddinquiryActvity.this);
        courseDialog.setContentView(R.layout.dialog_courses_selection);

        findCourseDialogViews(courseDialog);

        mBtnAssgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEdtSelectCourses.setText("");
                mStrSelectedCourseId = "";

                for(int i = 0 ; i < mAlCourses.size() ; i++){
                    if(mAlCourses.get(i).isSelected()){
                        mArraylistCoursesSelected.add(mAlCourses.get(i));
                    }
                }

                for(int i = 0 ; i < mArraylistCoursesSelected.size() ; i++){
                    if(mEdtSelectCourses.toString().length()>0){
                        mEdtSelectCourses.append(mArraylistCoursesSelected.get(i).getName()+", ");
                    }else{
                        mEdtSelectCourses.setText(mArraylistCoursesSelected.get(i).getName());
                    }
                }

                courseDialog.dismiss();
            }
        });

        mBtncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(courseDialog!=null && courseDialog.isShowing()){
                    courseDialog.dismiss();
                }
            }
        });

        coursesAdapter = new CoursesAdapter(AddinquiryActvity.this,
                mAlCourses,this);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        mRvCourses.setLayoutManager(linearLayoutManager);
        mRvCourses.setAdapter(coursesAdapter);
        coursesAdapter.notifyDataSetChanged();

        mEdtSelectCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mArraylistCoursesSelected != null && mArraylistCoursesSelected.size() > 0) {
                    mArraylistCoursesSelected.clear();
                }
                courseDialog.show();
            }
        });

    }

    private void setMessagesToListview() {
        SmsCategoryAdapter smsCategoryAdapter = new SmsCategoryAdapter(AddinquiryActvity.this,mAlcategorywisemsgs);
        listview.setVisibility(View.VISIBLE);
        listview.setAdapter(smsCategoryAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String id = mAlcategorywisemsgs.get(position).getMessage();
                if(dialog!=null && dialog.isShowing()){
                    mEdtsmstypeselect.setText(""+id);
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
        ArrayAdapter<String> adapterStandard = new ArrayAdapter<>(AddinquiryActvity.this,
                R.layout.my_spinner_item,R.id.tvCustomSpinner, mAlsmscategorylist);
        mSprcategories.setAdapter(adapterStandard);
    }

    private void saveLoginPrefrences(ModelClassForCheckSmsDays modelClassForCheckSmsDays) {
        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN_PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SMS_DAYS,""+modelClassForCheckSmsDays.getDays().get(0));
        editor.commit();
    }

    private void setPartnersIdsToSpinner(ArrayList<ModelClassForPartners.Course> mArrlistpartnerids) {
        mALpartner = new ArrayList<>();
        if(mArrlistpartnerids != null && mArrlistpartnerids.size()>0){
            for(int i  = 0 ; i < mArrlistpartnerids.size() ; i++){
                mALpartner.add(""+mArrlistpartnerids.get(i).getName());
            }
        }

        mALpartner.add(0,"Select Partner");
        ArrayAdapter<String> adapterPartners = new ArrayAdapter<>(AddinquiryActvity.this,android.R.layout.simple_spinner_dropdown_item,mALpartner);
        mSprpartner.setAdapter(adapterPartners);
    }

    private void setSubjectsListAdapter(ArrayList<ModelClassForSubjects.Subject> mArrstandardlist) {
        mAlSubjects = new ArrayList<>();

        if(mArrstandardlist.size()>0 && mArrstandardlist!=null){
            for(int i = 0 ; i < mArrstandardlist.size() ; i++){
                mAlSubjects.add(""+mArrstandardlist.get(i).getName());
            }
        }

        mAlSubjects.add(0,"Select Subject");
        ArrayAdapter<String> adapterSubjects = new ArrayAdapter<>(AddinquiryActvity.this,
                android.R.layout.simple_spinner_dropdown_item,mAlSubjects);
        mSprsubjects.setAdapter(adapterSubjects);
    }

    private void setStudentStreamData(ArrayList<ModelClassForStraem.Stream> streamArrayList) {
        mAlStreams = new ArrayList<>();
        if (streamArrayList.size() > 0 && streamArrayList != null) {
            for (int i = 0; i < streamArrayList.size(); i++) {
                mAlStreams.add("" + streamArrayList.get(i).getName());
            }
        }
        if(mAlStreams.size()==0){
            mAlStreams.add(0, "Do not need to select stream");
        }else{
            mAlStreams.add(0,"Select Stream");
        }
        ArrayAdapter<String> adapterStream = new ArrayAdapter<>(AddinquiryActvity.this,
                android.R.layout.simple_spinner_dropdown_item, mAlStreams);
        mSprstreams.setAdapter(adapterStream);
    }

    private void setCourseStandardListAdapter(ArrayList<ModelClassForStandard.Standard> mArrListstandard) {
        mAlStandard = new ArrayList<>();
        if (mArrListstandard != null && mArrListstandard.size() > 0) {
            for (int i = 0; i < mArrListstandard.size(); i++) {
                mAlStandard.add("Standard " + mArrListstandard.get(i).getName());
            }
        }
        mAlStandard.add(0, "Select Standard");
        ArrayAdapter<String> adapterStandard = new ArrayAdapter<>(AddinquiryActvity.this,
                android.R.layout.simple_spinner_dropdown_item, mAlStandard);
        mSprstandards.setAdapter(adapterStandard);
    }

    @Override
    public void onOkHttpFailure(int requestId, int statusCode, String response, Throwable error) {

    }

    @Override
    public void onOkHttpFinish(int requestId) {

    }

    @Override
    public void onCourseCheckSelection(int position, boolean value) {
        mAlCourses.get(position).isSelected = value;
        coursesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
                onPhotoReturned(imageFiles);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(AddinquiryActvity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }

        });
    }
}