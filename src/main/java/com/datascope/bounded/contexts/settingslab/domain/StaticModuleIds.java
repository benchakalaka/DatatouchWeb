package com.datascope.bounded.contexts.settingslab.domain;

import com.datascope.bounded.contexts.core.domain.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StaticModuleIds extends Model {

    @JsonProperty("DailyModuleId")
    private int dailyModuleId;

    @JsonProperty("AdvancedModuleId")
    private int advancedModuleId;

    @JsonProperty("SiteBriefingModuleId")
    private int siteBriefingModuleId;

    @JsonProperty("WhiteboardModuleId")
    private int whiteboardModuleId;


    @JsonProperty("ProjectModulesModuleId")
    private int projectModulesModuleId;


    @JsonProperty("AdHocMeetingModuleId")
    private int adHocMeetingModuleId;


    @JsonProperty("DailyProjectsModuleId")
    private int dailyProjectsModuleId;


    public StaticModuleIds() {
    }

    public int getDailyModuleId() {
        return dailyModuleId;
    }

    public void setDailyModuleId(int dailyModuleId) {
        this.dailyModuleId = dailyModuleId;
    }

    public int getAdvancedModuleId() {
        return advancedModuleId;
    }

    public void setAdvancedModuleId(int advancedModuleId) {
        this.advancedModuleId = advancedModuleId;
    }

    public int getSiteBriefingModuleId() {
        return siteBriefingModuleId;
    }

    public void setSiteBriefingModuleId(int siteBriefingModuleId) {
        this.siteBriefingModuleId = siteBriefingModuleId;
    }

    public int getWhiteboardModuleId() {
        return whiteboardModuleId;
    }

    public void setWhiteboardModuleId(int whiteboardModuleId) {
        this.whiteboardModuleId = whiteboardModuleId;
    }

    public int getProjectModulesModuleId() {
        return projectModulesModuleId;
    }

    public void setProjectModulesModuleId(int projectModulesModuleId) {
        this.projectModulesModuleId = projectModulesModuleId;
    }

    public int getAdHocMeetingModuleId() {
        return adHocMeetingModuleId;
    }

    public void setAdHocMeetingModuleId(int adHocMeetingModuleId) {
        this.adHocMeetingModuleId = adHocMeetingModuleId;
    }

    public int getDailyProjectsModuleId() {
        return dailyProjectsModuleId;
    }

    public void setDailyProjectsModuleId(int dailyProjectsModuleId) {
        this.dailyProjectsModuleId = dailyProjectsModuleId;
    }
}
