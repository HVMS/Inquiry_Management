package com.globalitians.inquiry.activities.Utility;

import com.globalitians.inquiry.R;

public class Constants {

    public static final String LOGIN_PREFRENCES = "LoginPrefrences";
    public static final String KEY_IS_LOGGED_IN="isLoggedIn";
    public static final String KEY_IS_ACTION_VOICE="isActionVoice";
    public static final String KEY_EMPLOYEE_BRANCH_ID="login_branch_id";
    public static final String KEY_SMS_DAYS="check_msg_days";
    public static final String KEY_LOGGEDIN_EMPLOYEE_ID="loggedin_user_id";
    public static final String KEY_LOGGEDIN_EMPLOYEE_USERNAME="loggedin_user_name";
    public static final String KEY_LOGGEDIN_EMPLOYEE_NAME="loggedin_name";
    public static final String KEY_LOGGEDIN_EMPLOYEE_IMAGE="loggedin_image";
    public static final String KEY_EMPLOYEE_STANDARD_ID="standard_id";
    public static final String KEY_FILTER_BY="filterBy";
    public static final String KEY_OUT_REQUIRED="out_requird";
    public static final String KEY_FILTER_SELECTED_DATE="selected_date";
    public static final String KEY_FILTER_START_DATE="start_date";
    public static final String KEY_FILTER_END_DATE="end_date";
    public static final String KEY_FILTER_MONTH="month";
    public static final String KEY_FILTER_MONTH_YEAR="month_year";
    public static final String KEY_FILTER_YEAR="year";

    public static final String KEY_FEES_FILTER_BY="filterBy";
    public static final String KEY_FEES_FILTER_SELECTED_DATE="selected_date";
    public static final String KEY_FEES_FILTER_START_DATE="start_date";
    public static final String KEY_FEES_FILTER_END_DATE="end_date";
    public static final String KEY_FEES_FILTER_MONTH="month";
    public static final String KEY_FEES_FILTER_MONTH_YEAR="month_year";
    public static final String KEY_FEES_FILTER_YEAR="year";



    public static final String BASE_URL="http://globalitians.com/demoapp/inquiry/json/";

    public static final String WS_INQUIRY_LOGIN=BASE_URL+"loginemployee";
    public static final String WS_COURSE_LIST=BASE_URL+"courses";
    public static final String WS_COURSE_STANDARD=BASE_URL+"standards";
    public static final String WS_COURSE_STREAM = BASE_URL+"streams";
    public static final String WS_SUBJECT_LIST = BASE_URL+"subjects";
    public static final String WS_ADD_STUDENT=BASE_URL+"add_student";
    public static final String WS_CHECK_USERNAME=BASE_URL+"check_username";
    public static final String WS_COURSE_DETAILS=BASE_URL+"course_detail";
    public static final String WS_CHECK_MESSAGE_DAYS = BASE_URL+"check_message_days";
    public static final String WS_INQUIRIES=BASE_URL+"inquiries";
    public static final String WS_UPCOMING_INQUIRIES=BASE_URL+"upcoming_inquiries";
    public static final String WS_INQUIRY_FILTER = BASE_URL+"inquiry_filter";
    public static final String WS_INQUIRY_SEARCH=BASE_URL+"search_inquiries";
    public static final String WS_UPCOMING_SEARCH=BASE_URL+"search_upcoming_inquiries";
    public static final String WS_BRANCH_LIST=BASE_URL+"branches";
    public static final String WS_STUDENT_LIST=BASE_URL+"students";
    public static final String WS_POST_INQUIRIES=BASE_URL+"postinquiries";
    public static final String WS_DELETE_INQUIRIES=BASE_URL+"deleteinquiries";
    public static final String WS_DELETE_STUDENT=BASE_URL+"delete_student";
    public static final String WS_STUDENT_DETAILS=BASE_URL+"student_detail";
    public static final String WS_INQUIRY_DETAILS=BASE_URL+"inquiry_detail";
    public static final String WS_DELETE_COURSE=BASE_URL+"delete_course";
    public static final String WS_ADD_COURSE=BASE_URL+"add_course";
    public static final String WS_POST_ATTENDENCE=BASE_URL+"post_attendence";
    public static final String WS_COURSE_DURATIONS=BASE_URL+"durations";
    public static final String WS_COURSE_CATEGORIES=BASE_URL+"categories";
    public static final String WS_ATTENDANCE_LIST=BASE_URL+"all_attendence_list";
    public static final String WS_STUDENT_FILTER=BASE_URL+"student_filter";
    public static final String WS_OUT_ENTRY=BASE_URL+"out_student";
    public static final String WS_TEST_LIST=BASE_URL+"tests";
    public static final String WS_FEES_LIST=BASE_URL+"student_payments";
    public static final String WS_FEES_LIST_ALL=BASE_URL+"filter_payment";
    public static final String WS_POST_PAYMENT=BASE_URL+"post_payment";
    public static final String WS_PARTNERS_LIST = BASE_URL+"partners";
    public static final String WS_INQUIRY_TODAY_FILTER = BASE_URL+"upcoming_inquiry_filter";
    public static final String WS_SMS_CATEGORIES = BASE_URL+"smscategories";
    public static final String WS_SMS_CATEGORIES_WISE_MESSAGES = BASE_URL+"smsmessages";
    public static final String WS_FEEDBACK_LIST = BASE_URL+"feedback_list";
    public static final String WS_FEEDBACK_LIST_UPDATE = BASE_URL+"postfeedback";
    public static final String WS_UPDATE_UPCOMING_DATE = BASE_URL+"update_upcoming_date";

