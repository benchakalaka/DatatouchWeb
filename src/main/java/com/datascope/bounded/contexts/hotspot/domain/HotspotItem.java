package com.datascope.bounded.contexts.hotspot.domain;

import com.datascope.core.domain.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class HotspotItem extends Model {
    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("HotspotId")
    private int hotspotId;

    @JsonProperty("Id")
    private int itemId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHotspotId() {
        return hotspotId;
    }

    public void setHotspotId(int hotspotId) {
        this.hotspotId = hotspotId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public static class List extends ArrayList<HotspotItem> {}
}
