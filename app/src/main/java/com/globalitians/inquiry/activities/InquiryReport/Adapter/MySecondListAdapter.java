package com.globalitians.inquiry.activities.InquiryReport.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalitians.inquiry.activities.InquiryReport.Model.SecondActivityLIstData;
import com.globalitians.inquiry.activities.Inquirydetails.activity.InquiryDetailsActivity;
import com.globalitians.inquiry.activities.NotificationSettings.Activities.NotificatonSettingsActivity;
import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.SmsAndNotification.Activities.SmsandnotificationActivity;

import java.util.List;

public class MySecondListAdapter extends RecyclerView.Adapter<MySecondListAdapter.MyViewHolder>{

    private Activity mContext;
    private Context context;
    private OnInquiryListListener mInquiryListClickListener;
    private List<SecondActivityLIstData> mSecondActivityLIstData;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.inquiry_report_list_items,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    public MySecondListAdapter(Activity activity,List<SecondActivityLIstData> msecondActivityLIstData,OnInquiryListListener mInquiryListClickListener){
        this.mSecondActivityLIstData = msecondActivityLIstData;
        this.mInquiryListClickListener = mInquiryListClickListener;
        this.mContext = activity;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        try{
            SecondActivityLIstData secondActivityLIstData = mSecondActivityLIstData.get(i);
            myViewHolder.mTxtPersonName.setText(secondActivityLIstData.getPersonName());
            myViewHolder.mTxtCourcename.setText(secondActivityLIstData.getCourseName());
            myViewHolder.mIvPersonImage.setImageResource(secondActivityLIstData.getPersonImageId());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mSecondActivityLIstData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView mIvPersonImage;
        private TextView mTxtPersonName;
        private TextView mTxtCourcename;
        private ImageView mIvMoreoptions;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            mTxtPersonName = itemView.findViewById(R.id.tv_person_name);
            mTxtCourcename = itemView.findViewById(R.id.tv_course);
            mIvPersonImage = itemView.findViewById(R.id.iv_person_image);

            mIvMoreoptions = itemView.findViewById(R.id.iv_more_options);

            context = itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, InquiryDetailsActivity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(mContext,
                                    mIvPersonImage,
                                    ViewCompat.getTransitionName(mIvPersonImage));
                    context.startActivity(intent,options.toBundle());
                }
            });

            mIvMoreoptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(mContext,itemView, Gravity.RIGHT);
                    popupMenu.inflate(R.menu.options_menu);

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.menu1:
                                    String phone = "6353064190";
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phone,null));
                                    mContext.startActivity(intent);
                                    return true;
                                case R.id.menu2:
                                    mContext.startActivity(new Intent(mContext, SmsandnotificationActivity.class));
                                    return true;
                                case R.id.menu3:
                                    mContext.startActivity(new Intent(mContext, NotificatonSettingsActivity.class));
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }
    public interface OnInquiryListListener {
        void onMoreOptionsClick(int adapterPosition, ImageView ivMoreOptions);
        void onClick(View v, int position);
    }
}
