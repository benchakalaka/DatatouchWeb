package com.datascope.bounded.contexts.user.service.requests;

import com.datascope.bounded.contexts.core.services.SuperRequestView;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginUserRequest extends SuperRequestView {


    @JsonProperty("Pin")
    private String pin;
    @JsonProperty("UserId")
    private int userId;

    public LoginUserRequest(int userId, String pin, int siteId) {
        setSiteId(siteId);
        this.pin = pin;
        this.userId = userId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static LoginUserRequest create(int userId, String pin, int siteId) {
        return new LoginUserRequest(userId, pin, siteId);
    }
}
