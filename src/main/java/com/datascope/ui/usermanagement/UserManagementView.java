package com.datascope.ui.usermanagement;

import com.datascope.bounded.contexts.sitesettings.domain.UserSettings;
import com.datascope.bounded.contexts.sitesettings.service.interfaces.ISiteSettingsService;
import com.datascope.ui.generated.UserManagementDesign;
import com.datascope.ui.usermanagement.controller.UserManagementViewController;
import com.datascope.ui.usermanagement.elements.UserSettingsGridItem;
import com.datascope.ui.utils.notifications.Notifications;
import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.github.appreciated.app.layout.annotations.NavigatorViewName;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;

import javax.annotation.PostConstruct;

@MenuCaption("User Management")
@MenuIcon(VaadinIcons.COMPILE)
@NavigatorViewName(UserManagementView.NAME)
@SpringView(name = UserManagementView.NAME )
public class UserManagementView extends UserManagementDesign implements
        View {

    static final String NAME = "UserManagementView";
    private ISiteSettingsService service;
    private Notifications notifications;
    private UserManagementViewController controller;

    public UserManagementView(
            ISiteSettingsService service,
            Notifications notifications,
            UserManagementViewController controller) {
        this.service = service;
        this.notifications = notifications;
        this.controller = controller;
    }

    @PostConstruct
    public void init() {
        controller.initGrid(getUserSettingsGrid());
        service.getUserSettings(this::setupUserSettings);
    }

    private void setupUserSettings(UserSettings.List userSettingsList) {
        UserSettings.List active = userSettingsList.getActiveUsers();
        UserSettingsGridItem.List items = controller.toGridItems(active);
        getUserSettingsGrid().setItems(items);
    }
}