    public static String SUCCESS_CODE="success";
    public static final int CODE_COURSE_LIST=1;
    public static final int CODE_COURSE_DETAILS=2;
    public static final int CODE_LOGIN_EMPLOYEE=3;
    public static final int CODE_INQUIRIES=4;
    public static final int CODE_UPCOMING_INQUIRIES=104;
    public static final int CODE_BRANCHES=5;
    public static final int CODE_STUDENTS=6;
    public static final int CODE_POST_INQUIRIES=7;
    public static final int CODE_DELETE_INQUIRIES=8;
    public static final int CODE_DELETE_STUDENT=9;
    public static final int CODE_STUDENT_DETAILS=10;
    public static final int CODE_INQUIRY_DETAILS=11;
    public static final int CODE_INQUIRY_FILTER=201;
    public static final int CODE_DELETE_COURSE=12;
    public static final int CODE_ADD_NEW_COURSE=13;
    public static final int CODE_ADD_NEW_STUDENT=14;
    public static final int CODE_POST_ATTENDENCE=15;
    public static final int CODE_GET_COURSE_DURATIONS =16;
    public static final int CODE_GET_COURSE_CATEGORIES =17;
    public static final int CODE_GET_ATTENDANCE_LIST=18;
    public static final int CODE_SEARCH_STUDNT=19;
    public static final int CODE_MAKE_OUT_ENTRY=20;
    public static final int CODE_POST_PAYMENT=21;
    public static final int CODE_GET_ALL_PAYMENT_LIST =22;
    public static final int CODE_GET_STUDENT_PAYMENT_LIST =23;
    public static final int CODE_SEARCH_FILTER_STUDENT=24;
    public static final int CODE_TEST_LIST=25;
    public static final int CODE_STUDENT_STANDARD=26;
    public static final int CODE_STUDENT_STREAM = 27;
    public static final int CODE_STUDENT_COURSES=28;
    public static final int CODE_STUDENT_SUBJCETS=29;
    public static final int CODE_MESSAGE_DAYS=30;
    public static final int CODE_PARTNER_IDS=31;
    public static final int CODE_UPCOMING_TODAY_OR_TOMORROW_FILTER=105;
    public static final int CODE_SMS_CATEGORIES = 123;
    public static final int CODE_SMS_CATRGORIES_WISE_MSGS = 124;
    public static final int CODE_FEEDBACK_LIST = 190;
    public static final int CODE_FEEDBACK_LIST_UPATE=191;
    public static final int CODE_UPDATE_DATE=192;
    public static final int CODE_SEARCH_INQUIRY=198;
    public static final int CODE_COURSE_SELECTION_FOR_ATTENDANCE=101;
    public static final int CODE_STUDENT_SELECTION_FOR_ATTENDANCE=102;
    public static final int CODE_COURSE_SELECTION_FOR_ADD_STUDENT=103;
    public static final int CODE_UPCOMING_SEARCH=117;
    public static final int CODE_CHECK_USERNAME=118;

