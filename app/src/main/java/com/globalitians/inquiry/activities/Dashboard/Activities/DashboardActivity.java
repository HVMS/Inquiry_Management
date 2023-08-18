package com.globalitians.inquiry.activities.Dashboard.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.activities.AddinquiryActvity;
import com.globalitians.inquiry.activities.Dashboard.Adapter.DashBoardOPtionsAdapter;
import com.globalitians.inquiry.activities.others.ContactUsActivity;
import com.globalitians.inquiry.activities.Dashboard.Model.DashboardOptionsModel;
import com.globalitians.inquiry.activities.InquiryReport.Activities.InquiryReportActivity;
import com.globalitians.inquiry.activities.SmsAndNotification.Activities.SmsandnotificationActivity;
import com.globalitians.inquiry.activities.UpcomingReport.Activities.UpcomingReportActivity;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.others.AboutUsActivity;
import com.globalitians.inquiry.activities.others.LoginActivity;
import com.globalitians.inquiry.activities.others.SettingActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.globalitians.inquiry.activities.Utility.CommonUtil.getSharedPrefrencesInstance;
import static com.globalitians.inquiry.activities.Utility.CommonUtil.setFullScreenMode;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_IS_LOGGED_IN;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_LOGGEDIN_EMPLOYEE_IMAGE;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_LOGGEDIN_EMPLOYEE_NAME;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DashBoardOPtionsAdapter.DashboardActivityClickListner,
        View.OnClickListener {

    private ArrayList<DashboardOptionsModel> mArrListDashboardOptions = new ArrayList<>();
    private RecyclerView mRvDashboardOptions;
    private DashBoardOPtionsAdapter mAdapterDashboardOptions;

    private ImageView mIvoptions;
    private ImageView mIvphone;

    private TextView mTvcallnow;
    private TextView mTvinquirypersonname;
    private CircleImageView mIvpersonprofile;

    private SharedPreferences mSharedPrefrences;
    private DrawerLayout drawerLayout;

    private RelativeLayout mRelHome;
    private RelativeLayout mRelAboutus;
    private RelativeLayout mRelViewonmap;
    private RelativeLayout mRelcallnow;
    private RelativeLayout mRelShare;
    private RelativeLayout mRellogout;
    private RelativeLayout mRelTopView;
    private Animation animation;
    private Animation animation1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode(DashboardActivity.this);
        setContentView(R.layout.activity_dashboard2);

        mSharedPrefrences = getSharedPreferences(Constants.LOGIN_PREFRENCES, Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViews();

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setTitleTextAppearance(this,R.style.RobotoBoldTextAppearance);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorWhite));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setDashBoardOptionsAdapter();

        CommonUtil.setCircularImageForUser(DashboardActivity.this, mIvpersonprofile, getSharedPrefrencesInstance(DashboardActivity.this).getString(KEY_LOGGEDIN_EMPLOYEE_IMAGE, ""));
        mTvinquirypersonname.setText(mSharedPrefrences.getString(KEY_LOGGEDIN_EMPLOYEE_NAME, ""));

        setOnClickListners();
    }

    private void findViews() {

        mIvoptions = findViewById(R.id.iv_more_options);
        mIvphone = findViewById(R.id.iv_phone);

        mTvcallnow = findViewById(R.id.tv_call_now);
        drawerLayout = findViewById(R.id.drawer_layout);
        mTvinquirypersonname = findViewById(R.id.tv_employee_name);
        mIvpersonprofile = findViewById(R.id.iv_profile_image);

        mRvDashboardOptions = findViewById(R.id.dashboardrecycler);

        mRelHome = findViewById(R.id.rel_home);
        mRelAboutus = findViewById(R.id.rel_about_us);
        mRelViewonmap = findViewById(R.id.rel_view_on_map);
        mRelShare = findViewById(R.id.share);
        mRelcallnow = findViewById(R.id.rel_phone);
        mRelTopView = findViewById(R.id.rel_drawer_top_view);
        mRellogout = findViewById(R.id.logout);
        callAnimationForRelativeLayout();

    }

    private void callAnimationForRelativeLayout() {
        animation = AnimationUtils.loadAnimation(DashboardActivity.this,R.anim.left_to_right);
        mRelHome.startAnimation(animation);
        mRelAboutus.startAnimation(animation);
        mRelViewonmap.startAnimation(animation);
        mRelcallnow.startAnimation(animation);
        mRelShare.startAnimation(animation);
        mRellogout.startAnimation(animation);

        animation1 = AnimationUtils.loadAnimation(DashboardActivity.this,
                R.anim.down_to_up);
        mIvpersonprofile.startAnimation(animation1);
        mTvinquirypersonname.startAnimation(animation1);
    }

    private void setOnClickListners() {
        mRelHome.setOnClickListener(this);
        mRelAboutus.setOnClickListener(this);
        mRelViewonmap.setOnClickListener(this);
        mRelShare.setOnClickListener(this);
        mRelcallnow.setOnClickListener(this);
        mRellogout.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(DashboardActivity.this, SettingActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setDashBoardOptionsAdapter() {
        setDashboardOptionsdata();
        mAdapterDashboardOptions = new DashBoardOPtionsAdapter(DashboardActivity.this, mArrListDashboardOptions, this);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mRvDashboardOptions.setLayoutManager(manager);
        mRvDashboardOptions.setAdapter(mAdapterDashboardOptions);
    }

    private void setDashboardOptionsdata() {

        DashboardOptionsModel dashboardOptionsModel = new DashboardOptionsModel();
        dashboardOptionsModel.setStrOptionTitle("Add New Inquiry");
        dashboardOptionsModel.setBackgroundImageId(R.drawable.ic_plus_button);
        dashboardOptionsModel.setOptionImageId(R.drawable.ic_arrow_right_dashboard);
        mArrListDashboardOptions.add(dashboardOptionsModel);

        DashboardOptionsModel dashboardOptionsModel1 = new DashboardOptionsModel();
        dashboardOptionsModel1.setStrOptionTitle("Inquiry Report");
        dashboardOptionsModel1.setBackgroundImageId(R.drawable.ic_inq_report);
        dashboardOptionsModel1.setOptionImageId(R.drawable.ic_arrow_right_dashboard);
        mArrListDashboardOptions.add(dashboardOptionsModel1);

        DashboardOptionsModel dashboardOptionsModel2 = new DashboardOptionsModel();
        dashboardOptionsModel2.setStrOptionTitle("SMS Service");
        dashboardOptionsModel2.setBackgroundImageId(R.drawable.ic_inq_upcoming);
        dashboardOptionsModel2.setOptionImageId(R.drawable.ic_arrow_right_dashboard);
        mArrListDashboardOptions.add(dashboardOptionsModel2);

        DashboardOptionsModel dashboardOptionsModel3 = new DashboardOptionsModel();
        dashboardOptionsModel3.setStrOptionTitle("Upcoming Report");
        dashboardOptionsModel3.setBackgroundImageId(R.drawable.ic_inq_sms);
        dashboardOptionsModel3.setOptionImageId(R.drawable.ic_arrow_right_dashboard);
        mArrListDashboardOptions.add(dashboardOptionsModel3);

    }

    @Override
    public void onDashBoardOptionClick(int position) {
        switch ("" + mArrListDashboardOptions.get(position).getStrOptionTitle()) {
            case "Add New Inquiry":
                startActivity(new Intent(DashboardActivity.this, AddinquiryActvity.class));
                break;
            case "Inquiry Report":
                startActivity(new Intent(DashboardActivity.this, InquiryReportActivity.class));
                break;
            case "SMS Service":
                startActivity(new Intent(DashboardActivity.this, SmsandnotificationActivity.class));
                break;
            case "Upcoming Report":
                startActivity(new Intent(DashboardActivity.this, UpcomingReportActivity.class));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_home:
                drawerLayout.closeDrawers();
                navigateToHomeScreen();
                break;
            case R.id.rel_about_us:
                drawerLayout.closeDrawers();
                navigateToQAboutUs();
                break;
            case R.id.rel_view_on_map:
                drawerLayout.closeDrawers();
                navigateToMap();
                break;
            case R.id.share:
                drawerLayout.closeDrawers();
                navigateToShare();
                break;
            case R.id.rel_phone:
                drawerLayout.closeDrawers();
                navigateToCallnow();
                break;
            case R.id.logout:
                drawerLayout.closeDrawers();
                clearLoginPreferences();
                navigateToLoginScreen();
                break;
        }
    }

    private void navigateToShare() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Checkout our Global It Student's Android Application at: https://drive.google.com/open?id=1PXUdPuopyI-De6lU5S1Ni8QFQmzJkYrr");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void navigateToMap() {
        String map = "http://maps.google.co.in/maps?q=" + getResources().getString(R.string.strOdhavBranchAddress);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
        startActivity(intent);
    }

    private void navigateToQAboutUs() {
        startActivity(new Intent(DashboardActivity.this, AboutUsActivity.class));
    }

    private void navigateToHomeScreen() {
    }

    private void navigateToLoginScreen() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        builder.setMessage("Are you Sure ?");
        builder.setPositiveButton(R.string.YES, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                clearLoginPreferences();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                finish();
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //nothing
            }
            // Create the AlertDialog object and return it
        });
        builder.show();
    }

    private void clearLoginPreferences() {
        if (mSharedPrefrences != null) {
            SharedPreferences.Editor editor = mSharedPrefrences.edit();
            editor.remove(KEY_IS_LOGGED_IN);
            editor.clear();
            editor.commit();
        }
    }

    private void navigateToCallnow() {
        Intent intent = new Intent(DashboardActivity.this, ContactUsActivity.class);
        startActivity(intent);
    }
}