package com.datascope.bounded.contexts.hotspot.service.requests;

import com.datascope.bounded.contexts.core.services.SuperRequestView;
import com.datascope.ui.utils.common.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class GetAreaHotspotsRequest extends SuperRequestView {

    @JsonProperty("Date")
    private String date;

    @JsonProperty("AreaId")
    private int areaId;

    public GetAreaHotspotsRequest(  int areaId, LocalDate date) {

        this.areaId = areaId;
        this.date = DateUtils.parseDate(date);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }
}
