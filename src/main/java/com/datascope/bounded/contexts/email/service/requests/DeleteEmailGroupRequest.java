package com.datascope.bounded.contexts.email.service.requests;

import com.datascope.bounded.contexts.core.services.SuperRequestView;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteEmailGroupRequest extends SuperRequestView {
    @JsonProperty("GroupId")
    private int groupId;


    public DeleteEmailGroupRequest(int groupId) {
        this.groupId = groupId;

    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
