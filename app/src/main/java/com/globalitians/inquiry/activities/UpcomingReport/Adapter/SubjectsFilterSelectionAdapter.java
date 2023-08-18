package com.globalitians.inquiry.activities.UpcomingReport.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForSubjects;
import java.util.ArrayList;

public class SubjectsFilterSelectionAdapter extends RecyclerView.Adapter<SubjectsFilterSelectionAdapter.MyViewHolder> {

    private ArrayList<ModelClassForSubjects.Subject> mArrListSubjects;
    private Activity mContext;
    private LayoutInflater mInflater;
    private SubjectsListner subjectsListner;

    public SubjectsFilterSelectionAdapter(Activity activity, ArrayList<ModelClassForSubjects.Subject> mAlsubjects, SubjectsListner subjectsListner) {
        this.mContext = activity;
        this.mArrListSubjects = mAlsubjects;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.subjectsListner = subjectsListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_filter_selection, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.mCbSubjectsFilter.setChecked(mArrListSubjects.get(position).isSelcted());
        myViewHolder.mCbSubjectsFilter.setText("" + mArrListSubjects.get(position).getName());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private CheckBox mCbSubjectsFilter;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mCbSubjectsFilter = (CheckBox) itemView.findViewById(R.id.cb_month_filter);
            mCbSubjectsFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        subjectsListner.onSubjectsClick(getAdapterPosition(), isChecked);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mArrListSubjects.size();
    }

    public interface SubjectsListner{
        void onSubjectsClick(int position, boolean isChecked);
    }

}
