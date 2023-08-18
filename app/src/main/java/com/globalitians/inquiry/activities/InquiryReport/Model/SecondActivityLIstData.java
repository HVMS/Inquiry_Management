package com.globalitians.inquiry.activities.InquiryReport.Model;

public class SecondActivityLIstData {

    private int PersonImageId;
    private String PersonName;
    private String CourseName="";

    public SecondActivityLIstData(String personName, String courseName, int personImageId) {
        this.PersonName = personName;
        this.CourseName = courseName;
        this.PersonName = personName;
    }

    public int getPersonImageId() {
        return PersonImageId;
    }

    public void setPersonImageId(int personImageId) {
        PersonImageId = personImageId;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }
}
