package com.globalitians.inquiry.activities.UpcomingReport.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.UpcomingReport.Model.ModelClassFeedback;

import java.util.ArrayList;

public class FeedBackAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<ModelClassFeedback.Feedback> mArrlistfeedback;

    public FeedBackAdapter(Activity mContext, ArrayList<ModelClassFeedback.Feedback> mALfeedback) {
        this.activity = mContext;
        this.mArrlistfeedback = mALfeedback;
    }

    @Override
    public int getCount() {
        return mArrlistfeedback.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rootView = inflater.inflate(R.layout.feedback_list_items,null,true);

        TextView mTxtfeedback = rootView.findViewById(R.id.tv_feedback_list_text);
        TextView mTxtfeedbackdate = rootView.findViewById(R.id.tv_feedback_date);

        mTxtfeedback.setText(""+mArrlistfeedback.get(position).getFeedback());
        mTxtfeedbackdate.setText(""+mArrlistfeedback.get(position).getCreatedAt());

        return rootView;
    }
}
