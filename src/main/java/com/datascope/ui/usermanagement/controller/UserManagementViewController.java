package com.datascope.ui.usermanagement.controller;

import com.datascope.bounded.contexts.sitesettings.domain.UserSettings;
import com.datascope.ui.usermanagement.dialog.ChangeUserPinDialog;
import com.datascope.ui.usermanagement.elements.UserSettingsGridItem;
import com.datascope.ui.utils.factories.ButtonFactory;
import com.datascope.ui.utils.helper.Labels;
import com.datascope.ui.utils.helper.UiHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


import static com.vaadin.icons.VaadinIcons.GRID_SMALL_O;
import static com.vaadin.ui.themes.ValoTheme.BUTTON_SMALL;

@Component
public class UserManagementViewController extends UiHelper {

    private final String STYLE_CENTER_ALIGN = "v-align-center";

    private ChangeUserPinDialog changeUserPinDialog;

    public UserManagementViewController(Labels labels, ChangeUserPinDialog changeUserPinDialog) {
        super(labels);
        this.changeUserPinDialog = changeUserPinDialog;
    }

    public UserSettingsGridItem.List toGridItems(UserSettings.List userSettingsList) {
        return userSettingsList.stream().map(UserSettingsGridItem::fromUserSettings).collect(Collectors.toCollection(UserSettingsGridItem.List::new));
    }

    public void initGrid(Grid<UserSettingsGridItem> grid) {
        grid.removeAllColumns();

        grid.addColumn(UserSettingsGridItem::getFullName)
                .setCaption(getLabel("usersettings.grid.usersettings.name"))
                .setExpandRatio(1);

        grid.addComponentColumn(item -> buildChangePinButton(item, grid))
            .setCaption(getLabel("usersettings.grid.usersettings.changepin"))
            .setStyleGenerator(item -> STYLE_CENTER_ALIGN);
    }

    private Button buildChangePinButton(UserSettingsGridItem item, Grid<UserSettingsGridItem> grid) {
        return ButtonFactory.buildButton(GRID_SMALL_O, BUTTON_SMALL, e -> {
            grid.select(item);
            onChangePinButtonClick(item);
        });
    }

    private void onChangePinButtonClick(UserSettingsGridItem item) {
        UI.getCurrent().addWindow(changeUserPinDialog);
    }
}
