package com.datascope.application.ui.company.requests;

import com.datascope.bounded.contexts.core.services.SuperRequestView;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangeCompanyColorRequest extends SuperRequestView {

    @JsonProperty("CompanyId")
    private int companyId;

    @JsonProperty("Colour")
    private String colour;

    public ChangeCompanyColorRequest(int companyId, int alpha, int r, int g, int b) {
        this.companyId = companyId;

        String alphaHex = To00Hex(alpha);
        String blueHex = To00Hex(b);
        String greenHex = To00Hex(g);
        String redHex = To00Hex(r);


        this.colour = "#" + alphaHex +
                blueHex +
                greenHex +
                redHex;
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

    private static String To00Hex(int value) {
        String hex = "00".concat(Integer.toHexString(value));
        return hex.substring(hex.length()-2, hex.length());
    }
}
