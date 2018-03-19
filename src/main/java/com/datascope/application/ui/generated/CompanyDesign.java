package com.datascope.application.ui.generated;

import com.datascope.application.ui.company.elements.CompanyGridItem;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.ColorPickerArea;

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
public class CompanyDesign extends HorizontalLayout {
    private Grid<CompanyGridItem> companiesGrid;
    private ColorPickerArea colorPickerArea;

    public CompanyDesign() {
        Design.read(this);
    }

    public Grid<CompanyGridItem> getCompaniesGrid() {
        return companiesGrid;
    }

    public ColorPickerArea getColorPickerArea() {
        return colorPickerArea;
    }

}