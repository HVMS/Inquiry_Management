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

import java.util.ArrayList;

public class MonthFilterSelectionAdapter extends RecyclerView.Adapter<MonthFilterSelectionAdapter.MyViewHolder> {

    private ArrayList<MonthModel> mArrListMonths;
    private Activity mContext;
    private LayoutInflater mInflater;
    private MonthClickListener mMonthClickListener;

    public MonthFilterSelectionAdapter(Activity activity,
                                       ArrayList<MonthModel> mArrListMonths,
                                       MonthClickListener monthClickListener) {
        this.mContext = activity;
        this.mArrListMonths = mArrListMonths;

        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mMonthClickListener = monthClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox mCbMonthFilter;
        private TextView mTvMonthName;

        public MyViewHolder(View view) {
            super(view);
            mCbMonthFilter = (CheckBox) view.findViewById(R.id.cb_month_filter);
            mCbMonthFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mMonthClickListener.onMonthClick(getAdapterPosition(), isChecked);
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
        holder.mCbMonthFilter.setChecked(mArrListMonths.get(position).isSelected);
        holder.mCbMonthFilter.setText("" + mArrListMonths.get(position).strMonthName);
    }

    @Override
    public int getItemCount() {
        return mArrListMonths.size();
    }

    public interface MonthClickListener {
        void onMonthClick(int position, boolean isChecked);
    }
}
