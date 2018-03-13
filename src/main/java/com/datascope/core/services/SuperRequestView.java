package com.datascope.core.services;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuperRequestView {
    @JsonProperty("SiteId")
    private int siteId;


    public SuperRequestView() {
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }
}
