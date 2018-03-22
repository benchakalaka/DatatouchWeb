package com.datascope.bounded.contexts.email.service.requests;

import com.datascope.bounded.contexts.core.services.SuperRequestView;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EditEmailTemplateRequest extends SuperRequestView {

    @JsonProperty("TemplateId")
    private int emailId;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("FirstName")
    private String name;

    @JsonProperty("LastName")
    private String lastName;


    public EditEmailTemplateRequest(int emailId, String email, String name, String lastName) {
        this.emailId = emailId;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
    }

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
