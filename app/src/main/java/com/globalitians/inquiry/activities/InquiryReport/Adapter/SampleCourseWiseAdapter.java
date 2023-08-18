package com.globalitians.inquiry.activities.InquiryReport.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
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
    private CourseWiseClickListner courseWiseClickListner;
    private ArrayList<ModelClassForCourses.Course> mArrListCourselist;

    public SampleCourseWiseAdapter(Activity mContext,ArrayList<ModelClassForCourses.Course> mArrListCourselist, CourseWiseClickListner courseWiseClickListner) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mArrListCourselist = mArrListCourselist;
        this.courseWiseClickListner = courseWiseClickListner;
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
    public View getView(final int position, View view, ViewGroup viewGroup) {

        view = mInflater.inflate(R.layout.sample_course_filter_items_dialog,null);

        holder = new ViewHolder();
        holder.relativeLayout = view.findViewById(R.id.rel_course);
        holder.circleImageView = view.findViewById(R.id.sample_course_wise_image);
        holder.mTxtCourseName = view.findViewById(R.id.tv_sample_course_wise);
        holder.checkBox = view.findViewById(R.id.check_box_box);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    courseWiseClickListner.onCourseListChecking(position,isChecked);
                }else{
                    courseWiseClickListner.onCourseListChecking(position,isChecked);
                }
            }
        });

        view.setTag(holder);
        setCourseListDataFromApi(position,holder,mArrListCourselist.get(position));
        return view;
    }

    private void setCourseListDataFromApi(final int position, final ViewHolder holder, final ModelClassForCourses.Course course) {
        holder.mTxtCourseName.setText(""+course.getName());

        holder.checkBox.setChecked(mArrListCourselist.get(position).isSelected());

        if(mArrListCourselist.get(position).isSelected()){
            holder.checkBox.setChecked(true);
        } else{
            holder.checkBox.setChecked(false);
        }

        if(!CommonUtil.isNullString(""+mArrListCourselist.get(position).getImage())) {
            CommonUtil.setCircularImageForUser(mContext,holder.circleImageView,""+mArrListCourselist.get(position).getImage());
        }else{
            holder.circleImageView.setImageResource(R.drawable.ic_profile_placeholder_dashboard);
        }
    }

    static class ViewHolder{
        private TextView mTxtCourseName;
        private RelativeLayout relativeLayout;
        private CircleImageView circleImageView;
        private CheckBox checkBox;
    }

    public interface CourseWiseClickListner{
        void onCourseListChecking(int position,boolean value);
    }
}
