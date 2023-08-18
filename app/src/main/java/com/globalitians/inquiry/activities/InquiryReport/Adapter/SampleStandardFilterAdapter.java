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
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForStandard;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SampleStandardFilterAdapter extends BaseAdapter {

    private Activity mContext;
    private LayoutInflater mInflater;
    private ViewHolder viewHolder;
    private OnStandardItemClickListner onStandardItemClickListner;
    private ArrayList<ModelClassForStandard.Standard> mArrliststandard;

    public SampleStandardFilterAdapter(Activity mContext,ArrayList<ModelClassForStandard.Standard> mArrListCourselist, OnStandardItemClickListner onStandardItemClickListner) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mArrliststandard = mArrListCourselist;
        this.onStandardItemClickListner = onStandardItemClickListner;
    }

    @Override
    public int getCount() {
        return mArrliststandard.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrliststandard.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.sample_standard_wise_filter_layout,null);

        viewHolder = new ViewHolder();
        viewHolder.relativeLayout = convertView.findViewById(R.id.rel_standard);
        viewHolder.mTxtCourseName = convertView.findViewById(R.id.tv_sample_course_wise);
        viewHolder.checkBox = convertView.findViewById(R.id.check_box_box);

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    onStandardItemClickListner.onStandardItemClick(position,isChecked);
                }else{
                    onStandardItemClickListner.onStandardItemClick(position,isChecked);
                }
            }
        });

        convertView.setTag(viewHolder);
        setStandardData(position,viewHolder,mArrliststandard.get(position));
        return convertView;
    }

    private void setStandardData(int position, ViewHolder viewHolder, ModelClassForStandard.Standard standard) {
        viewHolder.mTxtCourseName.setText(""+standard.getName());

        viewHolder.checkBox.setChecked(mArrliststandard.get(position).isSelected());
        
        if(mArrliststandard.get(position).isSelected()){
            viewHolder.checkBox.setChecked(true);
        }else{
            viewHolder.checkBox.setChecked(false);
        }

    }

    static class ViewHolder{
        private TextView mTxtCourseName;
        private RelativeLayout relativeLayout;
        private CircleImageView circleImageView;
        private CheckBox checkBox;
    }

    public interface OnStandardItemClickListner{
        void onStandardItemClick(int position, boolean value);
    }
}
