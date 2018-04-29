package com.datascope.bounded.contexts.company.service.requests;

import com.datascope.bounded.contexts.core.services.SuperRequestView;
import com.datascope.ui.utils.common.ColorUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangeCompanyColorRequest extends SuperRequestView {

    @JsonProperty("CompanyId")
    private int companyId;

    @JsonProperty("Colour")
    private String colour;

    public ChangeCompanyColorRequest(int companyId, int alpha, int r, int g, int b) {
        this.companyId = companyId;
        this.colour = ColorUtils.getHexColorFromARGB(alpha, r, g, b);
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
