package com.datascope.ui.login.elements;

import com.datascope.bounded.contexts.sitesettings.domain.UserSettings;

import java.util.ArrayList;

public class UserSettingsLoginGridItem {

    private int id;
    private String fullName;

    public UserSettingsLoginGridItem(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public static UserSettingsLoginGridItem fromUserSettings(UserSettings userSettings) {
        return new UserSettingsLoginGridItem(userSettings.getId(), userSettings.getFullName());
    }

    public  static class List extends ArrayList<UserSettingsLoginGridItem> {
    }
}
