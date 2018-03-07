package com.datascope.services.hotspot.requests;

import com.datascope.application.ui.utils.common.DateUtils;
import com.datascope.services.core.BaseRequestView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class GetAreaHotspotsRequest extends BaseRequestView {

    @JsonProperty("Date")
    private String date;

    @JsonProperty("SiteId")
    private int siteId;

    @JsonProperty("AreaId")
    private int areaId;

    public GetAreaHotspotsRequest(int siteId, int areaId, LocalDate date) {
        this.siteId = siteId;
        this.areaId = areaId;
        this.date = DateUtils.parseDate(date);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int getSiteId() {
        return siteId;
    }

    @Override
    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }
}
