package com.globalitians.inquiry.activities.Student.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForCourses;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForPartners;
import com.globalitians.inquiry.activities.Dashboard.Activities.DashboardActivity;
import com.globalitians.inquiry.activities.Inquirydetails.model.InquiryDetailsModel;
import com.globalitians.inquiry.activities.Student.model.AddNewCourseModelWhileAddingNewStudent;
import com.globalitians.inquiry.activities.Student.adapter.CourseListWhileAddingOrEditingStudentAdapter;
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
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_ADD_NEW_STUDENT;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_CHECK_USERNAME;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_INQUIRY_DETAILS;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_PARTNER_IDS;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_COURSES;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_EMPLOYEE_BRANCH_ID;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_INTENT_INQUIRY_ID;

public class StudentActivity extends AppCompatActivity
        implements View.OnClickListener,
        OkHttpInterface,
        CourseListWhileAddingOrEditingStudentAdapter.OnCourseListListener {

    private static final String PHOTOS_KEY = "easy_image_photos_list";
    private static final int CHOOSER_PERMISSIONS_REQUEST_CODE = 7459;
    private static final int CAMERA_REQUEST_CODE = 7500;
    private static final int CAMERA_VIDEO_REQUEST_CODE = 7501;
    private static final int GALLERY_REQUEST_CODE = 7502;
    private static final int DOCUMENTS_REQUEST_CODE = 7503;

    private CardView mCvone;
    private CardView mCvtwo;
    private CardView mCvthree;
    private CardView mCvfour;
    private CircleImageView ivStudentImagePick;
    private Button btnSubmit;

    private TextView mTxtperdetals;
    private TextView mTxtuserdetails;
    private TextView mTxtparentdetails;
    private TextView mTxtcoursedetails;
    private TextView mTxtaddnewcourse;
    private EditText edtBirthDate;
    private EditText edtFirstname;
    private EditText edtLastname;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfPassword;
    private EditText edtMobileno;
    private EditText edtParentname;
    private EditText edtParentMobileno;
    private EditText edtJoiningDate;
    private EditText edtInstallmentDate;
    private EditText edtusername;
    private EditText edtaddress;
    private DatePickerDialog mDatePickerDiaogBirthDate = null;
    private DatePickerDialog mDatePickerDialogJoinedDate = null;
    private DatePickerDialog mDatePickerDialogInstallmentDate = null;

    private View lineone;
    private View linetwo;
    private View linethree;
    private View linebtwtwothree;
    private View linebtwthreefour;
    private View linebtwonetwo;

    private Dialog dialogTotalCourseList = null;
    private Spinner spinnerCourseStatus;
    private Spinner spinnerCertificate;
    private Spinner spinnerBookMaterial;
    private Spinner spinnerCourseDuration;
    private Spinner spinnerBag;
    private Spinner spinnerPartner;
    private Spinner spinnerReference;
    private Spinner spinnerCourseselect;
    private Spinner spinnerGender;

    private LinearLayout linCourseDetails;
    private Dialog dialogStudentCourseDetails;
    private TextView tvCourses;
    private TextView tvCourseFees;
    private LinearLayout llFeesAmount;
    private TextView tvDecreaseFees;
    private EditText edtCoursefees;
    private TextView tvIncreaseFees;
    private TextView tvCourseDuration;

    private ModelClassForCourses modelClassForCourses;
    private ArrayList<String> mAlCourses;

    // all the string for the submitting the student form
    private InquiryDetailsModel inquiryDetailsModel;
    private String mStrfname="";
    private String mStrlname="";
    private String mStrmobileno="";
    private String mStremailid="";
    private String mStrbirthdate = "";
    private String mStrusername;
    private String mStrpwd="";
    private String mStrconfirmpwd="";
    private String mStrparentsname="";
    private String mStrparentsmobileno="";
    private String mStraddress="";
    private String mStrinstallmentdate="";
    private String mStrjoiningdate="";
    private String mStrselectedreference = "";
    private String mStrselectedpartner = "";
    private String mStrselectedgender="";
    private String strIntentInquiryId = "";
    private String mStrselectedcourse="";
    private String mStrselectedcourseduration="";
    private String mStrselectedcoursestatus="";
    private String mStrselectedcertificate="";
    private String mStrselectedbookmaterial="";
    private String mStrselectedbag="";
    private String strSelectedCourseId = "";
    private String strSelectedCourseImage = "";
    private String strSelectedCourseDuration = "";

    private ModelClassForPartners modelClassForPartners;
    private ArrayList<String> mALpartner;
    private String mStrselectedpartnerId="";
    private ArrayList<AddNewCourseModelWhileAddingNewStudent> mArrListStudentCourseDetails = new ArrayList<>();
    private AddNewCourseModelWhileAddingNewStudent modelCourseData;
    private CourseListWhileAddingOrEditingStudentAdapter mAdapterCourseList = null;
    private String strStudentIdToEdit = "";
    private TextView tvViewStudentCoursesAdded;

    private EasyImage easyImage;
    private ArrayList<File> photos = new ArrayList<File>();
    private File mFileimage=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode(StudentActivity.this);
        setContentView(R.layout.activity_student);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Student Form");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.bv_primaryColor)));

        findViews();
        getIdOfInquiry();
        initDates();
        callApiForPartnerDetails();
    }

    private void callApiForPartnerDetails() {
        if(!CommonUtil.isInternetAvailable(StudentActivity.this)){
            return;
        }

        new OkHttpRequest(StudentActivity.this,
                OkHttpRequest.Method.GET,
                Constants.WS_PARTNERS_LIST,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_PARTNER_IDS,
                false,this);
    }

    private void getIdOfInquiry() {
        Intent intentInquiryId = getIntent();
        strIntentInquiryId = ""+intentInquiryId.getStringExtra((KEY_INTENT_INQUIRY_ID));
        if(!CommonUtil.isNullString(strIntentInquiryId)){
            callApiToLoadInquiryDetails();
            Toast.makeText(this, "Api is called", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "The api is not called becz of null Inquiry Id", Toast.LENGTH_SHORT).show();
        }
    }

    private void callApiToLoadInquiryDetails() {
        if(!CommonUtil.isInternetAvailable(StudentActivity.this)){
            return;
        }

        new OkHttpRequest(StudentActivity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_DETAILS,
                RequestParam.inquirydetails(""+strIntentInquiryId),
                RequestParam.getNull(),
                CODE_INQUIRY_DETAILS,
                false,this);
    }

    private void initDates() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener birthdateListener;
        birthdateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if ((month + 1) < 10 && dayOfMonth < 10) {
                    edtBirthDate.setText("0" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if ((month + 1) < 10) {
                    edtBirthDate.setText("" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if (dayOfMonth < 10) {
                    edtBirthDate.setText("0" + dayOfMonth + "" + (month + 1) + "-" + year);
                } else {
                    edtBirthDate.setText("" + dayOfMonth + "" + (month + 1) + "-" + year);
                }
            }
        };

        DatePickerDialog.OnDateSetListener joinedDateListener;
        joinedDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if ((month + 1) < 10 && dayOfMonth < 10) {
                    edtJoiningDate.setText("0" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if ((month + 1) < 10) {
                    edtJoiningDate.setText("" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if (dayOfMonth < 10) {
                    edtJoiningDate.setText("0" + dayOfMonth + "" + (month + 1) + "-" + year);
                } else {
                    edtJoiningDate.setText("" + dayOfMonth + "" + (month + 1) + "-" + year);
                }
            }
        };

        DatePickerDialog.OnDateSetListener installmentDateListener;
        installmentDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if ((month + 1) < 10 && dayOfMonth < 10) {
                    edtInstallmentDate.setText("0" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if ((month + 1) < 10) {
                    edtInstallmentDate.setText("" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if (dayOfMonth < 10) {
                    edtInstallmentDate.setText("0" + dayOfMonth + "" + (month + 1) + "-" + year);
                } else {
                    edtInstallmentDate.setText("" + dayOfMonth + "" + (month + 1) + "-" + year);
                }
            }
        };

        //initializing date filter date picker dialog
        mDatePickerDiaogBirthDate = new DatePickerDialog(
                StudentActivity.this,R.style.DialogTheme, birthdateListener, year, month, day);
        mDatePickerDiaogBirthDate.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);

        //initializing date filter date picker dialog
        mDatePickerDialogJoinedDate = new DatePickerDialog(
                StudentActivity.this, R.style.DialogTheme,joinedDateListener, year, month, day);
        //mDatePickerDialogJoinedDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        //initializing date filter date picker dialog
        mDatePickerDialogInstallmentDate = new DatePickerDialog(
                StudentActivity.this, R.style.DialogTheme,installmentDateListener, year, month, day);
        //mDatePickerDialogInstallmentDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    private void findViews() {
        mCvone = findViewById(R.id.card_view);
        mCvtwo = findViewById(R.id.card_view_second);
        mCvthree = findViewById(R.id.card_view_third);
        mCvfour = findViewById(R.id.card_view_fourth);
        ivStudentImagePick = findViewById(R.id.student_image);
        btnSubmit = findViewById(R.id.btn_student_add);

        mTxtperdetals = findViewById(R.id.tv_personal_deetails);
        mTxtuserdetails = findViewById(R.id.tv_username_details);
        mTxtparentdetails = findViewById(R.id.tv_parent_details);
        mTxtcoursedetails = findViewById(R.id.tv_course_details);
        mTxtaddnewcourse = findViewById(R.id.tv_add_new);
        tvViewStudentCoursesAdded = findViewById(R.id.tv_view_student_courses);

        spinnerPartner = findViewById(R.id.spinner_partner_select);
        spinnerGender = findViewById(R.id.spinner_gender);
        spinnerReference = findViewById(R.id.spinner_referenceBy);

        lineone = findViewById(R.id.lineone);
        linetwo = findViewById(R.id.linetwo);
        linethree = findViewById(R.id.linethree);
        linebtwtwothree = findViewById(R.id.line_btw_two_three);
        linebtwthreefour = findViewById(R.id.line_btw_three_four);
        linebtwonetwo = findViewById(R.id.line_btw_one_two);

        edtFirstname = (EditText) findViewById(R.id.edt_firstname);
        edtLastname = (EditText) findViewById(R.id.edt_lastname);
        edtEmail = (EditText) findViewById(R.id.edt_email_id);
        edtusername = findViewById(R.id.edt_username);
        edtaddress = findViewById(R.id.edt_parent_address);
        edtPassword = (EditText) findViewById(R.id.edt_pwd);
        edtConfPassword = (EditText) findViewById(R.id.edt_confirm_pwd);
        edtMobileno = (EditText) findViewById(R.id.edt_mobile_number);
        edtBirthDate = (EditText) findViewById(R.id.edt_birth_date);
        edtParentname = (EditText) findViewById(R.id.edt_parentname);
        edtParentMobileno = (EditText) findViewById(R.id.edt_parent_mobileno);
        edtJoiningDate = (EditText) findViewById(R.id.edt_joining_date);
        edtInstallmentDate = (EditText) findViewById(R.id.edt_installment_date);

        edtBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDiaogBirthDate.show();
            }
        });

        edtJoiningDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialogJoinedDate.show();
            }
        });

        edtInstallmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialogInstallmentDate.show();
            }
        });

        mTxtperdetals.setOnClickListener(this);
        mTxtuserdetails.setOnClickListener(this);
        mTxtparentdetails.setOnClickListener(this);
        mTxtcoursedetails.setOnClickListener(this);
        mTxtaddnewcourse.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        tvViewStudentCoursesAdded.setOnClickListener(this);
        ivStudentImagePick.setOnClickListener(this);
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
            case R.id.tv_personal_deetails:
                mCvone.setVisibility(View.VISIBLE);
                mCvtwo.setVisibility(View.GONE);
                mCvthree.setVisibility(View.GONE);
                mCvfour.setVisibility(View.GONE);

                // line visibility
                lineone.setVisibility(View.VISIBLE);
                linetwo.setVisibility(View.GONE);
                linethree.setVisibility(View.GONE);

                linebtwonetwo.setVisibility(View.GONE);
                linebtwtwothree.setVisibility(View.VISIBLE);
                linebtwthreefour.setVisibility(View.VISIBLE);

                break;
            case R.id.tv_username_details:
                mCvone.setVisibility(View.GONE);
                mCvtwo.setVisibility(View.VISIBLE);
                mCvthree.setVisibility(View.GONE);
                mCvfour.setVisibility(View.GONE);

                // line visibility
                lineone.setVisibility(View.GONE);
                linetwo.setVisibility(View.VISIBLE);
                linethree.setVisibility(View.GONE);

                linebtwonetwo.setVisibility(View.VISIBLE);
                linebtwtwothree.setVisibility(View.GONE);
                linebtwthreefour.setVisibility(View.VISIBLE);

                break;
            case R.id.tv_parent_details:
                mCvone.setVisibility(View.GONE);
                mCvtwo.setVisibility(View.GONE);
                mCvthree.setVisibility(View.VISIBLE);
                mCvfour.setVisibility(View.GONE);

                // line visibility
                lineone.setVisibility(View.GONE);
                linetwo.setVisibility(View.GONE);
                linethree.setVisibility(View.VISIBLE);

                linebtwonetwo.setVisibility(View.VISIBLE);
                linebtwtwothree.setVisibility(View.VISIBLE);
                linebtwthreefour.setVisibility(View.GONE);

                break;
            case R.id.tv_course_details:
                mCvone.setVisibility(View.GONE);
                mCvtwo.setVisibility(View.GONE);
                mCvthree.setVisibility(View.GONE);
                mCvfour.setVisibility(View.VISIBLE);

                // line visibility
                lineone.setVisibility(View.GONE);
                linetwo.setVisibility(View.GONE);
                linethree.setVisibility(View.GONE);

                linebtwonetwo.setVisibility(View.VISIBLE);
                linebtwtwothree.setVisibility(View.VISIBLE);
                linebtwthreefour.setVisibility(View.VISIBLE);

                break;
            case R.id.tv_add_new:
                openCourseDialog(null, -1);
                break;
            case R.id.student_image:
                String[] necessaryPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
                requestPermissionsCompat(necessaryPermissions, CHOOSER_PERMISSIONS_REQUEST_CODE);
                break;
            case R.id.tv_view_student_courses:
                if(mArrListStudentCourseDetails.size() > 0 && mArrListStudentCourseDetails!=null){
                    showAddedCourseListDialog();
                }else{
                    playSoundForAttendance("No details added yet. Please, Add Course details first.", StudentActivity.this);
                    Toast.makeText(this, "No Details added yet. Add Course Details first. ", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_student_add:
                validateAndSubmitStudent();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CHOOSER_PERMISSIONS_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage.openChooserWithGallery(StudentActivity.this,"Choose Photo from",0);
        }else if (requestCode == CAMERA_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage.openCamera(StudentActivity.this,0);
        }else if (requestCode == GALLERY_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage.openGallery(StudentActivity.this,0);
        }
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
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(StudentActivity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }

        });
    }

    private void onPhotoReturned(List<File> imageFiles) {
        photos.addAll(imageFiles);
        mFileimage = photos.get(photos.size()-1);
        ivStudentImagePick.setImageURI(Uri.fromFile(photos.get(photos.size()-1)));
    }

    private void requestPermissionsCompat(String[] necessaryPermissions, int chooserPermissionsRequestCode) {
        ActivityCompat.requestPermissions(StudentActivity.this, necessaryPermissions, chooserPermissionsRequestCode);
    }

    private boolean arePermissionsGranted(String[] necessaryPermissions) {
        for (String permission : necessaryPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    private void showAddedCourseListDialog() {
        final Dialog dialogCourseAddedList = new Dialog(StudentActivity.this);
        dialogCourseAddedList.setContentView(R.layout.dialog_view_student_courses_list);
        dialogCourseAddedList.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ListView mLvCourses = dialogCourseAddedList.findViewById(R.id.lv_courses);
        TextView tvOkay = dialogCourseAddedList.findViewById(R.id.tv_okay);

        mAdapterCourseList = new CourseListWhileAddingOrEditingStudentAdapter(StudentActivity.this, mArrListStudentCourseDetails, this, "add_student");
        mLvCourses.setAdapter(mAdapterCourseList);

        tvOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogCourseAddedList != null && dialogCourseAddedList.isShowing()) {
                    dialogCourseAddedList.dismiss();
                }
            }
        });

        if (dialogCourseAddedList != null && !dialogCourseAddedList.isShowing()) {
            dialogCourseAddedList.show();
        }
    }

    private void validateAndSubmitStudent() {

        mStrfname = ""+edtFirstname.getText().toString().trim();
        mStrlname = ""+edtLastname.getText().toString().trim();
        mStrmobileno = ""+edtMobileno.getText().toString().trim();
        mStremailid = ""+edtEmail.getText().toString().trim();
        mStrbirthdate = ""+edtBirthDate.getText().toString().trim();

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStrselectedgender = ""+spinnerGender.getItemAtPosition(position).toString();
                Toast.makeText(StudentActivity.this, ""+mStrselectedgender, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mStrusername = ""+edtusername.getText().toString().trim();
        mStrpwd = ""+edtPassword.getText().toString().trim();
        mStrconfirmpwd = ""+edtConfPassword.getText().toString().trim();

        mStrparentsname = ""+edtParentname.getText().toString().trim();
        mStrparentsmobileno = ""+edtParentMobileno.getText().toString().trim();
        mStraddress = ""+edtaddress.getText().toString().trim();

        mStrinstallmentdate = ""+edtInstallmentDate.getText().toString().trim();
        mStrjoiningdate = ""+edtJoiningDate.getText().toString().trim();

        spinnerReference.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    mStrselectedreference = ""+spinnerReference.getItemAtPosition(position).toString();
                    Toast.makeText(StudentActivity.this, ""+mStrselectedreference, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPartner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    mStrselectedpartnerId = ""+modelClassForPartners.getCourses().get(position-1).getId();
                    mStrselectedpartner = ""+modelClassForPartners.getCourses().get(position-1).getPartnerUserid();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!CommonUtil.isInternetAvailable(StudentActivity.this)) {
            return;
        }

        if(CommonUtil.isNullString(""+mStrfname)){
            playSoundForAttendance("Please Enter First Name",StudentActivity.this);
            Toast.makeText(this, "Please Enter First Name", Toast.LENGTH_SHORT).show();
        }else if(mStrfname.length()<2){
            playSoundForAttendance("First name should be more than two characters",StudentActivity.this);
            Toast.makeText(this, "First name should be more than two characters", Toast.LENGTH_SHORT).show();
        }else if(CommonUtil.isNullString(""+mStrlname)){
            playSoundForAttendance("Please Enter Last Name",StudentActivity.this);
            Toast.makeText(this, "Please Enter Last Name", Toast.LENGTH_SHORT).show();
        }else if(mStrlname.length()<2){
            playSoundForAttendance("Last name should be more than two characters",StudentActivity.this);
            Toast.makeText(this, "Last name should be more than two characters", Toast.LENGTH_SHORT).show();
        }else if(CommonUtil.isNullString(""+mStrmobileno)){
            playSoundForAttendance("Please Enter mobile number",StudentActivity.this);
            Toast.makeText(this, "Please Enter mobile number", Toast.LENGTH_SHORT).show();
        }else if(mStrmobileno.length()<10){
            playSoundForAttendance("Mobile number must contain 10 digits",StudentActivity.this);
            Toast.makeText(this, "Mobile number must contain 10 digits", Toast.LENGTH_SHORT).show();
        }else if(CommonUtil.isNullString(""+mStremailid)){
            playSoundForAttendance("Please Enter Email Id",StudentActivity.this);
            Toast.makeText(this, "Please Enter Email Id", Toast.LENGTH_SHORT).show();
        }else if(CommonUtil.isNullString(""+mStrbirthdate)){
            playSoundForAttendance("Please Select Birth Date",StudentActivity.this);
            Toast.makeText(this, "Please Select Birth Date", Toast.LENGTH_SHORT).show();
        }else if(CommonUtil.isNullString(""+spinnerGender.getSelectedItem().toString())){
            playSoundForAttendance("Please Select Gender",StudentActivity.this);
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
        }else if(CommonUtil.isNullString(""+mStrusername)){
            playSoundForAttendance("Please Enter Username",StudentActivity.this);
            Toast.makeText(this, "Please Enter Username", Toast.LENGTH_SHORT).show();
        }else if(CommonUtil.isNullString(""+mStrpwd)){
            playSoundForAttendance("Please Enter Password",StudentActivity.this);
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }else if(CommonUtil.isNullString(""+mStrconfirmpwd)){
            playSoundForAttendance("Please Enter Confirm Password",StudentActivity.this);
            Toast.makeText(this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
        }else if(!mStrpwd.equals(""+mStrconfirmpwd)){
            playSoundForAttendance("Password and Confirm Password do not match try to match",StudentActivity.this);
            Toast.makeText(this, "Password and Confirm Password do not match try to match", Toast.LENGTH_SHORT).show();
        }else if(CommonUtil.isNullString(""+mStrparentsname)){
            playSoundForAttendance("Please Enter Parent's name",StudentActivity.this);
            Toast.makeText(this, "Please Enter Parent's name", Toast.LENGTH_SHORT).show();
        }else if(CommonUtil.isNullString(""+mStrparentsmobileno)){
            playSoundForAttendance("Please Enter Parent's Mobile Number",StudentActivity.this);
            Toast.makeText(this, "Please Enter Parent's Mobile Number", Toast.LENGTH_SHORT).show();
        }else if(mStrparentsmobileno.length()<10){
            playSoundForAttendance("Parent's mobile number must contain 10 digits",StudentActivity.this);
            Toast.makeText(this, "Parent's mobile number must contain 10 digits", Toast.LENGTH_SHORT).show();
        }else if(CommonUtil.isNullString(""+mStrinstallmentdate)){
            playSoundForAttendance("Please Enter Installment Data",StudentActivity.this);
            Toast.makeText(this, "Please Enter Installment Data", Toast.LENGTH_SHORT).show();
        }else if(CommonUtil.isNullString(""+mStrjoiningdate)){
            playSoundForAttendance("Please Enter Joining Date",StudentActivity.this);
            Toast.makeText(this, "Please Enter Joining Date", Toast.LENGTH_SHORT).show();
        }else if(CommonUtil.isNullString(""+mStrselectedreference)){
            playSoundForAttendance("Please Select Reference",StudentActivity.this);
            Toast.makeText(this, "Please Select Reference", Toast.LENGTH_SHORT).show();
        }else if(CommonUtil.isNullString(""+mStrselectedpartner)){
            playSoundForAttendance("Please Select Partner",StudentActivity.this);
            Toast.makeText(this, "Please Select Partner", Toast.LENGTH_SHORT).show();
        }else{
            if(mArrListStudentCourseDetails!=null && mArrListStudentCourseDetails.size()>0){
                    /*
                        same service is used for
                    */
                    callApiForCheckUsernameAvailabeOrnot();
                    callApitToConvertStudent();
            }else{
                playSoundForAttendance("Please Enter Course details for Student.", StudentActivity.this);
                Toast.makeText(this, "Please Enter Course details for Student.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    private void callApiForCheckUsernameAvailabeOrnot() {
        if(!CommonUtil.isInternetAvailable(StudentActivity.this)){
            return;
        }

        new OkHttpRequest(StudentActivity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_CHECK_USERNAME,
                RequestParam.checkUsername(""+edtusername.getText().toString().trim()),
                RequestParam.getNull(),
                CODE_CHECK_USERNAME,
                false,this);
    }

    private void callApitToConvertStudent() {
        if (!CommonUtil.isInternetAvailable(StudentActivity.this)) {
            return;
        }

        if(mFileimage!=null){
            ArrayList<File> arrayList = new ArrayList<>();
            arrayList.add(mFileimage);

            new OkHttpRequest(StudentActivity.this,
                    OkHttpRequest.Method.POST,
                    Constants.WS_ADD_STUDENT,
                    RequestParam.addNewStudent(""+strStudentIdToEdit,
                            ""+edtFirstname.getText().toString().trim(),
                            ""+edtLastname.getText().toString().trim(),
                            ""+edtEmail.getText().toString().trim(),
                            ""+edtPassword.getText().toString().trim(),
                            ""+edtMobileno.getText().toString().trim(),
                            ""+spinnerGender.getSelectedItem().toString(),
                            ""+edtusername.getText().toString().trim(),
                            ""+edtBirthDate.getText().toString().trim(),
                            ""+getSharedPrefrencesInstance(StudentActivity.this).getString(KEY_EMPLOYEE_BRANCH_ID,""),
                            ""+edtParentname.getText().toString().trim(),
                            ""+edtParentMobileno.getText().toString().trim(),
                            ""+mStrselectedreference,
                            ""+edtJoiningDate.getText().toString().trim(),
                            ""+edtaddress.getText().toString().trim(),
                            mArrListStudentCourseDetails,
                            ""+mStrselectedpartnerId,
                            ""+strIntentInquiryId),
                    RequestParam.getNull(),
                    RequestParam.addNewStudentImageParam(arrayList),
                    CODE_ADD_NEW_STUDENT,
                    true,this);

        }else{
            Toast.makeText(this, "file is not uploaded yet", Toast.LENGTH_SHORT).show();
        }
    }

    private void openCourseDialog(final AddNewCourseModelWhileAddingNewStudent modelEditCourse, final int clickedPosition) {

        if (dialogTotalCourseList != null && dialogTotalCourseList.isShowing()) {
            //dismissing dialog in case of EDIT
            dialogTotalCourseList.dismiss();
        }

        dialogStudentCourseDetails = new Dialog(StudentActivity.this);
        dialogStudentCourseDetails.setContentView(R.layout.add_new_student_layout);

        spinnerCourseStatus = (Spinner) dialogStudentCourseDetails.findViewById(R.id.spinner_course_status);
        spinnerCertificate = (Spinner) dialogStudentCourseDetails.findViewById(R.id.spinner_certificate);
        spinnerBookMaterial = (Spinner) dialogStudentCourseDetails.findViewById(R.id.spinner_book_material);
        spinnerBag = (Spinner) dialogStudentCourseDetails.findViewById(R.id.spinner_bag);
        spinnerCourseDuration = dialogStudentCourseDetails.findViewById(R.id.spinner_course_duration);
        spinnerCourseselect = dialogStudentCourseDetails.findViewById(R.id.spinner_course_select);

        callApitoloadcoursedata();

        linCourseDetails = (LinearLayout) dialogStudentCourseDetails.findViewById(R.id.lin_course_details);
        tvCourses = (TextView) dialogStudentCourseDetails.findViewById(R.id.tv_courses);
        tvCourseFees = (TextView) dialogStudentCourseDetails.findViewById(R.id.tv_course_fees);
        llFeesAmount = (LinearLayout) dialogStudentCourseDetails.findViewById(R.id.ll_fees_amount);
        tvDecreaseFees = (TextView) dialogStudentCourseDetails.findViewById(R.id.tv_decrease_fees);
        edtCoursefees = (EditText) dialogStudentCourseDetails.findViewById(R.id.edtCoursefees);
        tvIncreaseFees = (TextView) dialogStudentCourseDetails.findViewById(R.id.tv_increase_fees);
        tvCourseDuration = (TextView) dialogStudentCourseDetails.findViewById(R.id.tv_course_duration);

        TextView tvSubmit = (TextView) dialogStudentCourseDetails.findViewById(R.id.tv_submit);
        TextView tvCancel = (TextView) dialogStudentCourseDetails.findViewById(R.id.tv_cancel);

        tvIncreaseFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtil.isNullString(edtCoursefees.getText().toString().trim())) {
                    edtCoursefees.setText("0");
                }

                int fees = Integer.parseInt(edtCoursefees.getText().toString().trim());

                if (fees != 99999) {
                    fees += 500;
                    edtCoursefees.setText("" + fees);
                }
            }
        });

        tvDecreaseFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtil.isNullString(edtCoursefees.getText().toString().trim())) {
                    edtCoursefees.setText("00");
                }

                int fees2 = Integer.parseInt(edtCoursefees.getText().toString().trim());
                if (fees2 > 499) {
                    fees2 -= 500;
                    edtCoursefees.setText("" + fees2);
                }

            }
        });

        dialogStudentCourseDetails.show();

        spinnerCourseselect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    strSelectedCourseId = ""+modelClassForCourses.getCourses().get(position-1).getId();
                    mStrselectedcourse = ""+modelClassForCourses.getCourses().get(position-1).getName();
                    /*Toast.makeText(StudentActivity.this, ""+mStrselectedcourse, Toast.LENGTH_SHORT).show();*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCourseDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStrselectedcourseduration = ""+spinnerCourseDuration.getItemAtPosition(position).toString();
                /*Toast.makeText(StudentActivity.this, ""+mStrselectedcourseduration, Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCourseStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStrselectedcoursestatus = ""+spinnerCourseStatus.getItemAtPosition(position).toString();
                /*Toast.makeText(StudentActivity.this, ""+mStrselectedcoursestatus, Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerBookMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStrselectedbookmaterial = ""+spinnerBookMaterial.getItemAtPosition(position).toString();
                /*Toast.makeText(StudentActivity.this, ""+mStrselectedbookmaterial, Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCertificate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStrselectedcertificate = ""+spinnerCertificate.getItemAtPosition(position).toString();
                /*Toast.makeText(StudentActivity.this, ""+mStrselectedcertificate, Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerBag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStrselectedbag = ""+spinnerBag.getItemAtPosition(position).toString();
                /*Toast.makeText(StudentActivity.this, ""+mStrselectedbag, Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CommonUtil.isNullString(""+mStrselectedcourse)){
                    playSoundForAttendance("Please Select Course",StudentActivity.this);
                    Toast.makeText(getApplicationContext(), "Please Select Course", Toast.LENGTH_SHORT).show();
                }else if(CommonUtil.isNullString(""+mStrselectedcourseduration)){
                    playSoundForAttendance("Please Select Course Duration",StudentActivity.this);
                    Toast.makeText(getApplicationContext(), "Please Select Course Duration", Toast.LENGTH_SHORT).show();
                }else if(CommonUtil.isNullString(""+mStrselectedcoursestatus)){
                    playSoundForAttendance("Please Select Course Status",StudentActivity.this);
                    Toast.makeText(getApplicationContext(), "Please Select Course Status", Toast.LENGTH_SHORT).show();
                }else if(CommonUtil.isNullString(""+mStrselectedbookmaterial)){
                    playSoundForAttendance("Please Select book material",StudentActivity.this);
                    Toast.makeText(getApplicationContext(), "Please Select book material", Toast.LENGTH_SHORT).show();
                }else if(CommonUtil.isNullString(""+mStrselectedcertificate)){
                    playSoundForAttendance("Please Select Certificate",StudentActivity.this);
                    Toast.makeText(getApplicationContext(), "Please Select Certificate", Toast.LENGTH_SHORT).show();
                }else if(CommonUtil.isNullString(""+mStrselectedbag)){
                    playSoundForAttendance("Please Select Bag is given or not",StudentActivity.this);
                    Toast.makeText(getApplicationContext(), "Please Select Bag is given or not", Toast.LENGTH_SHORT).show();
                }else if(CommonUtil.isNullString(""+edtCoursefees.getText().toString().trim())){
                    playSoundForAttendance("Please Enter Course Fees",StudentActivity.this);
                    Toast.makeText(getApplicationContext(), "Please Enter Course Fees", Toast.LENGTH_SHORT).show();
                }else{
                    if(modelEditCourse != null){

                        modelCourseData = new AddNewCourseModelWhileAddingNewStudent();
                        Log.e(">>> BAG",""+spinnerBag.getSelectedItem().toString());
                        Log.e(">>> Book Material",""+spinnerBookMaterial.getSelectedItem().toString());

                        modelCourseData.setStrBag(spinnerBag.getSelectedItem().toString());
                        modelCourseData.setStrBookMaterial(spinnerBookMaterial.getSelectedItem().toString());
                        modelCourseData.setStrCertificate(spinnerCertificate.getSelectedItem().toString());
                        modelCourseData.setStrFees(edtCoursefees.getText().toString());

                        if (CommonUtil.isNullString(strSelectedCourseId)) {
                            modelCourseData.setStrCourseId(modelEditCourse.getStrCourseId());
                            modelCourseData.setStrCourseImage(modelEditCourse.getStrCourseImage());
                        } else {
                            modelCourseData.setStrCourseId(strSelectedCourseId);
                            modelCourseData.setStrCourseImage(strSelectedCourseImage);
                        }

                        modelCourseData.setStrCourseName(spinnerCourseselect.getSelectedItem().toString());
                        modelCourseData.setStrDuration(spinnerCourseDuration.getSelectedItem().toString());
                        modelCourseData.setStrCourseStatus(spinnerCourseStatus.getSelectedItem().toString());

                        mArrListStudentCourseDetails.set(clickedPosition,modelCourseData);
                        if(dialogStudentCourseDetails!=null){
                            dialogStudentCourseDetails.dismiss();
                        }

                        if (dialogStudentCourseDetails != null && dialogStudentCourseDetails.isShowing()) {
                            dialogStudentCourseDetails.dismiss();
                        }

                        strSelectedCourseId = "";
                        strSelectedCourseImage = "";
                    }else{
                        //ADD
                        int isFoundAdded = 0;
                        if (mArrListStudentCourseDetails != null && mArrListStudentCourseDetails.size() > 0) {
                            for (int count = 0; count < mArrListStudentCourseDetails.size(); count++) {
                                if (mArrListStudentCourseDetails.get(count).getStrCourseId().equals(strSelectedCourseId)) {
                                    isFoundAdded = 1;
                                }
                            }
                            if (isFoundAdded == 0) {
                                addCourseData();
                                if (dialogStudentCourseDetails != null && dialogStudentCourseDetails.isShowing()) {
                                    dialogStudentCourseDetails.dismiss();
                                }
                                strSelectedCourseDuration = null;
                            } else {
                                //Data exists already.
                                Toast.makeText(StudentActivity.this, "" + getResources().getString(R.string.toast_course_already_added), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            //First time
                            addCourseData();
                            if (dialogStudentCourseDetails != null && dialogStudentCourseDetails.isShowing()) {
                                dialogStudentCourseDetails.dismiss();
                            }
                            strSelectedCourseDuration = null;
                        }
                    }
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogStudentCourseDetails != null && dialogStudentCourseDetails.isShowing()) {
                    dialogStudentCourseDetails.dismiss();
                }
            }
        });

        if (dialogStudentCourseDetails != null && !dialogStudentCourseDetails.isShowing()) {
            dialogStudentCourseDetails.show();
        }
    }


    private void addCourseData(){
        modelCourseData = new AddNewCourseModelWhileAddingNewStudent();
        modelCourseData.setStrBag(spinnerBag.getSelectedItem().toString());
        modelCourseData.setStrBookMaterial(spinnerBookMaterial.getSelectedItem().toString());
        modelCourseData.setStrCertificate(spinnerCertificate.getSelectedItem().toString());
        modelCourseData.setStrFees(edtCoursefees.getText().toString());
        modelCourseData.setStrCourseId(strSelectedCourseId);
        modelCourseData.setStrCourseName(mStrselectedcourse);
        modelCourseData.setStrDuration(mStrselectedcourseduration);
        modelCourseData.setStrCourseImage(strSelectedCourseImage);
        modelCourseData.setStrCourseStatus(spinnerCourseStatus.getSelectedItem().toString());

        mArrListStudentCourseDetails.add(modelCourseData);
    }

    private void callApitoloadcoursedata() {
        if (!CommonUtil.isInternetAvailable(StudentActivity.this)) {
            return;
        }

        new OkHttpRequest(StudentActivity.this,
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
        Log.e(">>>", "" + response);

        if (response == null) {
            return;
        }

        switch (requestId){
            case CODE_STUDENT_COURSES:
                Log.e("Courses >>", "" + response);
                final Gson studentCoursesList = new Gson();
                try {
                    modelClassForCourses = studentCoursesList.fromJson(response, ModelClassForCourses.class);
                    if (modelClassForCourses.getCourses() != null &&
                            modelClassForCourses.getCourses().size() > 0) {
                        setCourseDatalistAdapter(modelClassForCourses.getCourses());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CODE_INQUIRY_DETAILS:
                Log.e("Innner Details >>>", "" + response);
                final Gson gson = new Gson();
                try {
                    inquiryDetailsModel = gson.fromJson(response, InquiryDetailsModel.class);
                    if (inquiryDetailsModel.getStatus().equals(Constants.SUCCESS_CODE)) {
                        setInquiryData(inquiryDetailsModel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CODE_PARTNER_IDS:
                Log.e("Partners ids >>> ",""+response);
                final Gson partner_ids = new Gson();
                try{
                    modelClassForPartners = partner_ids.fromJson(response, ModelClassForPartners.class);
                    if(modelClassForPartners.getCourses() != null && modelClassForPartners.getCourses().size() > 0){
                        setPartnersIdsToSpinner(modelClassForPartners.getCourses());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case CODE_ADD_NEW_STUDENT:
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject != null && jsonObject.has("status")){
                        if(jsonObject.getString("status").equals("success")){
                            Toast.makeText(this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case CODE_CHECK_USERNAME:
                Log.e(">>>username",""+response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject!=null && jsonObject.has("status")){
                        if(jsonObject.getString("status").equals("username not available")){
                            Toast.makeText(this, "Try to enter Another Username", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setPartnersIdsToSpinner(ArrayList<ModelClassForPartners.Course> mArrlistpartnerids) {
        mALpartner = new ArrayList<>();
        if(mArrlistpartnerids != null && mArrlistpartnerids.size()>0){
            for(int i  = 0 ; i < mArrlistpartnerids.size() ; i++){
                mALpartner.add(""+mArrlistpartnerids.get(i).getName());
            }
        }

        mALpartner.add(0,"Select Partner");
        ArrayAdapter<String> adapterPartners = new ArrayAdapter<>(StudentActivity.this,android.R.layout.simple_spinner_dropdown_item,mALpartner);
        spinnerPartner.setAdapter(adapterPartners);
    }

    private void setInquiryData(InquiryDetailsModel inquiryDetailsModel) {
        Log.e("fname",""+inquiryDetailsModel.getInquiryDetail().getFname());
        edtFirstname.setText(""+inquiryDetailsModel.getInquiryDetail().getFname());
        Log.e("lname",""+inquiryDetailsModel.getInquiryDetail().getLname());
        edtLastname.setText(""+inquiryDetailsModel.getInquiryDetail().getLname());
        Log.e("mobileno",""+inquiryDetailsModel.getInquiryDetail().getMobileno());
        edtMobileno.setText(""+inquiryDetailsModel.getInquiryDetail().getMobileno());
        Log.e("inquirydate",""+inquiryDetailsModel.getInquiryDetail().getInquiyDate());
        edtJoiningDate.setText(""+inquiryDetailsModel.getInquiryDetail().getInquiyDate());
    }

    private void setCourseDatalistAdapter(ArrayList<ModelClassForCourses.Course> courses) {
        mAlCourses = new ArrayList<String>();
        if(courses != null && courses.size()>0){
            for(int i  = 0 ; i < courses.size() ; i++){
                mAlCourses.add(""+courses.get(i).getName());
            }
        }

        mAlCourses.add(0,"Select Course");
        ArrayAdapter<String> adapterCourses = new ArrayAdapter<>(StudentActivity.this,android.R.layout.simple_spinner_dropdown_item,mAlCourses);
        spinnerCourseselect.setAdapter(adapterCourses);
    }

    @Override
    public void onOkHttpFailure(int requestId, int statusCode, String response, Throwable error) {

    }

    @Override
    public void onOkHttpFinish(int requestId) {

    }

    @Override
    public void onCourseRawClick(int position) {
        openCourseDialog(mArrListStudentCourseDetails.get(position), position);
    }

    @Override
    public void onDeleteCourse(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
        builder.setMessage("Are you Sure ?");
        builder.setPositiveButton(R.string.YES, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (mArrListStudentCourseDetails != null && mArrListStudentCourseDetails.size() > 0) {
                    mArrListStudentCourseDetails.remove(position);
                    mAdapterCourseList.notifyDataSetChanged();
                }
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //nothing
            }
            // Create the AlertDialog object and return it
        });

        builder.show();
    }
}
