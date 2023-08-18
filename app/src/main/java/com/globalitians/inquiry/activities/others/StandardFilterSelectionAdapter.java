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
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForStandard;

import java.util.ArrayList;

public class StandardFilterSelectionAdapter extends RecyclerView.Adapter<StandardFilterSelectionAdapter.MyViewHolder> {

    private ArrayList<ModelClassForStandard.Standard> mArrListStandards;
    private Activity mContext;
    private LayoutInflater mInflater;
    private StandardClickListner standardClickListner;

    public StandardFilterSelectionAdapter(Activity activity,
                                        ArrayList<ModelClassForStandard.Standard> mArrListStandards,
                                        StandardClickListner standardClickListner) {
        this.mContext = activity;
        this.mArrListStandards = mArrListStandards;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.standardClickListner = standardClickListner;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox mCbStandardFilter;

        public MyViewHolder(View view) {
            super(view);
            mCbStandardFilter = (CheckBox) view.findViewById(R.id.cb_month_filter);
            mCbStandardFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        standardClickListner.onStandardClick(getAdapterPosition(), isChecked);
                    }else{
                        standardClickListner.onStandardClick(getAdapterPosition(),isChecked);
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
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.mCbStandardFilter.setChecked(mArrListStandards.get(position).isSelected());
        myViewHolder.mCbStandardFilter.setText("" + mArrListStandards.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mArrListStandards.size();
    }

    public interface StandardClickListner{
        void onStandardClick(int position, boolean isChecked);
    }
}
