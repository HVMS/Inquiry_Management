package com.globalitians.inquiry.activities.SmsAndNotification.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.SmsAndNotification.Adapters.SmsCategoryAdapter;
import com.globalitians.inquiry.activities.SmsAndNotification.Models.CategoryWiseResponse;
import com.globalitians.inquiry.activities.SmsAndNotification.Models.Smscategoriesresponse;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.network.OkHttpInterface;
import com.globalitians.inquiry.activities.network.OkHttpRequest;
import com.globalitians.inquiry.activities.network.RequestParam;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.globalitians.inquiry.activities.Utility.CommonUtil.setFullScreenMode;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_SMS_CATEGORIES;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_SMS_CATRGORIES_WISE_MSGS;

public class PredefinedSmsActivity extends AppCompatActivity implements View.OnClickListener,OkHttpInterface {

    private Spinner spinner;
    private String mStrcategoryId="";
    private ListView listView;
    private Button button;
    private String mStrSelectedmsgs="";

    // api data only
    private ArrayList<String> mAlsmscategorylist;
    private Smscategoriesresponse smscategoriesresponse;

    private CategoryWiseResponse categoryWiseResponse;
    private ArrayList<CategoryWiseResponse.Message> mAlcategorywisemsgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode(PredefinedSmsActivity.this);
        setContentView(R.layout.activity_predefined_sms);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Predefined Messages");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.bv_primaryColor)));

        findViews();
        initarraylist();
        callApiToLoadCategoryData();
    }

    private void initarraylist() {
        mAlsmscategorylist = new ArrayList<>();
        mAlcategorywisemsgs = new ArrayList<>();
    }

    private void callApiToLoadCategoryData() {
        if(!CommonUtil.isInternetAvailable(PredefinedSmsActivity.this)){
            return;
        }

        new OkHttpRequest(PredefinedSmsActivity.this,
                OkHttpRequest.Method.GET,
                Constants.WS_SMS_CATEGORIES,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_SMS_CATEGORIES,
                false,this);
    }

    private void findViews() {
        spinner = findViewById(R.id.spinneer_predefined_sms);
        listView = findViewById(R.id.lv_predefined_sms_list);
        button = findViewById(R.id.select_btn);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    Toast.makeText(PredefinedSmsActivity.this, ""+smscategoriesresponse.getCategories().get(position-1).getName(), Toast.LENGTH_SHORT).show();
                    mStrcategoryId = ""+smscategoriesresponse.getCategories().get(position-1).getId();
                    callApiToLoadCategoryWiseSmsToListview();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void callApiToLoadCategoryWiseSmsToListview() {
        if(!CommonUtil.isInternetAvailable(PredefinedSmsActivity.this)){
            return;
        }

        new OkHttpRequest(PredefinedSmsActivity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_SMS_CATEGORIES_WISE_MESSAGES,
                RequestParam.categoryWiseFilter(""+mStrcategoryId),
                RequestParam.getNull(),
                CODE_SMS_CATRGORIES_WISE_MSGS,
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_btn:
                Intent intent = new Intent(PredefinedSmsActivity.this,SmsandnotificationActivity.class);
                intent.putExtra("msg",""+mStrSelectedmsgs);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onOkHttpStart(int requestId) {

    }

    @Override
    public void onOkHttpSuccess(int requestId, int statusCode, String response) {
        Log.e(">>>",""+response);
        if(response==null){
            return;
        }

        switch (requestId){
            case CODE_SMS_CATEGORIES:
                Log.e("Sms categories>>>",""+response);
                final Gson gson = new Gson();
                try{
                    smscategoriesresponse = gson.fromJson(response,Smscategoriesresponse.class);
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
                    categoryWiseResponse = msgs.fromJson(response,CategoryWiseResponse.class);
                    if(categoryWiseResponse.getStatus().equals(Constants.SUCCESS_CODE)){
                        mAlcategorywisemsgs = categoryWiseResponse.getMessages();
                        setMessagesToListview();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setMessagesToListview() {
        SmsCategoryAdapter smsCategoryAdapter = new SmsCategoryAdapter(PredefinedSmsActivity.this,mAlcategorywisemsgs);
        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(smsCategoryAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    for(int i = 0 ; i < listView.getChildCount() ; i++){
                        listView.getChildAt(position).setBackgroundColor(R.color.white);
                        mStrSelectedmsgs = String.valueOf(view);
                    }
            }
        });

    }

    private void setListners() {
        button.setOnClickListener(this);
    }

    private void setDatatospinnercategory(ArrayList<Smscategoriesresponse.Category> categories) {
        mAlsmscategorylist = new ArrayList<>();
        if(categories.size()>0 && categories!=null){
            for(int i = 0 ; i < categories.size() ; i++){
                mAlsmscategorylist.add(""+categories.get(i).getName());
            }
        }

        mAlsmscategorylist.add(0,"Select Category From Here");
        ArrayAdapter<String> adapterStandard = new ArrayAdapter<>(PredefinedSmsActivity.this,
                R.layout.my_spinner_item,R.id.tvCustomSpinner, mAlsmscategorylist);
        spinner.setAdapter(adapterStandard);
    }

    @Override
    public void onOkHttpFailure(int requestId, int statusCode, String response, Throwable error) {

    }

    @Override
    public void onOkHttpFinish(int requestId) {

    }
}
