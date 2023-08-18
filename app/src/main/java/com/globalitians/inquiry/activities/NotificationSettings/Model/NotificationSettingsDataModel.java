package com.globalitians.inquiry.activities.NotificationSettings.Model;

public class NotificationSettingsDataModel {

    private String NotificationDuraiton = "";
    private boolean isSelected = false;

    public String getNotificationDuraiton() {
        return NotificationDuraiton;
    }

    public void setNotificationDuraiton(String notificationDuraiton) {
        NotificationDuraiton = notificationDuraiton;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
