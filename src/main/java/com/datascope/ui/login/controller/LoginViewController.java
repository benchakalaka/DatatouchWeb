package com.datascope.ui.login.controller;

import com.datascope.bounded.contexts.site.domain.Site;
import com.datascope.bounded.contexts.sitesettings.domain.UserSettings;
import com.datascope.ui.login.elements.UserSettingsLoginGridItem;
import com.datascope.ui.utils.helper.Labels;
import com.datascope.ui.utils.helper.UiHelper;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class LoginViewController extends UiHelper {

    public LoginViewController(Labels labels) {
        super(labels);
    }

    private UserSettingsLoginGridItem.List toGridItems(UserSettings.List userSettingsList) {
        return userSettingsList.stream()
                .map(UserSettingsLoginGridItem::fromUserSettings)
                .collect(Collectors.toCollection(UserSettingsLoginGridItem.List::new));
    }

    public void initGrid(Grid<UserSettingsLoginGridItem> grid) {
        grid.removeAllColumns();

        grid.addColumn(UserSettingsLoginGridItem::getFullName)
                .setCaption(getLabel("login.grid.usersettings.name"))
                .setExpandRatio(1);
    }

    public void initSitesCombobox(ComboBox<Site> comboBox, Consumer<Site> onSiteSelectedCallback) {
        comboBox.addValueChangeListener(siteItem -> onSiteSelectedCallback.accept(siteItem.getValue()));
    }

    public void setupGridItems(Grid<UserSettingsLoginGridItem> grid, UserSettings.List userSettingsList) {
        UserSettings.List active = userSettingsList.getActiveUsers();
        UserSettingsLoginGridItem.List items = toGridItems(active);
        grid.setItems(items);
        if (CollectionUtils.isNotEmpty(items)) {
            grid.select(items.get(0));
        }
        grid.getDataProvider().refreshAll();
    }
}
