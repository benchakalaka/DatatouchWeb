package com.datascope.bounded.contexts.email.domain;

import com.datascope.bounded.contexts.core.domain.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


public class EmailGroup extends Model {

    @JsonProperty("Id")
    private int Id;

    @JsonProperty("SiteId")
    private int siteId;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Emails")
    private Email.List Emails = new Email.List();
    
    public Email.List getEmails() {
        return Emails;
    }

    public void setEmails(Email.List emails) {
        Emails = emails;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class List extends ArrayList<EmailGroup>{}
}
