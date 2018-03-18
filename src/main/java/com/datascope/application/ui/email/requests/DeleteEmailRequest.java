package com.datascope.application.ui.email.requests;

import com.datascope.bounded.contexts.core.services.SuperRequestView;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteEmailRequest extends SuperRequestView {
    @JsonProperty("GroupId")
    private int groupId;

    @JsonProperty("EmailId")
    private int emailId;

    public DeleteEmailRequest(int groupId, int emailId) {
        this.groupId = groupId;
        this.emailId = emailId;
    }

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
