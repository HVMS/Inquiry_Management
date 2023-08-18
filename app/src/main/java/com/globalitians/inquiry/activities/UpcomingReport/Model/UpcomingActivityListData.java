package com.globalitians.inquiry.activities.UpcomingReport.Model;

public class UpcomingActivityListData {

    private int PersonImageId;
    private String PersonName;
    private String Course;

    public UpcomingActivityListData(int personImageId, String personName, String course) {
        this.PersonImageId = personImageId;
        this.PersonName = personName;
        this.Course = course;
    }

    public int getPersonImageId() {
        return PersonImageId;
    }

    public void setPersonImageId(int personImageId) {
        this.PersonImageId = personImageId;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        this.PersonName = personName;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        this.Course = course;
    }
}
