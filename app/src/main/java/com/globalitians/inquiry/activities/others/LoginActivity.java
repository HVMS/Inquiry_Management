package com.globalitians.inquiry.activities.others;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.Dashboard.Activities.DashboardActivity;
import com.globalitians.inquiry.activities.another.EmployeeLoginModel;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.network.OkHttpInterface;
import com.globalitians.inquiry.activities.network.OkHttpRequest;
import com.globalitians.inquiry.activities.network.RequestParam;
import com.google.gson.Gson;

import static com.globalitians.inquiry.activities.Utility.CommonUtil.playSoundForAttendance;
import static com.globalitians.inquiry.activities.Utility.CommonUtil.setFullScreenMode;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_LOGIN_EMPLOYEE;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_EMPLOYEE_BRANCH_ID;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_IS_ACTION_VOICE;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_IS_LOGGED_IN;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_LOGGEDIN_EMPLOYEE_ID;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_LOGGEDIN_EMPLOYEE_IMAGE;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_LOGGEDIN_EMPLOYEE_NAME;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_LOGGEDIN_EMPLOYEE_USERNAME;
import static com.globalitians.inquiry.activities.Utility.Constants.LOGIN_PREFRENCES;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OkHttpInterface {

    private EditText mUsername;
    private EditText mPassword;
    private Button mLogintext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode(LoginActivity.this);
        setContentView(R.layout.activity_login);
        findViews();
    }

    private void findViews() {
        mUsername = findViewById(R.id.edt_username);
        mPassword = findViewById(R.id.edt_password);

        mLogintext = findViewById(R.id.tv_login);

        setListeners();
    }

    private void setListeners() {
        mLogintext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
//                startActivity(new Intent(this,DashboardActivity.class));
                ValidateAndNavigateToInquiryDashboard();
                break;
        }
    }

    private void ValidateAndNavigateToInquiryDashboard() {
        String strUsername = ""+mUsername.getText().toString().trim();
        String strPassword = ""+mPassword.getText().toString().trim();

        if (CommonUtil.isNullString(strUsername)) {
            playSoundForAttendance("" + getResources().getString(R.string.toast_invalid_username), LoginActivity.this);
            mUsername.setError("" + getResources().getString(R.string.toast_invalid_username));
            return;
        }
        if (CommonUtil.isNullString(strPassword)) {
            playSoundForAttendance("" + getResources().getString(R.string.toast_invalid_password), LoginActivity.this);
            mPassword.setError("" + getResources().getString(R.string.toast_invalid_password));
            return;
        }

        callApiToLoginInquiry(strUsername, strPassword);

    }

    private void callApiToLoginInquiry(String strUsername, String strPassword) {
        if (!CommonUtil.isInternetAvailable(LoginActivity.this)) {
            return;
        }

        new OkHttpRequest(LoginActivity.this,
                OkHttpRequest.Method.POST,
                Constants.WS_INQUIRY_LOGIN,
                RequestParam.loginEmployee(strUsername, strPassword),
                RequestParam.getNull(),
                Constants.CODE_LOGIN_EMPLOYEE, false,
                this);
    }

    private void saveLoginPrefrences(EmployeeLoginModel modelEmployeeLogin) {
        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN_PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IS_LOGGED_IN, "true");
        editor.putString(KEY_IS_ACTION_VOICE, "true");
        editor.putString(KEY_EMPLOYEE_BRANCH_ID, "" + modelEmployeeLogin.getEmployeeDetail().getBranchId());
        editor.putString(KEY_LOGGEDIN_EMPLOYEE_ID, "" + modelEmployeeLogin.getEmployeeDetail().getId());
        editor.putString(KEY_LOGGEDIN_EMPLOYEE_USERNAME, "" + modelEmployeeLogin.getEmployeeDetail().getUsername());
        editor.putString(KEY_LOGGEDIN_EMPLOYEE_NAME, "" + modelEmployeeLogin.getEmployeeDetail().getName());
        editor.putString(KEY_LOGGEDIN_EMPLOYEE_IMAGE, "" + modelEmployeeLogin.getEmployeeDetail().getImage());
        editor.commit();
    }

    @Override
    public void onOkHttpStart(int requestId) {

    }

    @Override
    public void onOkHttpSuccess(int requestId, int statusCode, String response) {
        Log.e(">>Login Response >>", response);
        if (response == null) {
            response = "null";
        }

        switch (requestId) {
            case CODE_LOGIN_EMPLOYEE:
                final Gson courseListgson = new Gson();
                try {
                    EmployeeLoginModel employeeLoginModel = courseListgson.fromJson(response, EmployeeLoginModel.class);

                    if (employeeLoginModel.getStatus().equals(Constants.SUCCESS_CODE)) {
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        saveLoginPrefrences(employeeLoginModel);
                        finish();
                    }

                    playSoundForAttendance("" + employeeLoginModel.getMessage(), LoginActivity.this);

                    Toast.makeText(this, "" + "" + employeeLoginModel.getMessage(), Toast.LENGTH_SHORT).show();

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

}
