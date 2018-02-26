package com.datascope.services.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseRequestView {
    @JsonProperty("SiteId")
    private int siteId;


    public BaseRequestView() {
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }
}
