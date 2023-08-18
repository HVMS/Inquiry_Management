package com.globalitians.inquiry.activities.others;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForCourses;

import java.util.ArrayList;

public class CourseFilterSelectionAdapter extends RecyclerView.Adapter<CourseFilterSelectionAdapter.MyViewHolder> {

    private ArrayList<ModelClassForCourses.Course> mArrListCourses;
    private Activity mContext;
    private LayoutInflater mInflater;
    private CourseClickListener mCourseClickListener;

    public CourseFilterSelectionAdapter(Activity activity,
                                       ArrayList<ModelClassForCourses.Course> mArrListCourses,
                                        CourseClickListener courseClickListener) {
        this.mContext = activity;
        this.mArrListCourses = mArrListCourses;

        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mCourseClickListener = courseClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox mCbCourseFilter;
        private TextView mTvCourseName;

        public MyViewHolder(View view) {
            super(view);
            mCbCourseFilter = (CheckBox) view.findViewById(R.id.cb_month_filter);
            mCbCourseFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mCourseClickListener.onCourseClick(getAdapterPosition(), isChecked);
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_filter_selection, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.mCbCourseFilter.setChecked(mArrListCourses.get(position).isSelected());
        holder.mCbCourseFilter.setText("" + mArrListCourses.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mArrListCourses.size();
    }

    public interface CourseClickListener {
        void onCourseClick(int position, boolean isChecked);
    }
}
