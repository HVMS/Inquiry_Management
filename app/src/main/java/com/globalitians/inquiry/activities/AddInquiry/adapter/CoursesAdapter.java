package com.globalitians.inquiry.activities.AddInquiry.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForCourses;
import com.globalitians.inquiry.activities.Utility.CommonUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.MyViewHolder> {

    private Activity activity;
    private ArrayList<ModelClassForCourses.Course> mAlCourseList;
    private OnCourseCheckListner onCourseCheckListner;
    private ModelClassForCourses.Course modelclassforcourses;

    public CoursesAdapter(Activity activity, ArrayList<ModelClassForCourses.Course> mAlCourseList, OnCourseCheckListner onCourseCheckListner) {
        this.activity = activity;
        this.mAlCourseList = mAlCourseList;
        this.onCourseCheckListner = onCourseCheckListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sample_course_filter_items_dialog, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        try{
            modelclassforcourses = mAlCourseList.get(position);
            setCoursesData(myViewHolder,position,modelclassforcourses);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setCoursesData(final MyViewHolder myViewHolder, final int position, ModelClassForCourses.Course modelclassforcourses) {
        myViewHolder.mTxtCourseName.setText(""+modelclassforcourses.getName());

        if(!CommonUtil.isNullString(""+modelclassforcourses.getImage())){
            CommonUtil.setCircularImageForUser(activity,
                    myViewHolder.circleImageView,
                    ""+modelclassforcourses.getImage());
        }else{
            myViewHolder.circleImageView.setImageResource(R.drawable.default_user_image);
        }

        myViewHolder.checkBox.setChecked(mAlCourseList.get(position).isSelected());
        myViewHolder.checkBox.setTag(position);

        myViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = (Integer) myViewHolder.checkBox.getTag();

                if(mAlCourseList.get(pos).isSelected()){
                    mAlCourseList.get(pos).setSelected(false);
                }else{
                    mAlCourseList.get(pos).setSelected(true);
                }

                onCourseCheckListner.onCourseCheckSelection(position,mAlCourseList.get(position).isSelected());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mAlCourseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mTxtCourseName;
        private RelativeLayout relativeLayout;
        private CircleImageView circleImageView;
        private CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            mTxtCourseName = itemView.findViewById(R.id.tv_sample_course_wise);
            relativeLayout = itemView.findViewById(R.id.rel_course);
            circleImageView = itemView.findViewById(R.id.sample_course_wise_image);
            checkBox = itemView.findViewById(R.id.check_box_box);

        }
    }

    public interface OnCourseCheckListner{
        void onCourseCheckSelection(int position,boolean value);
    }

}
