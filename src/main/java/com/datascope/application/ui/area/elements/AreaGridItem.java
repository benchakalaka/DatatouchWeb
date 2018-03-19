package com.datascope.application.ui.area.elements;




import com.datascope.bounded.contexts.area.domian.AreaFile;

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

    private boolean hasFiles() {
        return null != areaFiles && !areaFiles.isEmpty();
    }

    public String buildFileUrl(String fullUrl) {
        if (hasFiles())
            return fullUrl + areaFiles.get(0).getFileName();
        else
            return "";
    }

    public String getAreaName() {
        return areaName;
    }

    public String getCreatedAt() {
        return createdAt;
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


