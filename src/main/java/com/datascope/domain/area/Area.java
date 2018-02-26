package com.datascope.domain.area;

import com.datascope.domain.core.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Area extends Model{

    @JsonProperty("Id")
    private int id;

    @JsonProperty("IsActive")
    private boolean isActive;

    @JsonProperty("AreaName")
    private String areaName;

    @JsonProperty("CreatedAt")
    private String createdAt;

    @JsonProperty("AreaFiles")
    private AreaFile.List areaFiles;

    public int getId() {
        return this.id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean IsActive) {
        this.isActive = IsActive;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String AreaName) {
        this.areaName = AreaName;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String CreatedAt) {
        this.createdAt = CreatedAt;
    }

    public AreaFile.List getAreaFiles() {
        return this.areaFiles;
    }

    public void setAreaFiles(AreaFile.List areaFiles) {
        this.areaFiles = areaFiles;
    }

    public double getProgress() {
        return 0.5;
    }

    public static double[] getWeightHistory() {
        return new double[] {123,100,10,12,41,51,190,311};
    }

    public static class List extends ArrayList<Area> {

    }
}