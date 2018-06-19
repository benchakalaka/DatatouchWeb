package com.datascope.bounded.contexts.sitesettings.domain;

import com.datascope.bounded.contexts.core.domain.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SiteSettings extends Model {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("UserSettings")
    private UserSettings.List userSettings = new UserSettings.List();


    public SiteSettings() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserSettings.List getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings.List userSettings) {
        this.userSettings = userSettings;
    }
}
