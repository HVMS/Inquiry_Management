package com.globalitians.inquiry.activities.UpcomingReport.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.UpcomingReport.Model.FilterModelCourseList;
import com.globalitians.inquiry.activities.Utility.CircularImageView;

import java.util.ArrayList;

public class FilterCourseListAdapter extends BaseAdapter{

    private Activity mActivity;
    private ArrayList<FilterModelCourseList.Course> mArrayListCourseList;
    private OnCourseListListener mCourseListClickListener;
    private ViewHolder viewHolder;
    private LayoutInflater layoutInflater;

    public FilterCourseListAdapter(Activity context, ArrayList<FilterModelCourseList.Course> arrListNewsFeedData, OnCourseListListener onCourseListListener) {
        mActivity = context;
        mArrayListCourseList = arrListNewsFeedData;
        layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCourseListClickListener = onCourseListListener;
    }

    @Override
    public int getCount() {
        return mArrayListCourseList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrayListCourseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null){
            view = layoutInflater.inflate(R.layout.view_course_filter_list,null);
            viewHolder = new ViewHolder();
            viewHolder.mTvCourseName = view.findViewById(R.id.tv_course_name);
            viewHolder.mRelCourse = view.findViewById(R.id.rel_course);
            viewHolder.ivCourse = view.findViewById(R.id.iv_course);
            view.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder)view.getTag();
        }

        setCourseListData(i,viewHolder,mArrayListCourseList.get(i));
        return view;
    }

    private void setCourseListData(final int i, final ViewHolder viewHolder, final FilterModelCourseList.Course course) {
        viewHolder.mTvCourseName.setText("" + course.getName());
        if(mArrayListCourseList.get(i).isSelected()==true)
        {
            viewHolder.mRelCourse.setBackgroundColor(mActivity.getResources().getColor(R.color.colorThemeBlue));
            viewHolder.mTvCourseName.setTextColor(mActivity.getResources().getColor(R.color.colorWhite));
        }else{
            viewHolder.mRelCourse.setBackgroundColor(mActivity.getResources().getColor(R.color.colorWhite));
            viewHolder.mTvCourseName.setTextColor(mActivity.getResources().getColor(R.color.colorBlack));
        }

        viewHolder.mRelCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(course.isSelected()==false){
                    mCourseListClickListener.onCourseSelected(i,true);
                    viewHolder.mRelCourse.setBackgroundColor(mActivity.getResources().getColor(R.color.colorThemeBlue));
                    viewHolder.mTvCourseName.setTextColor(mActivity.getResources().getColor(R.color.colorWhite));
                }else{
                    mCourseListClickListener.onCourseSelected(i,false);
                    viewHolder.mRelCourse.setBackgroundColor(mActivity.getResources().getColor(R.color.colorWhite));
                    viewHolder.mTvCourseName.setTextColor(mActivity.getResources().getColor(R.color.colorBlack));
                }
                viewHolder.mRelCourse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(course.isSelected()==true)
                            mCourseListClickListener.onCourseSelected(i,false);
                        else
                            mCourseListClickListener.onCourseSelected(i,true);
                    }
                });
            }
        });
//        CommonUtil.setCircularImageToGlide(mActivity,viewHolder.ivCourse,""+mArrayListCourseList.get(i).getImage());
    }

    static class ViewHolder {
        private TextView mTvCourseName;
        private RelativeLayout mRelCourse;
        private CircularImageView ivCourse;
    }

    public interface OnCourseListListener{
        void onCourseSelected(int position,boolean b);
    }
}
