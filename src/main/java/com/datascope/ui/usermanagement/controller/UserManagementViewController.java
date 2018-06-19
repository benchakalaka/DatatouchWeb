package com.datascope.ui.usermanagement.controller;

import com.datascope.bounded.contexts.sitesettings.domain.UserSettings;
import com.datascope.ui.usermanagement.elements.UserSettingsGridItem;
import com.datascope.ui.utils.helper.Labels;
import com.datascope.ui.utils.helper.UiHelper;
import com.vaadin.ui.Grid;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserManagementViewController extends UiHelper {

    private final String STYLE_CENTER_ALIGN = "v-align-center";

    public UserManagementViewController(Labels labels) {
        super(labels);
    }

    public UserSettingsGridItem.List toGridItems(UserSettings.List userSettingsList) {
        return userSettingsList.stream().map(UserSettingsGridItem::fromUserSettings).collect(Collectors.toCollection(UserSettingsGridItem.List::new));
    }

    public void initGrid(Grid<UserSettingsGridItem> grid) {
        grid.removeAllColumns();

        grid.addColumn(UserSettingsGridItem::getFullName)
                .setCaption(getLabel("usersettings.grid.usersettings.name"))
                .setExpandRatio(1);
    }
}
