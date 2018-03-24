package com.datascope.ui.report.elements;

import com.datascope.bounded.contexts.settingslab.domain.SettingsModule;

import java.util.ArrayList;

public class ModuleComboBoxItem {
    public String name;
    public int id;

    public ModuleComboBoxItem(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s",name);
    }

    public static ModuleComboBoxItem from(SettingsModule module) {
        return new ModuleComboBoxItem(module.getName(), module.getModuleId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class List extends ArrayList<ModuleComboBoxItem> {
    }
}
