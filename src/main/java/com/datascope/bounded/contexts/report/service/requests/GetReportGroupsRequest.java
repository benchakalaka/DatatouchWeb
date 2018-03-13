package com.datascope.bounded.contexts.report.service.requests;

import com.datascope.core.services.SuperRequestView;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetReportGroupsRequest extends SuperRequestView {
    @JsonProperty("ModuleId")
    private int moduleId;

    @JsonProperty("DateGeneratedAt")
    private String dateGeneratedAt;

    public GetReportGroupsRequest(int moduleId, String dateGeneratedAt) {
        this.moduleId = moduleId;
        this.dateGeneratedAt = dateGeneratedAt;

    }
}
