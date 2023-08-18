package com.globalitians.inquiry.activities.InquiryReport.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.AddInquiry.models.ModelClassForCourses;
import com.globalitians.inquiry.activities.InquiryReport.Adapter.SampleCourseWiseAdapter;
import com.globalitians.inquiry.activities.InquiryReport.Model.ModelClassForInquiryDetails;
import com.globalitians.inquiry.activities.Utility.CommonUtil;
import com.globalitians.inquiry.activities.Utility.Constants;
import com.globalitians.inquiry.activities.network.OkHttpInterface;
import com.globalitians.inquiry.activities.network.OkHttpRequest;
import com.globalitians.inquiry.activities.network.RequestParam;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.globalitians.inquiry.activities.Utility.Constants.CODE_INQUIRY_FILTER;
import static com.globalitians.inquiry.activities.Utility.Constants.CODE_STUDENT_COURSES;

public class SampleCourseWiseFragment extends Fragment implements OkHttpInterface, SampleCourseWiseAdapter.CourseWiseClickListner {

    private ListView listView;
    private ModelClassForCourses modelClassForCoursesList=null;
    private SampleCourseWiseAdapter mAdapterCourseList = null;
    private ArrayList<ModelClassForCourses.Course> mAlcourselist;

    // api stuff will be called here
    private String strSelectedCourseIds="";

    public SampleCourseWiseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sample_course_wise, container, false);
        listView = view.findViewById(R.id.sample_course_wise_listview);
        callApiForLoadCourseData();
        return view;
    }

    // now part of api data will come in picture

    private void callApiForLoadCourseData() {
        if (!CommonUtil.isInternetAvailable(getContext())) {
            return;
        }

        new OkHttpRequest(getActivity(),
                OkHttpRequest.Method.GET,
                Constants.WS_COURSE_LIST,
                RequestParam.loadGetRequestsData(),
                RequestParam.getNull(),
                CODE_STUDENT_COURSES,
                false, this);
    }

    @Override
    public void onOkHttpStart(int requestId) {

    }

    @Override
    public void onOkHttpSuccess(int requestId, int statusCode, String response) {
        Log.e(">>>", "" + response);

        if (response == null) {
            return;
        }

        switch (requestId){
            case CODE_STUDENT_COURSES:
                Log.e("Courses >>", "" + response);
                final Gson studentCoursesList = new Gson();
                try {
                    mAlcourselist = new ArrayList<>();
                    modelClassForCoursesList = studentCoursesList.fromJson(response, ModelClassForCourses.class);
                    if (modelClassForCoursesList.getCourses() != null &&
                            modelClassForCoursesList.getCourses().size() > 0) {
                        mAlcourselist = modelClassForCoursesList.getCourses();
                        setCourseCourseListAdapter(mAlcourselist);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setCourseCourseListAdapter(ArrayList<ModelClassForCourses.Course> mArrCourseList) {
        mAdapterCourseList = new SampleCourseWiseAdapter(getActivity(),mArrCourseList,this);
        listView.setNestedScrollingEnabled(true);
        listView.setAdapter(mAdapterCourseList);
    }

    @Override
    public void onOkHttpFailure(int requestId, int statusCode, String response, Throwable error) {

    }

    @Override
    public void onOkHttpFinish(int requestId) {

    }

    @Override
    public void onCourseListChecking(int position, boolean value) {
        mAlcourselist.get(position).isSelected = value;
    }

    @SuppressLint("NewApi")
    public String world(){
        if(mAlcourselist!=null && mAlcourselist.size()>0){
            StringBuilder stringBuilder = new StringBuilder("");
            int size = mAlcourselist.size();
            for(int i = 0 ; i < size ; i++){
                if(mAlcourselist.get(i).isSelected()){
                    stringBuilder = stringBuilder.append(mAlcourselist.get(i).getId()+",");
                }
            }
            strSelectedCourseIds = stringBuilder.toString();
            if(strSelectedCourseIds.endsWith(",")){
                strSelectedCourseIds = strSelectedCourseIds.substring(0,strSelectedCourseIds.length()-1);
            }
        }else{
            strSelectedCourseIds="";
        }
        return strSelectedCourseIds;
    }

    public interface CourseResponse{
        void onCourseReponseToInquiry(ArrayList<ModelClassForInquiryDetails.Inquiry> marrlistfilteredcoursewise,int flagValue);
    }
}
