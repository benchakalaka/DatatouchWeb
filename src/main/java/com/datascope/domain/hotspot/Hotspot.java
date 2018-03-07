package com.datascope.domain.hotspot;

import com.datascope.application.ui.hotspot.HotspotGridItem;
import com.datascope.domain.core.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Hotspot extends Model {

    @JsonProperty("Amount")
    private int amount;

    @JsonProperty("AreaId")
    private int areaId;

    @JsonProperty("CompanyId")
    private int companyId;

    @JsonProperty("Completed")
    private int completed;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("HotspotItems")
    private HotspotItem.List hotspotItems = new HotspotItem.List();

    @JsonProperty("HotspotTypeId")
    private int hotspotTypeId;

    @JsonProperty("Id")
    private int id;

    @JsonProperty("SiteId")
    private int siteId;

    @JsonProperty("DurationFrom")
    private String validFromDate;

    @JsonProperty("DurationTo")
    private String validToDate;

    @JsonProperty("DateOfCreation")
    private String date;

    @JsonProperty("X")
    private double x;

    @JsonProperty("LaydownAreaId")
    private int laydownAreaId;

    @JsonProperty("GateId")
    private int gateId;

    @JsonProperty("Y")
    private double y;

    @JsonProperty("Files")
    private ArrayList<String> fileUrls;

    @JsonProperty("Url")
    private String url;

    @JsonProperty("ProjectId")
    private int projectId;

    @JsonProperty("IsSignedOff")
    private boolean isSignedOff;

    @JsonProperty("HotspotStateId")
    private int hotspotStateId;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getCompleted() {
        return completed;
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

    public HotspotItem.List getHotspotItems() {
        return hotspotItems;
    }

    public void setHotspotItems(HotspotItem.List hotspotItems) {
        this.hotspotItems = hotspotItems;
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

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getLaydownAreaId() {
        return laydownAreaId;
    }

    public void setLaydownAreaId(int laydownAreaId) {
        this.laydownAreaId = laydownAreaId;
    }

    public int getGateId() {
        return gateId;
    }

    public void setGateId(int gateId) {
        this.gateId = gateId;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public ArrayList<String> getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(ArrayList<String> fileUrls) {
        this.fileUrls = fileUrls;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public boolean isSignedOff() {
        return isSignedOff;
    }

    public void setSignedOff(boolean signedOff) {
        isSignedOff = signedOff;
    }

    public int getHotspotStateId() {
        return hotspotStateId;
    }

    public void setHotspotStateId(int hotspotStateId) {
        this.hotspotStateId = hotspotStateId;
    }

    public static class List extends ArrayList<Hotspot> {
        public HotspotGridItem.List toGridItems() {
            // TODO: set HotspotGridItem accept hotspotModel
            return stream()
                    .map(hs -> new HotspotGridItem(
                            hs.amount,
                            "Datascope",
                            hs.completed,
                            hs.description,
                            hs.hotspotTypeId,
                            hs.id,
                            hs.validFromDate,
                            hs.validToDate,
                            hs.fileUrls,
                            hs.isSignedOff)).collect(Collectors.toCollection(HotspotGridItem.List::new));

        }
    }
}
