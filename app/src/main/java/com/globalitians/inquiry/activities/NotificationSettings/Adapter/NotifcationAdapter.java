package com.globalitians.inquiry.activities.NotificationSettings.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.NotificationSettings.Model.NotificationSettingsDataModel;

import java.util.ArrayList;

public class NotifcationAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<NotificationSettingsDataModel> mALnotificaitonList;

    public NotifcationAdapter(Activity activity, ArrayList<NotificationSettingsDataModel> mALnotificaitonList) {
        this.activity = activity;
        this.mALnotificaitonList = mALnotificaitonList;
    }

    @Override
    public int getCount() {
        return mALnotificaitonList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View rootView = layoutInflater.inflate(R.layout.notification_duration_items,null,true);

        TextView mTxtnotidurtion = rootView.findViewById(R.id.tv_noti_duration);
        CheckBox cb_isSeleted = rootView.findViewById(R.id.noti_checkbox);
//        RadioButton radioButton = rootView.findViewById(R.id.noti_radio);

        // set the values to the UI
        mTxtnotidurtion.setText(mALnotificaitonList.get(position).getNotificationDuraiton());

        if(mALnotificaitonList.get(position).isSelected()){
            cb_isSeleted.setChecked(true);
        }else{
            cb_isSeleted.setChecked(false);
        }

        return rootView;

    }
}
