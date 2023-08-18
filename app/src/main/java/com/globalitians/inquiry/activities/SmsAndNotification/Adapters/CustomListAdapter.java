package com.globalitians.inquiry.activities.SmsAndNotification.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.InquiryReport.Model.ModelClassForInquiryDetails;
import com.globalitians.inquiry.activities.SmsAndNotification.Activities.SmsandnotificationActivity;
import com.globalitians.inquiry.activities.SmsAndNotification.Models.InquiryPersonModel;
import com.globalitians.inquiry.activities.Utility.CommonUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomListAdapter extends BaseAdapter{

    private Activity activity;
    private ArrayList<ModelClassForInquiryDetails.Inquiry> mAlInquiryPerson;
    private ModelClassForInquiryDetails.Inquiry modelClassForInquiryDetails;

    public CustomListAdapter(Activity activity, ArrayList<ModelClassForInquiryDetails.Inquiry> mAlInquiryPerson) {
        this.activity = activity;
        this.mAlInquiryPerson = mAlInquiryPerson;
    }

    @Override
    public int getCount() {
        return mAlInquiryPerson.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rootview = inflater.inflate(R.layout.sms_list_items,null,true);

        TextView tv_name = rootview.findViewById(R.id.tv_name);
        final CheckBox cb_isSelected = rootview.findViewById(R.id.chkCourse);
        CircleImageView iv_person = (CircleImageView)rootview.findViewById(R.id.circular_image);
        TextView tv_course_name = rootview.findViewById(R.id.tv_course_name);

        // set the values to listviews
        modelClassForInquiryDetails = mAlInquiryPerson.get(position);

        /*cb_isSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    onFilteredItemsClick.OnItemClickEvent(position,b);
                }else{
                    onFilteredItemsClick.OnItemClickEvent(position,b);
                }
            }
        });*/

        tv_name.setText(""+mAlInquiryPerson.get(position).getFname()+" "+mAlInquiryPerson.get(position).getLname());
        for(int i = 0 ; i < modelClassForInquiryDetails.getCourses().size() ; i++){
            if(CommonUtil.isNullString(tv_course_name.getText().toString())){
                tv_course_name.setText(""+modelClassForInquiryDetails.getCourses().get(i).getName());
            }else{
                tv_course_name.setText(tv_course_name.getText().toString()+","
                        +modelClassForInquiryDetails.getCourses().get(i).getName());
            }
        }

        cb_isSelected.setChecked(mAlInquiryPerson.get(position).isSelected());
        cb_isSelected.setTag(position);
        cb_isSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos = (Integer) cb_isSelected.getTag();

                if(mAlInquiryPerson.get(pos).isSelected()){
                    mAlInquiryPerson.get(pos).setSelected(false);
                }else{
                    mAlInquiryPerson.get(pos).setSelected(true);
                }

            }
        });

        if(mAlInquiryPerson.get(position).isSelected()){
            cb_isSelected.setChecked(true);
        }else{
            cb_isSelected.setChecked(false);
        }

        iv_person.setImageResource(R.drawable.default_user_image);
        return rootview;
    }

}
