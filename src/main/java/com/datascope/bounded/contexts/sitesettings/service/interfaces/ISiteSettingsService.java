package com.datascope.bounded.contexts.sitesettings.service.interfaces;

import com.datascope.bounded.contexts.sitesettings.domain.UserSettings;

import java.util.function.Consumer;

public interface ISiteSettingsService {
    void getUserSettings(Consumer<UserSettings.List> onSuccess);
}
