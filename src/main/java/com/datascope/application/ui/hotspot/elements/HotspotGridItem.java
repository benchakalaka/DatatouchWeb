package com.datascope.application.ui.hotspot.elements;

import java.util.ArrayList;

public class HotspotGridItem {

    public static final String DESCRIPTION_COLUMN = "Description";
    public static final String PROGRESS_COLUMN = "Progress";
    public static final String COMPANY_NAME_COLUMN = "Company";
    public static final String FROM_COLUMN_NAME = "From";
    public static final String TO_COLUMN_NAME = "To";
    public static final String ID_COLUMN = "Id";

    private int amount;
    private String companyName;
    private int completed;
    private String description;
    private int hotspotTypeId;
    private int id;
    private String validFromDate;
    private String validToDate;
    private ArrayList<String> fileUrls;
    private boolean isSignedOff;

    public HotspotGridItem(int amount, String companyName, int completed, String description, int hotspotTypeId, int id, String validFromDate, String validToDate, ArrayList<String> fileUrls, boolean isSignedOff) {
        this.amount = amount;
        this.companyName = companyName;
        this.completed = completed;
        this.description = description;
        this.hotspotTypeId = hotspotTypeId;
        this.id = id;
        this.validFromDate = validFromDate;
        this.validToDate = validToDate;
        this.fileUrls = fileUrls;
        this.isSignedOff = isSignedOff;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getCompleted() {
        return (double)completed / 100;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHotspotTypeId() {
        return hotspotTypeId;
    }

    public void setHotspotTypeId(int hotspotTypeId) {
        this.hotspotTypeId = hotspotTypeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(String validFromDate) {
        this.validFromDate = validFromDate;
    }

    public String getValidToDate() {
        return validToDate;
    }

    public void setValidToDate(String validToDate) {
        this.validToDate = validToDate;
    }

    public ArrayList<String> getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(ArrayList<String> fileUrls) {
        this.fileUrls = fileUrls;
    }

    public boolean isSignedOff() {
        return isSignedOff;
    }

    public void setSignedOff(boolean signedOff) {
        isSignedOff = signedOff;
    }

    public static class List extends ArrayList<HotspotGridItem>{
        public static HotspotGridItem.List empty() {
            return new HotspotGridItem.List();
        }
    }
}
