package com.globalitians.inquiry.activities.UpcomingReport.Adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.MediaRouteButton;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.activities.AddinquiryActvity;
import com.globalitians.inquiry.activities.Dashboard.Activities.DashboardActivity;
import com.globalitians.inquiry.activities.NotificationSettings.Adapter.NotifcationAdapter;
import com.globalitians.inquiry.activities.NotificationSettings.Model.NotificationSettingsDataModel;
import com.globalitians.inquiry.activities.SmsAndNotification.Activities.SmsandnotificationActivity;
import com.globalitians.inquiry.activities.SmsAndNotification.Adapters.SmsCategoryAdapter;
import com.globalitians.inquiry.activities.SmsAndNotification.Models.CategoryWiseResponse;
import com.globalitians.inquiry.activities.SmsAndNotification.Models.Smscategoriesresponse;
import com.globalitians.inquiry.activities.UpcomingReport.Activities.UpcomingReportActivity;
import com.globalitians.inquiry.activities.UpcomingReport.Model.FeedbackUpdateModel;
import com.globalitians.inquiry.activities.UpcomingReport.Model.ModelClassFeedback;
import com.globalitians.inquiry.activities.UpcomingReport.Model.UpcomingDataModel;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.Utility.LogUtil;
import com.globalitians.inquiry.activities.network.OkHttpInterface;
import com.globalitians.inquiry.activities.network.OkHttpRequest;
import com.globalitians.inquiry.activities.network.RequestParam;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import static com.globalitians.inquiry.activities.Utility.Constants.CODE_FEEDBACK_LIST;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_FEEDBACK_LIST_UPATE;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_SMS_CATEGORIES;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_SMS_CATRGORIES_WISE_MSGS;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_UPCOMING_INQUIRIES;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_UPDATE_DATE;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_SMS_DAYS;

