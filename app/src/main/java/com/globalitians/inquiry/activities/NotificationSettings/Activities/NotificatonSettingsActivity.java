package com.globalitians.inquiry.activities.NotificationSettings.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.NotificationSettings.Adapter.NotifcationAdapter;
import com.globalitians.inquiry.activities.NotificationSettings.Model.NotificationSettingsDataModel;

import java.util.ArrayList;

import static com.globalitians.inquiry.activities.Utility.CommonUtil.setFullScreenMode;

public class NotificatonSettingsActivity extends AppCompatActivity {

    private ListView mLvNotification;
    private ArrayList<NotificationSettingsDataModel> mALnotificaitonList;

    private NotifcationAdapter notifcationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode(NotificatonSettingsActivity.this);
        setContentView(R.layout.activity_notificaton_settings);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();
        initialization();
        sampleData();

        getSupportActionBar().setTitle("Notification Settings");

        notifcationAdapter = new NotifcationAdapter(NotificatonSettingsActivity.this,
                    mALnotificaitonList);
        mLvNotification.setAdapter(notifcationAdapter);

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

    private void sampleData() {
        NotificationSettingsDataModel model1 = new NotificationSettingsDataModel();
        model1.setNotificationDuraiton("After 2 days");
        model1.setSelected(false);

        NotificationSettingsDataModel model2 = new NotificationSettingsDataModel();
        model2.setNotificationDuraiton("After 7 days");
        model2.setSelected(false);

        NotificationSettingsDataModel model3 = new NotificationSettingsDataModel();
        model3.setNotificationDuraiton("After 15 days");
        model3.setSelected(false);

        NotificationSettingsDataModel model4 = new NotificationSettingsDataModel();
        model4.setNotificationDuraiton("After 1 Month");
        model4.setSelected(false);

        mALnotificaitonList.add(model1);
        mALnotificaitonList.add(model2);
        mALnotificaitonList.add(model3);
        mALnotificaitonList.add(model4);
    }

    private void initialization() {
        mALnotificaitonList = new ArrayList<>();
    }

    private void findViews() {
        mLvNotification = findViewById(R.id.listviewnotificaiton);
    }


}
