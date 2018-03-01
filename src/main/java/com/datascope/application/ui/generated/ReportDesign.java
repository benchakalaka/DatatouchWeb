package com.datascope.application.ui.generated;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.HorizontalSplitPanel;

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
public class ReportDesign extends VerticalLayout {
    private DateField datePicker;
    private HorizontalSplitPanel horizontalSplit;
    private BrowserFrame browserFrame;

    public ReportDesign() {
        Design.read(this);
    }

    public DateField getDatePicker() {
        return datePicker;
    }

    public HorizontalSplitPanel getHorizontalSplit() {
        return horizontalSplit;
    }

    public BrowserFrame getBrowserFrame() {
        return browserFrame;
    }

}
