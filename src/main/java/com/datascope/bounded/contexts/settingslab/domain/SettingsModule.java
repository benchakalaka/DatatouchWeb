package com.datascope.bounded.contexts.settingslab.domain;

import com.datascope.bounded.contexts.core.domain.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


public class SettingsModule extends Model {

    @JsonProperty("Id")
    private int moduleId;

    @JsonProperty("Colour")
    private String color;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;

    public SettingsModule() {
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

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

    public static class List extends ArrayList<SettingsModule> {}
}
