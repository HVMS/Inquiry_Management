package com.globalitians.inquiry.activities.SmsAndNotification.Models;

public class InquiryPersonModel {

    private String strName="";
    private int image_id;
    private boolean isSelected=false;

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
