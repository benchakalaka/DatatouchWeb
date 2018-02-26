package com.datascope.components.ui.grid.items;

import com.datascope.domain.area.AreaFile;

import java.util.ArrayList;

public class AreaGridItem {

    private String areaName;
    private String createdAt;
    private int id;



    private AreaFile.List areaFiles;

    public AreaGridItem(String areaName, String createdAt, int id, AreaFile.List areaFiles) {
        this.areaName = areaName;
        this.createdAt = createdAt;
        this.id = id;
        this.areaFiles = areaFiles;
    }

    public AreaFile.List getAreaFiles() {
        return areaFiles;
    }

    public void setAreaFiles(AreaFile.List areaFiles) {
        this.areaFiles = areaFiles;
    }

    public boolean hasFiles(){
        return null != areaFiles && !areaFiles.isEmpty();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class List extends ArrayList<AreaGridItem> {
    }
}


