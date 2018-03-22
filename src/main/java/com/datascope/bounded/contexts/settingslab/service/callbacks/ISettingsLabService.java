package com.datascope.bounded.contexts.settingslab.service.callbacks;

import com.datascope.bounded.contexts.settingslab.domain.SettingsLab;

import java.util.function.Consumer;

public interface ISettingsLabService {
    void getSettingsLab(Consumer<SettingsLab> onSuccess);
}
