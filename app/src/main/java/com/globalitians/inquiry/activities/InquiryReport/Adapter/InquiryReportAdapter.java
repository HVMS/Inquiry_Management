package com.globalitians.inquiry.activities.InquiryReport.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.InquiryReport.Model.ModelClassForInquiryDetails;
import com.globalitians.inquiry.activities.Inquirydetails.activity.InquiryDetailsActivity;
import com.globalitians.inquiry.activities.NotificationSettings.Adapter.NotifcationAdapter;
import com.globalitians.inquiry.activities.NotificationSettings.Model.NotificationSettingsDataModel;
import com.globalitians.inquiry.activities.SmsAndNotification.Adapters.SmsCategoryAdapter;
import com.globalitians.inquiry.activities.SmsAndNotification.Models.CategoryWiseResponse;
import com.globalitians.inquiry.activities.SmsAndNotification.Activities.SmsandnotificationActivity;
import com.globalitians.inquiry.activities.SmsAndNotification.Models.Smscategoriesresponse;
import com.globalitians.inquiry.activities.Student.activity.StudentActivity;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.network.OkHttpInterface;
import com.globalitians.inquiry.activities.network.OkHttpRequest;
import com.globalitians.inquiry.activities.network.RequestParam;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.globalitians.inquiry.activities.Utility.Constants.CODE_SMS_CATEGORIES;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_SMS_CATRGORIES_WISE_MSGS;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_INTENT_INQUIRY_ID;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_SMS_DAYS;

public class InquiryReportAdapter extends RecyclerView.Adapter<InquiryReportAdapter.MyViewHolder> implements OkHttpInterface {

    private Activity activity;
    private static ArrayList<ModelClassForInquiryDetails.Inquiry> mArrlistInquiryDetails;
    private ModelClassForInquiryDetails.Inquiry modelClassForInquiryDetails = null;

    private static ListView mLvNotification;
    private static ArrayList<NotificationSettingsDataModel> mALnotificaitonList;
    private static NotifcationAdapter notifcationAdapter;

    // other things
    private TextView textView;
    private Dialog dialog;
    private TextView txt;
    private Spinner mSprcategories;
    private ListView listview;
    private String mStrSelectedSmsTypeId="";
    private ArrayList<String> mAlsmscategorylist;
    private Smscategoriesresponse smscategoriesresponse;

    private CategoryWiseResponse categoryWiseResponse;
    private ArrayList<CategoryWiseResponse.Message> mAlcategorywisemsgs;

