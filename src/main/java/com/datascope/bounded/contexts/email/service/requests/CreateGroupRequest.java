package com.datascope.bounded.contexts.email.service.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateGroupRequest {

    @JsonProperty("GroupName")
    private String groupName;

    public CreateGroupRequest(String groupName) {
        this.groupName = groupName;
    }
}
