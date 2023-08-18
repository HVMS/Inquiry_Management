package com.globalitians.inquiry.activities.Inquirydetails.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class InquiryDetailsModel{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("inquiry_detail")
    @Expose
    private InquiryDetail inquiryDetail;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public InquiryDetail getInquiryDetail() {
        return inquiryDetail;
    }

    public void setInquiryDetail(InquiryDetail inquiryDetail) {
        this.inquiryDetail = inquiryDetail;
    }

    public class InquiryDetail {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("fname")
        @Expose
        private String fname;
        @SerializedName("lname")
        @Expose
        private String lname;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("mobileno")
        @Expose
        private String mobileno;
        @SerializedName("feedback")
        @Expose
        private String feedback;
        @SerializedName("reference")
        @Expose
        private String reference;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("stream_id")
        @Expose
        private Integer streamId;
        @SerializedName("stream_name")
        @Expose
        private String streamName;
        @SerializedName("standard_id")
        @Expose
        private Integer standardId;
        @SerializedName("standard_name")
        @Expose
        private String standardName;
        @SerializedName("subject_id")
        @Expose
        private Integer subjectId;
        @SerializedName("subject_name")
        @Expose
        private String subjectName;
        @SerializedName("message_notification")
        @Expose
        private Object messageNotification;
        @SerializedName("inquiy_date")
        @Expose
        private String inquiyDate;
        @SerializedName("installment_date")
        @Expose
        private String installmentDate;
        @SerializedName("upcoming_confirm_date")
        @Expose
        private String upcomingConfirmDate;
        @SerializedName("branch")
        @Expose
        private Branch branch;
        @SerializedName("courses")
        @Expose
        private ArrayList<Course> courses = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getMobileno() {
            return mobileno;
        }

        public void setMobileno(String mobileno) {
            this.mobileno = mobileno;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getStreamId() {
            return streamId;
        }

        public void setStreamId(Integer streamId) {
            this.streamId = streamId;
        }

        public String getStreamName() {
            return streamName;
        }

        public void setStreamName(String streamName) {
            this.streamName = streamName;
        }

        public Integer getStandardId() {
            return standardId;
        }

        public void setStandardId(Integer standardId) {
            this.standardId = standardId;
        }

        public String getStandardName() {
            return standardName;
        }

        public void setStandardName(String standardName) {
            this.standardName = standardName;
        }

        public Integer getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(Integer subjectId) {
            this.subjectId = subjectId;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public Object getMessageNotification() {
            return messageNotification;
        }

        public void setMessageNotification(Object messageNotification) {
            this.messageNotification = messageNotification;
        }

        public String getInquiyDate() {
            return inquiyDate;
        }

        public void setInquiyDate(String inquiyDate) {
            this.inquiyDate = inquiyDate;
        }

        public String getInstallmentDate() {
            return installmentDate;
        }

        public void setInstallmentDate(String installmentDate) {
            this.installmentDate = installmentDate;
        }

        public String getUpcomingConfirmDate() {
            return upcomingConfirmDate;
        }

        public void setUpcomingConfirmDate(String upcomingConfirmDate) {
            this.upcomingConfirmDate = upcomingConfirmDate;
        }

        public Branch getBranch() {
            return branch;
        }

        public void setBranch(Branch branch) {
            this.branch = branch;
        }

        public ArrayList<Course> getCourses() {
            return courses;
        }

        public void setCourses(ArrayList<Course> courses) {
            this.courses = courses;
        }

        public class Course {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("image")
            @Expose
            private String image;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

        }

        public class Branch {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("name")
            @Expose
            private String name;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

        }

    }

}

