package com.globalitians.inquiry.activities.Inquirydetails.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.activities.AddinquiryActvity;
import com.globalitians.inquiry.activities.Inquirydetails.adapter.CourseProfileListAdapter;
import com.globalitians.inquiry.activities.Inquirydetails.model.InquiryDetailsModel;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.network.OkHttpInterface;
import com.globalitians.inquiry.activities.network.OkHttpRequest;
import com.globalitians.inquiry.activities.network.RequestParam;
import com.google.gson.Gson;

import static com.globalitians.inquiry.activities.Utility.CommonUtil.setFullScreenMode;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_INQUIRY_DETAILS;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_INTENT_INQUIRY_ID;

public class InquiryDetailsActivity extends AppCompatActivity
    implements OkHttpInterface, View.OnClickListener {

    // new layout stuff
    private TextView mTxtfname;
    private TextView mTxtmobileno;
    private TextView mTxtfeedback;
    private TextView mTxtreference;
    private TextView mTxtstandard;
    private TextView mTxtstream;
    private TextView mTxtsubject;
    private TextView mTxtInquiryDate;
    private TextView mTxtUpcomingDate;
    private TextView mTxtmsgnotification;

    private FloatingActionButton fabInquirydetails;

    private InquiryDetailsModel inquiryDetailsModel;
    private String strIntentInquiryId = "";

    private RecyclerView mRvcourselist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode(InquiryDetailsActivity.this);
        setContentView(R.layout.activity_inquiry_details);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.bv_primaryColor)));
        getSupportActionBar().setTitle("Inquiry Details");

        findviews();
        getIntentFromAdapter();
        callApiToLoadInquiryDetails();
    }

    private void getIntentFromAdapter() {
        Intent intentInquiryId = getIntent();
        strIntentInquiryId = ""+intentInquiryId.getStringExtra((KEY_INTENT_INQUIRY_ID));
    }

    private void findviews() {
        mTxtfname = findViewById(R.id.edt_firstname);
        mTxtmobileno = findViewById(R.id.edt_mobileno);
        mTxtfeedback = findViewById(R.id.edt_feedback);
        mTxtreference = findViewById(R.id.edtReference);
        mTxtInquiryDate = findViewById(R.id.edt_inquiry_date);
        mTxtUpcomingDate = findViewById(R.id.edt_upcoming_date);
        mTxtstandard = findViewById(R.id.edtStandard);
        mTxtstream = findViewById(R.id.edtStream);
        mTxtsubject = findViewById(R.id.edt_subject);
        mTxtmsgnotification = findViewById(R.id.edt_msg_noti);
        mRvcourselist = findViewById(R.id.rv_course_list);
        fabInquirydetails = findViewById(R.id.FabChangeDetailsBtn);

        fabInquirydetails.setOnClickListener(this);
    }

    private void callApiToLoadInquiryDetails(){
        if(!CommonUtil.isInternetAvailable(InquiryDetailsActivity.this)){
            return;
        }

        new OkHttpRequest(InquiryDetailsActivity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_DETAILS,
                RequestParam.inquirydetails(strIntentInquiryId),
                RequestParam.getNull(),
                CODE_INQUIRY_DETAILS,
                false,this);
    }

    @Override
    public void onOkHttpStart(int requestId) {

    }

    @Override
    public void onOkHttpSuccess(int requestId, int statusCode, String response) {
        Log.e("Top Details >>>", "" + response);
        if (response == null) {
            return;
        }

        switch (requestId) {
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
        }
    }

    private void setInquiryData(InquiryDetailsModel inquiryDetailsModel) {
        getSupportActionBar().setTitle(""+inquiryDetailsModel.getInquiryDetail().getFname()
        +" "+inquiryDetailsModel.getInquiryDetail().getLname());

        CourseProfileListAdapter courseProfileListAdapter = new
                CourseProfileListAdapter(inquiryDetailsModel.getInquiryDetail().getCourses());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRvcourselist.setLayoutManager(linearLayoutManager);
        mRvcourselist.setAdapter(courseProfileListAdapter);

        mTxtfname.setText(""+inquiryDetailsModel.getInquiryDetail().getFname()+" "+inquiryDetailsModel.getInquiryDetail().getLname());
        mTxtmobileno.setText(""+inquiryDetailsModel.getInquiryDetail().getMobileno());
        mTxtInquiryDate.setText(""+inquiryDetailsModel.getInquiryDetail().getInquiyDate());
        mTxtUpcomingDate.setText(""+inquiryDetailsModel.getInquiryDetail().getUpcomingConfirmDate());
        mTxtreference.setText(""+inquiryDetailsModel.getInquiryDetail().getReference());

        if(CommonUtil.isNullString(inquiryDetailsModel.getInquiryDetail().getFeedback())){
            mTxtfeedback.setText("Not Filled In Inquiry From");
        }else{
            mTxtfeedback.setText(""+inquiryDetailsModel.getInquiryDetail().getFeedback());
        }

        if(CommonUtil.isNullString(inquiryDetailsModel.getInquiryDetail().getStandardName())){
            mTxtstandard.setText("Not Filled In Inquiry Form");
        }else{
            mTxtstandard.setText(""+inquiryDetailsModel.getInquiryDetail().getStandardName());
        }

        if(CommonUtil.isNullString(inquiryDetailsModel.getInquiryDetail().getStreamName())){
            mTxtstream.setText("Not Filled In Inquiry Form");
        }else{
            mTxtstream.setText(""+inquiryDetailsModel.getInquiryDetail().getStreamName());
        }

        if(CommonUtil.isNullString(inquiryDetailsModel.getInquiryDetail().getSubjectName())){
            mTxtsubject.setText("Not Filled In Inquiry Form");
        }else{
            mTxtsubject.setText(""+inquiryDetailsModel.getInquiryDetail().getSubjectName());
        }

        if(!CommonUtil.isNullString(""+inquiryDetailsModel.getInquiryDetail().getMessageNotification())){
            mTxtmsgnotification.setText("not yet calculated");
        }else{
            mTxtmsgnotification.setText(""+inquiryDetailsModel.getInquiryDetail().getMessageNotification());
        }
    }

    @Override
    public void onOkHttpFailure(int requestId, int statusCode, String response, Throwable error) {

    }

    @Override
    public void onOkHttpFinish(int requestId) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.FabChangeDetailsBtn:
                Intent intent = new Intent(this,AddinquiryActvity.class);
                intent.putExtra("sample_inquiry_id",""+strIntentInquiryId);
                startActivity(intent);
                break;
        }
    }
}
