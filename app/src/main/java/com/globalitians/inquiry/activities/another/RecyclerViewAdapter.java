package com.globalitians.inquiry.activities.another;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalitians.inquiry.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<ContactModel> mContactModel;

    public RecyclerViewAdapter(Context context, List<ContactModel> mContactModel) {
        this.context = context;
        this.mContactModel = mContactModel;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.course_items_recyclerview,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.mTvcourse.setText(mContactModel.get(i).getCourse_name());
        myViewHolder.mIvcourseimage.setImageResource(mContactModel.get(i).getCourse_image());
    }

    @Override
    public int getItemCount() {
        return mContactModel.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvcourse;
        private ImageView mIvcourseimage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvcourse = itemView.findViewById(R.id.item_course_name);
            mIvcourseimage = itemView.findViewById(R.id.item_course_image);


        }
    }
}
