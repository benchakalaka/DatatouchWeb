package com.datascope.bounded.contexts.user.service.requests;

import com.datascope.bounded.contexts.core.services.SuperRequestView;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnassignPinRequest extends SuperRequestView {

    @JsonProperty("UserId")
    public int userId;

    public UnassignPinRequest(int userId, int siteId) {
        setSiteId(siteId);
        this.userId = userId;
    }
}