    public static final String KEY_INTENT_COURSE_ID="course_id";
    public static final String KEY_INTENT_COURSE_NAME="course_name";
    public static final String KEY_INTENT_COURSE_IMAGE="course_image";

    public static final String KEY_INTENT_STUDENT_ID="student_id";
    public static final String KEY_INTENT_STANDARD_ID="standard_id";
    public static final String KEY_INTENT_STUDENT_NAME="student_name";
    public static final String KEY_INTENT_STUDENT_COURSE="student_course";
    public static final String KEY_INTENT_STUDENT_UNPAID_AMOUNT="student_unpaid_amount";
    public static final String KEY_INTENT_STUDENT_IMAGE="student_image";
    public static final String KEY_INTENT_STUDENT_INOUT="student_inout";
    public static final String KEY_INTENT_INQUIRY_ID="inquiry_id";
    public static final String KEY_INQUIRY_DATE="inquiry_date";
    public static final String INTENT_KEY_COURSE_ID="course_id";
    public static final String INTENT_KEY_COURSE_NAME="course_name";
    public static final String KEY_COURSE_SELECTION="course_selection";
    public static final String KEY_VALUE_COURSE_SELECTION_YES="course_selection_yes";
    public static final String KEY_STUDENT_SELECTION="student_selection";
    public static final String KEY_COURSE_SELECTION_TAG="course_selection_tag";
    public static final String KEY_STUDENT_SELECTION_FOR_UNPAID_STUDENT_FEES="student_selection_for_fees";

    public static final int REQUEST_PHONE_CALL = 1999;

    public static int[] ANIMATION_ARRAY = {
            R.anim.layout_animation_up_to_down,
            R.anim.layout_animation_right_to_left,
            R.anim.layout_animation_down_to_up,
            R.anim.layout_animation_left_to_right};

    // Runtime permission code
    public static final int CODE_RUNTIME_LOCATION_PERMISSION = 21;
    public static final int CODE_RUNTIME_STORAGE_PERMISSION = 22;

    // Directory name
    public static String DIRECTORY_NAME = "GLOBAL_IT";
    public static String DIRECTORY_REGISTER_PROFILE = "GLOBAL_IT/GLOBAL_IT_Profile";
    public static String DIRECTORY_SENT_PHOTO = "GLOBAL_IT/GLOBAL_IT_Images/Sent";
    public static String DIRECTORY_RECEIVE_PHOTO = "GLOBAL_IT/GLOBAL_IT_Images";
    public static String DIRECTORY_SENT_VIDEO = "GLOBAL_IT/GLOBAL_IT_Videos/Sent";
    public static String DIRECTORY_RECEIVE_VIDEO = "GLOBAL_IT/GLOBAL_IT_Videos";
    public static String DIRECTORY_FILE_NOMEDIA = ".nomedia";
    public static String DIRECTORY_GIT_MEDIA = "GLOBAL_IT/GLOBAL_IT_Media";


    // Attachment type
    public static String ATTACHMENT_IMAGE = "IMAGE";
    public static String ATTACHMENT_VIDEO = "VIDEO";
    public static String ATTACHMENT_PDF = "PDF";
    public static String ATTACHMENT_IMAGE_VIDEO = "IMAGE_VIDEO_GALLERY";
    public static String KEY_VIDEO_URL="video_url";

    // Attachment body
    public static String IMAGE_ATTACHMENT_BODY = "Photo";
    public static String VIDEO_ATTACHMENT_BODY = "Video";
    public static String VIDEO_FORMATE = ".mp4";

}
