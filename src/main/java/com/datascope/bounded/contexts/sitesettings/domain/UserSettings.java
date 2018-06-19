package com.datascope.bounded.contexts.sitesettings.domain;

import com.datascope.bounded.contexts.core.domain.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserSettings extends Model {

    @JsonProperty("Id")
    private int id;

    @JsonProperty("PinCode")
    private String pinCode;

    @JsonProperty("FullName")
    private String fullName;

    @JsonProperty("IsActive")
    private boolean isActive;

    @JsonProperty("IsAdmin")
    private boolean isAdmin;

    @JsonProperty("CompanyId")
    private int companyId;

    @JsonProperty("SiteId")
    private int siteId;

    @JsonProperty("Token")
    private String token;

    public UserSettings() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSettings)) return false;

        UserSettings settings = (UserSettings) o;

        return id == settings.id;
    }

    public static class List extends ArrayList<UserSettings> {
        public static List empty() {
            return new List();
        }

        public void changeUserPin(int userId, String newPin) {
            this.stream()
                    .filter(userSettings -> userSettings.getId()==userId)
                    .findFirst()
                    .ifPresent(userSettings -> userSettings.setPinCode(newPin));
        }

        public UserSettings.List getActiveUsers() {
            return stream()
                    .filter(UserSettings::isActive)
                    .collect(Collectors.toCollection(UserSettings.List::new));
        }
    }
}
