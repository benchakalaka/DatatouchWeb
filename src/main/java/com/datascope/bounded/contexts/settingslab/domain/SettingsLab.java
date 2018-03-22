package com.datascope.bounded.contexts.settingslab.domain;

import com.datascope.bounded.contexts.core.domain.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SettingsLab extends Model {

    @JsonProperty("StaticModuleIds")
    private StaticModuleIds staticModuleIds = new StaticModuleIds();


    @JsonProperty("Modules")
    private SettingsModule.List modules = new SettingsModule.List();

    public SettingsLab() {
    }
}
