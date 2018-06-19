package com.datascope.ui.usermanagement.elements;

import com.datascope.bounded.contexts.sitesettings.domain.UserSettings;

import java.util.ArrayList;

public class UserSettingsGridItem {

    private int id;
    private String fullName;

    public UserSettingsGridItem(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public static UserSettingsGridItem fromUserSettings(UserSettings userSettings) {
        return new UserSettingsGridItem(userSettings.getId(), userSettings.getFullName());
    }

    public  static class List extends ArrayList<UserSettingsGridItem> {
    }
}
