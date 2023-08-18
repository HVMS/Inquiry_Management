package com.globalitians.inquiry.activities.network;


import android.util.Log;

import com.globalitians.inquiry.activities.Student.model.AddNewCourseModelWhileAddingNewStudent;
import com.globalitians.inquiry.activities.Utility.CommonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RequestParam {

    private static String TAG = "RequestParam";

    public static Map<String, String> getNull() {
        Map<String, String> mParam = new HashMap<String, String>();
        return mParam;
    }

    public static Map<String, String> loadGetRequestsData() {
        Map<String, String> requestBody = new HashMap<>();
        return requestBody;
    }

    public static Map<String, String> deleteStudent(String id, String branch_id) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", "" + id);
        requestBody.put("branch_id", branch_id);
        return requestBody;
    }

    public static Map<String, String> inquiriesList(String branch_id) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("branch_id", branch_id);
        return requestBody;
    }

    public static Map<String, String> fees_list(String student_id) {
        Map<String, String> requestBody = new HashMap<>();
        if (!CommonUtil.isNullString(student_id)) {
            requestBody.put("student_id", "" + student_id);
        }
        return requestBody;
    }

    public static Map<String, String> loadAllPaymentList(String date, String month,
                                                         String year, String student_id,
                                                         String start_date, String end_date,
                                                         String branch_id) {
        Map<String, String> requestBody = new HashMap<>();
        if (!CommonUtil.isNullString(date)) {
            requestBody.put("date", date);
        } else if (!CommonUtil.isNullString(month)) {
            requestBody.put("month", month);
            requestBody.put("year", year);
        } else if (!CommonUtil.isNullString(year)) {
            requestBody.put("year", year);
        } else if (!CommonUtil.isNullString(start_date)) {
            requestBody.put("start_date", start_date);
            requestBody.put("end_date", end_date);
        }

        if (!CommonUtil.isNullString(student_id)) {
            requestBody.put("student_id", student_id);
        }

        requestBody.put("branch_id", branch_id);

        return requestBody;
    }

    public static Map<String, String> deleteCourse(String id, String branch_id) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", "" + id);
        requestBody.put("branch_id", branch_id);
        return requestBody;
    }

    public static Map<String, String> deleteInquiries(String slug, String branch_id) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("slug", "" + slug);
        requestBody.put("branch_id", branch_id);
        return requestBody;
    }

    public static Map<String, String> loadCourseDetails(String course_id, String branch_id) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", "" + course_id);
        requestBody.put("branch_id", branch_id);
        return requestBody;
    }

    public static Map<String, String> studentList(String branch_id) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("branch_id", branch_id);
        return requestBody;
    }

    public static Map<String, String> temp() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("partnerid", "gitp1006");
        requestBody.put("password", "123456");
        return requestBody;
    }

    public static Map<String, String> searchFilterStudent(String search, String branch_id) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("search", search);
        requestBody.put("branch_id", branch_id);
        return requestBody;
    }


    public static Map<String, String> studentDetails(String id, String branch_id) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", "" + id);
        requestBody.put("branch_id", branch_id);
        return requestBody;
    }

    public static Map<String, String> inquiryDetails(String id, String branch_id) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", "" + id);
        requestBody.put("branch_id", branch_id);
        return requestBody;
    }

    public static Map<String, String> loginEmployee(String username, String password) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "" + username);
        requestBody.put("password", "" + password);
        return requestBody;
    }

    public static Map<String, String> studentSubjetcs(String mStrSelectedStandardId) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("standard_id",mStrSelectedStandardId);
        return requestBody;
    }

    public static Map<String, String> studentStream(String standard_id) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("standard_id", standard_id);
        return requestBody;
    }

    public static Map<String, String> postAttendance(String student_id,
                                                     String in_out_val,
                                                     String branch_id) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("student_id", "" + student_id);
        requestBody.put("branch_id", branch_id);
        if (in_out_val.equalsIgnoreCase("in")) {
            requestBody.put("out", "" + "1");
        } else {
            requestBody.put("in", "" + "1");
        }
        return requestBody;
    }

    public static Map<String, String> submitPostInquiry(String fname,
                                                    String lname,
                                                    String branch_id,
                                                    String feedback,
                                                    String reference,
                                                    String mobileNo,
                                                    String partner_id,
                                                    String courseId,
                                                    String standardId,
                                                    String streamId,
                                                    String subjectId,
                                                    String incoming_date,
                                                    String slug,
                                                    String upcoming_date,
                                                    String msg_notificaiton,
                                                    String msg_id) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("fname", "" + fname);
        requestBody.put("lname", "" + lname);
        requestBody.put("branch_id",""+branch_id);
        requestBody.put("feedback", "" + feedback);
        requestBody.put("reference", "" + reference);
        requestBody.put("mobileno", "" + mobileNo);
        requestBody.put("partner_id",""+partner_id);
        requestBody.put("course_id", "" + courseId);
        requestBody.put("standard_id","" + standardId);
        requestBody.put("stream_id",""+streamId);
        requestBody.put("subject_id",""+subjectId);
        if (!CommonUtil.isNullString(slug)) {
            requestBody.put("slug", "" + slug);//slug to update inquiry
        }
        requestBody.put("date",""+incoming_date);
        requestBody.put("upcoming_confirm_date",""+upcoming_date);
        requestBody.put("message_notification",""+msg_notificaiton);
        requestBody.put("smsmessage_id",""+msg_id);
        return requestBody;
    }

    public static Map<String, String> addNewCourse(String name,
                                                   String cat_id,
                                                   String duration,
                                                   String fees,
                                                   String branch_id) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("category_id", cat_id);
        requestBody.put("duration", duration);
        requestBody.put("fees", fees);
        requestBody.put("branch_id", branch_id);
        return requestBody;
    }

    public static Map<String, String> testList() {
        Map<String, String> requestBody = new HashMap<>();
        return requestBody;
    }

    public static Map<String, String> searchStudent(String strSearch, String branch_id) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("search", strSearch);
        requestBody.put("branch_id", branch_id);
        return requestBody;
    }

    public static Map<String, String> makeOutEntry(String attendence_id, String time, String branch_id) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("attendence_id", attendence_id);//17
        requestBody.put("time", time);//10:50
        requestBody.put("branch_id", branch_id);
        return requestBody;
    }

    public static Map<String, String> postPayment(String user_id,
                                                  String date,
                                                  String amount,
                                                  String student_id,
                                                  String payment_type,
                                                  String check_no) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", user_id);
        requestBody.put("date", date);
        requestBody.put("payment", amount);
        requestBody.put("student_id", student_id);
        requestBody.put("payment_type", payment_type);
        if (payment_type.equalsIgnoreCase("cheque")) {
            requestBody.put("check_no", check_no);
        }
        return requestBody;
    }

    public static Map<String, String> loadAttendenceList(String date, String month,
                                                         String year, String student_id,
                                                         String start_date, String end_date,
                                                         String out_required, String branch_id) {
        Map<String, String> requestBody = new HashMap<>();
        if (!CommonUtil.isNullString(date)) {
            requestBody.put("date", date);
        } else if (!CommonUtil.isNullString(month)) {
            requestBody.put("month", month);
            requestBody.put("year", year);
        } else if (!CommonUtil.isNullString(year)) {
            requestBody.put("year", year);
        } else if (!CommonUtil.isNullString(start_date)) {
            requestBody.put("start_date", start_date);
            requestBody.put("end_date", end_date);
        }

        if (!CommonUtil.isNullString(student_id)) {
            requestBody.put("student_id", student_id);
        }

        if (!CommonUtil.isNullString(out_required)) {
            requestBody.put("out_required", out_required);
        }
        requestBody.put("branch_id", branch_id);

        return requestBody;
    }

    /*public static Map<String, String> addNewStudent(String studentId,
                                                    String fname,
                                                    String lname,
                                                    String email,
                                                    String password,
                                                    String mobileNo,
                                                    String birthDate,
                                                    String branchId,
                                                    String parentName,
                                                    String parentMobile,
                                                    String referenceBy,
                                                    String joiningDate,
                                                    String address,
                                                    ArrayList<AddNewCourseModelWhileAddingNewStudent> listStudentCourseData) {
        Map<String, String> requestBody = new HashMap<>();
        if (!CommonUtil.isNullString(studentId)) {
            //EDIT STUDENT
            requestBody.put("id", studentId);
        } else {
            //password and cpassword will pass only at the time of ADD new student
            //not at the time of EDIT student.
            requestBody.put("password", password);
            requestBody.put("cpassword", password);
        }
        requestBody.put("fname", fname);
        requestBody.put("lname", lname);
        requestBody.put("mobileno", mobileNo);
        requestBody.put("dob", birthDate);
        requestBody.put("branch_id", branchId);
        requestBody.put("parentname", parentName);
        requestBody.put("parentmobileno", parentMobile);
        requestBody.put("reference", referenceBy);
        requestBody.put("joining_date", joiningDate);
        requestBody.put("address", address);
        requestBody.put("email", email);
        requestBody.put("installment_date", "15-10-2019");
        for (int i = 0; i < listStudentCourseData.size(); i++) {
            requestBody.put("fees[" + i + "]", listStudentCourseData.get(i).getStrFees());
            requestBody.put("course_id[" + i + "]", listStudentCourseData.get(i).getStrCourseId());
            requestBody.put("duration[" + i + "]", listStudentCourseData.get(i).getStrDuration());
            requestBody.put("course_status[" + i + "]", listStudentCourseData.get(i).getStrCourseStatus());
            requestBody.put("certificate[" + i + "]", listStudentCourseData.get(i).getStrCertificate());
            requestBody.put("book_material[" + i + "]", listStudentCourseData.get(i).getStrBookMaterial());
            requestBody.put("bag[" + i + "]", listStudentCourseData.get(i).getStrBag());
        }
        return requestBody;
    }*/

    public static Map<String, String> addNewStudent(String studentId,
                                                    String fname,
                                                    String lname,
                                                    String email,
                                                    String password,
                                                    String mobileNo,
                                                    String gender,
                                                    String username,
                                                    String birthDate,
                                                    String branchId,
                                                    String parentName,
                                                    String parentMobile,
                                                    String referenceBy,
                                                    String joiningDate,
                                                    String address,
                                                    ArrayList<AddNewCourseModelWhileAddingNewStudent> listStudentCourseData,
                                                    String partnerId,
                                                    String id) {
        Map<String, String> requestBody = new HashMap<>();
        if (!CommonUtil.isNullString(studentId)) {
            //EDIT STUDENT
            requestBody.put("id", studentId);
        } else {
            //password and cpassword will pass only at the time of ADD new student
            //not at the time of EDIT student.
            requestBody.put("password", password);
            requestBody.put("cpassword", password);
        }
        requestBody.put("fname", fname);
        requestBody.put("lname", lname);
        requestBody.put("mobileno", mobileNo);
        requestBody.put("dob", birthDate);
        requestBody.put("branch_id", branchId);
        requestBody.put("parentname", parentName);
        requestBody.put("gender",""+gender);
        requestBody.put("username",""+username);
        requestBody.put("parentmobileno", parentMobile);
        requestBody.put("reference", referenceBy);
        requestBody.put("joining_date", joiningDate);
        requestBody.put("address", address);
        requestBody.put("email", email);
        requestBody.put("installment_date", "15-10-2019");
        requestBody.put("partner_id",""+partnerId);
        requestBody.put("inquiry_id",""+id);
        for (int i = 0; i < listStudentCourseData.size(); i++) {
            requestBody.put("fees[" + i + "]", listStudentCourseData.get(i).getStrFees());
            requestBody.put("course_id[" + i + "]", listStudentCourseData.get(i).getStrCourseId());
            requestBody.put("duration[" + i + "]", listStudentCourseData.get(i).getStrDuration());
            requestBody.put("course_status[" + i + "]", listStudentCourseData.get(i).getStrCourseStatus());
            requestBody.put("certificate[" + i + "]", listStudentCourseData.get(i).getStrCertificate());
            requestBody.put("book_material[" + i + "]", listStudentCourseData.get(i).getStrBookMaterial());
            requestBody.put("bag[" + i + "]", listStudentCourseData.get(i).getStrBag());
        }
        return requestBody;
    }

    public static Map<String, File> addNewCourseImageParam(File fileCourseImage,
                                                           File fileCoursePdf) {
        Map<String, File> requestBody = new HashMap<>();

        if (fileCourseImage != null) {
            requestBody.put("picfile", fileCourseImage);
        }

        if (fileCoursePdf != null) {
            requestBody.put("syllabusfile", fileCoursePdf);
        }
        //requestBody.put("branch_id",branch_id);

        return requestBody;
    }


    public static Map<String, File> addNewStudentImageParam(ArrayList<File> file) {
        Map<String, File> requestBody = new HashMap<>();
        for (int i = 0; i < file.size(); i++) {
            Log.e(">>> register >>", "" + file.get(i));
            requestBody.put("profileimage", file.get(i));
            //requestBody.put("register_user_photo" + i, file.get(i));
        }
        return requestBody;
    }

    public static Map<String, File> registerUserPhoto(ArrayList<File> file) {
        Map<String, File> requestBody = new HashMap<>();
        for (int i = 0; i < file.size(); i++) {
            Log.e(">>> register >>", "" + file.get(i));
            requestBody.put("register_user_photo" + i, file.get(i));
        }
        return requestBody;
    }

    public static Map<String, String> checkMessageDays(String incoming_date, String upcoming_date) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("date",""+incoming_date);
        requestBody.put("upcoming_date",""+upcoming_date);
        return requestBody;
    }

    public static Map<String, String> inquirydetails(String strSelectedInquiryId) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("id", strSelectedInquiryId);
        return requestBody;
    }

    public static Map<String, String> applyFilters(String strSelectedstartdate,
                                                   String strSelectedRangefirstdate,
                                                   String strSelectedRangelastdate,
                                                   String course_id,
                                                   String standard_id,
                                                   String branch_id) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("date",""+strSelectedstartdate);
        requestBody.put("start_date",""+strSelectedRangefirstdate);
        requestBody.put("end_date",""+strSelectedRangelastdate);
        requestBody.put("courses_id",""+course_id);
        requestBody.put("standard_id",""+standard_id);
        requestBody.put("branch_id",""+branch_id);
        return requestBody;
    }

    public static Map<String, String> filterbottomsheet(String strSelectedstartdate) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("date",""+strSelectedstartdate);
        return requestBody;
    }

    public static Map<String, String> todayFilter(String today,String tommorow,String withinsevendays) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("today",""+today);
        requestBody.put("tomorrow",""+tommorow);
        requestBody.put("7days",""+withinsevendays);
        return requestBody;
    }

    public static Map<String,String> standardWiseFilter(String standardIds,String courses_id) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("standard_id",""+standardIds);
        requestBody.put("courses_id",""+courses_id);
        return requestBody;
    }

    public static Map<String,String> OnlystandardWiseFilter(String standardIds) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("standard_id",""+standardIds);
        return requestBody;
    }

    public static Map<String,String> categoryWiseFilter(String category_id) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("smscategory_id",""+category_id);
        return requestBody;
    }

    public static Map<String, String> setlistfeedback(String inquiry_id) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("inquiry_id",""+inquiry_id);
        return requestBody;
    }

    public static Map<String, String> updateFeedbackList(String inquiry_id,String Feedback) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("inquiry_id",""+inquiry_id);
        requestBody.put("feedback",""+Feedback);
        return requestBody;
    }

    public static Map<String,String> updateDate(String id, String date) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("id",""+id);
        requestBody.put("upcoming_confirm_date",""+date);
        return requestBody;
    }

    public static Map<String, String> dateBeetween(String start, String end) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("start_date",""+start);
        requestBody.put("end_date",""+end);
        return requestBody;
    }

    public static Map<String, String> courseWiseData(String coursesId) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("courses_id",""+coursesId);
        return requestBody;
    }

    public static Map<String,String> standardWiseData(String standardId){
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("standard_id",""+standardId);
        return requestBody;
    }

    public static Map<String, String> searchInquiry(String search) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("search",""+search);
        return requestBody;
    }

    public static Map<String,String> searchUpcomingInquiries(String search) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("search",""+search);
        return requestBody;
    }

    public static Map<String, String> checkUsername(String username) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("username",""+username);
        return requestBody;
    }

    public static Map<String, String> getSearchFromFilteredItems(String ids, String search) {
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("courses_id",""+ids);
        requestBody.put("search",""+search);
        return requestBody;
    }

    /* sms and notification stuff */

    /*
        user: Your login username.
        password: Your login password.
        msisdn: Single mobile number or multiple mobile numbers separated by comma(10 digits or +91).
        sid: Approved sender id(Only 6 characters).
        msg: Your message content(Minimum 459 characters/3 messages).
        fl: if flash message then 1 or else 0
        gwid: 2 (its for Transactions route.
        Note: Only 100 mobile numbers are allowed.
    */

}