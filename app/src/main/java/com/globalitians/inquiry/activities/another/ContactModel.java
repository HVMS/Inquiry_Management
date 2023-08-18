package com.globalitians.inquiry.activities.another;

public class ContactModel {
    private String course_name="";
    private int course_image;

    public ContactModel(String course_name, int course_image) {
        this.course_name = course_name;
        this.course_image = course_image;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getCourse_image() {
        return course_image;
    }

    public void setCourse_image(int course_image) {
        this.course_image = course_image;
    }
}
