package com.datascope.bounded.contexts.user.service.requests;

import com.datascope.bounded.contexts.core.services.SuperRequestView;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateUserPinRequest extends SuperRequestView {

    @JsonProperty("Id")
    public int userId;

    @JsonProperty("PinCode")
    public String pin;

    public UpdateUserPinRequest(int userId, String pin, int siteId) {
        setSiteId(siteId);
        this.userId = userId;
        this.pin = pin;
    }
}
