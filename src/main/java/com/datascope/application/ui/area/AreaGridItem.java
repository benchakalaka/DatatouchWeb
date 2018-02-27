package com.datascope.application.ui.area;


import com.datascope.application.ui.utils.html.HtmlFormatter;
import com.datascope.domain.area.AreaFile;

import java.util.ArrayList;


public class AreaGridItem {


    private String areaName;
    private String createdAt;
    private int id;

    private AreaFile.List areaFiles;

    static final String AREA_NAME_COLUMN = "Area Name";
    static final String CREATED_COLUMN = "Created";
    static final String DRAWING = "PDF";

    public AreaGridItem(String areaName, String createdAt, int id, AreaFile.List areaFiles) {
        this.areaName = areaName;
        this.createdAt = createdAt;
        this.id = id;
        this.areaFiles = areaFiles;
    }

    private boolean hasFiles() {
        return null != areaFiles && !areaFiles.isEmpty();
    }

    public String buildUrl(String fUrl) {
        if (hasFiles())
            return HtmlFormatter.Link(fUrl + areaFiles.get(0).getFileName());
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


