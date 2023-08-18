package com.globalitians.inquiry.activities.Dashboard.Model;

public class DashboardOptionsModel {
    private String strOptionTitle;
    private int optionImageId;
    private int backgroundImageId;

    public int getBackgroundImageId() {
        return backgroundImageId;
    }

    public void setBackgroundImageId(int backgroundImageId) {
        this.backgroundImageId = backgroundImageId;
    }

    public String getStrOptionTitle() {
        return strOptionTitle;
    }

    public void setStrOptionTitle(String strOptionTitle) {
        this.strOptionTitle = strOptionTitle;
    }

    public int getOptionImageId() {
        return optionImageId;
    }

    public void setOptionImageId(int optionImageId) {
        this.optionImageId = optionImageId;
    }
}
