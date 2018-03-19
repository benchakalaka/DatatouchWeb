package com.datascope.bounded.contexts.email.service.requests;

import com.datascope.bounded.contexts.core.services.SuperRequestView;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EditEmailGroupName extends SuperRequestView {

    @JsonProperty("GroupName")
    private String newName;

    @JsonProperty("EmailGroupId")
    private int groupId;

    public EditEmailGroupName(String newName, int groupId) {

        this.newName = newName;
        this.groupId = groupId;
    }

    public String getNewName() {
        return newName;
    }

    public int getGroupId() {
        return groupId;
    }
}
