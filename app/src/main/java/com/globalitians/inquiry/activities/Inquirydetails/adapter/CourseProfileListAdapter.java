package com.globalitians.inquiry.activities.Inquirydetails.adapter;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.Inquirydetails.model.InquiryDetailsModel;

import java.util.ArrayList;

public class CourseProfileListAdapter extends RecyclerView.Adapter<CourseProfileListAdapter.MyViewHolder> {

    private ArrayList<InquiryDetailsModel.InquiryDetail.Course> mArrlistcourse;

    public CourseProfileListAdapter(ArrayList<InquiryDetailsModel.InquiryDetail.Course> courses) {
        this.mArrlistcourse = courses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_course_list_student_profile, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvCoursename.setText(mArrlistcourse.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mArrlistcourse.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvCoursename;

        @SuppressLint("ResourceAsColor")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCoursename = itemView.findViewById(R.id.tv_course_name);
            tvCoursename.setTextColor(R.color.bv_primaryColor);
        }
    }
}