public class UpcomingActivityAdapter extends RecyclerView.Adapter<UpcomingActivityAdapter.MyViewHolder>
    implements OkHttpInterface {

    private Activity mContext;
    private UpcomingActivityAdapter.OnUpcomingListListener mupcomingListListener;
    private ArrayList<UpcomingDataModel.Inquiry> mupcomingActivityListData;
    private UpcomingDataModel.Inquiry upcomingDataModel;

    // another data just
    private DatePickerDialog mDatePickerDialogJoinedDate = null;
    private EditText mEdtupcoming_date;
    private ListView listView;
    private FeedBackAdapter feedBackAdapter=null;
    private FloatingActionButton floatingActionButton;

    private ListView mLvNotification;
    private ArrayList<NotificationSettingsDataModel> mALnotificaitonList=null;
    private NotifcationAdapter notifcationAdapter;

    private ArrayList<String> mAlsmscategorylist;
    private Smscategoriesresponse smscategoriesresponse;

    private CategoryWiseResponse categoryWiseResponse;
    private ArrayList<CategoryWiseResponse.Message> mAlcategorywisemsgs;

    // feedback things
    private String inquiry_id = "";
    private ModelClassFeedback modelClassFeedback;
    private Dialog dialog1=null;
    private Dialog dialog3=null;
    private String update_fedback="";
    private EditText editText;
    private FeedbackUpdateModel feedbackUpdateModel;
    private Button btnupdate;
    private String mStrSelectedSmsTypeId="";
    private Spinner mSprcategories;
    private ListView listview;
    private TextView txt;
    private TextView textView;
    private Dialog dialog;
    private UpcomingDataModel upcomingUpdateDataModel;
    private TextView mTxtnofeedback;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.upcoming_report_items_list,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    public UpcomingActivityAdapter(Activity activity, ArrayList<UpcomingDataModel.Inquiry> mupcomingActivityListData, UpcomingActivityAdapter.OnUpcomingListListener mupcomingListListener){
        this.mupcomingActivityListData = mupcomingActivityListData;
        this.mupcomingListListener = mupcomingListListener;
        this.mContext = activity;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        try{
            upcomingDataModel = mupcomingActivityListData.get(position);
            setUpcomingInquiryDetails(position, myViewHolder, upcomingDataModel);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setUpcomingInquiryDetails(int position, MyViewHolder myViewHolder, UpcomingDataModel.Inquiry upcomingDataModel) {
        myViewHolder.mTxtCourse.setText("" );
        for(int i  = 0 ; i < upcomingDataModel.getCourses().size() ; i++){
            if(CommonUtil.isNullString(myViewHolder.mTxtCourse.getText().toString())){
                myViewHolder.mTxtCourse.setText(""+upcomingDataModel.getCourses().get(i).getName());
            }else{
                myViewHolder.mTxtCourse.setText(myViewHolder.mTxtCourse.getText().toString()+","
                        +upcomingDataModel.getCourses().get(i).getName());
            }
        }
        myViewHolder.mTxtPersonName.setText(""+upcomingDataModel.getFname()+" "+upcomingDataModel.getLname());
        myViewHolder.mIvPersonImage.setImageResource(R.drawable.default_user_image);
    }

    @Override
    public int getItemCount() {
        return mupcomingActivityListData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView mIvPersonImage;
        private TextView mTxtPersonName;
        private TextView mTxtCourse;
        private ImageView mIvMoreoptions;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            mTxtPersonName = itemView.findViewById(R.id.tv_person_name);
            mTxtCourse = itemView.findViewById(R.id.tv_course);
            mIvPersonImage = itemView.findViewById(R.id.iv_person_image);
            mIvMoreoptions = itemView.findViewById(R.id.iv_more_options);

            mIvMoreoptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mupcomingListListener.onMoreOptionsClick(getAdapterPosition(),mIvMoreoptions);

                    final PopupMenu popupMenu = new PopupMenu(mContext,itemView);
                    popupMenu.setGravity(Gravity.END);
                    popupMenu.inflate(R.menu.upcoming_data_options);

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.menu1:
                                    String phone = mupcomingActivityListData.get(getAdapterPosition()).getContact();
                                    mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phone,null)));
                                    return true;
                                case R.id.menu2:
                                    mContext.startActivity(new Intent(mContext, SmsandnotificationActivity.class));
                                    return true;
                                case R.id.menu3:
                                    final Dialog dialog2 = new Dialog(mContext);
                                    dialog2.setContentView(R.layout.dialog_for_notification);
                                    intialization();

                                    // open a sms select category dialog
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
                                    notifcationAdapter = new NotifcationAdapter(mContext,
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
                                    return true;
                                case R.id.menu4:
                                    inquiry_id = ""+mupcomingActivityListData.get(getAdapterPosition()).getId();
                                    callApiToLoadFeebackList();
                                    return true;
                                case R.id.upcoming_date:
                                    final Dialog dialog = new Dialog(mContext);
                                    dialog.setContentView(R.layout.upcoming_date_dialog);

                                    btnupdate = dialog.findViewById(R.id.btn_update);
                                    mEdtupcoming_date = dialog.findViewById(R.id.edt_upcoming_date);
                                    inquiry_id = ""+mupcomingActivityListData.get(getAdapterPosition()).getId();
                                    callApitoloadupcominginquiries();

                                    initializeDatePickers();
                                    if(mEdtupcoming_date.getText().toString().trim()!=null){
                                        btnupdate.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                callApitoupdatedata(inquiry_id,mEdtupcoming_date.getText().toString().trim());
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    dialog.show();
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

    private void callApiToLoadSmsMessagesCategoryWise() {
        if(!CommonUtil.isInternetAvailable(mContext)){
            return;
        }

        new OkHttpRequest(mContext,
                OkHttpRequest.Method.POST,
                Constants.WS_SMS_CATEGORIES_WISE_MESSAGES,
                RequestParam.categoryWiseFilter(""+mStrSelectedSmsTypeId),
                RequestParam.getNull(),
                CODE_SMS_CATRGORIES_WISE_MSGS,
                false,this);
    }

    private void calApiToLoadSmsTYpeData() {
        if(!CommonUtil.isInternetAvailable(mContext)){
            return;
        }

        new OkHttpRequest(mContext,
                OkHttpRequest.Method.GET,
                Constants.WS_SMS_CATEGORIES,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_SMS_CATEGORIES,
                false,this);
    }

    private void callApitoloadupcominginquiries() {
        if(!CommonUtil.isInternetAvailable(mContext)){
            return;
        }

        new OkHttpRequest((mContext),
                OkHttpRequest.Method.GET,
                Constants.WS_UPCOMING_INQUIRIES,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_UPCOMING_INQUIRIES,
                false, this);
        }

    private void callApitoupdatedata(String inquiry_id,String date) {
        if(!CommonUtil.isInternetAvailable(mContext)){
            return;
        }

        new OkHttpRequest(mContext,
                OkHttpRequest.Method.POST,
                Constants.WS_UPDATE_UPCOMING_DATE,
                RequestParam.updateDate(""+inquiry_id,""+date),
                RequestParam.getNull(),
                CODE_UPDATE_DATE,
                true,this);
    }

    private void callApiToLoadFeebackList() {
        if(!CommonUtil.isInternetAvailable(mContext)){
            return;
        }

        new OkHttpRequest(mContext,
                OkHttpRequest.Method.POST,
                Constants.WS_FEEDBACK_LIST,
                RequestParam.setlistfeedback(""+inquiry_id),
                RequestParam.getNull(),
                CODE_FEEDBACK_LIST,
                true,this);
    }

    private void intialization() {
        mALnotificaitonList = new ArrayList<>();
    }

    private void sampleData() {
        NotificationSettingsDataModel model1 = new NotificationSettingsDataModel();
        model1.setNotificationDuraiton("after 2 days");
        model1.setSelected(false);

        NotificationSettingsDataModel model2 = new NotificationSettingsDataModel();
        model2.setNotificationDuraiton("after 7 days");
        model2.setSelected(false);

        NotificationSettingsDataModel model3 = new NotificationSettingsDataModel();
        model3.setNotificationDuraiton("after 15 days");
        model3.setSelected(false);

        NotificationSettingsDataModel model4 = new NotificationSettingsDataModel();
        model4.setNotificationDuraiton("after 30 days");
        model4.setSelected(false);

        mALnotificaitonList.add(model1);
        mALnotificaitonList.add(model2);
        mALnotificaitonList.add(model3);
        mALnotificaitonList.add(model4);
    }

    private void initializeDatePickers() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener joinedDateListener;
        joinedDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if ((month + 1) < 10 && dayOfMonth < 10) {
                    mEdtupcoming_date.setText("0" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if ((month + 1) < 10) {
                    mEdtupcoming_date.setText("" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if (dayOfMonth < 10) {
                    mEdtupcoming_date.setText("0" + dayOfMonth + "-" + (month + 1) + "-" + year);
                } else {
                    mEdtupcoming_date.setText("" + dayOfMonth + "-" + (month + 1) + "-" + year);
                }
            }
        };

        mDatePickerDialogJoinedDate = new DatePickerDialog(
                mContext,R.style.DialogTheme, joinedDateListener, year, month, day);
        mDatePickerDialogJoinedDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        mEdtupcoming_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePickerDialogJoinedDate.show();
            }
        });
    }

    public interface OnUpcomingListListener {
        void onMoreOptionsClick(int adapterPosition, ImageView ivMoreOptions);
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
            case CODE_UPCOMING_INQUIRIES:
                Log.e("Updated Date",""+response);
                final Gson updateDategson = new Gson();
                try{
                    upcomingUpdateDataModel = updateDategson.fromJson(response,UpcomingDataModel.class);
                    mupcomingActivityListData = new ArrayList<>();
                    if(upcomingUpdateDataModel.getStatus().equals(Constants.SUCCESS_CODE)){
                        if(upcomingUpdateDataModel.getInquiries()!=null &&
                            upcomingUpdateDataModel.getInquiries().size()>0){
                            mupcomingActivityListData = upcomingUpdateDataModel.getInquiries();

                            for(int i = 0 ; i < mupcomingActivityListData.size() ; i++){
                                if(inquiry_id.equalsIgnoreCase(""+mupcomingActivityListData.get(i).getId())){
                                    mEdtupcoming_date.setText(""+mupcomingActivityListData.get(i).getUpcomingConfirmDate());
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case CODE_FEEDBACK_LIST:
                Log.e("Fedback list>>>",""+response);
                final Gson gson = new Gson();
                try{
                    modelClassFeedback = gson.fromJson(response,ModelClassFeedback.class);
                    if(modelClassFeedback.getStatus().equals(Constants.SUCCESS_CODE)){
                        setFeebacklist(modelClassFeedback.getFeedbacks());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case CODE_FEEDBACK_LIST_UPATE:
                Log.e("Update>>>",""+response);
                final Gson gson1 = new Gson();
                try{
                    feedbackUpdateModel = gson1.fromJson(response,FeedbackUpdateModel.class);
                    if(feedbackUpdateModel.getStatus().equals(Constants.SUCCESS_CODE)){
                        Toast.makeText(mContext, ""+feedbackUpdateModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case CODE_UPDATE_DATE:
                Log.e("date>>>",""+response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject!=null && jsonObject.has("status")){
                        if(jsonObject.getString("status").equalsIgnoreCase("success")){
                            Toast.makeText(mContext, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
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
        SmsCategoryAdapter smsCategoryAdapter = new SmsCategoryAdapter(mContext,mAlcategorywisemsgs);
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
        ArrayAdapter<String> adapterStandard = new ArrayAdapter<>(mContext,
                R.layout.my_spinner_item,R.id.tvCustomSpinner, mAlsmscategorylist);
        mSprcategories.setAdapter(adapterStandard);
    }

    private void setFeebacklist(ArrayList<ModelClassFeedback.Feedback> feedbacks) {
        callDialogBox(feedbacks);
    }

    private void callDialogBox(ArrayList<ModelClassFeedback.Feedback> feedbacks) {
        dialog1 = new Dialog(mContext);
        dialog1.setContentView(R.layout.feedback_dialog);
        mTxtnofeedback = dialog1.findViewById(R.id.tv_no_feedback);

        if(feedbacks!=null && feedbacks.size()>0){
            listView = dialog1.findViewById(R.id.lv_feedback);
            feedBackAdapter = new FeedBackAdapter(mContext,feedbacks);
            listView.setAdapter(feedBackAdapter);
            mTxtnofeedback.setVisibility(View.GONE);
        }else{
            CommonUtil.playSoundForAttendance("No Feedback is found",mContext);
            mTxtnofeedback.setVisibility(View.VISIBLE);
        }

        dialog1.show();

        floatingActionButton = dialog1.findViewById(R.id.fabupcoming_inquiry);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog3 = new Dialog(mContext);
                dialog3.setContentView(R.layout.feedback_update_layout);
                editText = dialog3.findViewById(R.id.edt_feedback_update);
                dialog3.show();

                final Button update_btn = dialog3.findViewById(R.id.btn_update);
                update_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        update_fedback = ""+editText.getText().toString().trim();
                        if(CommonUtil.isNullString(update_fedback)){
                            Toast.makeText(mContext, "Do not pass null feedback", Toast.LENGTH_SHORT).show();
                        }else{
                            callApitoupdatefeedbacklist(update_fedback);
                            if(dialog3!=null && dialog3.isShowing()){
                                callApiToLoadFeebackList();
                                dialog3.dismiss();
                            }
                        }
                    }
                });

                final Button cancel_btn = dialog3.findViewById(R.id.btn_cancel);
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dialog3!=null && dialog3.isShowing()){
                            dialog3.dismiss();
                        }
                    }
                });
            }
        });
    }

    private void callApitoupdatefeedbacklist(String update_fedback) {
        if(!CommonUtil.isInternetAvailable(mContext)){
            return;
        }

        new OkHttpRequest(mContext,
                OkHttpRequest.Method.POST,
                Constants.WS_FEEDBACK_LIST_UPDATE,
                RequestParam.updateFeedbackList(""+inquiry_id,
                        ""+update_fedback),
                RequestParam.getNull(),
                CODE_FEEDBACK_LIST_UPATE,
                false,this);
    }

    @Override
    public void onOkHttpFailure(int requestId, int statusCode, String response, Throwable error) {

    }

    @Override
    public void onOkHttpFinish(int requestId) {

    }
}