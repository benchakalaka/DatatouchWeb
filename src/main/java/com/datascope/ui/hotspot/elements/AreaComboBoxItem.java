package com.datascope.ui.hotspot.elements;

import com.datascope.bounded.contexts.area.domian.Area;

import java.util.ArrayList;

public class AreaComboBoxItem {

    private String areaName;
    private int areaId;

    public AreaComboBoxItem(String areaName, int areaId) {
        this.areaName = areaName;
        this.areaId = areaId;
    }



    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public static AreaComboBoxItem FromArea(Area area){
        return new AreaComboBoxItem(area.getAreaName(), area.getId());
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public static class List extends ArrayList<AreaComboBoxItem> {}

    @Override
    public String toString() {
        return String.valueOf(areaName);
    }
}
