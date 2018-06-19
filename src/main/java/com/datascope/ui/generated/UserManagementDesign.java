package com.datascope.ui.generated;

import com.datascope.ui.usermanagement.elements.UserSettingsGridItem;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.declarative.Design;

/**
 * !! DO NOT EDIT THIS FILE !!
 * <p>
 * This class is generated by Vaadin Designer and will be overwritten.
 * <p>
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class UserManagementDesign extends HorizontalLayout {

    private Grid<UserSettingsGridItem> userSettingsGrid;

    public UserManagementDesign() {
        Design.read(this);
    }

    public Grid<UserSettingsGridItem> getUserSettingsGrid() {
        return userSettingsGrid;
    }
}