    public InquiryReportAdapter(Activity activity, ArrayList<ModelClassForInquiryDetails.Inquiry> mArrlistInquiryDetails) {
        this.activity = activity;
        this.mArrlistInquiryDetails = mArrlistInquiryDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.inquiry_report_list_items,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        try {
            modelClassForInquiryDetails = mArrlistInquiryDetails.get(position);
            setInquiryDetails(position, myViewHolder, modelClassForInquiryDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setInquiryDetails(final int position, MyViewHolder myViewHolder, ModelClassForInquiryDetails.Inquiry modelClassForInquiryDetails) {
        myViewHolder.mTxtCourse.setText("");
        for(int i  = 0 ; i < modelClassForInquiryDetails.getCourses().size() ; i++){
            if(CommonUtil.isNullString(myViewHolder.mTxtCourse.getText().toString())){
                myViewHolder.mTxtCourse.setText(""+modelClassForInquiryDetails.getCourses().get(i).getName());
            }else{
                myViewHolder.mTxtCourse.setText(myViewHolder.mTxtCourse.getText().toString()+","
                +modelClassForInquiryDetails.getCourses().get(i).getName());
            }
        }
        myViewHolder.mTxtPersonName.setText(""+modelClassForInquiryDetails.getFname()+" "+modelClassForInquiryDetails.getLname());
        myViewHolder.mIvPersonImage.setImageResource(R.drawable.default_user_image);
    }

    @Override
    public int getItemCount() {

        return mArrlistInquiryDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final Context mContext;
        private ImageView mIvPersonImage;
        private TextView mTxtPersonName;
        private TextView mTxtCourse;
        private ImageView mIvMoreoptions;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            mTxtCourse = itemView.findViewById(R.id.tv_course);
            mIvPersonImage = itemView.findViewById(R.id.iv_person_image);
            mTxtPersonName = itemView.findViewById(R.id.tv_person_name);
            mIvMoreoptions = itemView.findViewById(R.id.iv_more_options);

            mContext = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, InquiryDetailsActivity.class);
                    intent.putExtra(KEY_INTENT_INQUIRY_ID,""+mArrlistInquiryDetails.get(getAdapterPosition()).getId());
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) mContext,
                                    mIvPersonImage,
                                    ViewCompat.getTransitionName(mIvPersonImage));
                    mContext.startActivity(intent,options.toBundle());
                }
            });

            mIvMoreoptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PopupMenu popupMenu = new PopupMenu(mContext,itemView);
                    popupMenu.setGravity(Gravity.END);
                    popupMenu.inflate(R.menu.options_menu);

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.menu1:
                                    String phone = "" + mArrlistInquiryDetails.get(getAdapterPosition()).getContact();
                                    mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)));
                                    return true;
                                case R.id.menu2:
                                    mContext.startActivity(new Intent(mContext, SmsandnotificationActivity.class));
                                    return true;
                                case R.id.menu3:
                                    final Dialog dialog2 = new Dialog(mContext);
                                    dialog2.setContentView(R.layout.dialog_for_notification);
                                    intialization();

                                    // sms category dialog
                                    textView = dialog2.findViewById(R.id.tv_sms_select);
                                    textView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog = new Dialog(mContext);
                                            dialog.setContentView(R.layout.custom_sms_type_layout);

                                            // sms category dialog
                                            txt = dialog.findViewById(R.id.vis);

                                            calApiToLoadSmsTYpeData();
                                            mSprcategories = dialog.findViewById(R.id.spinneer_predefined_sms);
                                            listview = dialog.findViewById(R.id.lv_predefined_sms_list);

                                            mSprcategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    if(position>0){
                                                        Toast.makeText(mContext, ""+smscategoriesresponse.getCategories().get(position-1).getName(), Toast.LENGTH_SHORT).show();
                                                        mStrSelectedSmsTypeId = ""+smscategoriesresponse.getCategories().get(position-1).getId();
                                                        txt.setVisibility(View.GONE);
                                                        callApiToLoadSmsMessagesCategoryWise();
                                                    }else{
                                                        txt.setVisibility(View.VISIBLE);
                                                        listview.setVisibility(View.GONE);
                                                    }
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            });
                                            dialog.show();
                                        }
                                    });
                                    // closes here also

                                    Button btnapply = dialog2.findViewById(R.id.btn_sms_apply);
                                    Button btncancel = dialog2.findViewById(R.id.btn_sms_cancel);

                                    btnapply.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(dialog2.isShowing() && dialog2!=null){
                                                Toast.makeText(mContext, "Successfully Applied", Toast.LENGTH_SHORT).show();
                                                dialog2.dismiss();
                                            }
                                        }
                                    });

                                    btncancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(dialog2.isShowing() && dialog2!=null){
                                                dialog2.dismiss();
                                            }
                                        }
                                    });

                                    mLvNotification = dialog2.findViewById(R.id.listviewnotificaiton);
                                    sampleData();
                                    notifcationAdapter = new NotifcationAdapter((Activity) mContext,
                                            mALnotificaitonList);
                                    mLvNotification.setAdapter(notifcationAdapter);

                                    mLvNotification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                            for(int i = 0 ; i < mLvNotification.getChildCount() ; i++){
                                                if(mLvNotification.getChildAt(position).toString().equals(
                                                        CommonUtil.getSharedPrefrencesInstance(mContext).getString(KEY_SMS_DAYS,"")
                                                )){
                                                    mLvNotification.getChildAt(position).setSelected(true);
                                                }else{
                                                    // dp nothing here
                                                }
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });

                                    dialog2.show();

                                    break;
                                case R.id.student_convert:
                                    Intent intent = new Intent(mContext, StudentActivity.class);
                                    intent.putExtra(KEY_INTENT_INQUIRY_ID,""+mArrlistInquiryDetails.get(getAdapterPosition()).getId());
                                    mContext.startActivity(intent);
                                default:
                                    return false;
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });
        }

        private void sampleData() {
            NotificationSettingsDataModel model1 = new NotificationSettingsDataModel();
            model1.setNotificationDuraiton("After 2 days");
            model1.setSelected(false);

            NotificationSettingsDataModel model2 = new NotificationSettingsDataModel();
            model2.setNotificationDuraiton("After 7 days");
            model2.setSelected(false);

            NotificationSettingsDataModel model3 = new NotificationSettingsDataModel();
            model3.setNotificationDuraiton("After 15 days");
            model3.setSelected(false);

            NotificationSettingsDataModel model4 = new NotificationSettingsDataModel();
            model4.setNotificationDuraiton("After 1 Month");
            model4.setSelected(false);

            mALnotificaitonList.add(model1);
            mALnotificaitonList.add(model2);
            mALnotificaitonList.add(model3);
            mALnotificaitonList.add(model4);
        }

        private void intialization() {
            mALnotificaitonList = new ArrayList<>();
        }
    }

    private void callApiToLoadSmsMessagesCategoryWise() {
        if(!CommonUtil.isInternetAvailable(activity)){
            return;
        }

        new OkHttpRequest(activity,
                OkHttpRequest.Method.POST,
                Constants.WS_SMS_CATEGORIES_WISE_MESSAGES,
                RequestParam.categoryWiseFilter(""+mStrSelectedSmsTypeId),
                RequestParam.getNull(),
                CODE_SMS_CATRGORIES_WISE_MSGS,
                false,this);
    }

    private void calApiToLoadSmsTYpeData() {
        if(!CommonUtil.isInternetAvailable(activity)){
            return;
        }

        new OkHttpRequest(activity,
                OkHttpRequest.Method.GET,
                Constants.WS_SMS_CATEGORIES,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_SMS_CATEGORIES,
                false,this);
    }

    @Override
    public void onOkHttpStart(int requestId) {

    }

    @Override
    public void onOkHttpSuccess(int requestId, int statusCode, String response) {
        Log.e("temperory >>>",""+response);
        if(response==null){
            return;
        }

        switch (requestId){
            case CODE_SMS_CATEGORIES:
                Log.e("Sms categories>>>",""+response);
                final Gson gsonone = new Gson();
                try{
                    smscategoriesresponse = gsonone.fromJson(response, Smscategoriesresponse.class);
                    if(smscategoriesresponse.getCategories()!=null && smscategoriesresponse.getCategories().size()>0){
                        setDatatospinnercategory(smscategoriesresponse.getCategories());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case CODE_SMS_CATRGORIES_WISE_MSGS:
                Log.e("msgs >>>",""+response);
                final Gson msgs = new Gson();
                try{
                    categoryWiseResponse = msgs.fromJson(response, CategoryWiseResponse.class);
                    if(categoryWiseResponse.getStatus().equals(Constants.SUCCESS_CODE)){
                        mAlcategorywisemsgs = categoryWiseResponse.getMessages();
                        setMessagesToListview();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }

    }

    private void setMessagesToListview() {
        SmsCategoryAdapter smsCategoryAdapter = new SmsCategoryAdapter(activity,mAlcategorywisemsgs);
        listview.setVisibility(View.VISIBLE);
        listview.setAdapter(smsCategoryAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String id = mAlcategorywisemsgs.get(position).getMessage();
                if(dialog!=null && dialog.isShowing()){
                    textView.setText(""+id);
                    dialog.dismiss();
                }
            }
        });
    }

    private void setDatatospinnercategory(ArrayList<Smscategoriesresponse.Category> categories) {
        mAlsmscategorylist = new ArrayList<>();
        if(categories.size()>0 && categories!=null){
            for(int i = 0 ; i < categories.size() ; i++){
                mAlsmscategorylist.add(""+categories.get(i).getName());
            }
        }

        mAlsmscategorylist.add(0,"Select Category From Here");
        ArrayAdapter<String> adapterStandard = new ArrayAdapter<>(activity,
                R.layout.my_spinner_item,R.id.tvCustomSpinner, mAlsmscategorylist);
        mSprcategories.setAdapter(adapterStandard);
    }

    @Override
    public void onOkHttpFailure(int requestId, int statusCode, String response, Throwable error) {

    }

    @Override
    public void onOkHttpFinish(int requestId) {

    }

}