package com.globalitians.inquiry.activities.UpcomingReport.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForStraem;

import java.util.ArrayList;

public class StreamFilterSelectionAdapter extends RecyclerView.Adapter<StreamFilterSelectionAdapter.MyViewHolder> {

    private ArrayList<ModelClassForStraem.Stream> mArrListStreams;
    private Activity mContext;
    private LayoutInflater mInflater;
    private StreamClickListner streamClickListner;

    public StreamFilterSelectionAdapter(Activity activity,
                                          ArrayList<ModelClassForStraem.Stream> mArrListStreams,
                                        StreamClickListner streamClickListner) {
        this.mContext = activity;
        this.mArrListStreams = mArrListStreams;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.streamClickListner = streamClickListner;
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
        myViewHolder.mCbStreamFilter.setChecked(mArrListStreams.get(position).isSelcted());
        myViewHolder.mCbStreamFilter.setText("" + mArrListStreams.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mArrListStreams.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox mCbStreamFilter;

        public MyViewHolder(View view) {
            super(view);
            mCbStreamFilter = (CheckBox) view.findViewById(R.id.cb_month_filter);
            mCbStreamFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        streamClickListner.onStreamClick(getAdapterPosition(), isChecked);
                    }
                }
            });
        }
    }

    public interface StreamClickListner{
        void onStreamClick(int position, boolean isChecked);
    }
}
