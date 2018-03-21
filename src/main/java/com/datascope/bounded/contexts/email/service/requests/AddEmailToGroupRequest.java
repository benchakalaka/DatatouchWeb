package com.datascope.bounded.contexts.email.service.requests;

import com.datascope.bounded.contexts.core.services.SuperRequestView;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddEmailToGroupRequest extends SuperRequestView {
    @JsonProperty("GroupId")
    private int groupId;

    @JsonProperty("EmailTemplateId")
    private int emailId;

    public AddEmailToGroupRequest(int groupId, int emailId) {
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
