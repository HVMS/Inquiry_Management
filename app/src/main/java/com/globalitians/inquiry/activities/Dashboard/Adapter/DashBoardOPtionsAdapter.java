package com.globalitians.inquiry.activities.Dashboard.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.Dashboard.Model.DashboardOptionsModel;

import java.util.ArrayList;

public class DashBoardOPtionsAdapter extends RecyclerView.Adapter<DashBoardOPtionsAdapter.MyViewHolder>{

    private ArrayList<DashboardOptionsModel> mArrListDashboardOptions;
    private Activity mActivity;
    private DashboardActivityClickListner mDashboardActivityClickListner;

    public DashBoardOPtionsAdapter(Activity mActivity, ArrayList<DashboardOptionsModel> mArrListDashboardOptions
                                        , DashboardActivityClickListner mDashboardActivityClickListner){
        this.mActivity = mActivity;
        this.mArrListDashboardOptions = mArrListDashboardOptions;
        this.mDashboardActivityClickListner = mDashboardActivityClickListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.dashboard_layout,viewGroup,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        try{
            DashboardOptionsModel dashboardOptionsModel
                    = mArrListDashboardOptions.get(i);

            myViewHolder.tvTxtimagetext.setText(dashboardOptionsModel.getStrOptionTitle());
            myViewHolder.ivImage.setImageResource(dashboardOptionsModel.getBackgroundImageId());
            myViewHolder.ivImageCenter.setImageResource(dashboardOptionsModel.getOptionImageId());

            myViewHolder.relContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDashboardActivityClickListner.onDashBoardOptionClick(i);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mArrListDashboardOptions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTxtimagetext;
        public ImageView ivImage,ivImageCenter;
        public RelativeLayout relContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTxtimagetext = itemView.findViewById(R.id.tv_student_option);
            ivImage = itemView.findViewById(R.id.iv_student_option);
            ivImageCenter = itemView.findViewById(R.id.ic_menu_share);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDashboardActivityClickListner.onDashBoardOptionClick(getAdapterPosition());
                }
            });

        }
    }

    public interface DashboardActivityClickListner{
        void onDashBoardOptionClick(int position);
    }
}
