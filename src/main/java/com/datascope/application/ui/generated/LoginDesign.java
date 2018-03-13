package com.datascope.application.ui.generated;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;

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
public class LoginDesign extends VerticalLayout {
    private ComboBox<com.datascope.bounded.contexts.site.domain.Site> cbSites;
    private Grid<com.datascope.bounded.contexts.user.domain.User> usersGrid;
    private FormLayout fLogin;
    private PasswordField etPassword;
    private Button btnLogin;

    public LoginDesign() {
        Design.read(this);
    }

    public ComboBox<com.datascope.bounded.contexts.site.domain.Site> getCbSites() {
        return cbSites;
    }

    public Grid<com.datascope.bounded.contexts.user.domain.User> getUsersGrid() {
        return usersGrid;
    }

    public FormLayout getFLogin() {
        return fLogin;
    }

    public PasswordField getEtPassword() {
        return etPassword;
    }

    public Button getBtnLogin() {
        return btnLogin;
    }

}
