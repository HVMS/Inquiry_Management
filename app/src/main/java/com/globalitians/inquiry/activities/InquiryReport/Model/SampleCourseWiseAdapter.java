package com.globalitians.inquiry.activities.InquiryReport.Model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForCourses;
import com.globalitians.inquiry.activities.Utility.CommonUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SampleCourseWiseAdapter extends BaseAdapter {

    private Activity mContext;
    private LayoutInflater mInflater;
    private ViewHolder holder;
    private ArrayList<ModelClassForCourses.Course> mArrListCourselist;

    public SampleCourseWiseAdapter(Activity mContext,ArrayList<ModelClassForCourses.Course> mArrListCourselist) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mArrListCourselist = mArrListCourselist;
    }

    @Override
    public int getCount() {
        return mArrListCourselist.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrListCourselist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = mInflater.inflate(R.layout.sample_course_filter_items_dialog,null);

        holder = new ViewHolder();
        holder.circleImageView = view.findViewById(R.id.sample_course_wise_image);
        holder.mTxtCourseName = view.findViewById(R.id.tv_sample_course_wise);
        view.setTag(holder);

        setCourseListDataFromApi(position,holder,mArrListCourselist.get(position));
        return view;
    }

    private void setCourseListDataFromApi(int position, ViewHolder holder, ModelClassForCourses.Course course) {
        holder.mTxtCourseName.setText(""+course.getName());
        CommonUtil.setCircularImageForUser(mContext,holder.circleImageView,""+mArrListCourselist.get(position).getImage());

    }

    static class ViewHolder{
        private TextView mTxtCourseName;
        private CircleImageView circleImageView;
    }
}
