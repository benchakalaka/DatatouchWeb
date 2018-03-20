package com.datascope.ui.generated;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Button;

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
public class EmailGroupDeisgn extends VerticalLayout {
    private HorizontalSplitPanel horizontalSplitPanel;
    private Grid<com.datascope.ui.email.elements.EmailGroupGridItem> emailGroupsGrid;
    private Grid<com.datascope.ui.email.elements.EmailGridItem> emailsGrid;
    private Button btnAddEmailToGroup;

    public EmailGroupDeisgn() {
        Design.read(this);
    }

    public HorizontalSplitPanel getHorizontalSplitPanel() {
        return horizontalSplitPanel;
    }

    public Grid<com.datascope.ui.email.elements.EmailGroupGridItem> getEmailGroupsGrid() {
        return emailGroupsGrid;
    }

    public Grid<com.datascope.ui.email.elements.EmailGridItem> getEmailsGrid() {
        return emailsGrid;
    }

    public Button getBtnAddEmailToGroup() {
        return btnAddEmailToGroup;
    }

}