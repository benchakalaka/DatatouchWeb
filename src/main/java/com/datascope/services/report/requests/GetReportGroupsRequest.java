package com.datascope.services.report.requests;

import com.datascope.services.core.BaseRequestView;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetReportGroupsRequest extends BaseRequestView {
    @JsonProperty("ModuleId")
    private int moduleId;

    @JsonProperty("DateGeneratedAt")
    private String dateGeneratedAt;

    public GetReportGroupsRequest(int moduleId, String dateGeneratedAt, int siteId) {
        this.moduleId = moduleId;
        this.dateGeneratedAt = dateGeneratedAt;
        setSiteId(siteId);
    }
}
