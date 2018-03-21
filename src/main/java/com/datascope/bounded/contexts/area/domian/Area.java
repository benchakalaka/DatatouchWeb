package com.datascope.bounded.contexts.area.domian;

import com.datascope.bounded.contexts.core.domain.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Area extends Model {

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




    public static class List extends ArrayList<Area> {


    }
}