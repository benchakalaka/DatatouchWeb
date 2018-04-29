package com.datascope.ui.company.controller;

import com.datascope.bounded.contexts.company.domain.Company;
import com.datascope.ui.company.callbacks.CompanyColorChangedCallback;
import com.datascope.ui.company.callbacks.OnCompanySelectedCallback;
import com.datascope.ui.company.elements.CompanyGridItem;
import com.datascope.ui.utils.common.ColorUtils;
import com.datascope.ui.utils.factories.ColorPickerFactory;
import com.datascope.ui.utils.helper.Labels;
import com.datascope.ui.utils.helper.UiHelper;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CompanyViewController extends UiHelper {

    private final String STYLE_CENTER_ALIGN = "v-align-center";
    private final String STYLE_COLOR_PICKER_CAPTION = ".v-colorpicker .v-button-caption {display: none;}";
    private final String STYLE_COLOR_PICKER_MARGIN = ".v-colorpicker {margin-top: 5px; margin-bottom: 5px;}";

    @Value("${company.grid.company.color.picker.popup.caption}")
    private String colorPickerPopupCaption;

    public CompanyViewController(Labels labels) {
        super(labels);
    }

    public CompanyGridItem.List toGridItems(Company.List companies) {
        return companies.stream().map(CompanyGridItem::fromCompany).collect(Collectors.toCollection(CompanyGridItem.List::new));
    }

    public void initGrid(Grid<CompanyGridItem> grid, OnCompanySelectedCallback selectedCallback, CompanyColorChangedCallback colorChangedCallback) {
        grid.removeAllColumns();

        grid.addColumn(CompanyGridItem::getName)
                .setCaption(getLabel("company.grid.company.name"))
                .setExpandRatio(1);

        grid.addComponentColumn(item -> buildCompaniesGridColorPicker(item, colorChangedCallback))
            .setCaption(getLabel("company.grid.company.color"))
            .setStyleGenerator(item -> STYLE_CENTER_ALIGN);

        grid.addSelectionListener(item -> item.getFirstSelectedItem().ifPresent(selectedCallback::companySelected));
    }

    public void applyColorPickerStyle() {
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(STYLE_COLOR_PICKER_CAPTION);
    }

    private ColorPicker buildCompaniesGridColorPicker(CompanyGridItem companyItem, CompanyColorChangedCallback callback) {
        return ColorPickerFactory.buildColorPicker(colorPickerPopupCaption, companyItem.getColour(), item -> {
            Color color = item.getValue();
            companyItem.setColour(ColorUtils.getColorFromARGB(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue()));
            callback.companyColorChanged(companyItem, color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
        });
    }
}