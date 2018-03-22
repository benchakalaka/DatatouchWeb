package com.datascope.bounded.contexts.email.service.requests;

import com.datascope.bounded.contexts.core.services.SuperRequestView;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteEmailTemplateRequest extends SuperRequestView {


    @JsonProperty("TemplateId")
    private int templateId;

    public DeleteEmailTemplateRequest(int templateId) {

        this.templateId = templateId;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }
}
